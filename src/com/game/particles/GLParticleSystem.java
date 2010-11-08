package com.game.particles;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class GLParticleSystem {
    public final int PARTICLECOUNT = 25;
    public float GRAVITY = 10.0f;
    public float PSIZE = 0.6f;
    
    public GLParticle[] mParticles;
    
    public FloatBuffer mVertexBuffer;
    public FloatBuffer mTextureBuffer;
    public ByteBuffer mIndexBuffer;
    
    public long mLastTime;
    public int col;
    public int delay;
    public Random mGen;
    
    public int mTexture1;

    public final float colors[][] =
    {
       {1.0f,0.5f,0.5f},{1.0f,0.75f,0.5f},{1.0f,1.0f,0.5f},{0.75f,1.0f,0.5f},
       {0.5f,1.0f,0.5f},{0.5f,1.0f,0.75f},{0.5f,1.0f,1.0f},{0.5f,0.75f,1.0f},
       {0.5f,0.5f,1.0f},{0.75f,0.5f,1.0f},{1.0f,0.5f,1.0f},{1.0f,0.5f,0.75f}
    };    
    
    public GLParticleSystem() {
        mParticles = new GLParticle[PARTICLECOUNT];
        mGen = new Random(System.currentTimeMillis());
        
        for (int i=0; i < PARTICLECOUNT; i++) {
            mParticles[i] = new GLParticle(mGen.nextFloat(), mGen.nextFloat(), mGen.nextFloat(), R.drawable.particle);
            
            mParticles[i].life = 1.0f;
            mParticles[i].brightness = (mGen.nextFloat() * 100.0f) / 700.0f + 0.003f;
            mParticles[i].r = colors[i * (12 / PARTICLECOUNT)][0];		// Select Red Rainbow Color
            mParticles[i].g = colors[i * (12 / PARTICLECOUNT)][1];		// Select Green Rainbow Color
            mParticles[i].b = colors[i * (12 / PARTICLECOUNT)][2];		// Select Blue Rainbow Color
        }

        float vertices[] = {
	    		-PSIZE,	-PSIZE,	1.0f,
	    		PSIZE,	-PSIZE,	1.0f,
	    		-PSIZE,	PSIZE,	1.0f,
	    		PSIZE,	PSIZE,	1.0f,
                };
        

        float textureUV[] = { 
        		0.0f, 0.0f,
        		0.0f, 1.0f,
        		1.0f, 0.0f,
        		1.0f, 1.0f
        		};
        
        byte indices[] = { 
        		0, 1, 3,
        		0, 3, 2
        		};

        mVertexBuffer = makeFloatBuffer(vertices);
        mTextureBuffer = makeFloatBuffer(textureUV);
        mIndexBuffer = makeByteBuffer(indices);
    }

    // use to make native order buffers
    public FloatBuffer makeFloatBuffer(float[] vertices) {
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(vertices);
        fb.position(0);
        
        return fb;
    }

    // use to make native order buffers
    public ByteBuffer makeByteBuffer(byte[] indices) {
        ByteBuffer bb = ByteBuffer.allocateDirect(indices.length);
        bb.order(ByteOrder.nativeOrder());
        
		bb.put(indices);
		bb.position(0);        
        
        return bb;
    }
    

    // update the particle system, move everything
    public void update()
    {
        // calculate time between frames in seconds
        long currentTime = System.currentTimeMillis();
        float timeFrame = (currentTime - mLastTime)/1000f;
        
        mLastTime = currentTime;

        // move the particles
        for (int i = 0; i < PARTICLECOUNT; i++) {
        	// apply a gravity to the z speed, in this case
        	mParticles[i].vel.y -= (GRAVITY * timeFrame);
	
        	// move the particle according to it's speed
        	mParticles[i].pos.x += mParticles[i].vel.x * timeFrame;
        	mParticles[i].pos.y += mParticles[i].vel.y * timeFrame;
        	mParticles[i].pos.z += mParticles[i].vel.z * timeFrame;
	            
        	// Reduce Particles Life By 'brightness'
        	mParticles[i].life -= mParticles[i].brightness;
	
        	// if the particle has died, respawn it
        	if (mParticles[i].life < 0.0f)
        		initParticle(i);
        }
    }
    
    private void initParticle(int i)
    {
        mParticles[i].life = 1.0f;
        mParticles[i].brightness = (mGen.nextFloat() * 100.0f) / 700.0f + 0.003f;
        
    	// loop through all the particles and create new instances of each one
    	mParticles[i].pos.x = 0f;
    	mParticles[i].pos.y = 0f;
    	mParticles[i].pos.z = 0f;
    	
    	// random x and z speed between -1.0 and 1.0
    	mParticles[i].vel.x = (mGen.nextFloat() * 2.0f) - 1.0f;
    	mParticles[i].vel.z = (mGen.nextFloat() * 2.0f) - 1.0f;
                
    	// random y speed between 4.0 and 7.0
    	mParticles[i].vel.y = (mGen.nextFloat() * 3.0f) + 4.0f;
    	
    	mParticles[i].r = colors[col][0];
    	mParticles[i].g = colors[col][1];
    	mParticles[i].b = colors[col][2];	
    }

    public void draw(GL10 gl) {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture1);
 
		//Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        
		//Set the face rotation
		gl.glFrontFace(GL10.GL_CCW);
        
        for (int i = 0; i < PARTICLECOUNT; i++) {
            gl.glPushMatrix();
            gl.glColor4f(mParticles[i].r, mParticles[i].g, mParticles[i].b, mParticles[i].life);
            gl.glTranslatef(mParticles[i].pos.x, mParticles[i].pos.y, mParticles[i].pos.z);
            gl.glDrawElements(GL10.GL_TRIANGLES, mIndexBuffer.capacity(), GL10.GL_UNSIGNED_BYTE, mIndexBuffer);
            gl.glPopMatrix();
        }
        
        gl.glDisable(GL10.GL_TEXTURE_2D);
        
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		if(delay > 25) {
			col++;
			delay = 0;
			
			if(col > 11)
				col=0;
		}
		
		delay++;
    }
    
}
