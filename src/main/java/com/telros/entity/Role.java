package com.telros.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="roles")
@Data
public class Role {

    @Id
//    @SequenceGenerator(name = "roles_id_generator", sequenceName = "roles_id_seq")
//    @GeneratedValue(generator = "roles_id_generator")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name="name", unique = true)
    String name;
}
