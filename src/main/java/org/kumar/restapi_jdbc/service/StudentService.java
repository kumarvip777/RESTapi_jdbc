package org.kumar.restapi_jdbc.service;

import org.kumar.restapi_jdbc.entity.Address;
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

        if (student.getAddress() != null) {
            student.getAddress().setStudent(student);
        }
        return repository.save(student);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public void deleteStudent(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public Student updateStudent(Long id, Student updatedStudent) {


        Student existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        if (updatedStudent.getName() != null)
            existing.setName(updatedStudent.getName());

        if (updatedStudent.getEmail() != null)
            existing.setEmail(updatedStudent.getEmail());

        if (updatedStudent.getAge() != 0)
            existing.setAge(updatedStudent.getAge());

        if (updatedStudent.getDepartment() != null)
            existing.setDepartment(updatedStudent.getDepartment());

        if (updatedStudent.getPhoneNumber() != null)
            existing.setPhoneNumber(updatedStudent.getPhoneNumber());


        if (updatedStudent.getAddress() != null) {

            if (existing.getAddress() != null) {

                existing.getAddress().setLocation(
                        updatedStudent.getAddress().getLocation()
                );
            } else {

                Address newAddress = updatedStudent.getAddress();
                newAddress.setStudent(existing);
                existing.setAddress(newAddress);
            }
        }


        return repository.save(existing);
    }
}