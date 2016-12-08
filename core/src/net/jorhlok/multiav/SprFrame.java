package net.jorhlok.multiav;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Map;

/**
 *
 * @author Jorhlok
 */
public class SprFrame {
    //input
    protected String Name;
    protected String Image;
    protected int TX;
    protected int TY;
    protected int TWidth;
    protected int THeight;
    protected boolean HFlip;
    protected boolean VFlip;
    
    //generated
    protected TextureRegion Data;
    
    public SprFrame(String key, String img, int tx, int ty, int tw, int th, boolean hf, boolean vf) {
        Name = key;
        Image = img;
        TX = tx;
        TY = ty;
        TWidth = tw;
        THeight = th;
        HFlip = hf;
        VFlip = vf;
    }
    
    public String getName() {
        return Name;
    }
    
    public void Generate(Map<String,TexGrid> map) {
        try {
            TexGrid tex = map.get(Image);
            Data = tex.GetRegion(TX, TY, TWidth, THeight);
            Data.flip(HFlip, VFlip);
        } catch (Exception e) {
            Data = null;
        }
    }
    
    public TextureRegion getReg() {
        return Data;
    }
    
    public void dispose() {
        Data = null;
    }
}
