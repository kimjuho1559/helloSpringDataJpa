package kr.ac.hansung.cse.hellospringdatajpa.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String rolename;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
