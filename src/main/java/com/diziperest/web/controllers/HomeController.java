package com.diziperest.web.controllers;

import com.diziperest.web.models.Series;
import com.diziperest.web.models.User;
import com.diziperest.web.repositories.SeriesRepository;
import com.diziperest.web.services.concretes.SeriesService;
import com.diziperest.web.services.concretes.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/")
    public String index(){

        return "index";
    }

    @GetMapping("/register")
    public String Register(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        model.addAttribute("user", user);

        if (result.hasErrors()){
            return "register";
        } else {
            userService.Add(user);

        }
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }
}
