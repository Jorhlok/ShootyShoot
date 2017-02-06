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
    public Vector2 Tolerance = new Vector2(); //the extra space of each axis
    public boolean VerticalFirst = true; //order each axis is checked
    
    //runtime
    public byte CollisionFlags = 0; //0000UDLR
    public Queue<TMPCO> CollisionTiles = new LinkedList<TMPCO>();
    public Vector2 PrePosition = new Vector2(); //before simple physics
    public Vector2 PreVelocity = new Vector2();
    
    @Override
    public void update(float deltatime) {
        prestep(deltatime); //anything beforehand
        
        if (Physics) {
            PrePosition.set(Position);
            PreVelocity.set(Velocity);
            doCorporealPhysics(deltatime);
            Rectangle projected = new Rectangle(AABB).setPosition(AABB.x+Position.x, AABB.y+Position.y);
            Rectangle aoi = projected.merge( 
                    new Rectangle(AABB).setPosition(AABB.x+PrePosition.x, AABB.y+PrePosition.y) );
            //collect tiles from area of interest
            Maestro.PhysicalCollisions(CollisionTiles,aoi,projected);
        }
        
        step(deltatime); //object specific tile checking
        
        if (Physics) {
            //do tile collisions
            int len = CollisionTiles.size();
            CollisionFlags = 0;
            Rectangle vRect = new Rectangle(AABB.x+Position.x + AABB.width*Tolerance.x/2, AABB.y+Position.y, 
                    AABB.width*Tolerance.x, AABB.height);
            Rectangle hRect = new Rectangle(AABB.x+Position.x, AABB.y+Position.y + AABB.height*Tolerance.y/2, 
                    AABB.width, AABB.height*Tolerance.y);
            
            //first pass
            for (int i=0; i<len; ++i){
                TMPCO t = CollisionTiles.poll();
                t.CollisionFlags = 0;
                if (t.AABB.overlaps(vRect)) {
                    //check if it's a bottom hit or a top hit
                    float tHi = t.AABB.y + t.AABB.height;
                    float pHi = vRect.y + vRect.height;
                    float up = pHi - tHi; 
                    float down = t.AABB.y - vRect.y;
                    if (Velocity.y < 0 || ( Velocity.y >= 0 && up >= 0 ) ) t.CollisionFlags |= 8;
                    if (Velocity.y > 0 || ( Velocity.y <= 0 && down >= 0 ) ) t.CollisionFlags |= 4;
                    if (!t.CollisionUp() && !t.CollisionDown()) t.CollisionFlags |= 8+4;
                    CollisionFlags |= t.CollisionFlags;
                }
                if (t.AABB.overlaps(hRect)) {
                    //check if it's a left hit or a right hit
                    float tRi = t.AABB.x + t.AABB.width;
                    float pRi = hRect.x + hRect.width;
                    float left =  t.AABB.x - hRect.x;
                    float right = pRi - tRi;
                    if (Velocity.x > 0 || ( Velocity.x <= 0 && left >= 0 ) ) t.CollisionFlags |= 2;
                    if (Velocity.x < 0 || ( Velocity.x >= 0 && right >= 0 ) ) t.CollisionFlags |= 1;
                    if (!t.CollisionLeft() && !t.CollisionRight()) t.CollisionFlags |= 2+1;
                    CollisionFlags |= t.CollisionFlags;
                }
                CollisionTiles.add(t);
            }
            
            //second pass
            for (int j=0; j<2; ++j) {
                len = CollisionTiles.size();
                for (int i=0; i<len; ++i) {
                    //this removes non-colliders and finds where things are pushing this
                    
                    TMPCO t = CollisionTiles.poll();
                    if (t.CollisionFlags != 0) {
                        CollisionTiles.add(t);
                        //adjust position if it still overlaps
                        if ( t.AABB.overlaps(new Rectangle(AABB).setPosition(Position.cpy().add(AABB.x, AABB.y))) ) {
                            if ( (j == 0 && VerticalFirst) || (j == 1 && !VerticalFirst) ) {
                                //vertical shunting
                                if (t.CollisionUp() && !t.CollisionDown()) {
                                    //top of block
                                    Position.y +=  t.AABB.y + t.AABB.height - (Position.y + AABB.y);
                                    Velocity.y = 0;
                                }
                                else if (t.CollisionDown() && !t.CollisionUp()) {
                                    //bottom of block
                                    Position.y -= Position.y + AABB.y + AABB.height - t.AABB.y;
                                    Velocity.y = 0;
                                }
                            }
                            else {
                                //horizontal shunting
                                if (t.CollisionRight() && !t.CollisionLeft()) {
                                    //right side of block
                                    Position.x +=  t.AABB.x + t.AABB.width - (Position.x + AABB.x);
                                    Velocity.x = 0;
                                }
                                else if (t.CollisionLeft() && !t.CollisionRight()) {
                                    //left side of block
                                    Position.x -= Position.x + AABB.x + AABB.width - t.AABB.x;
                                    Velocity.x = 0;
                                }
                            }
                        }
                    }
                }
            }
            //do entity collisions
            Maestro.CorporealCollisions(CollidesWith,CollideQueue,AABB);
        }
        
        poststep(deltatime); //real interaction goes in here
        
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
