package com.game.particles;

import android.content.Context;
import android.opengl.GLSurfaceView;

class GLView extends GLSurfaceView {
/*	public GLRenderer mRenderer;

	public final float TOUCH_SCALE = 0.2f;
    public float mOldX;
    public float mOldY;
*/
	public GLView(Context context) {
		super(context);
		/*
        mRenderer = new GLRenderer(context);
        setRenderer(mRenderer);
        */
    }
/*
	@Override
    public boolean onTouchEvent(final MotionEvent event) {
		final float x = event.getX();
        final float y = event.getY();
        
        queueEvent(new Runnable(){
            public void run() {
				switch(event.getAction()) {
					case MotionEvent.ACTION_MOVE:
						float dx = x - mOldX;
						float dy = y - mOldY;
		      		
						mRenderer.mXRot += dy * TOUCH_SCALE;
						mRenderer.mYRot += dx * TOUCH_SCALE;
		
						mOldX = x;
						mOldY = y;
					break;
				}
        }});

        return true;
	}
 */
}