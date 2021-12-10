package com.example.lab3.maze.model;

/**
 * Class representing cell.
 */
public class Cell{
    public boolean topWall = true;
    public boolean leftWall = true;
    public boolean bottomWall = true;
    public boolean rightWall = true;
    /**
     * Bool used for creating maze on start.
     */
    public boolean visited = false;
    public int col;
    public int row;

    public Cell(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
