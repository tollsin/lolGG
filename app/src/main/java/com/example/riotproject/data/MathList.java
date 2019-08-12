package com.example.riotproject.data;

import java.util.ArrayList;
import java.util.List;

public class MathList {
    private ArrayList<MathReferenceDto>mathes;
    private int totalGames,startIndex,endIndex;
    private long gameId;

    public ArrayList<MathReferenceDto> getMathes() {
        return mathes;
    }

    public void setMathes(ArrayList<MathReferenceDto> mathes) {
        this.mathes = mathes;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
