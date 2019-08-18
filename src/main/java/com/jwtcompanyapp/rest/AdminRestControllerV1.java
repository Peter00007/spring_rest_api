package com.jwtcompanyapp.rest;

import com.jwtcompanyapp.dto.DepartamentDto;
import com.jwtcompanyapp.dto.EmployeeDto;
import com.jwtcompanyapp.dto.UserDto;
import com.jwtcompanyapp.model.Departament;
import com.jwtcompanyapp.model.Employee;
import com.jwtcompanyapp.model.Role;
import com.jwtcompanyapp.model.User;
import com.jwtcompanyapp.service.DepartamentService;
import com.jwtcompanyapp.service.EmployeeService;
import com.jwtcompanyapp.service.RoleService;
import com.jwtcompanyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

    private final UserService userService;
    private final RoleService roleService;
    private final DepartamentService departamentService;
    private final EmployeeService employeeService;


    @Autowired
    public AdminRestControllerV1(UserService userService, RoleService roleService, DepartamentService departamentService, EmployeeService employeeService) {
        this.userService = userService;
        this.roleService = roleService;
        this.departamentService = departamentService;
        this.employeeService = employeeService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "users/")
    public ResponseEntity getAllUsers() {
        List<User> userList = userService.getAll();

        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            userDtoList.add(UserDto.fromUser(user));
        }

        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "users/")
    public ResponseEntity saveUser(@RequestBody UserDto saveUser) {
        if (saveUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = saveUser.toUser();

        List<Role> roleList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roleList.add(roleService.findById(role.getId()));
        }
        user.setRoles(roleList);

        this.userService.save(user);
        saveUser.setPassword(user.getPassword());

        return new ResponseEntity<>(saveUser, HttpStatus.OK);
    }

    @PutMapping(value = "users/")
    public ResponseEntity updateUser(@RequestBody UserDto userDto) {
        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userDto.toUser();

        String password = userService.findById(user.getId()).getPassword();

        user.setPassword(password);

        List<Role> roleList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roleList.add(roleService.findById(role.getId()));
        }
        user.setRoles(roleList);
        this.userService.update(user);
        userDto.setPassword(user.getPassword());

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "users/{id}")
    public ResponseEntity deleteUser(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        this.userService.delete(id);
        UserDto userDto = UserDto.fromUser(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
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