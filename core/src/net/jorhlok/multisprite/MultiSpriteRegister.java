package net.jorhlok.multisprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.Map;

public class MultiSpriteRegister {
    Class<?> MapClass;
    Map<String,TexGrid> Image;
    Map<String,Sprite> Frame;
    Map<String,AnimSeq> Anim;
    
    Batch DrawBatch;
    
    MultiSpriteRegister() {
    }
}
