package com.spring.tour.controller;

import com.spring.tour.entity.JobPostActivity;
import com.spring.tour.entity.RecruiterJobsDto;
import com.spring.tour.entity.RecruiterProfile;
import com.spring.tour.entity.Users;
import com.spring.tour.service.JobPostActivityService;
import com.spring.tour.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class JobPostActivityController {
    private final UserService userService;
    private final JobPostActivityService jobPostActivityService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Object currentUserProfile = userService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(currentUserProfile instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("username", authentication.getName());
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("recruiter"))) {
                List<RecruiterJobsDto> recruiterJobs = jobPostActivityService.getRecruiterJobs(((RecruiterProfile) currentUserProfile).getUserAccountId());
                model.addAttribute("jobPost", recruiterJobs);
            }
        }
        model.addAttribute("user", currentUserProfile);
        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJob(Model model) {
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String addNew(JobPostActivity jobPostActivity, Model model) {
        Users user = userService.getCurrentUser();
        if (user != null) {
            jobPostActivity.setPostedById(user);
        }
        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("jobPostActivity", jobPostActivity);
        JobPostActivity saved = jobPostActivityService.addNEw(jobPostActivity);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/edit/{id}")
    public String editJob(@PathVariable int id, Model model) {
        JobPostActivity jobPostActivity = jobPostActivityService.getById((id));
        model.addAttribute("jobPostActivity", jobPostActivity);
        model.addAttribute("user", userService.getCurrentUser());
        return "add-jobs";
    }
}
