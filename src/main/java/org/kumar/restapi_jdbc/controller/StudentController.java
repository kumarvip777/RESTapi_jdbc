package org.kumar.restapi_jdbc.controller;

import org.kumar.restapi_jdbc.entity.Student;
import org.kumar.restapi_jdbc.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {


    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }


    @PostMapping
    public String saveStudent(@RequestBody Student student) {
        service.save(student);
        return "Student saved successfully";
    }


    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAll();
    }


    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return service.getById(id);
    }


    @PutMapping
    public String updateStudent(@RequestBody Student student) {
        service.update(student);
        return "Student updated successfully";
    }


    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        service.delete(id);
        return "Student deleted successfully";
    }


}