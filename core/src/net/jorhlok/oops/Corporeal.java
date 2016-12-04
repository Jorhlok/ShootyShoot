package net.jorhlok.oops;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * An entity that exists in the worldspace. Not subject to physics.
 * @author Jorhlok
 */
abstract public class Corporeal extends Entity {
    public Rectangle AABB; //relative to the bottom left corner of the sprite
    public Vector2 Position; //in world space
    public float Rotation; //counterclockwise
    public Vector2 Velocity; //world units per second
    public Array<String> CollidesWith; //types of corporeal entities this can collide with
}
