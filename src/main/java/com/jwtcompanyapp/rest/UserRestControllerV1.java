package com.jwtcompanyapp.rest;

import com.jwtcompanyapp.dto.DepartamentDto;
import com.jwtcompanyapp.dto.EmployeeDto;
import com.jwtcompanyapp.dto.UserDto;
import com.jwtcompanyapp.model.Departament;
import com.jwtcompanyapp.model.Employee;
import com.jwtcompanyapp.model.User;
import com.jwtcompanyapp.service.DepartamentService;
import com.jwtcompanyapp.service.EmployeeService;
import com.jwtcompanyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {
    private final UserService userService;
    private final EmployeeService employeeService;
    private final DepartamentService departamentService;

    @Autowired
    public UserRestControllerV1(UserService userService, EmployeeService employeeService, DepartamentService departamentService) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.departamentService = departamentService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(value = "employees/")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAll();

        if (employeeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<EmployeeDto> employees = new ArrayList<>();

        for (Employee emp : employeeList) {
            employees.add(EmployeeDto.fromEmployee(emp));
        }

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(value = "employees/{id}")
    public ResponseEntity<EmployeeDto> getByIdEmployee(@PathVariable(name = "id") Long employeeId) {
        Employee employee = employeeService.findById(employeeId);

        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EmployeeDto employeeDto = EmployeeDto.fromEmployee(employee);

        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping(value = "departaments/")
    public ResponseEntity<List<DepartamentDto>> getAllDepartaments() {
        List<Departament> departamentList = departamentService.getAll();

        if (departamentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<DepartamentDto> departamentDtoList = new ArrayList<>();

        for (Departament dep : departamentList) {
            departamentDtoList.add(DepartamentDto.fromDepartament(dep));
        }

        return new ResponseEntity<>(departamentDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "departaments/{id}")
    public ResponseEntity<DepartamentDto> getByIdDepartament(@PathVariable(name = "id") Long departamentId) {
        Departament departament = departamentService.findById(departamentId);

        if (departament == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DepartamentDto departamentDto = DepartamentDto.fromDepartament(departament);

        return new ResponseEntity<>(departamentDto, HttpStatus.OK);
    }
}
