package net.jorhlok.multiav;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 
 * @author Jorhlok
 */
public class TexGrid {
    //setup
    protected String Name;
    protected String URI;
    protected int TWidth;
    protected int THeight;
    
    //runtime
    protected Texture Tex;

    public TexGrid(String key, String uri, int tw, int th) {
        Name = key;
//        Tex = new Texture(uri);
        URI = uri;
        TWidth = tw;
        THeight = th;
    }
    
    public String getName() {
        return Name;
    }
    
    public void Generate() {
        
    }
    
    public TextureRegion GetRegion(int tx, int ty) {
        return GetRegion(tx,ty,1,1);
    }
    
    public TextureRegion GetRegion(int tx, int ty, int tw, int th) {
        try {
            return new TextureRegion(Tex,tx*TWidth,ty*THeight,tw*TWidth,th*THeight);
        } catch (Exception e) {
            return null;
        }
    }
    
    public void dispose() {
        try {
            Tex.dispose();
        } catch (Exception e) {
            //nothing
        }
    }
}
