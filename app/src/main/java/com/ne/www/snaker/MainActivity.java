package com.ne.www.snaker;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    LinearLayout liner;
    private TextView snaker;
    Snaker snaker2;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            log("msg.what:"+msg.what+",snaker2.getWidth():"+snaker2.getWidth()+",getHeight:"+snaker2.getHeight());
            if (snaker2.curX >= Snaker.width || snaker2.curY + 60 >= Snaker.height ||snaker2.curX <= 0){
                Toast.makeText(MainActivity.this, "game over", Toast.LENGTH_SHORT).show();
                isOver = false;
            }else {
                snaker2.invalidate(); //强制刷新画布
            }
        }
    };

    private void log(String msg) {
        Log.e(TAG,msg);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //引用父类的方法
        setContentView(R.layout.activity_main);
        DisplayMetrics metrics = new DisplayMetrics(); //获取屏幕对象
        getWindowManager().getDefaultDisplay().getMetrics(metrics);  //屏幕尺寸
        Snaker. width = metrics.widthPixels;
        Snaker. height = metrics.heightPixels;
        log("width："+Snaker.width+",height:"+Snaker.height);
        snaker2 = new Snaker(this);
        snaker = (TextView) findViewById(R.id.snaker);
        liner = (LinearLayout)findViewById(R.id.liner);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
        liner.addView(snaker2);
        log("getWidth:" + snaker2.getWidth() + ",getHeight:" + snaker2.getHeight());

        snaker.setText("我是一条蛇");
        isOver = true;
        thread.start(); //线程执行启动
    }

    boolean isOver = true;

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (isOver){
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    });

}
