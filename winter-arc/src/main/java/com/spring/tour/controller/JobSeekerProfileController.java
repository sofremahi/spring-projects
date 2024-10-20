package com.spring.tour.controller;

import com.spring.tour.entity.JobSeekerProfile;
import com.spring.tour.entity.Skills;
import com.spring.tour.entity.Users;
import com.spring.tour.repository.UsersRepo;
import com.spring.tour.service.JobSeekerProfileService;
import com.spring.tour.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker-profile")
@RequiredArgsConstructor
public class JobSeekerProfileController {
    private final UsersRepo usersRepo;

    private final JobSeekerProfileService jobSeekerProfileService;

    @GetMapping("")
    public String JobSeekerProfile(Model model) {
        JobSeekerProfile profile = new JobSeekerProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepo.findByEmail(authentication.getName()).orElseThrow(() ->
                    new UsernameNotFoundException("user not found"));
            Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.getOne((long) user.getUserId());
            if (jobSeekerProfile.isPresent()) {
                profile = jobSeekerProfile.get();
                if (profile.getSkills().isEmpty()) {
                    skills.add(new Skills());
                    profile.setSkills(skills);
                }
            }
            model.addAttribute("profile", profile);
            model.addAttribute("skills", skills);
        }
        return "job-seeker-profile";
    }

    @PostMapping("/addNew")
    public String addNew(
            JobSeekerProfile profile, Model model,
            @RequestParam("image") MultipartFile image,
            @RequestParam("pdf") MultipartFile pdf
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = usersRepo.findByEmail(authentication.getName()).orElseThrow(() ->
                    new UsernameNotFoundException("user not found"));
            profile.setUserId(user);
            profile.setUserAccountId(user.getUserId());
        }
        List<Skills> skills = new ArrayList<>();
        model.addAttribute("profile", profile);
        model.addAttribute("skills", skills);
        for (Skills skill : profile.getSkills()) {
            skill.setJobSeekerProfile(profile);
        }
        String imageName = "";
        String resumeName = "";
        if (!Objects.equals(image.getOriginalFilename(), "")) {
            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            profile.setProfilePhoto(imageName);
        }
        if (!Objects.equals(pdf.getOriginalFilename(), "")) {
            resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
            profile.setResume(resumeName);
        }
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.addNew(profile, user);
        try {
            String UploadDir = "photos/candidate/" + jobSeekerProfile.getUserAccountId();
            if (!Objects.equals(image.getOriginalFilename(), "")) {
                FileUploadUtil.saveFile(UploadDir, imageName, image);
            }
            if (!Objects.equals(pdf.getOriginalFilename(), "")) {
                FileUploadUtil.saveFile(UploadDir, resumeName, pdf);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/dashboard";
    }



}
