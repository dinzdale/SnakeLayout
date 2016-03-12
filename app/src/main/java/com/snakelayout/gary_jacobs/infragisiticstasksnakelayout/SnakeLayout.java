package com.snakelayout.gary_jacobs.infragisiticstasksnakelayout;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by gary jacobs on 3/8/16.
 */

public class SnakeLayout extends ViewGroup {
    private int deviceWidth;
    private int deviceHeight;


    public SnakeLayout(Context context) {
        super(context, null, 0);
    }

    public SnakeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnakeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

//    public SnakeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    public void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDimensions = new Point();
        display.getSize(deviceDimensions);
        deviceWidth = deviceDimensions.x;
        deviceHeight = deviceDimensions.y;
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (false) {
            layoutForLeftRight(changed, l, t, r, b);
        } else {
            layoutForRightLeft(changed, l, t, r, b);
        }
    }

    private void layoutForLeftRight(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int curWidth, curHeight, curLeft, curTop, maxHeight;

        //get the available size of child view
        final int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childWidth = childRight - childLeft;
        final int childHeight = childBottom - childTop;

        maxHeight = 0;
        curLeft = childLeft;
        curTop = childTop;

        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                return;

            //Get the maximum size of the child
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
            curWidth = child.getMeasuredWidth();
            curHeight = child.getMeasuredHeight();
            //wrap is reach to the end
            if (curLeft + curWidth >= childRight) {
                curLeft = childLeft;
                curTop += maxHeight;
                maxHeight = 0;
            }
            //do the layout
            child.layout(curLeft, curTop, curLeft + curWidth, curTop + curHeight);
            //store the max height
            if (maxHeight < curHeight)
                maxHeight = curHeight;
            curLeft += curWidth;
        }
    }

    private void layoutForRightLeft(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        //final int count  = 4;
        int curWidth, curHeight, curLeft, curRight, curTop, maxHeight;

        //get the available size of child view
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childTop = this.getPaddingTop();
        final int childLeft = this.getPaddingLeft();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childWidth = childLeft - childRight;
        final int childHeight = childBottom - childTop;

        maxHeight = 0;
        curRight = childLeft - childWidth;
        curTop = childTop;

        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                return;

            //Get the maximum size of the child
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
            curWidth = child.getMeasuredWidth();
            curHeight = child.getMeasuredHeight();
            //wrap is reach to the end
            if (curRight - curWidth > childLeft) {
                curRight -= curWidth;
                //curTop += maxHeight;
                //maxHeight = 0;
            }
            else {
                curRight = this.getMeasuredWidth() - this.getPaddingRight() - curWidth;
                curTop += maxHeight;
            }
            //do the layout
            child.layout(curRight, curTop, curRight+curWidth, curTop + curHeight);
            //store the max height
            if (maxHeight < curHeight)
                maxHeight = curHeight;
//            curRight -= curWidth;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        // Measurement will ultimately be computing these values.
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        int mLeftWidth = 0;
        int rowCount = 0;

        // Iterate through all children, measuring them and computing our dimensions
        // from their size.
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                continue;

            // Measure the child.
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            maxWidth += Math.max(maxWidth, child.getMeasuredWidth());
            mLeftWidth += child.getMeasuredWidth();

            if ((mLeftWidth / deviceWidth) > rowCount) {
                maxHeight += child.getMeasuredHeight();
                rowCount++;
            } else {
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
            }
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));

    }
}
