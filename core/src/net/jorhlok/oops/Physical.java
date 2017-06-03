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

        if (getPhysics()) {
            PrePosition.set(getPosition());
            PreVelocity.set(getVelocity());
            doCorporealPhysics(deltatime);
            Rectangle projected = new Rectangle(getAABB()).setPosition(getAABB().x+ getPosition().x, getAABB().y+ getPosition().y);
            Rectangle aoi = projected.merge(
                    new Rectangle(getAABB()).setPosition(getAABB().x+PrePosition.x, getAABB().y+PrePosition.y) );
            //collect tiles from area of interest
            getMaestro().PhysicalCollisions(CollisionTiles,aoi,projected);
        }

        step(deltatime); //object specific tile checking

        if (getPhysics()) {
            //do tile collisions
            int len = CollisionTiles.size();
            CollisionFlags = 0;
            Rectangle vRect = new Rectangle(getAABB().x+ getPosition().x + getAABB().width*Tolerance.x/2, getAABB().y+ getPosition().y,
                    getAABB().width*Tolerance.x, getAABB().height);
            Rectangle hRect = new Rectangle(getAABB().x+ getPosition().x, getAABB().y+ getPosition().y + getAABB().height*Tolerance.y/2,
                    getAABB().width, getAABB().height*Tolerance.y);

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
                    if (getVelocity().y < 0 || ( getVelocity().y >= 0 && up >= 0 ) ) t.CollisionFlags |= 8;
                    if (getVelocity().y > 0 || ( getVelocity().y <= 0 && down >= 0 ) ) t.CollisionFlags |= 4;
                    if (!t.CollisionUp() && !t.CollisionDown()) t.CollisionFlags |= 8+4;
                    CollisionFlags |= t.CollisionFlags;
                }
                if (t.AABB.overlaps(hRect)) {
                    //check if it's a left hit or a right hit
                    float tRi = t.AABB.x + t.AABB.width;
                    float pRi = hRect.x + hRect.width;
                    float left =  t.AABB.x - hRect.x;
                    float right = pRi - tRi;
                    if (getVelocity().x > 0 || ( getVelocity().x <= 0 && left >= 0 ) ) t.CollisionFlags |= 2;
                    if (getVelocity().x < 0 || ( getVelocity().x >= 0 && right >= 0 ) ) t.CollisionFlags |= 1;
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
                        if ( t.AABB.overlaps(new Rectangle(getAABB()).setPosition(getPosition().cpy().add(getAABB().x, getAABB().y))) ) {
                            if ( (j == 0 && VerticalFirst) || (j == 1 && !VerticalFirst) ) {
                                //vertical shunting
                                if (t.CollisionUp() && !t.CollisionDown()) {
                                    //top of block
                                    getPosition().y +=  t.AABB.y + t.AABB.height - (getPosition().y + getAABB().y);
                                    getVelocity().y = 0;
                                }
                                else if (t.CollisionDown() && !t.CollisionUp()) {
                                    //bottom of block
                                    getPosition().y -= getPosition().y + getAABB().y + getAABB().height - t.AABB.y;
                                    getVelocity().y = 0;
                                }
                            }
                            else {
                                //horizontal shunting
                                if (t.CollisionRight() && !t.CollisionLeft()) {
                                    //right side of block
                                    getPosition().x +=  t.AABB.x + t.AABB.width - (getPosition().x + getAABB().x);
                                    getVelocity().x = 0;
                                }
                                else if (t.CollisionLeft() && !t.CollisionRight()) {
                                    //left side of block
                                    getPosition().x -= getPosition().x + getAABB().x + getAABB().width - t.AABB.x;
                                    getVelocity().x = 0;
                                }
                            }
                        }
                    }
                }
            }
            //do entity collisions
            getMaestro().CorporealCollisions(getCollidesWith(), getCollideQueue(), getAABB());
        }

        poststep(deltatime); //real interaction goes in here

        //clear queues
        getMailbox().clear();
        getCollideQueue().clear();
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
