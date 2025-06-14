package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(@RequestParam(required = false) Boolean done, Model model) {
        if (Boolean.TRUE.equals(done)) {
            model.addAttribute("tasks", service.findDone());
        } else if (Boolean.FALSE.equals(done)) {
            model.addAttribute("tasks", service.findNew());
        } else {
            model.addAttribute("tasks", service.findAll());
        }
        return "tasks/index";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/create";
    }

    @GetMapping("/one/{id}")
    public String getSingleTask(Model model, @PathVariable int id) {
        var taskOptional = service.findById(id);
        if (taskOptional.isEmpty()) {
            return "redirect:/tasks/index";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model, HttpSession httpSession) {
        try {
            task.setCreated(LocalDateTime.now());
            task.setDone(false);
            task.setUser((User) httpSession.getAttribute("user"));
            service.create(task);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(@PathVariable int id, Model model) {
        var taskOptional = service.findById(id);
        model.addAttribute("task", taskOptional.get());
        return "tasks/update";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute Task task, Model model, @PathVariable int id) {
        try {
            var isUpdated = service.update(task);
            if (!isUpdated) {
                model.addAttribute("message", "Update was not completed");
                return "redirect:/errors/404";
            }
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "redirect:/errors/404";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteById(Model model, @PathVariable int id) {
        try {
            var isDeleted = service.deleteById(id);
            if (!isDeleted) {
                model.addAttribute("message", "Update was not completed");
                return "redirect:/errors/404";
            }
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "redirect:/errors/404";
        }
    }

    @PostMapping("/setDoneById/{id}")
    public String setDoneById(@PathVariable int id) {
        service.setDoneById(id);
        return "redirect:/tasks";
    }
}