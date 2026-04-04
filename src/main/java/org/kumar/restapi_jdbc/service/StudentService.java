package org.kumar.restapi_jdbc.service;


import org.kumar.restapi_jdbc.entity.Student;
import org.kumar.restapi_jdbc.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public void save(Student student) {
        repository.save(student);
    }

    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student getById(Long id) {
        return repository.findById(id);
    }

    public void update(Student student) {
        repository.update(student);
    }

    public void delete(Long id) {
        repository.delete(id);
    }


}
