package net.jorhlok.multisprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
    public Map<Character,TextureRegion> Letters;
    
    public Vector2 Scale;
    public Vector2 CamPos;
    public Batch MyBatch;
    public BitmapFont Font; //assumes monospace font
    public String DrawableChars;
    public float TabLength = 4;
    
    public MultiSpriteRegister() {
        Image = new HashMap<String,TexGrid>();
        Frame = new HashMap<String,Sprite>();
        Anim = new HashMap<String,AnimSeq>();
        Letters = new HashMap<Character,TextureRegion>();
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
        for (Sprite s : Frame.values()) {
            s.Generate(Image);
            newAnim("_" + s.Name, new String[] { s.Name },0,null); //auto generate single frame animation for each frame
        }
        for (AnimSeq a : Anim.values())
            a.Generate(Frame);
        if (Font != null && DrawableChars != null && !DrawableChars.isEmpty()) {
            BitmapFont.BitmapFontData dat = Font.getData();
            for (int i=0; i<DrawableChars.length(); ++i) {
                BitmapFont.Glyph glyph = dat.getGlyph(DrawableChars.charAt(i));
                if (glyph != null) {
                    Letters.put(DrawableChars.charAt(i), new TextureRegion(Font.getRegion(glyph.page).getTexture(),glyph.u,glyph.v,glyph.u2,glyph.v2));
                }
            }
        }
    }
    
    public void drawRegion(TextureRegion reg, float x, float y, float sw, float sh, float rot) {
        //For some reson was drawing with width and height swapped and rotated 90 degrees when done normally
        try {
            MyBatch.draw(reg, x, y
                    , 0, 0
                    , reg.getRegionHeight()*Scale.y*sh, reg.getRegionWidth()*Scale.x*sw
                    , 1, 1
                    , rot-90, false);
        } catch (Exception e) {
            //nothing
//            System.err.println("Error drawing: " + e.toString()); //debug
        }
    }
    
    public void draw(String anim, float statetime, float x, float y) {
        draw(anim,statetime,x,y,1,1,0);
    }
    
    public void draw(String anim, float statetime, float x, float y, float sw, float sh) {
        draw(anim,statetime,x,y,sw,sh,0);
    }
    
    public void draw(String anim, float statetime, float x, float y, float sw, float sh, float rot) {
        try {
            TextureRegion reg = Anim.get(anim).getKeyFrame(statetime);
            x += reg.getRegionHeight()*Scale.y*sh*Math.sin( Math.toRadians(-1*rot) );
            y += reg.getRegionHeight()*Scale.y*sh*Math.cos( Math.toRadians(-1*rot) );
            drawRegion(reg,x,y,sw,sh,rot);
        } catch (Exception e) {
            //nothing
        }
    }
    
    public void drawString(String str, float x, float y) {
        drawString(str,x,y,1,1,0);
    }
    
    public void drawString(String str, float x, float y, float sw, float sh) {
        drawString(str,x,y,sw,sh,0);
    }
    
    public void drawString(String str, float x, float y, float sw, float sh, float rot) {
        x /= 2;
        y /= 2;
        float w = Font.getSpaceWidth();
        float h = Font.getLineHeight();
        x += h*Scale.y*sh*0.5f*Math.sin( Math.toRadians(-1*rot) );
        y += h*Scale.y*sh*0.5f*Math.cos( Math.toRadians(-1*rot) );
        double xtrav = w*Math.cos( Math.toRadians(rot) )*sw*Scale.x;
        double ytrav = w*Math.sin( Math.toRadians(rot) )*sw*Scale.y;
        double xjmp = h*Math.sin( Math.toRadians(rot+180) )*sh*Scale.x;
        double yjmp = h*Math.cos( Math.toRadians(rot+180) )*sh*Scale.y;
        
        double xcur = x;
        double ycur = y;
        int linesdown = 0;
        for (int i=0; i<str.length(); ++i) {
            char c = str.charAt(i);
            switch (c) {
                case '\n':
                    ++linesdown;
                    xcur = x-xjmp*linesdown;
                    ycur = y+yjmp*linesdown;
                    break;
                case '\t':
                    xcur += xtrav*TabLength;
                    ycur += ytrav*TabLength;
                    break;
                default:
                    TextureRegion reg = Letters.get(c);
                    double xoff = Font.getData().getGlyph(c).xoffset/w;
                    double yoff = Font.getData().getGlyph(c).yoffset/h;
                    if (reg != null) drawRegion(reg, x + (float)(xcur + xoff*xtrav + yoff*xjmp)
                            , y + (float)(ycur + xoff*ytrav - yoff*yjmp)
                            , sw, sh*-1, rot);
                    xcur += xtrav;
                    ycur += ytrav;
            }
        }
    }
    
    public void dispose() {
        Font.dispose();
        for (TexGrid t : Image.values())
            t.dispose();
    }
}
