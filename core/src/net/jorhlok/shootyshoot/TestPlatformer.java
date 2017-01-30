package net.jorhlok.shootyshoot;

import com.badlogic.gdx.math.Vector2;
import net.jorhlok.multiav.MultiAVRegister;
import net.jorhlok.oops.Physical;
import net.jorhlok.oops.TMPCO;

public class TestPlatformer extends Physical {
    public Vector2 Gravity;
    
    public TestPlatformer() {
        AABB.set(0,0,1,1);
        Tolerance.set(0.5f, 0.5f);
        Position.set(5, 8);
        Gravity = new Vector2 (0, -1f);
        Velocity.set(3f, -2f);
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
    public void draw(MultiAVRegister mav) {
        mav.draw("_gunrt", 0, Position.x+0.75f, Position.y);
        mav.draw("_guyrt", 0, Position.x, Position.y);
    }
    
    
}
