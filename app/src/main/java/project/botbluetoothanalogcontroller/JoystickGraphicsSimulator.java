package project.botbluetoothanalogcontroller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Observable;


public class JoystickGraphicsSimulator extends Observable {
    private static final float CIRCLE_RADIUS = 25;

    private Context context;
    private int drawableID;
    private ImageView imageView;
    private Bitmap workingBitmap;
    private Paint paint;
    private float radiusX;
    private float radiusY;
    private float width;
    private float height;
    private float radius;

    private AnalogCoordinates analogInfo;

    public JoystickGraphicsSimulator(Context context, int drawableID, final ImageView imageView) {
        this.context = context;
        this.drawableID = drawableID;
        this.imageView = imageView;

        init();

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    drawCircleInTheCenter();
                } else {
                    drawCircleWithCoefs(event.getX() / imageView.getWidth(), event.getY() / imageView.getHeight());
                }
                return false;
            }
        });
        imageView.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                return false;
            }
        });
        imageView.setOnGenericMotionListener(new View.OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View v, MotionEvent event) {
                return false;
            }
        });

        imageView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void init() {
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        myOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableID, myOptions);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);


        workingBitmap = Bitmap.createBitmap(bitmap);
        width = workingBitmap.getWidth();
        height = workingBitmap.getHeight();

        radiusX = width / 2;
        radiusY = height / 2;

        radius = (width < height) ? radiusX : radiusY;
        radius -= CIRCLE_RADIUS;

        drawCircleInTheCenter();
    }

    private void drawCircleInTheCenter() {
        drawCircle(radiusX, radiusY);
    }

    private void drawCircleWithCoefs(float xCoef, float yCoef) {
        drawCircle(xCoef * workingBitmap.getWidth(), yCoef * workingBitmap.getHeight());
    }

    private void drawCircle(float x, float y) {

        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(mutableBitmap);

        float xDiff = x - radiusX;
        float yDiff = y - radiusY;
        float currentRadius = (float) Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
        if (currentRadius > radius) {
            float coef = radius / currentRadius;
            x = radiusX + xDiff * coef;
            y = radiusY + yDiff * coef;
        }
        canvas.drawCircle(x, y, CIRCLE_RADIUS, paint);

        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(mutableBitmap);

        analogInfo = new AnalogCoordinates();
        analogInfo.setX((x - radiusX) / radius);
        analogInfo.setY((radiusY - y) / radius);

        super.setChanged();
        super.notifyObservers();
    }

    public AnalogCoordinates getAnalogInfo() {
        return analogInfo;
    }
}
