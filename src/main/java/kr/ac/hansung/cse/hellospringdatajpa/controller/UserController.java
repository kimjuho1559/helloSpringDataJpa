package kr.ac.hansung.cse.hellospringdatajpa.controller;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.RoleRepository;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("user") User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("emailExists", true);
            return "signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRolename("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        user.setRoles(List.of(userRole));
        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}

