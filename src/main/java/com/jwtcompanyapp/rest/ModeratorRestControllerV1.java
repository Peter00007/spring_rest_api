package com.jwtcompanyapp.rest;

import com.jwtcompanyapp.dto.AdminUserDto;
import com.jwtcompanyapp.dto.DepartamentDto;
import com.jwtcompanyapp.dto.EmployeeDto;
import com.jwtcompanyapp.model.Departament;
import com.jwtcompanyapp.model.Employee;
import com.jwtcompanyapp.model.User;
import com.jwtcompanyapp.service.DepartamentService;
import com.jwtcompanyapp.service.EmployeeService;
import com.jwtcompanyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/moderator/")
public class ModeratorRestControllerV1 {

    private final UserService userService;
    private final EmployeeService employeeService;
    private final DepartamentService departamentService;

    @Autowired
    public ModeratorRestControllerV1(UserService userService, EmployeeService employeeService, DepartamentService departamentService) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.departamentService = departamentService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminUserDto result = AdminUserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "employees/")
    public ResponseEntity saveEmployee(@RequestBody EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Employee employee = employeeDto.toSaveEmployee();

        List<Departament> departamentList = new ArrayList<>();
        for (Departament dep : employee.getDepartaments()) {
            departamentList.add(departamentService.findById(dep.getId()));
        }

        employee.setDepartaments(departamentList);

        this.employeeService.save(employee);

        employeeDto.setId(employee.getId());

        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @PutMapping(value = "employees/")
    public ResponseEntity updateEmployee(@RequestBody EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Employee employee = employeeDto.toUpdateEmployee();

        List<Departament> departamentList = new ArrayList<>();
        for (Departament dep : employee.getDepartaments()) {
            departamentList.add(departamentService.findById(dep.getId()));
        }

        employee.setDepartaments(departamentList);

        this.employeeService.update(employee);

        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "employees/{id}")
    public ResponseEntity deleteEmployee(@PathVariable(name = "id") Long id) {
        Employee employee = employeeService.findById(id);

        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        this.employeeService.delete(id);

        EmployeeDto employeeDto = EmployeeDto.fromEmployee(employee);

        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
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


    @PostMapping(value = "departaments/")
    public ResponseEntity saveDepartament(@RequestBody DepartamentDto departamentDto) {
        if (departamentDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Departament departament = departamentDto.toDepartament(departamentDto);

        this.departamentService.update(departament);

        departamentDto.setId(departament.getId());

        return new ResponseEntity<>(departamentDto, HttpStatus.OK);
    }

    @PutMapping(value = "departaments/")
    public ResponseEntity updateDepartament(@RequestBody DepartamentDto departamentDto) {
        if (departamentDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.departamentService.update(departamentDto.toDepartament(departamentDto));

        return new ResponseEntity<>(departamentDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "departaments/{id}")
    public ResponseEntity deleteDepartament(@PathVariable(name = "id") Long id) {
        Departament departament = departamentService.findById(id);

        if (departament == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.departamentService.delete(id);

        DepartamentDto departamentDto = DepartamentDto.fromDepartament(departament);
        return new ResponseEntity<>(departamentDto, HttpStatus.OK);
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
