package net.jorhlok.multisprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.HashMap;
import java.util.Map;

/**
 * Multi-Function Sprite Organization Engine
 * @author Jorhlok
 */
public class MultiSpriteRegister {
    public Map<String,TexGrid> Image;
    public Map<String,Sprite> Frame;
    public Map<String,AnimSeq> Anim;
    
    public Batch MyBatch;
    
    public MultiSpriteRegister() {
        Image = new HashMap<String,TexGrid>();
        Frame = new HashMap<String,Sprite>();
        Anim = new HashMap<String,AnimSeq>();
    }
    
    public void newImage(String key, String uri, int tw, int th) {
        TexGrid i = Image.get(key);
        if (i != null) i.dispose();
        Image.put(key, new TexGrid(key,uri,tw,th));
    }
    
    public void newSprite(String key, String img, int tx, int ty, int tw, int th, boolean hf, boolean vf) {
        Frame.put(key, new Sprite(key,img,tx,ty,tw,th,hf,vf));
    }
    
    public void newAnim(String key, String[] frames, float speed, Animation.PlayMode mode) {
        Anim.put(key, new AnimSeq(key,frames,speed,mode));
    }
    
    public void Generate() {
        for (Sprite s : Frame.values())
            s.Generate(Image);
        for (AnimSeq a : Anim.values())
            a.Generate(Frame);
    }
    
    public void draw(String anim, float statetime, float x, float y) {
        draw(anim,statetime,x,y,1,1,0,0.5f,0.5f);
    }
    
    public void draw(String anim, float statetime, float x, float y, float sw, float sh) {
        draw(anim,statetime,x,y,sw,sh,0,0.5f,0.5f);
    }
    
    public void draw(String anim, float statetime, float x, float y, float sw, float sh, float rot) {
        draw(anim,statetime,x,y,sw,sh,rot,0.5f,0.5f);
    } 
    
    public void draw(String anim, float statetime, float x, float y, float sw, float sh, float rot, float cx, float cy) {
        try {
            TextureRegion reg = Anim.get(anim).getKeyFrame(statetime);
            MyBatch.draw(reg, x, y, reg.getRegionWidth()*cx, reg.getRegionHeight()*cy, reg.getRegionWidth(), reg.getRegionHeight(), sw, sh, rot, false);
        } catch (Exception e) {
            //nothing
        }
    }
    
    public void dispose() {
        for (TexGrid t : Image.values())
            t.dispose();
    }
}
