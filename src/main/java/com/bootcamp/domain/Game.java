package com.bootcamp.domain;

import com.bootcamp.domain.Coordinate;
import com.bootcamp.domain.Player;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Game {
    private final int EmptyCell = -1;
    private final int InvalidCell = -2;
    private final int Player1 = 1;
    private final int Player2 = 2;

    private List<Player> playerMap;

    private int currentPlayer;

    private int[][] map;

    public Game(int w, int h) {
        Init(w,h);
    }

    private void Init(int w, int h) {
        map = new int[w][h];

        for (int x = 0; x < w; ++x)
            for (int y = 0; y < h; ++y)
                map[x][h] = EmptyCell;

        map[1][1] = Player1;
        map[w - 2][h - 2] = Player2;
        currentPlayer = Player1;

        playerMap = new ArrayList<>();
        playerMap.add(new InvalidPlayer());
        playerMap.add(new Player("Player 1", 1));
        playerMap.add(new Player("Player 2", 1));
        playerMap.get(Player1).spots.add(new Coordinate(1,1));
        playerMap.get(Player2).spots.add(new Coordinate(8,8));
    }

    public void Move(Coordinate from, Coordinate to) {
        if (!CanMove(from, to))
            return;

        if (map[from.x][from.y] != currentPlayer)
            return;

        int dx = Math.abs(to.x-from.x);
        int dy = Math.abs(to.y-from.y);

        if(dx > 1 || dy > 1)
            map[from.x][from.y] = EmptyCell;

        map[to.x][to.y] = currentPlayer;
        OccupyNeighbours(to);
        currentPlayer = OpponentTo(currentPlayer);
    }

    private boolean CanMove(@NotNull Coordinate from, @NotNull Coordinate to) {
        if(map[to.x][to.y] != EmptyCell)
            return false;

        return !(Math.abs(from.x-to.x) > 2 || Math.abs(from.y-to.y) > 2);
    }

    public int CurrentPlayer() {
        return currentPlayer;
    }

    private void OccupyNeighbours(Coordinate pt) {
        for (int dx = -1; dx < 2; ++dx)
            for (int dy = -1; dy < 2; ++dy){
                if(pt.x+dx < 0 || pt.x+dx > map.length || pt.y+dy < 0 || pt.y+dy > map[0].length)
                    continue;

                if(map[pt.x+dx][pt.y+dy] != OpponentTo(currentPlayer))
                    continue;

                map[pt.x+dx][pt.y+dy] = currentPlayer;
                --playerMap.get(OpponentTo(currentPlayer)).points;
                ++playerMap.get(currentPlayer).points;
            }
    }

    public int[][] GetMap(){
        return map;
    }

    private int OpponentTo(int player) {
        return player == Player1 ? Player2 : Player1;
    }

    public int CheckWin() {
        int sum = 0;

        for (int i = 1; i < playerMap.size(); ++i) {
            if (playerMap.get(i).points == 0) {
                FillEmptySpaces(OpponentTo(i));
                return TheWinner();
            }

            sum += playerMap.get(i).points;
        }

        if(sum == 100)
            return TheWinner();

        for(int plID = 1; plID < playerMap.size(); ++plID) {
            boolean canMove = false;
            Player cp = playerMap.get(plID);
            for(Coordinate spot : cp.spots)
                for(int i = -2; i < 3; ++i)
                    for(int j = -2; j < 3; ++j) {
                        if (CanMove(spot, new Coordinate(spot.x + i, spot.y + j)))
                            canMove = true;
                    }
                    if(!canMove) {
                        FillEmptySpaces(OpponentTo(plID));
                        return TheWinner();
                    }
        }

        return -1;
    }

    private int TheWinner() {
        if(playerMap.get(1).points == playerMap.get(2).points)
            return 0;

        if(playerMap.get(1).points > playerMap.get(2).points)
            return 1;

        return 2;
    }

    private void FillEmptySpaces(int playerId) {
        for(int x = 0; x < map.length; ++x)
            for(int y = 0; y < map[0].length; ++y)
                if(map[x][y] == EmptyCell) {
                    map[x][y] = playerId;
                    ++playerMap.get(playerId).points;
                }
    }
}