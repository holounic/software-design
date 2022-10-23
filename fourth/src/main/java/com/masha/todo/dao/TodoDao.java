package com.masha.todo.dao;

import com.masha.todo.model.Todo;
import com.masha.todo.model.TodoList;
import com.masha.todo.repository.TodoListRepository;
import com.masha.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoDao {

    private final TodoRepository todoRepository;
    private final TodoListRepository todoListRepository;

    public TodoDao(TodoRepository repository, TodoListRepository todoListRepository) {
        this.todoRepository = repository;
        this.todoListRepository = todoListRepository;
    }

    public void addTodo(Todo todo) {
        todo.setId(0);
        todoRepository.save(todo);
    }

    public void addTodoList(TodoList todoList) {
        todoList.setId(0);
        todoListRepository.save(todoList);
    }

    public List<TodoList> getTodoLists() {
        return todoListRepository.findAll();
    }

    public void deleteTodoList(long id) {
        todoListRepository.deleteById(id);
    }

    public void toggleTodo(long id) {
        Todo todo = todoRepository.getById(id);
        todo.setDone(!todo.getDone());
        todoRepository.save(todo);
    }
}
