package com.game.particles;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLCartesianLines {
    private FloatBuffer mRedVertBuffer;
    private FloatBuffer mBlueVertBuffer;
    private FloatBuffer mGreenVertBuffer;
    
	public GLCartesianLines() {
		float redLine[] = {
				0.0f, 0.0f, 0.0f,
				100.0f, 0.0f, 0.0f
		};
		
		float greenLine[] = {
				0.0f, 0.0f, 0.0f,
				0.0f, 100.0f, 0.0f
		};
		
		float blueLine[] = {
				0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 100.0f
		};
		
		mRedVertBuffer = makeFloatBuffer(redLine);
		mBlueVertBuffer = makeFloatBuffer(greenLine);
		mGreenVertBuffer = makeFloatBuffer(blueLine);
	}

    public FloatBuffer makeFloatBuffer(float[] vertices) {
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(vertices);
        fb.position(0);
        
        return fb;
    }

    public ByteBuffer makeByteBuffer(byte[] indices) {
        ByteBuffer bb = ByteBuffer.allocateDirect(indices.length);
        bb.order(ByteOrder.nativeOrder());
        
		bb.put(indices);
		bb.position(0);        
        
        return bb;
    }	
	 
    public void draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mRedVertBuffer);
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_LINES, 0, 2);
		
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mBlueVertBuffer);
        gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_LINES, 0, 2);
        
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mGreenVertBuffer);
        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_LINES, 0, 2);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
