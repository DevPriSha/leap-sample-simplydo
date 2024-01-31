package com.sample.simplydo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import java.net.URI;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;
import org.springframework.web.util.UriComponentsBuilder;
//import java.io.*;

@RestController
@RequestMapping("/todolist")
class TodoItemController {
    private final SimplyDoRepository simplyDoRepository;

    private TodoItemController(SimplyDoRepository simplyDoRepository) {
        this.simplyDoRepository = simplyDoRepository;
//        System.out.println("Repo Created");
    }

    //READ
    @GetMapping("/{requestedId}")
    private ResponseEntity<TodoItem> findById(@PathVariable Long requestedId) {
        Optional<TodoItem> todoItemOptional = simplyDoRepository.findById(requestedId);
        if (todoItemOptional.isPresent()) {
            return ResponseEntity.ok(todoItemOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //CREATE
    @PostMapping
    private ResponseEntity<Void> createTodoItem(@RequestBody TodoItem newTodoItemRequest, UriComponentsBuilder ucb) {
        TodoItem savedTodoItem = simplyDoRepository.save(newTodoItemRequest);
        URI locationOfNewTodoItem = ucb
                .path("todolist/{id}")
                .buildAndExpand(savedTodoItem.id())
                .toUri();
        return ResponseEntity.created(locationOfNewTodoItem).build();
    }

    //READ ALL
    @GetMapping()
    private ResponseEntity<Iterable<TodoItem>> findAll() {
        return ResponseEntity.ok(simplyDoRepository.findAll());
    }
}