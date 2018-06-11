package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService dbService;

    @Test
    public void shouldFetchEmptyTasksList() throws Exception {
        //Given
        List<Task> tasks = new ArrayList<>();
        when(dbService.getAllTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchAllTasks() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(new TaskDto(1L, "Task_title", "Task_content"));
        when(taskMapper.mapToTaskDtoList(dbService.getAllTasks())).thenReturn(taskDtos);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Task_title")))
                .andExpect(jsonPath("$[0].content", is("Task_content")));
    }

//    @Test
//    public void shouldgetTaskById() throws Exception {
//        //Given
//        Task task = new Task(1L, "Task_title", "Task_content");
//        TaskDto taskDto = new TaskDto(1L, "Task_title", "Task_content");
//        when(dbService.getTask(1L)).thenReturn(Optional.of(task));
//        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
//
//        //When & Then
//        mockMvc.perform(get("/v1/task/getTask/1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.title", is("Task_title")))
//                .andExpect(jsonPath("$.content", is("Task_content")));
//    }

//    @Test
//    public void shouldDeleteTaskById() throws Exception {
//        //Given
//        Task task = new Task(1L, "Task_title", "Task_content");
//        dbService.deleteTask(task.getId());
//
//        //When & Then
//        mockMvc.perform(delete("/v1/task/deleteTask/1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

    @Test
    public void updateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task_title", "Task_content");
        when(taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto))))
                .thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task_title")))
                .andExpect(jsonPath("$.content", is("Task_content")));
    }

    @Test
    public void createTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task_title", "Task_content");
        dbService.saveTask(taskMapper.mapToTask(taskDto));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}