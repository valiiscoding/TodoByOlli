package de.hhu.propra.link.services;

import de.hhu.propra.link.entities.Todo;
import de.hhu.propra.link.repositories.TodoRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> allTodos() {
        return todoRepository.findAll();
    }


    public void fetchAllTodosFromSource() {
        //HTTP request auf die Seite und dann alle Todos in der DB sichern.
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos", String.class);
        JSONArray jsonObject = new JSONArray(response);
        for (int i = 0; i < jsonObject.length(); i++) {
            JSONObject currentTodo = jsonObject.getJSONObject(i);


            Todo todo = new Todo(currentTodo.getLong("id"),
                    getUsername(currentTodo.getLong("userId")),
                    currentTodo.getLong("userId"),
                    currentTodo.getString("title"),
                    currentTodo.getBoolean("completed"));


            todoRepository.save(todo);
        }
    }

    private String getUsername(long userId) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users/" + userId, String.class);
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("username");
    }


    public void save(Todo link) {
        todoRepository.save(link);
    }

    public void changeToFinished(Long todo_id) {
        Optional<Todo> todo = todoRepository.findById(todo_id);
        if(todo.isPresent()){
            Todo todoChange = todo.get();
            todoChange.setCompleted(true);
            todoRepository.save(todoChange);
        }
    }

}
