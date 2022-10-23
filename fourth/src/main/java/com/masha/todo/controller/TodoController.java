package com.masha.todo.controller;

import com.masha.todo.model.Todo;
import com.masha.todo.dao.TodoDao;
import com.masha.todo.model.TodoList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TodoController {

    private final TodoDao todoDao;

    public TodoController(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    @GetMapping("/todos")
    public String getTodos(ModelMap map) {
        prepareModelMap(map, todoDao.getTodoLists());
        return "index";
    }

    @PostMapping("/add-todo")
    public String addTodo(@ModelAttribute("todo") Todo todo) {
        todoDao.addTodo(todo);
        return "redirect:/todos";
    }

    @PostMapping("/add-todo-list")
    public String addTodoList(@ModelAttribute("todoList") TodoList todoList) {
        todoDao.addTodoList(todoList);
        return "redirect:/todos";
    }

    @GetMapping("/delete-todo-list")
    public String deletetodoList(@RequestParam("id") long id) {
        todoDao.deleteTodoList(id);
        return "redirect:/todos";
    }

    @GetMapping("/toggle-todo")
    public String toggleTodo(@RequestParam("id") long id) {
        todoDao.toggleTodo(id);
        return "redirect:/todos";
    }

    private void prepareModelMap(ModelMap map, List<TodoList> todos) {
        map.addAttribute("todoLists", todos);
        map.addAttribute("todo", new Todo());
        map.addAttribute("todoList", new TodoList());
    }
}
