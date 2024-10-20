package com.spring.tour.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "recruiter_profile")
@Data
public class RecruiterProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userAccountId;
    @OneToOne
    @JoinColumn(name = "user_account-id")
    @MapsId
    private Users userId;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String state;
    private String company;
    @Column(nullable = true, length = 64)
    private String profilePhoto;

    public String getPhotosImagePath() {
        if (profilePhoto == null) return null;
        return "/photos/recruiter/" + userAccountId + "/" + profilePhoto;
    }

}
