package org.kumar.restapi_jdbc.service;

import org.kumar.restapi_jdbc.entity.Department;
import org.kumar.restapi_jdbc.entity.Employee;
import org.kumar.restapi_jdbc.exception.BadRequestException;
import org.kumar.restapi_jdbc.exception.ResourceNotFoundException;
import org.kumar.restapi_jdbc.repository.DepartmentRepository;
import org.kumar.restapi_jdbc.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }


    public Employee saveEmployee(Employee employee) {

        if (employee == null) {
            throw new BadRequestException("Employee cannot be null");
        }

        if (employee.getDepartment() == null || employee.getDepartment().getId() == null) {
            throw new BadRequestException("Department ID is required");
        }

        Department dept = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id: "
                                + employee.getDepartment().getId()));

        employee.setDepartment(dept);

        return employeeRepository.save(employee);
    }


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public Employee getEmployeeById(Long id) {

        if (id == null || id <= 0) {
            throw new BadRequestException("Invalid employee ID");
        }

        return findEmployeeOrThrow(id);
    }


    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        Employee existing = findEmployeeOrThrow(id);

        if (updatedEmployee.getName() != null)
            existing.setName(updatedEmployee.getName());

        if (updatedEmployee.getEmail() != null)
            existing.setEmail(updatedEmployee.getEmail());

        if (updatedEmployee.getAge() != 0)
            existing.setAge(updatedEmployee.getAge());

        if (updatedEmployee.getRole() != null)
            existing.setRole(updatedEmployee.getRole());

        if (updatedEmployee.getSalary() != 0)
            existing.setSalary(updatedEmployee.getSalary());

        if (updatedEmployee.getPhoneNumber() != null)
            existing.setPhoneNumber(updatedEmployee.getPhoneNumber());

        // 🔥 Update department if provided
        if (updatedEmployee.getDepartment() != null &&
                updatedEmployee.getDepartment().getId() != null) {

            Department dept = departmentRepository.findById(
                            updatedEmployee.getDepartment().getId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Department not found with id: "
                                    + updatedEmployee.getDepartment().getId()));

            existing.setDepartment(dept);
        }

        return employeeRepository.save(existing);
    }

    public void deleteEmployee(Long id) {

        Employee employee = findEmployeeOrThrow(id);
        employeeRepository.delete(employee);
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {

        if (departmentId == null || departmentId <= 0) {
            throw new BadRequestException("Invalid department ID");
        }

        return employeeRepository.findByDepartmentId(departmentId);
    }


    private Employee findEmployeeOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + id));
    }
}