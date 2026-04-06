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


    public Student saveStudent(Student student) {
        return repository.save(student);
    }


    public List<Student> getAllStudents() {
        return repository.findAll();
    }


    public Student getStudentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public Student updateStudent(Long id, Student student) {
        Student existing = getStudentById(id);

        if (student.getName() != null)
            existing.setName(student.getName());

        if (student.getEmail() != null)
            existing.setEmail(student.getEmail());

        if (student.getAge() != 0)
            existing.setAge(student.getAge());

        if (student.getCourse() != null)
            existing.setCourse(student.getCourse());

        if (student.getDepartment() != null)
            existing.setDepartment(student.getDepartment());

        if (student.getPhoneNumber() != null)
            existing.setPhoneNumber(student.getPhoneNumber());

        if (student.getAddress() != null)
            existing.setAddress(student.getAddress());

        return repository.save(existing);
    }


    public void deleteStudent(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        repository.deleteById(id);
    }
}