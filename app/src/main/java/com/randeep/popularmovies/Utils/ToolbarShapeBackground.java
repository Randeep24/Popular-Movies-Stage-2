package com.randeep.popularmovies.Utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by Randeeppulp on 9/5/17.
 */

public class ToolbarShapeBackground extends Shape {

    Path path;
    public ToolbarShapeBackground(){
        path = new Path();
    }
    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.TRANSPARENT);
        paint.setColor(Color.WHITE);

        path.moveTo(0,0);
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0,getHeight());
        path.lineTo(0,0);
        canvas.drawPath(path, paint);
    }
}
