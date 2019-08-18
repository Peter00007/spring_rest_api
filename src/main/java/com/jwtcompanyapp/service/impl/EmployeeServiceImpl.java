package com.jwtcompanyapp.service.impl;


import com.jwtcompanyapp.model.Employee;
import com.jwtcompanyapp.model.Status;
import com.jwtcompanyapp.repository.EmployeeRepository;
import com.jwtcompanyapp.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;



@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee save(Employee employee) {
        employee.setUpdated(new Date());
        employee.setStatus(Status.ACTIVE);
        employeeRepository.save(employee);
        log.info("In save - employee: {} successfully seaved", employee);
        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        employee.setUpdated(new Date());
        employee.setStatus(Status.ACTIVE);
        employeeRepository.save(employee);
        log.info("In updated - employee: {} successfully updated", employee);
        return employee;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employeeList = employeeRepository.findAll();
        log.info("IN getAll - {} employees found", employeeList.size());
        return employeeList;
    }

    @Override
    public Employee findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            log.warn("IN findById - no employee found by id: {}", id);
            return null;
        }

        log.info("IN findById - employee: {} found by id: {}", employee);
        return employee;
    }

    @Override
    public void delete(Long id) {
        Employee employee = employeeRepository.getOne(id);
        employee.setStatus(Status.DELETED);
        employeeRepository.save(employee);
        log.info("IN delete - employee with id: {} successfully deleted", id);
    }
}
