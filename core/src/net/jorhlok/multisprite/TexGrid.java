package net.jorhlok.multisprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 
 * @author Jorhlok
 */
public class TexGrid {
    public String Name;
    public Texture Tex;
    public int TWidth;
    public int THeight;

    public TexGrid(String key, String uri, int tw, int th) {
        Name = key;
        Tex = new Texture(uri);
        TWidth = tw;
        THeight = th;
    }
    
    public TextureRegion GetRegion(int tx, int ty) {
        return GetRegion(tx,ty,1,1);
    }
    
    public TextureRegion GetRegion(int tx, int ty, int tw, int th) {
        return new TextureRegion(Tex,tx*TWidth,ty*THeight,tw*TWidth,th*THeight);
    }
    
    public void dispose() {
        Tex.dispose();
    }
}
