package ru.job4j.todo.controller;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "tasks/index";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        return "tasks/create";
    }

    @GetMapping("/{id}")
    public String getSingleTask(Model model, @PathVariable int id) {
        var taskOptional = service.findById(id);
        if (taskOptional.isEmpty()) {
            return "redirect:/tasks/index";
        }
        model.addAttribute("task", taskOptional);
        return "tasks/one";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        try {
            service.create(task);
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/update")
    public String getUpdatePage(Model model) {
        return "task/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        try {
            var isUpdated = service.update(task);
            if (!isUpdated) {
                model.addAttribute("message", "Update was not completed");
                return "errors/404";
            }
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @PostMapping("/delete")
    public String deleteById(Model model, @PathVariable int id) {
        try {
            var isDeleted = service.deleteById(id);
            if (!isDeleted) {
                model.addAttribute("message", "Update was not completed");
                return "errors/404";
            }
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/done")
    public String getDone(Model model) {
        model.addAttribute("tasks", service.findDone());
        return "tasks/index";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("tasks", service.findNew());
        return "tasks/index";
    }
    @GetMapping("/{taskId}")
    public String getSingleTask(@PathVariable int taskId, Model model) {
        var taskOptional = service.findById(taskId);
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }
}