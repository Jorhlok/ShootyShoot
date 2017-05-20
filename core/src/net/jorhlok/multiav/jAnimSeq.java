package net.jorhlok.multiav;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.Map;

/**
 *
 * @author Jorhlok
 */
public class jAnimSeq {
    //input
    protected String Name;
    protected String[] FrameNames;
    protected float FrameTime;
    protected Animation.PlayMode PlayMode;
    
    //generated
    protected Animation<TextureRegion> Anim;
    
    public jAnimSeq(String key, String[] frames, float speed, Animation.PlayMode mode) {
        Name = key;
        FrameNames = frames;
        FrameTime = speed;
        PlayMode = mode;
    }
    
    public String getName() {
        return Name;
    }
    
    public void Generate(Map<String,jSprFrame> map) {
        try {
            Array<TextureRegion> Frames = new Array();
            for (int i=0; i<FrameNames.length; ++i) {
                jSprFrame spr = map.get(FrameNames[i]);
                Frames.add( (spr==null)?null:spr.getReg() );
            }
            Anim = new Animation(FrameTime,Frames,PlayMode);
        } catch (Exception e) {
            Anim = null;
            //nothing
        }
    }
    
    public TextureRegion getKeyFrame(float StateTime) {
        if (Anim == null) return null;
        return Anim.getKeyFrame(StateTime);
    }
    
    public void dispose() {
        Anim = null;
    }
}