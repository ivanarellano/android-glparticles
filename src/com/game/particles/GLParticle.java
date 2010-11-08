package com.game.particles;

public class GLParticle extends GLSprite {
	public float life;
	public float brightness;
	
	// Color
	public float r;
	public float g;
	public float b;
	
	public GLParticle(float newx, float newy, float newz, int resourceId) {
        super(resourceId);
        
        pos.x = newx;
        pos.y = newy;
        pos.z = newz;
    }

}
