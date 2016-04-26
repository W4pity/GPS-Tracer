package com.example.w4pity.gpstracer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by W4pity on 20/04/2016.
 */
public class CustomView extends View {

    public boolean started = true;
    public float[] tabSpeed = new float[100];
    public int currentIndex = 0;
    Paint white = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint red = new Paint(Paint.ANTI_ALIAS_FLAG);
    int height;
    int width ;
    static int  maxSpeedView = 6;


    public CustomView(Context c) {
        super(c);

    }

    public CustomView(Context c, AttributeSet as) {
        super(c, as);

        init();
        height = getWidth();
        width =getHeight();
    }

    // constructor that take in a context, attribute set and also a default
// style in case the view is to be styled in a certian way
    public CustomView(Context c, AttributeSet as, int default_style) {
        super(c, as, default_style);

    }

    // refactored init method as most of this code is shared by all the
// constructors
    private void init() {
        white.setColor(0xFFFFFFFF);
        red.setColor(Color.RED);
    }
//draw

    
    public void onDraw(Canvas canvas) {


        super.onDraw(canvas);

        for(int i = 1; i<maxSpeedView; i++)
        {

            canvas.drawLine(0, i*getHeight()/maxSpeedView, getWidth(), i*getHeight()/maxSpeedView, white);
        }
        canvas.drawLine(0,getHeight()-(MainActivity.avSpeed/MainActivity.time*getHeight()/(maxSpeedView*10)),getWidth(),getHeight()-(MainActivity.avSpeed/MainActivity.time*getHeight()/(maxSpeedView*10)),red);


        //canvas.drawRect(new Rect(0,0,10,10), white);
        for(int i = 0; i<99; i++)
        {
            if(i == currentIndex-1)
            canvas.drawRect( i * getWidth()/100, getHeight()-(tabSpeed[i] * getHeight() / (maxSpeedView*10)),i * getWidth()/100+10, getHeight()- (tabSpeed[i] * getHeight() /(maxSpeedView*10))+10, white);
            //canvas.drawPoint(i * getWidth()/100, tabSpeed[i] * getHeight() / 60, white);
            if(i != currentIndex-1 && i != currentIndex && i != currentIndex+1 && i!= currentIndex+2)
            canvas.drawLine(i*getWidth()/100, getHeight()-(tabSpeed[i]*getHeight()/(maxSpeedView*10)), (i+1)*getWidth()/100, getHeight()-(tabSpeed[i+1]*getHeight()/(maxSpeedView*10)), white);
          //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<!<!<<<<<<<<<<!<<<<< <!<<<<<< canvas.drawLine();
        }
        invalidate();
    }


    public boolean onTouchEvent(MotionEvent event) {
     return true;
    }
}
