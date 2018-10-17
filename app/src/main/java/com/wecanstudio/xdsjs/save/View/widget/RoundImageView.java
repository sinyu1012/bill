package com.wecanstudio.xdsjs.save.View.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆形头像
 *
 * @author XDSJS
 * @date 2015-09-22 00:41
 */
public class RoundImageView extends ImageView {

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            Drawable drawable = getDrawable();
            if (drawable == null) {
                setMeasuredDimension(0, 0);
            } else {
                int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
                int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
                if (measuredHeight == 0 && measuredWidth == 0) { // Height and
                    // width set
                    // to
                    // wrap_content
                    setMeasuredDimension(measuredWidth, measuredHeight);
                } else if (measuredHeight == 0) { // Height set to wrap_content
                    int width = measuredWidth;
                    int height = width * drawable.getIntrinsicHeight()
                            / drawable.getIntrinsicWidth();
                    setMeasuredDimension(width, height);
                } else if (measuredWidth == 0) { // Width set to wrap_content
                    int height = measuredHeight;
                    int width = height * drawable.getIntrinsicWidth()
                            / drawable.getIntrinsicHeight();
                    setMeasuredDimension(width, height);
                } else { // Width and height are explicitly set (either to
                    // match_parent or to exact value)
                    setMeasuredDimension(measuredWidth, measuredHeight);
                }
            }
        } catch (Exception e) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable != null) {
            if (getWidth() != 0 && getHeight() != 0) {
                // Draw image
                try {
                    Bitmap b;
                    try {
                        b = ((BitmapDrawable) drawable).getBitmap();
                    } catch (ClassCastException e) {
                        b = createWhitePaintCircle();
                    }
                    Bitmap bitmap = b.copy(Config.ARGB_8888, true);

                    Bitmap roundBitmap = getCroppedBitmap(bitmap, getWidth());
                    canvas.drawBitmap(roundBitmap, 0, 0, null);

                    canvas.save();
                    canvas.restore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * If the image is empty or is not set, display a white circle.
     *
     * @return
     */
    private Bitmap createWhitePaintCircle() {
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(),
                Config.ARGB_8888);
        Canvas _canvas = new Canvas(b);

        int color = Color.WHITE;

        Paint paint = new Paint();
        paint.setColor(color);

        _canvas.drawRect(rect, paint);
        return b;
    }

    /**
     * @param bmp    Bitmap to crop
     * @param radius Diameter of the image
     * @return
     */
    public Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp = scaleBitmap(bmp, radius, radius);

        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f,
                sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2.1f + 0.1f,
                paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }

    /**
     * Scale bitmap keep aspect-ratio
     *
     * @param original bitmap to scale
     * @param width
     * @param height
     * @return
     */
    private static Bitmap scaleBitmap(Bitmap original, int width, int height) {
        Bitmap background = Bitmap.createBitmap(width, height,
                Config.ARGB_4444);
        float originalWidth = original.getWidth();
        float originalHeight = original.getHeight();
        Canvas canvas = new Canvas(background);
        float scaleX = width / originalWidth;
        float scaleY = height / originalHeight;
        float xTranslation = (width - originalWidth * scaleX) / 2.0f;
        float yTranslation = (height - originalHeight * scaleY) / 1.0f;
        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation, yTranslation);
        transformation.preScale(scaleX, scaleY);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(original, transformation, paint);
        return background;
    }
}
