package com.example.users.controller;

import com.example.users.entities.User;
import com.example.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private final UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();



    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public String users(Model model){
        List<User> usersList = userRepository.findAll();
        model.addAttribute("usersList", usersList);
        return "users";
    }

    @PostMapping(value = "/CreateUser")
    public String CreateUser(User user, Model model){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("message","user Created!");
        return "redirect:/";
    }

    @GetMapping("/CreateUser")
    public String CreatedUser(Model model){
        model.addAttribute("user", new User());
        return "CreateUser";
    }


    @GetMapping("/getUserByFname")
    public String getUserByFname(@RequestParam(name = "firstname") String firstname,Model model) {
        List<User> usersList  = userRepository.findByfirstname(firstname);
        model.addAttribute("usersList", usersList);
    return "users";
    }
    @RequestMapping("/delete/{id}")
    public String deletestudent(@PathVariable(name = "id") int id) {
        userRepository.deleteById(id);
        return "redirect:/";
    }
}
