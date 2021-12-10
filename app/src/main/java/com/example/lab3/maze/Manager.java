package com.example.lab3.maze;

import com.example.lab3.maze.objects.Cell;
import com.example.lab3.maze.objects.Maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Class for managing and generating maze..
 */
public class Manager {

    private final Maze maze;
    private Cell playerCell;
    private final Cell exitCell;

    private static Random random = new Random();

    public Manager(int columns, int rows) {
        maze = new Maze(columns, rows);
        random = new Random();

        int[][] array = new int[][]{{0, 0}, {columns - 1, 0}, {columns - 1, rows - 1}, {0, rows - 1}};
        ArrayList<int[]> list = new ArrayList<>(Arrays.asList(array));

        int playerPos[] = list.remove(new Random().nextInt(list.size()));
        int exitPos[] = list.remove(new Random().nextInt(list.size()));

        playerCell = maze.getCell(playerPos[0], playerPos[1]);
        exitCell = maze.getCell(exitPos[0], exitPos[1]);
    }

    public void generateMaze() {
        Stack<Cell> way = new Stack<>();
        Cell current, next;

        current = maze.getCell(0, 0);
        current.visited = true;

        do {
            next = getNextRandomWall(current);
            if (next != null) {
                createWays(current, next);
                way.push(current);
                current = next;
                current.visited = true;
            } else {
                current = way.pop();
            }
        } while (!way.empty());

    }

    /**
     * Gets random unvisited neighbor cell to this cell.
     * @param cell
     * @return
     */
    private Cell getNextRandomWall(Cell cell) {
        List<Cell> neighbours = maze.getCellNeighbours(cell).stream()
                .filter(c -> !c.visited).collect(Collectors.toList());

        if (neighbours.size() > 0) {
            int index = random.nextInt(neighbours.size());
            return neighbours.get(index);
        }
        return null;
    }

    /**
     * Creates all ways in maze.
     * @param current
     * @param next
     */
    public static void createWays(Cell current, Cell next) {
        if (current.col == next.col && current.row == next.row + 1) {
            current.topWall = false;
            next.bottomWall = false;
        }
        if (current.col == next.col && current.row == next.row - 1) {
            current.bottomWall = false;
            next.topWall = false;
        }
        if (current.col == next.col + 1 && current.row == next.row) {
            current.leftWall = false;
            next.rightWall = false;
        }
        if (current.col == next.col - 1 && current.row == next.row) {
            current.rightWall = false;
            next.leftWall = false;
        }
    }

    public boolean hasRightWall(int x, int y) {
        return maze.getCell(x, y).rightWall;
    }

    public boolean hasLeftWall(int x, int y) {
        return maze.getCell(x, y).leftWall;
    }

    public boolean hasTopWall(int x, int y) {
        return maze.getCell(x, y).topWall;
    }

    public boolean hasBottomWall(int x, int y) {
        return maze.getCell(x, y).bottomWall;
    }

    public Cell getPlayerPosition() {
        return playerCell;
    }

    public Cell getExitPosition() {
        return exitCell;
    }

    public void movePlayerUp() {
        if (!playerCell.topWall)
            playerCell = maze.getCell(playerCell.col, playerCell.row - 1);
    }

    public void movePlayerDown() {
        if (!playerCell.bottomWall)
            playerCell = maze.getCell(playerCell.col, playerCell.row + 1);
    }

    public void movePlayerRight() {
        if (!playerCell.rightWall)
            playerCell = maze.getCell(playerCell.col + 1, playerCell.row);
    }

    public void movePlayerLeft() {
        if (!playerCell.leftWall)
            playerCell = maze.getCell(playerCell.col - 1, playerCell.row);
    }
}
