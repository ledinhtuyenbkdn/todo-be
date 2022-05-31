package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/todo")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        todo = todoRepository.save(todo);
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/todo")
    public ResponseEntity<List<Todo>> getTodo() {
        List<Todo> todos = todoRepository.findAll();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay"));
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/todo")
    public ResponseEntity<Todo> updateTodoById(@RequestBody Todo todo) {
        Todo existingTodo = todoRepository.findById(todo.getId())
                .orElseThrow(() -> new RuntimeException("Khong tim thay"));

        existingTodo.setMessage(todo.getMessage());
        existingTodo.setCompleted(todo.getCompleted());

        existingTodo = todoRepository.save(existingTodo);

        return ResponseEntity.ok(existingTodo);
    }
}
