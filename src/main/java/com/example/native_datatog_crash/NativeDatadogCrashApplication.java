package com.example.native_datatog_crash;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RegisterReflectionForBinding(UUID[].class)
public class NativeDatadogCrashApplication {

    public static void main(String[] args) {
        SpringApplication.run(NativeDatadogCrashApplication.class, args);
    }

}

@RestController
class TodoController {

    private final TodoService todoService;

    TodoController( TodoService todoService) {
        this.todoService = todoService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void populateData() {

        todoService.addTodo("Buy milk");
        todoService.addTodo("Send postcard");
        todoService.addTodo("Water plants");
    }

    @GetMapping("/todos")
    public List<String> getTodo() {
        return todoService.getTodos();
    }

    @PostMapping("/todos")
    public void addTodo(@RequestBody String todo) {
        todoService.addTodo(todo);
    }

}

@Service
class TodoService {

    private final TodoRepo todoRepo;

    TodoService(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    public List<String> getTodos() {
        return todoRepo.findAll()
            .stream()
            .map(Todo::getContent)
            .toList();
    }

    public void addTodo(String todo) {
        todoRepo.save(new Todo().setId(UUID.randomUUID()).setContent(todo));
    }
}

interface TodoRepo extends ListCrudRepository<Todo, UUID> {
}

@Entity
@Table(name = "todos")
class Todo {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String content;

    public UUID getId() {return id;}

    public Todo setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Todo setContent(String todo) {
        this.content = todo;
        return this;
    }

}