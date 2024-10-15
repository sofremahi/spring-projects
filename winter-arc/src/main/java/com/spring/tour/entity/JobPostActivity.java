package com.spring.tour.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@Entity
@ToString
public class JobPostActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobPostId;
    @ManyToOne
    @JoinColumn(name = "postedById", referencedColumnName = "userId")
    private Users postedById;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobLcoation", referencedColumnName = "id")
    private JobLocation jobLocation;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobCompany", referencedColumnName = "id")
    private JobCompany jobCompany;
    @Transient
    private Boolean isActive;
    @Transient
    private Boolean isSaved;
    @Length(max = 10000)
    private String descriptionOfJob;
    private String jobType;
    private String salary;
    private String remote;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date postedDate;
    private String jobTitle;

}
