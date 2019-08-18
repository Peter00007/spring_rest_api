package com.jwtcompanyapp.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jwtcompanyapp.model.Departament;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartamentDto {
    private Long id;
    private String name;
    private String address;
    private Date created;

    public Departament toDepartament(DepartamentDto departamentDto) {
        Departament departament = new Departament();
        departament.setId(departamentDto.getId());
        departament.setName(departamentDto.getName());
        departament.setAddress(departamentDto.getAddress());
        departament.setCreated(departamentDto.getCreated());

        return departament;
    }

    public static DepartamentDto fromDepartament(Departament departament) {
        DepartamentDto departamentDto = new DepartamentDto();
        departamentDto.setId(departament.getId());
        departamentDto.setName(departament.getName());
        departamentDto.setAddress(departament.getAddress());
        departamentDto.setCreated(departament.getCreated());

        return departamentDto;
    }
}
