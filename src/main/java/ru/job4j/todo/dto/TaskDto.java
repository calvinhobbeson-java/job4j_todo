package ru.job4j.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.todo.model.Priority;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Integer id;
    private Integer userId;
    private String description;
    private LocalDateTime created;
    private Boolean done;
    private String priority;
}