package com.ne.www.snaker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by 27687 on 2018/1/9.
 * 1。蛇和食物的碰撞检测
 * 2。蛇身体的加长和升级
 * 3。蛇身体的转弯
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

    int curX = 0;
    int curY = 0;
    int blockX = 0;
    int blockY = 0;
    int setpXNum = 0;  // X点的极限位置和步数
    int setpYNum = 0;  // Y点人极限位置和步数
    ArrayList <SnakerBody>arrayList; //数组，蛇的身体
    SnakerBody snakerBody; //食物
    boolean isFlush; //食物刷新
    SnakerBody snakerHeader; //蛇头
    public Snaker(Context context) {
        super(context);
        rect = new Rect();
        paint = new Paint();
        random = new Random();  //随机数
        setpXNum = width/rectSize;
        setpYNum = height/rectSize;
        arrayList = new ArrayList<SnakerBody>();
        snakerBody = new SnakerBody();
        snakerHeader = new SnakerBody();
        isFlush = true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        curX += setpX;
        curY += setpY;
        snakerHeader.x = curX / rectSize;
        snakerHeader.y = curY / rectSize;
        log("curX:"+curX + ",curY:" +curY);
        rect.set(curX,curY,curX+rectSize,curY+rectSize);
        paint.setColor(0xffff0000); //颜色
        canvas.drawRect(rect,paint);//canvas 为画布，paint 为画笔
        if (isFlush) {
            int randomX = random.nextInt();
            int randomY = random.nextInt();
            randomX = Math.abs(randomX);
            randomY = Math.abs(randomY);
            randomX = randomX % setpXNum;
            randomY = randomY % setpYNum;
            snakerBody.x = randomX;
            snakerBody.y = randomY;
            blockX = randomX * rectSize;
            blockY = randomY * rectSize;
            log("数字：" + random.nextInt());
            isFlush = false;
        }
        Rect blockRect = new Rect(blockX,blockY,blockX+rectSize,blockY+rectSize);
        paint.setColor(0xff0000ff);
        canvas.drawRect(blockRect,paint);
        isCrash(snakerHeader,snakerBody);

    }

    /**
     * 碰撞检测
     * @param snakerBody1
     * @param snakerBody2
     * @return
     */
    private boolean isCrash(SnakerBody snakerBody1,SnakerBody snakerBody2){
        if (snakerBody1 == null || snakerBody2 == null){
            log("传入参数为空");
            return false;
        }
        if (snakerBody1.x == snakerBody2.x && snakerBody1.y == snakerBody2.y){
            log("成功撞击:"+ snakerBody1.toString() + ",snakerBody2:" + snakerBody2.toString());
            isFlush = true;
            return true;
        }
        log("未碰撞：" + snakerBody1.toString() + ",snakerBody2:" + snakerBody2.toString());
        return false;
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
