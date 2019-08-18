package com.jwtcompanyapp.service;

import com.jwtcompanyapp.model.Departament;

import java.util.List;

public interface DepartamentService {
    Departament save(Departament departament);

    Departament update(Departament departament);

    List<Departament> getAll();

    Departament findById(Long id);

    void delete(Long id);
}
