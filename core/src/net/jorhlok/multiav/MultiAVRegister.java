package net.jorhlok.multiav;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.HashMap;
import java.util.Map;

/**
 * Multi-Function Audio-Visual Organization Engine
 * @author Jorhlok
 */
public class MultiAVRegister {
    protected Map<String,TexGrid> Image;
    protected Map<String,SprFrame> Frame;
    protected Map<String,AnimSeq> Anim;
    protected Map<Character,TextureRegion> Letters;
    protected Map<String,SEffect> SFX;
    protected Map<String,MTrack> Mus;
    protected BitmapFont Font; //assumes monospace font
    protected String DrawableChars;
    
    protected Batch MyBatch;
    protected Vector2 Scale;
    protected Vector2 CamPos;
    protected float TabLength = 4;
    protected float FontSampling = 1;
    
    public MultiAVRegister() {
        Image = new HashMap<String,TexGrid>();
        Frame = new HashMap<String,SprFrame>();
        Anim = new HashMap<String,AnimSeq>();
        Letters = new HashMap<Character,TextureRegion>();
        SFX = new HashMap<String,SEffect>();
        Mus = new HashMap<String,MTrack>();
    }
    
    public void newImage(String key, String uri, int tw, int th) {
        TexGrid i = Image.get(key);
        if (i != null) i.dispose();
        Image.put(key, new TexGrid(key,uri,tw,th));
    }
    
    public void newSprite(String key, String img, int tx, int ty, int tw, int th, boolean hf, boolean vf) {
        Frame.put(key, new SprFrame(key,img,tx,ty,tw,th,hf,vf));
    }
    
    public void newAnim(String key, String[] frames, float speed, Animation.PlayMode mode) {
        Anim.put(key, new AnimSeq(key,frames,speed,mode));
    }
    
    public void setFont(BitmapFont f, String chars) {
        if (Font != null) Font.dispose();
        Font = f;
        DrawableChars = chars;
    }
    
    public void newSFX(String key, String uri) {
        SEffect s = SFX.get(key);
        if (s != null) s.dispose();
        SFX.put(key, new SEffect(key,uri));
    }
    
    public void newMusic(String key, String uri) {
        MTrack m = Mus.get(key);
        if (m != null) m.dispose();
        Mus.put(key, new MTrack(key,uri) );
    }
    
    public void newMusic(String key, String intro, String body) {
        MTrack m = Mus.get(key);
        if (m != null) m.dispose();
        Mus.put(key, new MTrack(key,intro,body) );
    }

    public Batch getBatch() {
        return MyBatch;
    }

    public void setBatch(Batch MyBatch) {
        this.MyBatch = MyBatch;
    }

    public Vector2 getScale() {
        return Scale;
    }

    public void setScale(Vector2 Scale) {
        this.Scale = Scale;
    }

    public Vector2 getCamPos() {
        return CamPos;
    }

    public void setCamPos(Vector2 CamPos) {
        this.CamPos = CamPos;
    }

    public float getTabLength() {
        return TabLength;
    }

    public void setTabLength(float TabLength) {
        this.TabLength = TabLength;
    }

    public float getFontSampling() {
        return FontSampling;
    }

    public void setFontSampling(float FontSampling) {
        this.FontSampling = FontSampling;
    }
    
    public void Generate() {
        for (TexGrid t : Image.values())
            t.Generate();
        for (SprFrame s : Frame.values()) {
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
        for (SEffect s : SFX.values())
            s.Generate();
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
        x += h*Scale.y*sh*0.5f*Math.sin( Math.toRadians(-1*rot) )/FontSampling;
        y += h*Scale.y*sh*0.5f*Math.cos( Math.toRadians(-1*rot) )/FontSampling;
        double xtrav = w*Math.cos( Math.toRadians(rot) )*sw*Scale.x/FontSampling;
        double ytrav = w*Math.sin( Math.toRadians(rot) )*sw*Scale.y/FontSampling;
        double xjmp = h*Math.sin( Math.toRadians(rot) )*sh*Scale.x/FontSampling;
        double yjmp = h*Math.cos( Math.toRadians(rot) )*sh*Scale.y/FontSampling;
        
        double xcur = x;
        double ycur = y;
        int linesdown = 0;
        for (int i=0; i<str.length(); ++i) {
            char c = str.charAt(i);
            switch (c) {
                case '\n':
                    ++linesdown;
                    xcur = x+xjmp*linesdown;
                    ycur = y-yjmp*linesdown;
                    break;
                case '\t':
                    xcur += xtrav*TabLength;
                    ycur += ytrav*TabLength;
                    break;
                default:
                    TextureRegion reg = Letters.get(c);
                    double xoff = Font.getData().getGlyph(c).xoffset/w;
                    double yoff = Font.getData().getGlyph(c).yoffset/h;
                    if (reg != null) drawRegion(reg, x + (float)(xcur + xoff*xtrav - yoff*xjmp)
                            , y + (float)(ycur + xoff*ytrav + yoff*yjmp)
                            , sw/FontSampling, sh*-1/FontSampling, rot);
                    xcur += xtrav;
                    ycur += ytrav;
            }
        }
    }
    
    public SEffect getSFX(String key) {
        return SFX.get(key);
    }
    
    public MTrack getMus(String key) {
        return Mus.get(key);
    }
    
    public void playSFX(String key) {
        try {
            SFX.get(key).play();
        } catch (Exception e) {
            //nothing
        }
    }
    
    public void setSFXVolume(float v) {
        for (SEffect s : SFX.values())
            s.setVolume(v);
    }
    
    public void setMusVolume(float v) {
        for (MTrack m : Mus.values())
            m.setVolume(v);
    }
    
    public void killMusic() {
        for (MTrack m : Mus.values()) {
            m.stop();
            m.dispose();
        }
    }
    
    public void dispose() {
        Font.dispose();
        MyBatch.dispose();
        for (TexGrid t : Image.values())
            t.dispose();
        for (SprFrame s : Frame.values())
            s.dispose();
        for (AnimSeq a : Anim.values())
            a.dispose();
        for (SEffect s : SFX.values())
            s.dispose();
        for (MTrack m : Mus.values())
            m.dispose();
    }
}
