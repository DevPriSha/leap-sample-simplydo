package com.sample.simplydo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.io.*;

@RestController
@RequestMapping("/todoitem")
class TodoItemController {
    private final SimplyDoRepository simplyDoRepository;



    private TodoItemController(SimplyDoRepository simplyDoRepository) {
        this.simplyDoRepository = simplyDoRepository;
        System.out.println("Repo Created");
    }

//    private TodoItemController() {
//        System.out.println("constructor called without repo");
//    }
    @GetMapping("/{requestedId}")
    private ResponseEntity<TodoItem> findById(@PathVariable Long requestedId) {
        Optional<TodoItem> todoItemOptional = simplyDoRepository.findById(requestedId);
        if (todoItemOptional.isPresent()) {
            return ResponseEntity.ok(todoItemOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}