package android.denchopen.roundimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;


public class RoundImageView extends AppCompatImageView {
    private static final String TAG = "RoundImageView";
    private float radius;
    private float border;
    private int borderColor;

    public RoundImageView(Context context) {
        this(context, null, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0);
        radius = a.getDimension(R.styleable.RoundImageView_radius, 0f);
        border = a.getDimension(R.styleable.RoundImageView_border, 0f);
        borderColor = a.getColor(R.styleable.RoundImageView_border_color, Color.WHITE);
        a.recycle();

        setupView();
    }

    private void setupView() {
        Log.i(TAG, String.format("setupView: radius = %f, border = %f, borderColor = %d", radius, border, borderColor));
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (border > 0) {
            Paint borderPaint = new Paint();
            borderPaint.setAntiAlias(true);
            borderPaint.setColor(borderColor);
            canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, borderPaint);

        }
        Drawable drawable = getDrawable();
        if (drawable == null) return;
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap == null) return;
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        Matrix matrix = new Matrix();
        float scale = Math.min(((float) width) / bitmap.getWidth(), ((float) height) / bitmap.getHeight());
        matrix.setScale(scale, scale);
        shader.setLocalMatrix(matrix);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        canvas.drawRoundRect(new RectF(border, border, width - border, height - border), radius, radius, paint);
    }
}
