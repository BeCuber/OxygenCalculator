package com.example.calculadorao2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;

public class ArcSeekBar extends AppCompatSeekBar {
    private Paint arcPaint;
    private RectF arcRect;
    private Drawable thumbDrawable;
    private int thumbWidth, thumbHeight;

    public ArcSeekBar(Context context) {
        super(context);
        init();
    }

    public ArcSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArcSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(10); // Ajusta el ancho del arco según sea necesario
        arcPaint.setColor(ContextCompat.getColor(this.getContext(), android.R.color.black)); // Ajusta el color del arco según sea necesario

        arcRect = new RectF();

        thumbDrawable = getThumb(); // Obtén el drawable del thumb
        thumbWidth = thumbDrawable.getIntrinsicWidth();
        thumbHeight = thumbDrawable.getIntrinsicHeight();

        setPadding(thumbWidth / 2, thumbHeight / 2, thumbWidth / 2, thumbHeight / 2); // Asegúrate de que haya suficiente espacio para el thumb en todas las direcciones
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibuja el arco
        float startAngle = 135; // Ángulo de inicio del arco
        float sweepAngle = 270; // El sector que dibuja
        arcRect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        canvas.drawArc(arcRect, startAngle, sweepAngle, false, arcPaint);

        // Calcula la posición del thumb a lo largo del arco
        float progressRatio = (float) getProgress() / getMax();
        float thumbAngle = startAngle + (progressRatio * sweepAngle);
        float thumbX = arcRect.centerX() + (float) (arcRect.width() / 2 * Math.cos(Math.toRadians(thumbAngle)));
        float thumbY = arcRect.centerY() + (float) (arcRect.height() / 2 * Math.sin(Math.toRadians(thumbAngle)));

        // Dibuja el thumb en la posición calculada
        thumbDrawable.setBounds((int) (thumbX - thumbWidth / 2), (int) (thumbY - thumbHeight / 2),
                (int) (thumbX + thumbWidth / 2), (int) (thumbY + thumbHeight / 2));
        thumbDrawable.draw(canvas);
    }
}

