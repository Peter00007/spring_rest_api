package com.jwtcompanyapp.service.impl;


import com.jwtcompanyapp.model.Departament;
import com.jwtcompanyapp.model.Status;
import com.jwtcompanyapp.repository.DepartamentRepository;
import com.jwtcompanyapp.service.DepartamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class DepartamentServiceImpl implements DepartamentService {

    private final DepartamentRepository departamentRepository;

    @Autowired
    public DepartamentServiceImpl(DepartamentRepository departamentRepository) {
        this.departamentRepository = departamentRepository;
    }

    @Override
    public Departament save(Departament departament) {
        departament.setUpdated(new Date());
        departament.setStatus(Status.ACTIVE);

        departamentRepository.save(departament);
        log.info("In save - departament: {} successfully seaved", departament);
        return departament;
    }

    @Override
    public Departament update(Departament departament) {
        departament.setUpdated(new Date());
        departament.setStatus(Status.ACTIVE);
        departamentRepository.save(departament);
        log.info("In updated - departament: {} successfully updated", departament);
        return departament;
    }

    @Override
    public List<Departament> getAll() {
        List<Departament> departamentList = departamentRepository.findAll();
        log.info("IN getAll - {} departaments found", departamentList.size());
        return departamentList;
    }

    @Override
    public Departament findById(Long id) {
        Departament departament = departamentRepository.findById(id).orElse(null);

        if (departament == null) {
            log.warn("IN findById - no departament found by id: {}", id);
            return null;
        }

        log.info("IN findById - departament: {} found by id: {}", departament);
        return departament;
    }

    @Override
    public void delete(Long id) {
        Departament departament = departamentRepository.getOne(id);
        departament.setStatus(Status.DELETED);
        departamentRepository.save(departament);
        log.info("IN delete - departament with id: {} successfully deleted", id);
    }
}
