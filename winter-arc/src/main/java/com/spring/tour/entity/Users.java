package com.spring.tour.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @NotEmpty
    private String name;
    @Column(unique = true)
    private String email;
    @NotEmpty
    private String password;
    private boolean isActive;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date registrationDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userTypeId" , referencedColumnName = "userTypeId")
    private UsersType userTypeId;

}
