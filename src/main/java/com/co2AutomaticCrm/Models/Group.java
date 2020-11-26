package com.co2AutomaticCrm.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "groups")
public class Group implements Serializable {

    public Group(Integer id, String name) {

        this.id = id;
        this.name = name;

    }

    @Id
    private int id;

    @Column(name = "name")
    private String name;


}
