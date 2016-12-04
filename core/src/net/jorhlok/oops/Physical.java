package net.jorhlok.oops;

import com.badlogic.gdx.math.Vector2;

/**
 * A physical object that can collide with terrain and have other physics.
 * @author Jorhlok
 */
abstract public class Physical extends Corporeal {
    public Vector2 Tolerance; //the extra space of each axis
    public boolean VerticalFirst; //order each axis is checked
    public Vector2 Gravity;
}
