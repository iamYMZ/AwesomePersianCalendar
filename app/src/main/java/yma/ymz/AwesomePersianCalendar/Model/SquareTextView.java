package yma.ymz.AwesomePersianCalendar.Model;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class SquareTextView extends TextView {

    public SquareTextView(Context context) {
        super(context);
        init(context);
    }

    public SquareTextView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SquareTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        //setTextColor(Color.parseColor("#000000"));
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/yekan.ttf"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();
        int size = Math.max(width, height);
        int widthSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, heightSpec);
    }


}
