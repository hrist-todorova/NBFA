package com.bootcamp.domain;

import com.bootcamp.domain.Coordinate;
import com.bootcamp.domain.Player;

import java.util.ArrayList;
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

    void Init(int w, int h) {
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
    }

    public void Move(Coordinate from, Coordinate to) {
        int x = from.x;
        int y = from.y;
        int x1 = to.x;
        int y1 = to.y;
        if (map[x][y] != currentPlayer)
            return;

        int dx = Math.abs(x1-x);
        int dy = Math.abs(y1-y);

        if(dx > 1 || dy > 1)
            map[x][y] = EmptyCell;

        map[x1][y1] = currentPlayer;
        OccupyNeighbours(new Coordinate(x1,y1));

        if(CheckWin() != 0)
            return;

        currentPlayer = OpponentTo(currentPlayer);
    }

    public int CurrentPlayer()
    {
        return currentPlayer;
    }

    void OccupyNeighbours(Coordinate point) {
        int x = point.x;
        int y = point.y;
        for (int dx = -1; dx < 2; ++dx)
            for (int dy = -1; dy < 2; ++dy){
                if(x+dx < 0 || x+dx > map.length || y+dy < 0 || y+dy > map[0].length)
                    continue;

                if(map[x+dx][y+dy] != OpponentTo(currentPlayer))
                    continue;

                map[x+dx][y+dy] = currentPlayer;
                --playerMap.get(OpponentTo(currentPlayer)).points;
                ++playerMap.get(currentPlayer).points;
            }
    }

    int OpponentTo(int player) {
        return player == Player1 ? Player2 : Player1;
    }

    public int CheckWin() {
        for (int i = 1; i < playerMap.size(); ++i) {
            if (playerMap.get(i).points == 0)
                return OpponentTo(i);
        }

        return 0;
    }
}