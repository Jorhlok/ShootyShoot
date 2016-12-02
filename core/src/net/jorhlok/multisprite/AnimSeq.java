package net.jorhlok.multisprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.Map;

/**
 *
 * @author Jorhlok
 */
public class AnimSeq {
    //input
    public String Name;
    public String[] FrameNames;
    public float FrameTime;
    public Animation.PlayMode PlayMode;
    
    //generated
    public Sprite[] Sprites;
    public Array<TextureRegion> Frames;
    public Animation Anim;
    
    public AnimSeq(String key, String[] frames, float speed, Animation.PlayMode mode) {
        Name = key;
        FrameNames = frames;
        FrameTime = speed;
        PlayMode = mode;
    }
    
    public void Generate(Map<String,Sprite> map) {
        if (map == null) {
            Sprites = null;
            Anim = null;
            return;
        }
        Sprites = new Sprite[FrameNames.length];
        Frames = new Array();
        for (int i=0; i<FrameNames.length; ++i) {
            Sprites[i] = map.get(FrameNames[i]);
            Frames.add( (Sprites[i]==null)?null:Sprites[i].Data );
        }
        Anim = new Animation(FrameTime,Frames,PlayMode);
    }
    
    public TextureRegion getKeyFrame(float StateTime) {
        if (Anim == null) return null;
        return Anim.getKeyFrame(StateTime);
    }
}
