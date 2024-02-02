package com.sample.simplydo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
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
    @CrossOrigin(origins = "http://localhost:3000")
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
    @CrossOrigin(origins = "http://localhost:3000")
    private ResponseEntity<Void> createTodoItem(@RequestBody TodoItem newTodoItemRequest, UriComponentsBuilder ucb) {
        TodoItem savedTodoItem = simplyDoRepository.save(newTodoItemRequest);
        URI locationOfNewTodoItem = ucb
                .path("todolist/{id}")
                .buildAndExpand(savedTodoItem.id())
                .toUri();
        return ResponseEntity.created(locationOfNewTodoItem).build();
    }

    //READ ALL
//    @GetMapping()
//    @CrossOrigin(origins = "http://localhost:3000")
//    private ResponseEntity<Iterable<TodoItem>> findAll() {
//        return ResponseEntity.ok(simplyDoRepository.findAll());
//    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    private ResponseEntity<List<TodoItem>> findAll(Pageable pageable) {
        Page<TodoItem> page = simplyDoRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "duedate"))
                ));

        return ResponseEntity.ok(page.getContent());
    }

    @PutMapping("/{requestedId}")
    @CrossOrigin(origins = "http://localhost:3000")
    private ResponseEntity<Void> putTodoItem(@PathVariable Long requestedId, @RequestBody TodoItem todoItemUpdate) {
        Optional<TodoItem> optionalTodoItem = simplyDoRepository.findById(requestedId);
        TodoItem todoItem = optionalTodoItem.orElse(null);
        if (todoItem == null)
            return ResponseEntity.notFound().build();
        else {
            TodoItem updatedTodoItem = new TodoItem(todoItem.id(), todoItemUpdate.title(), todoItemUpdate.description(), todoItemUpdate.duedate(), todoItemUpdate.completed());
            simplyDoRepository.save(updatedTodoItem);

            return ResponseEntity.noContent().build();
        }
    }

    @PatchMapping("/{id}/completed")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updateCompletedField(@PathVariable Long id, @RequestBody boolean completed) {
        Optional<TodoItem> optionalTodoItem = simplyDoRepository.findById(id);
        TodoItem todoItem = optionalTodoItem.orElse(null);
        if (todoItem == null)
            return ResponseEntity.notFound().build();
        else {
            TodoItem updatedTodoItem = new TodoItem(todoItem.id(), todoItem.title(), todoItem.description(), todoItem.duedate(), completed);
            simplyDoRepository.save(updatedTodoItem);

            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    private ResponseEntity<Void> deleteTodoItem(@PathVariable Long id) {
        if (!simplyDoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        simplyDoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

