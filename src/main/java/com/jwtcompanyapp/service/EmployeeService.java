package com.jwtcompanyapp.service;


import com.jwtcompanyapp.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);

    Employee update(Employee employee);

    List<Employee> getAll();

    Employee findById(Long id);

    void delete(Long id);
}
