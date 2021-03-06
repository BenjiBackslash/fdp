package com.fractureof.demos.location;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by hanan on 23/02/2016.
 */
public class RoundedAvatarImageView extends ImageView {
    RoundedAvatarDrawable mRoundedAvatarDrawable;

    public RoundedAvatarImageView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }
    public RoundedAvatarImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        setWillNotDraw(false);
        mRoundedAvatarDrawable =
                 new RoundedAvatarDrawable(
                         BitmapFactory.decodeResource(getResources(),R.drawable.partner_avatar_f)
                         );
    }
    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (drawable instanceof RoundedAvatarDrawable) {
            mRoundedAvatarDrawable = (RoundedAvatarDrawable) drawable;
            int w = mRoundedAvatarDrawable.getBitmap().getWidth();
            int h = mRoundedAvatarDrawable.getBitmap().getHeight();
            onSizeChanged(w,h,0,0);
        }

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRoundedAvatarDrawable.setBounds(0, 0, w, h);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        mRoundedAvatarDrawable.draw(canvas);
    }

}
