package net.jorhlok.oops;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An entity that exists in the worldspace. Not subject to physics.
 * @author Jorhlok
 */
public class Corporeal extends Entity {
    //properties
    public Rectangle AABB = new Rectangle(); //relative to the bottom left corner of the sprite
    public List<String> CollidesWith = new ArrayList<String>(); //types of corporeal entities this can collide with
    public boolean Physics = true;
    
    //runtime
    public Vector2 Position = new Vector2(); //in world space
    public float Rotation = 0; //counterclockwise in degrees
    public Vector2 Velocity = new Vector2(); //world units per second
    public Vector2 Friction = new Vector2(); //clamps velocity to zero
    public Queue<Corporeal> CollideQueue = new LinkedList<Corporeal>();
    
    @Override
    public void update(float deltatime) {
        prestep(deltatime);
        if (Physics) doCorporealPhysics(deltatime);
        step(deltatime);
        //collect collisions
        Maestro.CorporealCollisions(CollidesWith,CollideQueue,AABB);
        poststep(deltatime);
        //clear queues
        Mailbox.clear();
        CollideQueue.clear();
    }
    
    protected void doCorporealPhysics(float deltatime) {
        //apply velocity
        Position.add(Velocity.cpy().scl(deltatime));
        //apply friction
        if (Velocity.x > 0) {
            Velocity.x -= Friction.x*deltatime;
            if (Velocity.x < 0) Velocity.x = 0;
        }
        else if (Velocity.x < 0) {
            Velocity.x += Friction.x*deltatime;
            if (Velocity.x > 0) Velocity.x = 0;
        }
        if (Velocity.y > 0) {
            Velocity.y -= Friction.y*deltatime;
            if (Velocity.y < 0) Velocity.y = 0;
        }
        else if (Velocity.y < 0) {
            Velocity.y += Friction.y*deltatime;
            if (Velocity.y > 0) Velocity.y = 0;
        }
    }
}
