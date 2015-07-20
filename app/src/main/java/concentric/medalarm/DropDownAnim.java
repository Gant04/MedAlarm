package concentric.medalarm;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by MatthewAry on 7/16/2015.
 */
public class DropDownAnim extends Animation {
    private final int targetHeight;
    private final View view;
    private final boolean down;

    public DropDownAnim(View view, int targetHeight, boolean down) {
        this.view = view;
        this.targetHeight = targetHeight;
        this.down = down;
    }

    /**
     * The Apply Transformation
     *
     * @param interpolatedTime takes a float interpolatedTime
     * @param t                takes a Transformation
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight;
        if (down) {
            newHeight = (int) (targetHeight * interpolatedTime);
        } else {
            newHeight = (int) (targetHeight * (1 - interpolatedTime));
        }
        view.getLayoutParams().height = newHeight;
        view.requestLayout();
    }

    /**
     * The Initialized
     *
     * @param width        takes int width
     * @param height       takes int height
     * @param parentWidth  takes int parentWidth
     * @param parentHeight takes int parentHeight
     */
    @Override
    public void initialize(int width, int height, int parentWidth,
                           int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
