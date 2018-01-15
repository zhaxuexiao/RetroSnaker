package com.ne.www.snaker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;


/**
 * Created by 27687 on 2018/1/9.
 */

public class Snaker extends View{
    private static final String TAG = "Snaker";
    public Rect rect; // 矩形
    Paint paint;  //画笔
    int setpX = 60;
    int setpY = 0;
    static final int rectSize = 60;  //矩形大小
    Random random;   //随机
    public static int width;
    public static int height;

    public Snaker(Context context) {
        super(context);
        rect = new Rect();
        paint = new Paint();
        random = new Random();
        setpXNum = width/rectSize;
        setpYNum = height/rectSize;
    }
    int curX = 0;
    int curY = 0;
    int blockX = 0;
    int blockY = 0;
    int setpXNum = 0;
    int setpYNum = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        curX += setpX;
        curY += setpY;
        log("curX:"+curX + ",curY:" +curY);
        rect.set(curX,curY,curX+rectSize,curY+rectSize);
//        paint.setColor(Color.RED);  //颜色
        paint.setColor(0xffff0000); //颜色
        canvas.drawRect(rect,paint);//canvas 为画布，paint 为画笔
        int randomX = random.nextInt();
        int randomY = random.nextInt();
        randomX = Math.abs(randomX);
        randomY = Math.abs(randomY);
        randomX = randomX%setpXNum;
        randomY = randomY%setpYNum;
        blockX = randomX * rectSize;
        blockY = randomY * rectSize;
        log("数字：" + random.nextInt());
        Rect blockRect = new Rect(blockX,blockY,blockX+rectSize,blockY+rectSize);
        paint.setColor(0xff0000ff);
        canvas.drawRect(blockRect,paint);
    }

    private void log(String msg) {
        Log.e(TAG,msg);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        log("onTouchEvent:"+event.toString());
        return super.onTouchEvent(event);
    }
    float xDown = 0;
    float yDown = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
         super.dispatchTouchEvent(event);
        log("dispatchTouchEvent:" + event.toString());

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            xDown = event.getX();
            yDown = event.getY();
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            float moveX = xDown - event.getX();
            float moveY = yDown - event.getY();
            moveX = Math.abs(moveX);  //取绝对值 Math.abs
            moveY = Math.abs(moveY);
            if (setpY != 0 ||setpX == 0){
                if (moveX > moveY){
                    if (xDown - event.getX() > 0){
                                 //向左走
                         setpX = -rectSize;
                         setpY = 0;
                    }else if (xDown - event.getX() < 0 ){
                                 //向右走
                         setpX = rectSize;
                         setpY = 0;
                    }else {
                        log("动次打次");
                    }
                }
            } else  if (setpX != 0  || setpY == 0){
                if (moveX < moveY) {
                    if (yDown - event.getY() > 0) {
                        //向下走
                        setpY = -rectSize;
                        setpX = 0;
                    } else if (yDown - event.getY() < 0) {
                        //向上走
                        setpY = rectSize;
                        setpX = 0;
                    } else {
                        log("打次动次");
                    }
                }
            }
        }
         return true;
    }
}
