package org.kumar.restapi_jdbc.service;

import org.kumar.restapi_jdbc.entity.Department;
import org.kumar.restapi_jdbc.entity.Employee;
import org.kumar.restapi_jdbc.exception.ResourceNotFoundException;
import org.kumar.restapi_jdbc.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }


    public Department saveDepartment(Department department) {

        if (department.getEmployees() != null) {
            department.getEmployees().forEach(emp -> emp.setDepartment(department));
        }

        return repository.save(department);
    }


    public List<Department> getAllDepartments() {
        return repository.findAll();
    }


    public Department getDepartmentById(Long id) {
        return findDepartmentOrThrow(id);
    }

    public Department updateDepartment(Long id, Department updatedDept) {

        Department existing = findDepartmentOrThrow(id);

        if (updatedDept.getName() != null)
            existing.setName(updatedDept.getName());

        if (updatedDept.getCode() != null)
            existing.setCode(updatedDept.getCode());

        if (updatedDept.getLocation() != null)
            existing.setLocation(updatedDept.getLocation());

        return repository.save(existing);
    }

    public void deleteDepartment(Long id) {
        Department dept = findDepartmentOrThrow(id);
        repository.delete(dept);
    }

    public List<Employee> getEmployeesByDepartment(Long id) {
        Department dept = findDepartmentOrThrow(id);
        return dept.getEmployees();
    }

    public Department addEmployeeToDepartment(Long id, Department requestDept) {

        Department existingDept = findDepartmentOrThrow(id);

        if (requestDept.getEmployees() != null) {

            requestDept.getEmployees().forEach(emp -> {
                emp.setDepartment(existingDept);
                existingDept.getEmployees().add(emp);
            });
        }

        return repository.save(existingDept);
    }

    private Department findDepartmentOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id: " + id));
    }
}