package com.game.particles;

import android.app.Activity;
import android.os.Bundle;

public class GLActivity extends Activity {
	private GLRenderer mGLRenderer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLRenderer = new GLRenderer(this);
        
        setContentView(mGLRenderer);
        mGLRenderer.requestFocus();
        mGLRenderer.setFocusableInTouchMode(true);
	}

    @Override
	protected void onPause() {
		super.onPause();
		mGLRenderer.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mGLRenderer.onResume();
	}
}