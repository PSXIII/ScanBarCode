package com.pisek.scanbarcode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;

import me.dm7.barcodescanner.core.ViewFinderView;

public class CustomScannerDrawer extends ViewFinderView {
    private final Paint drawContent = new Paint();
    private final Paint markLine = new Paint();
    private String message = "";
    private int textSize = 20;
    private boolean isMarkLine = false;

    public CustomScannerDrawer(Context context) {
        super(context);
        init();
    }

    public CustomScannerDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        drawContent.setColor(Color.WHITE);
        drawContent.setAntiAlias(true);
        float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                textSize, getResources().getDisplayMetrics());
        drawContent.setTextSize(textPixelSize);

        markLine.setColor(Color.RED);
        markLine.setStrokeWidth(5);
        setSquareViewFinder(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMessage(canvas);
    }

    private void drawMessage(Canvas canvas) {
        Rect framingRect = getFramingRect();
        float messageTop;
        float messageLeft;

        Rect textLength = new Rect();
        drawContent.getTextBounds(message, 0, message.length(), textLength);

        if (framingRect != null) {
            messageTop = framingRect.bottom + drawContent.getTextSize() + 150;
            messageLeft = framingRect.centerX() - (textLength.width() / 2);
        } else {
            messageTop = 10;
            messageLeft = (canvas.getWidth() / 2) - (textLength.width() / 2);
        }
        canvas.drawText(message, messageLeft, messageTop, drawContent);

        if (isMarkLine)
            canvas.drawLine(150, canvas.getHeight() / 2, canvas.getWidth() - 150, canvas.getHeight() / 2, markLine);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMarkLine(boolean isMarkLine) {
        this.isMarkLine = isMarkLine;
    }
}
