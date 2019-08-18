package com.jwtcompanyapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "departaments")
@Data
public class Departament extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @ManyToMany(mappedBy = "departaments", fetch = FetchType.LAZY)
    private List<Employee> employees;

    @Override
    public String toString() {
        return "Departament{" +
                "id='" + super.getId() +
                "name='" + name + '\'' +
                ", address='" + address +
                ", created='" + super.getCreated() +
                '}';
    }
}
