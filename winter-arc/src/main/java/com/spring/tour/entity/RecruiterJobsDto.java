package com.spring.tour.entity;

import lombok.Data;

@Data
public class RecruiterJobsDto {
    private Long totalCandidates;
    private Long jobPostId;
    private Long jobTitle;
    private Long jobLocationId;
    private Long jobCompanyId;

}
