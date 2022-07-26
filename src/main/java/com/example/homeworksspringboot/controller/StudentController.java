package com.example.homeworksspringboot.controller;

import com.example.homeworksspringboot.model.Student;
import com.example.homeworksspringboot.repository.StudentRepository;
import com.example.homeworksspringboot.rest.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @PostMapping
    private Student saveStudent(@RequestBody StudentDto dto) {
        return studentRepository.save(
                new Student(
                        dto.getFullName(),
                        dto.getEmail())
        );
    }

    @GetMapping("/{id}")
    private Student findById(@PathVariable Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.orElse(null);
    }

    @GetMapping
    private List<Student> findByAll() {
        return studentRepository.findAll();
    }

    @PutMapping("/{id}")
    private String editStudent(@PathVariable(value = "id") Long studentId,@RequestBody Student student) {
        boolean exists = studentRepository.existsById(studentId);
        if (exists) {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            optionalStudent.get().setFullName(student.getFullName());
            optionalStudent.get().setEmail(student.getEmail());
            Student student1 = studentRepository.save(optionalStudent.get());
            return "Successfully edited";
        }
        return "Bunday student mavjud emas";
    }

    @DeleteMapping("/{id}")
    private String deleteStudent(@PathVariable Long id) {
        boolean exists = studentRepository.existsById(id);
        if (exists) {
            studentRepository.deleteById(id);
            return "Successfully deleted student by id {"+ id +"}";
        }
        return "Student by id {"+ id +"} Not found";
    }
}
