package com.spring.tour.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users_type")
public class UsersType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTypeId;
    private String userTypeName;

    @OneToMany(targetEntity = Users.class, mappedBy = "userTypeId", cascade = CascadeType.ALL)
    private List<Users> users;
}
