package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloValidatorTestSuite {

    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    public void validateTrelloBoards() {
        //Given
        List<TrelloList> trelloLists = Arrays.asList(
                new TrelloList("1", "name", false));
        List<TrelloBoard> trelloBoards = Arrays.asList(new TrelloBoard(
                "1", "test_name", trelloLists));

        //When
        List<TrelloBoard> boards = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertNotNull(boards);
        assertEquals(1, boards.size());
    }
}