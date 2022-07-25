package com.example.homeworksspringboot.controller;

import com.example.homeworksspringboot.model.Student;
import com.example.homeworksspringboot.repository.StudentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/students")
@Api(value = "language")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "111")
    private Student save(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private Student findById(@PathVariable Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET)
    private List<Student> findByAll() {
        return studentRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    private String deleteStudent(@PathVariable Long id) {
        boolean exists = studentRepository.existsById(id);
        if (exists) {
            studentRepository.deleteById(id);
            return "Successfully deleted student by id {"+ id +"}";
        }
        return "Student by id {"+ id +"} Not found";
    }
}
