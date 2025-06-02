package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService service;


    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute("user", new User());
        return "users/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute User user, Model model, HttpSession session) {
        try {
            service.save(user);
            session.setAttribute("user", user);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/500";
        }
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
        var optionalUser = service.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        session.setAttribute("user", optionalUser.get());
        return "redirect:/tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
