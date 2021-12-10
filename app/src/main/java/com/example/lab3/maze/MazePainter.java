package com.example.lab3.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.lab3.maze.model.Cell;
import com.example.lab3.maze.model.Direction;

/**
 * Class to paint cells, walls, player and exit.
 */
public class MazePainter {
    private Canvas canvas;
    private final int rows, cols;
    private final float thickness = 4;
    private float cellSize;
    private int horizontalMargin;
    private int verticalMargin;
    int backgroundColor = Color.parseColor("#FAFAFA");

    MazeManager manager;

    public MazePainter(int cols, int rows){
        this.cols = cols;
        this.rows = rows;

        manager = new MazeManager(cols, rows);
        manager.generateMaze();
    }

    /**
     * Painting walls, player and exit.
     * @param canvas
     * @param wallPaint
     * @param playerPaint
     * @param exitPaint
     */
    public void paint(Canvas canvas, Paint wallPaint, Paint playerPaint, Paint exitPaint){
        this.canvas = canvas;
        this.canvas.drawColor(backgroundColor);
        this.cellSize = getCellSize();

        addMargins();

        drawWalls(wallPaint);
        drawExit(exitPaint);
        drawPlayer(playerPaint);
    }

    /**
     * Calculates cell size depending on maze cells number.
     * @return A float with side size.
     */
    private float getCellSize(){
        float width = canvas.getWidth();
        float height = canvas.getHeight();

        return width/height < cols / rows ? height / (rows +1) : width / (cols + 1);
    }

    /**
     * Adding margins to the canvas.
     */
    private void addMargins(){
        float width = canvas.getWidth();
        float height = canvas.getHeight();

        this.horizontalMargin = (int) ((width - cols * cellSize)/2);
        this.verticalMargin = (int) ((height - rows * cellSize)/2);

        canvas.translate(horizontalMargin, verticalMargin);
    }

    private void drawWalls(Paint wallPaint){
        for(int x = 0; x < cols; x++){
            for(int y = 0; y < rows; y++){
                if(manager.hasRightWall(x, y))
                    canvas.drawLine((x + 1) * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, wallPaint);

                if(manager.hasLeftWall(x, y))
                    canvas.drawLine(x * cellSize, y * cellSize, x * cellSize, (y + 1) * cellSize, wallPaint);

                if(manager.hasTopWall(x, y))
                    canvas.drawLine(x*cellSize, y*cellSize, (x+1)*cellSize, y*cellSize, wallPaint);

                if(manager.hasBottomWall(x, y))
                    canvas.drawLine(x * cellSize, (y + 1) * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, wallPaint);
            }
        }
    }

    private void drawPlayer(Paint playerPaint){
        Cell player = manager.getPlayerPosition();
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.rgb(135, 0, 0));
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setStrokeWidth(thickness +1);

        canvas.drawCircle(
                (float) (player.col + 0.5) * cellSize,
                (float) (player.row + 0.5) * cellSize,
                cellSize/3,
                playerPaint);

        canvas.drawCircle(
                (float) (player.col + 0.5) * cellSize,
                (float) (player.row + 0.5) * cellSize,
                cellSize/3,
                borderPaint);
    }

    private void drawExit(Paint exitPaint){
        float margin = cellSize/10;
        Cell exit = manager.getExitPosition();
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.DKGRAY);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(thickness +1);

        canvas.drawRect(
                exit.col * cellSize+margin,
                exit.row * cellSize+margin,
                (exit.col + 1) * cellSize-margin,
                (exit.row + 1) * cellSize-margin,
                exitPaint);
        canvas.drawRect(
                exit.col * cellSize+margin,
                exit.row * cellSize+margin,
                (exit.col + 1) * cellSize-margin,
                (exit.row + 1) * cellSize-margin,
                borderPaint);
    }

    /**
     * Moving player for 1 cell to some direction.
     * @param direction
     */
    private void movePlayer(Direction direction) {
        switch (direction){
            case UP:
                manager.movePlayerUp();
                break;
            case DOWN:
                manager.movePlayerDown();
                break;
            case LEFT:
                manager.movePlayerLeft();
                break;
            case RIGHT:
                manager.movePlayerRight();
                break;
        }
    }

    /**
     * Move player to the position if it's not wall.
     * @param x
     * @param y
     */
    public void movePlayerTo(float x, float y) {
        float playerCenterX = horizontalMargin + (manager.getPlayerPosition().col+0.5f)*cellSize;
        float playerCenterY = verticalMargin + (manager.getPlayerPosition().row+0.5f)*cellSize;

        float dx = x - playerCenterX;
        float dy = y - playerCenterY;

        float absDX = Math.abs(dx);
        float absDY = Math.abs(dy);

        if(absDX > cellSize || absDY > cellSize){
            if(absDX > absDY){
                if(dx > 0) {
                    movePlayer(Direction.RIGHT);
                } else {
                    movePlayer(Direction.LEFT);
                }
            } else {
                if(dy > 0) {
                    movePlayer(Direction.DOWN);
                } else {
                    movePlayer(Direction.UP);
                }
            }
        }
    }

    /**
     * Check if player is on exit cell.
     * @return
     */
    public boolean checkExit() {
        return manager.getPlayerPosition() == manager.getExitPosition();
    }

    /**
     * Redraw canvas.
     * @param newColor
     */
    public void changeCanvasAtFinish(String newColor){
        this.canvas.drawColor(Color.parseColor(newColor));
    }
}
