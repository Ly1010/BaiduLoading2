package baiduloading2.ly.com.baiduloading2;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BaseInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.android.internal.view.animation.NativeInterpolatorFactory;
import com.android.internal.view.animation.NativeInterpolatorFactoryHelper;

public class ParabolicView extends ViewGroup{

    private int width;
    private int height;
    private int nowH = 0;
    private int time = 1000;
    private float beforeTime = 0;

    public ParabolicView(Context context) {
        this(context, null);
    }

    public ParabolicView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParabolicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        ImageView leftImg = new ImageView(context);
        leftImg.setBackground(getResources().getDrawable(R.drawable.leftcircle));
        addView(leftImg);
        ImageView centerImg = new ImageView(context);
        centerImg.setBackground(getResources().getDrawable(R.drawable.centercircle));
        addView(centerImg);
        ImageView rightImg = new ImageView(context);
        rightImg.setBackground(getResources().getDrawable(R.drawable.rightcircle));
        addView(rightImg);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(0, widthMeasureSpec);
        height = getDefaultSize(0, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View left = getChildAt(0);
        left.layout(width / 2 - height / 2 - nowH, 0, width / 2 + height / 2 - nowH, height);
        View center = getChildAt(1);
        center.layout(width / 2 - height / 2, 0, width / 2 + height / 2, height);
        View right = getChildAt(2);
        right.layout(width / 2 - height / 2 + nowH, 0, width / 2 + height / 2 + nowH, height);
    }

    public static int getDefaultSize(int size, int measureSpec){
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode){
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    public void startAnim(){
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ParabolicEvaluator(), 0, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                nowH = (Integer) animation.getAnimatedValue();
                requestLayout();
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(time);
        valueAnimator.setRepeatCount(Animation.INFINITE);
        valueAnimator.start();
    }

    public class ParabolicEvaluator implements TypeEvaluator<Integer> {

        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {

            int r = width / 2 - height / 2;//圆半径
            double v0 = Math.PI * r * 2;//匀速速度
            double c = v0 * fraction;//圆弧长
            double a = (c / r);//弧度
            int h = (int)(r * Math.sin(a));

            if((fraction >= 0.5 && beforeTime <= 0.5) || (beforeTime > 0.9 && fraction > 0)){

                View left = getChildAt(0);
                View center = getChildAt(1);
                View right = getChildAt(2);
                Drawable leftD = left.getBackground();
                Drawable centerD = center.getBackground();
                Drawable rightD = right.getBackground();
                left.setBackground(centerD);
                center.setBackground(rightD);
                right.setBackground(leftD);
            }

            beforeTime = fraction;
            return h;
        }
    }
}
