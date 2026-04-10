package org.kumar.restapi_jdbc.controller;

import jakarta.validation.Valid;
import org.kumar.restapi_jdbc.entity.Department;
import org.kumar.restapi_jdbc.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping
    public Department createDepartment(@Valid @RequestBody Department department) {
        return service.saveDepartment(department);
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return service.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return service.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id,
                                       @Valid @RequestBody Department department) {
        return service.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        service.deleteDepartment(id);
        return "Department deleted successfully";
    }

    @GetMapping("/{id}/employees")
    public List<?> getEmployeesByDepartment(@PathVariable Long id) {
        return service.getEmployeesByDepartment(id);
    }

    @PostMapping("/{id}/employees")
    public Department addEmployeeToDepartment(@PathVariable Long id,
                                              @RequestBody Department department) {
        return service.addEmployeeToDepartment(id, department);
    }
}