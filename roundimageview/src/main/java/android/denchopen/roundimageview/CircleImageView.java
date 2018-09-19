package android.denchopen.roundimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;


public class CircleImageView extends AppCompatImageView {
    private static final String TAG = "CircleImageView";
    private float radius;
    private float border;
    private int borderColor;

    public CircleImageView(Context context) {
        this(context, null, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        border = a.getDimension(R.styleable.CircleImageView_border, 0f);
        borderColor = a.getColor(R.styleable.CircleImageView_border_color, Color.WHITE);
        a.recycle();

//        setupView();
    }

//    private void setupView() {
//        Log.i(TAG, String.format("setupView: border = %f, borderColor = %d", border, borderColor));
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width != height) throw new RuntimeException("The width and height must be equal.");
        radius = width / 2 - border;
        Log.i(TAG, "onMeasure: radius = " + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        canvas.save();
        int width = getWidth();
        int height = getHeight();
        if (border > 0) {
            Paint borderPaint = new Paint();
            borderPaint.setAntiAlias(true);
            borderPaint.setColor(borderColor);
            canvas.drawCircle(width / 2, height / 2, width / 2, borderPaint);
        }

        Drawable drawable = getDrawable();
        if (drawable == null) return;
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap == null) return;
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = radius * 2 / Math.min(bitmap.getWidth(), bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        shader.setLocalMatrix(matrix);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        canvas.drawCircle(width / 2, height / 2, radius, paint);

        canvas.restore();

    }
}
