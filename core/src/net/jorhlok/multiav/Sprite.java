package net.jorhlok.multiav;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Map;

/**
 *
 * @author Jorhlok
 */
public class Sprite {
    //input
    public String Name;
    public String Image;
    public int TX;
    public int TY;
    public int TWidth;
    public int THeight;
    public boolean HFlip;
    public boolean VFlip;
    
    //generated
    public TextureRegion Data;
    
    public Sprite(String key, String img, int tx, int ty, int tw, int th, boolean hf, boolean vf) {
        Name = key;
        Image = img;
        TX = tx;
        TY = ty;
        TWidth = tw;
        THeight = th;
        HFlip = hf;
        VFlip = vf;
    }
    
    public void Generate(Map<String,TexGrid> map) {
        if (map == null) {
            Data = null;
            return;
        }
        TexGrid tex = map.get(Image);
        if (tex == null) {
            Data = null;
            return;
        }
        Data = tex.GetRegion(TX, TY, TWidth, THeight);
        Data.flip(HFlip, VFlip);
    }
}
