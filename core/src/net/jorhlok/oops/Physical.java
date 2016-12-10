package net.jorhlok.oops;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A physical object that can collide with terrain and have other physics.
 * @author Jorhlok
 */
public class Physical extends Corporeal {
    //properties
    public Vector2 Tolerance; //the extra space of each axis
    public boolean VerticalFirst; //order each axis is checked
    
    //runtime
    public byte CollisionFlags = 0; //0000UDLR
    public Queue<TMPCO> CollisionTiles = new LinkedList<TMPCO>();
    public Vector2 PrePosition = new Vector2(); //before simple physics
    public Vector2 PreVelocity = new Vector2();
    
    @Override
    public void update(float deltatime) {
        prestep(deltatime);
        if (Physics) {
            PrePosition.set(Position);
            PreVelocity.set(Velocity);
            doCorporealPhysics(deltatime);
            Rectangle aoi = new Rectangle(AABB).setPosition(AABB.x+Position.x, AABB.y+Position.y).merge( 
                    new Rectangle(AABB).setPosition(AABB.x+PrePosition.x, AABB.y+PrePosition.y) );
            //collect tiles from area of interest
            Maestro.PhysicalCollisions(CollisionTiles,aoi);
        }
        step(deltatime); //object specific tile checking
        if (Physics) {
            //do tile collisions
            int len = CollisionTiles.size();
            CollisionFlags = 0;
            boolean vCheck = VerticalFirst;
            Rectangle vRect = new Rectangle(AABB.x+Position.x + AABB.width*Tolerance.x/2, AABB.y+Position.y, 
                    AABB.width - AABB.width*Tolerance.x/2, AABB.height);
            Rectangle hRect = new Rectangle(AABB.x+Position.x, AABB.y+Position.y + AABB.height*Tolerance.y/2, 
                    AABB.width, AABB.height - AABB.height*Tolerance.y/2);
            for (int j=0; j<2; ++j) {
                for (int i=0; i<len; ++i){
                    TMPCO t = CollisionTiles.poll();
                    if (vCheck) {
                        
                    }
                    else {
                        
                    }
                    CollisionTiles.add(t);
                }
                vCheck = !vCheck;
            }
            //do entity collisions
            Maestro.CorporealCollisions(CollidesWith,CollideQueue,AABB);
        }
        poststep(deltatime);
        //clear queues
        Mailbox.clear();
        CollideQueue.clear();
        CollisionTiles.clear();
    }
    
    public boolean CollisionUp() {
        return (CollisionFlags&8) != 0;
    }
    
    public boolean CollisionDown() {
        return (CollisionFlags&4) != 0;
    }
    
    public boolean CollisionLeft() {
        return (CollisionFlags&2) != 0;
    }
    
    public boolean CollisionRight() {
        return (CollisionFlags&1) != 0;
    }
}
