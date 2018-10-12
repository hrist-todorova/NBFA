package com.bootcamp.NBFA;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.domain.Coordinate;
import com.bootcamp.domain.Game;

@RestController("/playgame")
public class GameController {

    public static int width = 10;
    public static int height = 10;
    static Game currentGame;

    @PostConstruct
    void init() {
        currentGame = new Game(width, height);
    }

    @GetMapping
    public GameState initialState() {
        GameState currentState = new GameState();
        currentState.board = currentGame.GetMap();
        currentState.isFinished = currentGame.CheckWin() != -1 ? true : false;
        currentState.winner = currentGame.CheckWin();
        currentState.playerTurn = currentGame.CurrentPlayer();
        return currentState;
    }

    @GetMapping(path = "/move")
    public GameState moveFromTo(@RequestParam("i1") int i1, @RequestParam("j1") int j1,
                                @RequestParam("i2") int i2, @RequestParam("j2") int j2) {
        currentGame.Move(new Coordinate(i1, j1), new Coordinate(i2, j2));
        GameState currentState = new GameState();
        currentState.board = currentGame.GetMap();
        currentState.isFinished = currentGame.CheckWin() != -1 ? true : false;
        currentState.winner = currentGame.CheckWin();
        currentState.playerTurn = currentGame.CurrentPlayer();
        return currentState;
    }

}