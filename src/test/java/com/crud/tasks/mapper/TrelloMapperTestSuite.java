package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void mapToBoards() {
        //Given
        List<TrelloListDto> trelloListDto = Arrays.asList(
                new TrelloListDto("1", "listDto", false));
        List<TrelloBoardDto> trelloBoardDtos = Arrays.asList(
                new TrelloBoardDto("1", "boardDto", trelloListDto));

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);

        //Then
        assertEquals(1, trelloBoards.size());
        assertEquals("boardDto", trelloBoards.get(0).getName());
        assertFalse(trelloBoards.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void mapToBoardsDto() {
        //Given
        List<TrelloList> trelloList = Arrays.asList(
                new TrelloList("1", "list", false));
        List<TrelloBoard> trelloBoards = Arrays.asList(
                new TrelloBoard("1", "board", trelloList));

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(1, trelloBoardDtos.size());
        assertEquals("board", trelloBoardDtos.get(0).getName());
        assertFalse(trelloBoardDtos.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void mapToList() {
        //Given
        List<TrelloListDto> trelloListDtos = Arrays.asList(
                new TrelloListDto("1", "listDto", false));

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);

        //Then
        assertEquals(1, trelloLists.size());
        assertEquals("listDto", trelloLists.get(0).getName());
        assertFalse(trelloLists.get(0).isClosed());
    }

    @Test
    public void mapToListDto() {
        //Given
        List<TrelloList> trelloLists = Arrays.asList(
                new TrelloList("1", "list", false));

        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertEquals(1, trelloLists.size());
        assertEquals("list", trelloLists.get(0).getName());
        assertFalse(trelloListDtos.get(0).isClosed());
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard(
                "ToDo card", "Thing to do", "top", "1");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("ToDo card", trelloCard.getName());
        assertEquals("Thing to do", trelloCard.getDescription());
        assertEquals("top", trelloCard.getPos());
        assertEquals("1", trelloCard.getListId());
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "ToDo cardDto", "Thing to do", "top", "1");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("ToDo cardDto", trelloCard.getName());
        assertEquals("Thing to do", trelloCard.getDescription());
        assertEquals("top", trelloCard.getPos());
        assertEquals("1", trelloCard.getListId());
    }
}