package com.example.lab3;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;

import com.example.lab3.maze.Painter;

/**
 * Class to generate new maze and paint walls, cells, player and exit.
 */
public class GameView extends View {
    private static final float WALL_THICKNESS = 10;
    /**
     * Paints for different objects.
     */
    private Paint wallPaint, playerPaint, exitPaint;
    Painter maze;
    Button button;
    PopupWindow popupWindow;
    Canvas canvas;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createView();
    }

    private void createView(){
        maze = new Painter(8, 12);

        wallPaint = new Paint();
        wallPaint.setColor(Color.DKGRAY);
        wallPaint.setStrokeWidth(WALL_THICKNESS);

        playerPaint = new Paint();
        playerPaint.setColor(Color.GREEN);

        exitPaint = new Paint();
        exitPaint.setColor(Color.GREEN);
    }

    private void Redraw(){
        canvas.drawColor(Color.LTGRAY);
        maze.paint(canvas, wallPaint, playerPaint, exitPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        Redraw();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
            return true;
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            float x = event.getX();
            float y = event.getY();

            maze.movePlayerTo(x, y);
            checkExit();
            invalidate();

            return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * Creates popup with message and button.
     */
    public void createPopup(){
        LayoutInflater inflater = (LayoutInflater) getContext().
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, (ViewGroup) getRootView(), false);

        //maze.changeCanvasAtFinish("3B393C");

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it

        popupWindow = new PopupWindow(popupView, width, height, false);
        popupWindow.showAtLocation((View) getParent(), Gravity.CENTER, 0, 0);

        button = popupView.findViewById(R.id.buttonReplay);

        setAlpha(0.2f);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createView();
                Redraw();
                popupView.setVisibility(GONE);
                popupWindow.showAsDropDown(popupView);
                popupWindow = null;
                setAlpha(1);
                invalidate();
            }
        });
    }

    /**
     * Method to check if player reached an exit.
     */
    public void checkExit() {
        if(maze.checkExit() && popupWindow == null){
            createPopup();
        }
    }
}
