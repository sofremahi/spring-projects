package com.spring.tour.controller;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.service.JobPostActivityService;
import com.spring.tour.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class JobSeekerApplyController {
    private final JobPostActivityService jobPostActivityService;
    private final UserService userService;

    @GetMapping("/job-details-apply/{id}")
    public String display(@PathVariable("id") int id ,  Model model) {
        JobPostActivity jobPostActivity = jobPostActivityService.getById(id);
        model.addAttribute("jobDetails", jobPostActivity);
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "job-details";
    }
}


