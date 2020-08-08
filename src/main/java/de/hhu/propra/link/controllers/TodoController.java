package de.hhu.propra.link.controllers;

import de.hhu.propra.link.services.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    //Alle Todos abfragen.
    @GetMapping("/")
    public String index(Model model) {
        // Wird übergeben an das HTML und kann über Thymeleaf abgerufen werden !
        model.addAttribute("todos", todoService.allTodos());
        return "index";
    }

    @PostMapping("/fetch")
    public String fetch(){
        todoService.fetchAllTodosFromSource();
        return "redirect:/";
    }

    @PostMapping("/finished")
    public String finished(@RequestParam("todo_id") Long todo_id) {

        todoService.changeToFinished(todo_id);

        return "redirect:/";
    }


}
