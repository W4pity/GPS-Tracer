package com.example.w4pity.gpstracer;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by W4pity on 20/04/2016.
 */
public class CustomView extends View {

    public CustomView(Context c) {
        super(c);

    }

    public CustomView(Context c, AttributeSet as) {
        super(c, as);

        init();


    }

    // constructor that take in a context, attribute set and also a default
// style in case the view is to be styled in a certian way
    public CustomView(Context c, AttributeSet as, int default_style) {
        super(c, as, default_style);

    }

    // refactored init method as most of this code is shared by all the
// constructors
    private void init() {

    }

    public void onDraw(Canvas canvas) {
// call the superclass method
        super.onDraw(canvas);
    }


    public boolean onTouchEvent(MotionEvent event) {
     return true;
    }
}
