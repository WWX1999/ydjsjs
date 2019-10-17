package com.android.DrawLineSample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


class TestView extends View {

    private Canvas canvas;
    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private float x,y;
    int paintcolor=Color.BLACK;
    int paintsize=5;
    int touch_num;
    boolean special;
   // ArrayList<Path> pathlist=new ArrayList<>();
    List<Path>pathlist=new ArrayList<>();

    public TestView(Context context) {
        super(context);

    }
    public TestView(Context context, AttributeSet attrs){
        super(context, attrs);
        touch_num=0;
        special=false;



        //bgColor = Color.WHITE;               //设置背景颜色

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int mScreenWidth = dm.widthPixels;
        int mScreenHeight = dm.heightPixels;
        bitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight-250, Bitmap.Config.ARGB_8888);    //设置位图，线就画在位图上面，第一二个参数是位图宽和高
        canvas=new Canvas();
        canvas.setBitmap(bitmap);
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);                //设置抗锯齿，一般设为true
        paint.setColor(paintcolor);              //设置线的颜色
        paint.setStrokeCap(Paint.Cap.ROUND);     //设置线的类型
        paint.setStrokeWidth(paintsize);                //设置线的宽度
    }


    //触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {    //拖动屏幕
            //canvas.drawLine(x, y, event.getX(), event.getY(), paint);   //画线，x，y是上次的坐标，event.getX(), event.getY()是当前坐标
            path.lineTo(event.getX(), event.getY());
            invalidate();

        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {    //按下屏幕
            switch(touch_num){
                case 0:
                    //canvas.drawPoint(event.getX(),  event.getY(), paint);                //画点
                    path=new Path();
                    path.moveTo(event.getX(),  event.getY());

                    break;
                case 1:
                    if(special) {
                        //canvas.drawCircle(event.getX(),  event.getY(),50,paint);
                        special=false;

                    }
                    else{
                        float r=Math.abs(x-event.getX());
                        canvas.drawCircle(x,y,r,paint);
                        touch_num=0;
                    }
                    break;
                case 2:
                    if(special){
                        canvas.drawPoint(event.getX(),  event.getY(), paint);
                        special=false;
                    }
                    else{
                        canvas.drawLine(x, y, event.getX(), event.getY(), paint);
                        //path.lineTo(event.getX(), event.getY());
                        touch_num=0;
                    }
                    break;
            }
            x=event.getX();
            y=event.getY();
            invalidate();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {    //松开屏幕
            //path.lineTo(x,y);
            if(path!=null){
                //path.lineTo(x,y);
                canvas.drawPath(path, paint);
                pathlist.add(path);
                path = null;
                invalidate();
            }

        }
        return true;
    }



    @Override
    public void onDraw(Canvas c) {
        c.drawBitmap(bitmap, 0, 0, null);
        //c.drawCircle(120,40,30,paint);

        if(path!=null){
            canvas.drawPath(path,paint);
        }
    }

    public void setSize(int size){
        paint.setStrokeWidth(size);
        paint.setColor(paintcolor);
    }

    public void setcolor(int c){
        paintcolor=c;
        paint.setColor(paintcolor);
        paint.setStrokeWidth(paintsize);
    }

    public void eraser(){
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(50);
    }

    public void brush(){
        paint.setColor(paintcolor);
        paint.setStrokeWidth(40);
    }

    public void clear(){
        pathlist.clear();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        paint.setColor(paintcolor);
        paint.setStrokeWidth(paintsize);
        invalidate();
    }

    public void drawcircle(){
        paint.setColor(paintcolor);
        paint.setStrokeWidth(paintsize);
        touch_num=1;
        special=true;
    }

    public void drawline(){
        paint.setColor(paintcolor);
        paint.setStrokeWidth(paintsize);
        touch_num=2;
        special=true;
    }

    public void undo(){
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if(pathlist.size()>0){
            pathlist.remove(pathlist.size()-1);
            Iterator<Path> it=pathlist.iterator();
            while(it.hasNext()){
                path=it.next();
                canvas.drawPath(path,paint);
            }
        }
        invalidate();
    }


}
