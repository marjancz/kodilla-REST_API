package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTestSuite {

    @InjectMocks
    private DbService dbService;

    @Mock
    TaskRepository taskRepository;

    @Test
    public void getAllTasks() {
        //Given
        List<Task> tasks = Arrays.asList(new Task(1L, "title", "description"));
        when(taskRepository.findAll()).thenReturn(tasks);

        //When
        List<Task> result = dbService.getAllTasks();

        //Then
        assertNotNull(result);
        assertEquals(1, result.size());
        result.forEach(task -> {
            assertEquals(1L, (long)task.getId());
            assertEquals("title", task.getTitle());
            assertEquals("description", task.getContent());
        });
    }

    @Test
    public void getTaskById() {
        //Given
        Task task = new Task(1L, "title", "description");
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        //When
        Optional<Task> result = dbService.getTask(1L);

        //Then
        assertNotNull(result);
        assertEquals(1L, (long)result.get().getId());
        assertEquals("title", result.get().getTitle());
        assertEquals("description", result.get().getContent());
    }

    @Test
    public void saveTask() {
        //Given
        Task task = new Task(1L, "title", "description");
        when(taskRepository.save(task)).thenReturn(task);

        //When
        Task result = dbService.saveTask(task);

        //Then
        assertNotNull(result);
        assertEquals(1L, (long)result.getId());
        assertEquals("title", result.getTitle());
        assertEquals("description", result.getContent());
    }

    @Test
    public void deleteTask() {
        //Given
        Task task = new Task(1L, "title", "description");


        //When
        dbService.deleteTask(1L);

        //Then

    }
}