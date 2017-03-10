package view;

import android.content.Context;
import android.util.AttributeSet;

import io.vov.vitamio.widget.VideoView;

/**
 * Created by Administrator on 2017/2/21.
 */

public class MyVideoView extends VideoView {

    int defaultWidth=1280;
    int defaultHeight=720;

    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=getDefaultSize(defaultWidth,widthMeasureSpec);
        int height=getDefaultSize(defaultHeight,heightMeasureSpec);
        setMeasuredDimension(width,height);

    }
}
