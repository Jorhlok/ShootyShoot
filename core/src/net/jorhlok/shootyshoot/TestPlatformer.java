package net.jorhlok.shootyshoot;

import com.badlogic.gdx.math.Vector2;
import net.jorhlok.multiav.MultiAVRegister;
import net.jorhlok.oops.Physical;
import net.jorhlok.oops.Postage;
import net.jorhlok.oops.TMPCO;

public class TestPlatformer extends Physical {
    public Vector2 Gravity;
    public boolean Grounded;
    
    protected boolean jump;
    protected boolean left;
    protected boolean right;
    
    public TestPlatformer() {
        AABB.set(0,0,1,1);
        Tolerance.set(0.5f, 0.5f);
        Position.set(5, 8);
        Gravity = new Vector2 (0, -10f);
    }
    
    @Override
    public void prestep(float deltatime) {
        
        Velocity.add(Gravity.cpy().scl(deltatime));
        
        for (Postage p : Mailbox) {
            if (p.Type.equals("control")) {
                if (p.Name.equals("jump")) {
                    boolean oldjump = jump;
                    jump = p.iValue != 0;
                    if (/*Grounded &&*/ jump && !oldjump) {
                        //jump!
                        Velocity.add(0, 16);
                        if (Velocity.y > 16) Velocity.y = 16;
                        Grounded = false;
                    }
                } else if (p.Name.equals("left")) {
                    left = p.iValue != 0;
                } else if (p.Name.equals("right")) {
                    right = p.iValue != 0;
                }
                
                if (left && !right) Velocity.x = -6;
                else if (right && !left) Velocity.x = 6;
                else Velocity.x = 0;
            }
        }
    }
    
    @Override
    public void step(float deltatime) {
        int len = CollisionTiles.size();
        //ignore non-tiles
        for (int i=0; i<len; ++i) {
            TMPCO tmp = CollisionTiles.poll();
            if (tmp != null && tmp.cell != null) {
                CollisionTiles.add(tmp);
            }
        }
    }
    
    @Override
    public void poststep(float deltatime) {
        if (Velocity.y < 0) Grounded = false;
        else if (Velocity.y == 0 ) Grounded = true;
    }
    
    @Override
    public void draw(MultiAVRegister mav) {
        mav.draw("_gunrt", 0, Position.x+0.75f, Position.y);
        mav.draw("_guyrt", 0, Position.x, Position.y);
    }
    
    
}
