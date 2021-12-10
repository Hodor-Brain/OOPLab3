package com.example.lab3;

import com.example.lab3.maze.objects.Cell;
import com.example.lab3.maze.objects.Maze;
import com.example.lab3.maze.Manager;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MazeTest {

    @Test
    public void testDimensions(){
        Maze maze = new Maze(10, 10);
        Assert.assertEquals(maze.getCols(), 10);
        Assert.assertEquals(maze.getRows(), 10);
    }

    @Test
    public void testMazeCells(){
        Manager maze = new Manager(5, 5);
        Cell player = maze.getPlayerPosition();
        Cell exit = maze.getExitPosition();
        Assert.assertNotEquals(player.col, exit.col);
        Assert.assertNotEquals(player.row, exit.row);
    }

    @Test
    public void testNeighbors(){
        Maze maze = new Maze(10, 10);
        List<Cell> list = maze.getCellNeighbours(maze.getCell(5, 5));
        Assert.assertEquals(list.get(0), maze.getCell(4, 5));
        Assert.assertEquals(list.get(1), maze.getCell(6, 5));
        Assert.assertEquals(list.get(2), maze.getCell(5, 4));
        Assert.assertEquals(list.get(3), maze.getCell(5, 6));
    }
}
