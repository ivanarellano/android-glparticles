package com.game.particles;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;

public class GLRenderer extends GLSurfaceView implements Renderer {
    public Context mContext;
    public GLParticleSystem mParticleSystem;
    public GLCartesianLines mCartesianLines;

	public static final float TOUCH_SCALE = 0.2f;
    public float mOldX;
    public float mOldY;
    
	// Rotation values
    public float mXRot;
    public float mYRot;

	public GLRenderer(Context context) {
		super(context);
		
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);

    	mContext = context;
    	mParticleSystem = new GLParticleSystem();
    	mCartesianLines = new GLCartesianLines();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {		
		mParticleSystem.mTexture1 = mParticleSystem.mParticles[0].loadGLTexture(gl, mContext);
		
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glClearDepthf(1.0f);		
		
		gl.glDisable(GL10.GL_DEPTH_TEST);
        gl.glDisable(GL10.GL_DITHER);
        gl.glDisable(GL10.GL_LIGHTING);
        
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		
	    gl.glEnable(GL10.GL_LINE_SMOOTH);
	    gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();		
		
        GLU.gluLookAt(	gl,
				0.0f, -10.0f, 15.0f,
				0.0f, 0.0f, 1.0f,
				0.0f, 1.0f, 0.0f);	
		
		gl.glRotatef(mXRot, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(mYRot, 0.0f, 1.0f, 0.0f);
		
		mCartesianLines.draw(gl);		 
        mParticleSystem.update();
        mParticleSystem.draw(gl);
	}		

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0)
			height = 1;

		// draw on the entire screen
		gl.glViewport(0, 0, width, height);
		
		// setup projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
        GLU.gluPerspective(gl, 30.0f, (float)width / (float)height, 1.0f, 100.0f);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_MOVE) {
        	float dx = x - mOldX;
	        float dy = y - mOldY;
	        
	        mXRot += dy * TOUCH_SCALE;
	        mYRot += dx * TOUCH_SCALE;
        }
        
        mOldX = x;
        mOldY = y;

		return true;
	}
}