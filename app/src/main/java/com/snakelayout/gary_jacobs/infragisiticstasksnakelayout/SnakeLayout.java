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

/**
 * This class implements a top
 */
public class SnakeLayout extends ViewGroup {

    // Defines the wrapping direction for the child views when positioning the views
    // within the layout
    public static enum Orientation {
        WRAP_LEFT_TO_RIGHT(0), WRAP_RIGHT_TO_LEFT(1);
        int value;

        Orientation(int value) {
            this.value = value;
        }
    }

    private Orientation orientation = Orientation.WRAP_RIGHT_TO_LEFT;
    private int deviceWidth;

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


    public void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDimensions = new Point();
        display.getSize(deviceDimensions);
        deviceWidth = deviceDimensions.x;
        setOrientation(Orientation.WRAP_RIGHT_TO_LEFT);
    }


    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        requestLayout();
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void toggleOrientaton() {
        setOrientation(getOrientation() == Orientation.WRAP_LEFT_TO_RIGHT ? Orientation.WRAP_RIGHT_TO_LEFT : Orientation.WRAP_LEFT_TO_RIGHT);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        switch (getOrientation()) {
            case WRAP_LEFT_TO_RIGHT:
                layoutSnake(changed, l, t, r, b, true);
                break;
            case WRAP_RIGHT_TO_LEFT:
                layoutSnake(changed, l, t, r, b, false);
                break;
        }
    }


    private void layoutSnake(boolean changed, int l, int t, int r, int b, boolean orientation) {
        final int count = getChildCount();
        boolean leftToRight = orientation;
        int curWidth, curHeight, curLeft, curTop, maxHeight;

        //get the starting position of each child
        final int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childWidth = childRight - childLeft;
        final int childHeight = childBottom - childTop;

        maxHeight = 0;

        // initialize current position depending left to right/right to left orientation
        if (leftToRight) {
            curLeft = childLeft;
        } else {
            curLeft = childRight;
        }

        curTop = childTop;

        // For each child in the layout, determine it's proper layout position
        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                return;

            // Get the width and height of the next child
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
            curWidth = child.getMeasuredWidth();
            curHeight = child.getMeasuredHeight();

            // position this child view
            if (leftToRight) {
                // keep moving left to right until we run out of room
                if (curLeft + curWidth >= childRight) {
                    // no more room on this line, move child down and start moving right to left (snake!)
                    curLeft = childRight - curWidth;
                    // position top of this view below tallest view on previous line
                    curTop += maxHeight;
                    maxHeight = 0;
                    leftToRight = false;
                }
            } else {
                // keep moving right to left until we run out of room
                if (curLeft - curWidth < childLeft) {
                    // no more room on this line, move child down and start moving left to right again
                    curLeft = childLeft;
                    // position top of this view below tallest view on previous line
                    curTop += maxHeight;
                    maxHeight = 0;
                    leftToRight = true;
                }
            }
            //render this child view
            child.layout(curLeft, curTop, curLeft + curWidth, curTop + curHeight);

            //save the maximum height, or tallest height view in the current row
            if (maxHeight < curHeight)
                maxHeight = curHeight;

            // position the next view left position for the next child
            if (leftToRight) {
                curLeft += curWidth;
            } else {
                curLeft -= curWidth;
            }
        }
    }


    /**
     * System Callback requesting measurements of all children in this layout container
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
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
