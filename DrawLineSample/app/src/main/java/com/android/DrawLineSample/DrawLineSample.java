package com.android.DrawLineSample;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaCodecInfo;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class DrawLineSample extends AppCompatActivity implements View.OnClickListener {

    /* 下拉模块*/
    private TextView colorview,sizeview,toolview;
    /** popup窗口里的ListView */
    private ListView colorlistview,sizelistview,toollistview;
    /** popup窗口 */
    private PopupWindow colorselect,sizeselect,toolselect;
    /** 模拟的假数据 */
    private List<String> colors,size,tools;
    /** 数据适配器 */
    private ArrayAdapter<String> colorsAdapter,sizeAdapter,toolAdapter;



    private TestView testview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        InitListview();
        testview =(TestView) findViewById(R.id.iv);
        //initview();
        //InitPaint();



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.color:
                setColorPopup();
                if(!colorselect.isShowing()){
                    //Toast.makeText(this,"1234567",Toast.LENGTH_SHORT).show();
                    colorselect.showAsDropDown(colorview,0,5);
                }
                break;
            case R.id.size:
                setSizePopup();
                if(!sizeselect.isShowing()){
                    sizeselect.showAsDropDown(sizeview,0,5);
                }
                break;
            case R.id.tool:
                setToolPopup();
                if(!toolselect.isShowing()){
                    toolselect.showAsDropDown(toolview,0,5);
                }
        }
    }


    private void InitListview(){
        colorview=(TextView) findViewById(R.id.color);
        colorview.setOnClickListener(this);
        colors=new ArrayList<String>();
        String[] c={"黑","红","绿","蓝"};
        for(String i:c){
            colors.add(i);
        }

        sizeview=(TextView)findViewById(R.id.size);
        sizeview.setOnClickListener(this);
        size=new ArrayList<String>();
        for(int i=1;i<=10;i++){
            size.add(String.valueOf(i)+"号");
        }

        toolview=(TextView)findViewById(R.id.tool);
        toolview.setOnClickListener(this);
        tools=new ArrayList<String>();
        String[]t={"橡皮","刷子","清除","圆形","直线","撤销"};
        for(String i:t){
            tools.add(i);
        }

    }
    
    private void setColorPopup(){
        colorlistview=new ListView(this);
        colorsAdapter=new ArrayAdapter<String>(this,R.layout.textitem,colors);
        colorlistview.setAdapter(colorsAdapter);
        colorlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String c=colors.get(i);
                colorview.setText(c);
                switch (i){
                    case 0:
                        testview.setcolor(Color.BLACK);
                        break;
                    case 1:
                        testview.setcolor(Color.RED);
                        break;
                    case 2:
                        testview.setcolor(Color.GREEN);
                        break;
                    case 3:
                        testview.setcolor(Color.BLUE);
                        break;
                }
                colorselect.dismiss();
            }
        });
        colorselect=new PopupWindow(colorlistview,colorview.getWidth(),ActionBar.LayoutParams.WRAP_CONTENT,true);
        Drawable drawable= ContextCompat.getDrawable(this,R.drawable.bg_cornor);
        colorselect.setBackgroundDrawable(drawable);
        colorselect.setFocusable(true);
        colorselect.setOutsideTouchable(true);

        colorselect.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭popup窗口
                colorselect.dismiss();
            }
        });
    }

    private void setSizePopup(){

        sizelistview=new ListView(this);
        sizeAdapter=new ArrayAdapter<String>(this,R.layout.textitem,size);
        sizelistview.setAdapter(sizeAdapter);
        sizelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s=size.get(i);
                sizeview.setText(s);
                //paint.setStrokeWidth(i+1);
                testview.setSize(i+1);

                sizeselect.dismiss();
            }
        });

        sizeselect=new PopupWindow(sizelistview,sizeview.getWidth(),ActionBar.LayoutParams.WRAP_CONTENT,true);
        Drawable drawable= ContextCompat.getDrawable(this,R.drawable.bg_cornor);
        sizeselect.setBackgroundDrawable(drawable);
        sizeselect.setFocusable(true);
        sizeselect.setOutsideTouchable(true);
        sizeselect.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭popup窗口
                sizeselect.dismiss();
            }
        });
    }

    private void setToolPopup(){
        toollistview =new ListView(this);
        toolAdapter=new ArrayAdapter<String>(this,R.layout.textitem,tools);
        toollistview.setAdapter(toolAdapter);
        toollistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String t=tools.get(i);
                switch (i){
                    case 0:
                        testview.eraser();
                        break;
                    case 1:
                        testview.brush();
                        break;
                    case 2:
                        testview.clear();
                      //  canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        break;
                    case 3:
                        testview.drawcircle();
                        break;
                    case 4:
                        testview.drawline();
                        break;
                    case 5:
                        testview.undo();
                        break;
                }
                toolselect.dismiss();
            }
        });
        toolselect=new PopupWindow(toollistview,toolview.getWidth(),ActionBar.LayoutParams.WRAP_CONTENT,true);
        Drawable drawable= ContextCompat.getDrawable(this,R.drawable.bg_cornor);
        toolselect.setBackgroundDrawable(drawable);
        toolselect.setFocusable(true);
        toolselect.setOutsideTouchable(true);
        toolselect.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭popup窗口
                toolselect.dismiss();
            }
        });
    }
}

