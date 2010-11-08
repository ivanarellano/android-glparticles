package com.game.particles;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;
//import javax.microedition.khronos.opengles.GL11Ext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class GLSprite extends Renderable {
    // The OpenGL ES texture handle to draw.
    public int mTextureName;
    
    // The id of the original resource that mTextureName is based on.
    public int mResourceId;
	
    // Texture pointer
	private int[] textures = new int[1];    
    
    public GLSprite(int resourceId) {
        super();
        
        mResourceId = resourceId;
    }
    
	/**
	 * Load the textures
	 * 
	 * @param gl - The GL Context
	 * @param context - The Activity context
	 * @param resourceID - The texture from the resource directory
	 */
	public int loadGLTexture(GL10 gl, Context context) {
        if (context != null && gl != null) {
			InputStream is = context.getResources().openRawResource(mResourceId);
			Bitmap bitmap = null;
			
			try {
				bitmap = BitmapFactory.decodeStream(is);
	
			} finally {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
				}
			}
	
			gl.glGenTextures(1, textures, 0);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
			
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
			
			//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
			
			bitmap.recycle();
        }
        
        mTextureName = textures[0];
        
        return mTextureName;
	}
    
	/*
    public void draw(GL10 gl) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureName);
        
        // Draw using the DrawTexture extension.
        ((GL11Ext) gl).glDrawTexfOES(pos.x, pos.y, pos.z, width, height);
    }
    */
}