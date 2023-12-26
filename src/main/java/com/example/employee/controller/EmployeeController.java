package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.service.EmpService;
import lombok.Getter;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@UUID
public class EmployeeController {

    @Autowired
    private EmpService employeeService;



    @PostMapping
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok("Employee added with ID: " + employeeService.addEmployee(employee));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee with ID " + id + " deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok("Employee with ID " + id + " updated successfully");
    }


    @GetMapping("/manager/{employeeId}/level/{level}")
    public ResponseEntity<String> getNthLevelManager(@PathVariable String employeeId, @PathVariable int level) {
        return employeeService.getNthLevelManager(employeeId, level);
    }

    @GetMapping("/paged")
    public ResponseEntity<List<Employee>> getAllEmployeesWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "Name") String sortBy) {
        return ResponseEntity.ok(employeeService.getAllEmployeesWithPaginationAndSorting(page, pageSize, sortBy));
    }

}

