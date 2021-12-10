package com.example.lab3.maze;

import com.example.lab3.maze.model.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class representing maze.
 */
public class Maze {

    private final Cell[][] cells;

    private final int cols;
    private final int rows;

    public Maze(int columns, int rows){
        this.cols = columns;
        this.rows = rows;

        this.cells = new Cell[cols][this.rows];
        for(int x = 0; x < cols; x++){
            for(int y = 0; y < this.rows; y++){
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    public List<Cell> getCellNeighbours(Cell cell){
        List<Cell> neighbours = new ArrayList<>();
        neighbours.add(getLeftNeighbour(cell));
        neighbours.add(getRightNeighbour(cell));
        neighbours.add(getTopNeighbour(cell));
        neighbours.add(getBottomNeighbour(cell));
        return neighbours.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private Cell getLeftNeighbour(Cell cell){
        if(cell.col > 0)
            return cells[cell.col - 1][cell.row];
        else return null;
    }

    private Cell getRightNeighbour(Cell cell){
        if(cell.col < cols -1)
            return cells[cell.col + 1][cell.row];
        else return null;
    }

    private Cell getTopNeighbour(Cell cell){
        if(cell.row > 0)
            return cells[cell.col][cell.row - 1];
        else return null;
    }

    private Cell getBottomNeighbour(Cell cell){
        if(cell.row < rows -1)
            return cells[cell.col][cell.row + 1];
        else return null;
    }

    public Cell getCell(int col, int row){
        return cells[col][row];
    }
}
