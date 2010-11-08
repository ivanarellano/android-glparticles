package com.game.particles;

/** 
 * Base class defining the core set of information necessary to render (and move
 * an object on the screen.  This is an abstract type and must be derived to
 * add methods to actually draw (see CanvasSprite and GLSprite).
 */
public abstract class Renderable
{
    // Position.
    public Vector3 pos = new Vector3();
    
    // Velocity.
    public Vector3 vel = new Vector3();
    
    // Size.
    public float width;
    public float height;
}