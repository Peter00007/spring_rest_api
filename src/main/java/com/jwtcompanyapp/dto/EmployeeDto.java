package com.jwtcompanyapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jwtcompanyapp.model.Departament;
import com.jwtcompanyapp.model.Employee;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private int salary;
    private Date dateOfStartWork;
    private Date dayOfBirth;
    private Date created;
    private List<DepartamentDto> departaments;

    public Employee toUpdateEmployee() {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setSalary(salary);
        employee.setDateOfStartWork(dateOfStartWork);
        employee.setDayOfBirth(dayOfBirth);
        employee.setCreated(created);

        List<Departament> departamentList = new ArrayList<>();

        DepartamentDto departamentDto = new DepartamentDto();

        for (DepartamentDto dep : departaments) {
            departamentList.add(departamentDto.toDepartament(dep));
        }

        employee.setDepartaments(departamentList);

        return employee;
    }

    public Employee toSaveEmployee() {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setSalary(salary);
        employee.setDateOfStartWork(dateOfStartWork);
        employee.setDayOfBirth(dayOfBirth);
        employee.setCreated(created);

        List<Departament> departamentList = new ArrayList<>();

        DepartamentDto departamentDto = new DepartamentDto();

        for (DepartamentDto dep : departaments) {
            departamentList.add(departamentDto.toDepartament(dep));
        }

        employee.setDepartaments(departamentList);

        return employee;
    }

    public static EmployeeDto fromEmployee(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setDateOfStartWork(employee.getDateOfStartWork());
        employeeDto.setDayOfBirth(employee.getDayOfBirth());
        employeeDto.setCreated(employee.getCreated());

        List<DepartamentDto> departamentDtoList = new ArrayList<>();
        for (Departament departament : employee.getDepartaments()) {
            departamentDtoList.add(DepartamentDto.fromDepartament(departament));
        }

        employeeDto.setDepartaments(departamentDtoList);

        return employeeDto;
    }
}
