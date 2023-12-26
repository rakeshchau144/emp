package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmpService {

    @Autowired
    private EmpRepo employeeRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    public String addEmployee(Employee employee) {
        employee.setId(java.util.UUID.randomUUID().toString());
        employeeRepository.save(employee);
        notifyLevel1Manager(employee);
        return employee.getId();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }

    public void updateEmployee(String id, Employee updatedEmployee) {
        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(id);

        if (existingEmployeeOptional.isPresent()) {
            Employee existingEmployee = existingEmployeeOptional.get();
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setReportsTo(updatedEmployee.getReportsTo());
            existingEmployee.setProfileImage(updatedEmployee.getProfileImage());
            existingEmployee.setLevel(updatedEmployee.getLevel());
            employeeRepository.save(existingEmployee);
        } else {
            throw new RuntimeException("Employee with ID " + id + " not found");
        }
    }
    public ResponseEntity<String> getNthLevelManager(String employeeId, int level) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            for (int i = 0; i < level; i++) {
                if (employee.getReportsTo() == null) {
                    return ResponseEntity.ok("Employee has no manager at level " + level);
                }
                Optional<Employee> managerOptional = employeeRepository.findById(employee.getReportsTo());
                if (managerOptional.isPresent()) {
                    employee = managerOptional.get();
                } else {
                    return ResponseEntity.ok("Manager not found at level " + level);
                }
            }

            return ResponseEntity.ok("Manager at level " + level + ": " + employee.getName());
        } else {
            // Handle the case where the employee with the given ID is not found
            return ResponseEntity.ok("Employee with ID " + employeeId + " not found");
        }
    }
    public List<Employee> getAllEmployeesWithPaginationAndSorting(int page, int pageSize, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(sortBy));
        Page<Employee> employeePage = employeeRepository.findAll(pageRequest);
        return employeePage.getContent();
    }

    private void notifyLevel1Manager(Employee employee) {
        String level1ManagerId = employee.getReportsTo();
        Optional<Employee> level1ManagerOptional = employeeRepository.findById(level1ManagerId);
        if (level1ManagerOptional.isPresent()) {
            Employee level1Manager = level1ManagerOptional.get();
            String subject = "New Employee Addition Notification";
            String message = String.format(
                    "%s will now work under you. Mobile number is %s and email is %s",
                    employee.getName(), employee.getPhoneNumber(), employee.getEmail());
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(level1Manager.getEmail());
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            javaMailSender.send(mailMessage);
        }
    }
}
