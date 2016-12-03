package net.jorhlok.shootyshoot;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;
import com.badlogic.gdx.math.Vector2;
import net.jorhlok.multisprite.MultiSpriteRegister;



public class ShootyShoot extends ApplicationAdapter {
    SpriteBatch batch;
    BitmapFont font;
    MultiSpriteRegister msr;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Vector2 campos = new Vector2();
    
    float time = 0;

    @Override
    public void create () {
        msr = new MultiSpriteRegister();
        mkgfx();
        msr.Generate();
        batch = new SpriteBatch();
        msr.MyBatch = batch;
        camera.setToOrtho(false, 640/16, 360/16);
        camera.position.x = campos.x = 640/32;
        camera.position.y = campos.y = 360/32;
        msr.Scale = new Vector2(1f/16,1f/16);
        msr.CamPos = campos;
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        msr.draw("_greenblock", time, 320/16, 180/16, 640/16, 1, time*30);
        msr.drawString("||Hello, World||\n||\t||", 320/16, 180/16, 2, 1, time*30);
        msr.draw("_greyblock", 0, 1, 1, 1, 1);
        msr.draw("_alchemy", 0, 2, 2, 2, 2);
        msr.draw("pacrt", time, 6, 2, 1, 1);
        time += Gdx.graphics.getDeltaTime();
        batch.end();
    }
    
    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void dispose () {
            batch.dispose();
    }

    public void mkgfx() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("gfx/libmono.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14*6;
//        parameter.genMipMaps = true;
        parameter.magFilter = Texture.TextureFilter.Nearest;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        //parameter.characters = parameter.characters + ",▄■";
        parameter.hinting = Hinting.Full;
        msr.Font = font = generator.generateFont(parameter);
        msr.DrawableChars = parameter.characters;
        msr.FontSampling = 6f;
        generator.dispose();

        msr.newImage("sprites", "gfx/sprites.png", 16, 16);

        msr.newSprite("guyrt", "sprites", 0, 0, 1, 1, false, false);
        msr.newSprite("guylf", "sprites", 0, 0, 1, 1, true, false);
        msr.newSprite("gunrt", "sprites", 1, 0, 1, 1, false, false);
        msr.newSprite("gunlf", "sprites", 1, 0, 1, 1, true, false);
        msr.newSprite("pac0lf", "sprites", 14, 0, 1, 1, false, false);
        msr.newSprite("pac0rt", "sprites", 14, 0, 1, 1, true, false);
        msr.newSprite("pac1lf", "sprites", 15, 0, 1, 1, false, false);
        msr.newSprite("pac1rt", "sprites", 15, 0, 1, 1, true, false);

        msr.newSprite("shot0", "sprites", 0, 1, 1, 1, false, false);
        msr.newSprite("shot1", "sprites", 1, 1, 1, 1, false, false);
        msr.newSprite("shot2", "sprites", 2, 1, 1, 1, false, false);
        msr.newSprite("shot3", "sprites", 3, 1, 1, 1, false, false);

        msr.newSprite("door", "sprites", 0, 2, 1, 1, false, false);
        msr.newSprite("pedestal", "sprites", 1, 2, 1, 1, false, false);
        msr.newSprite("alchemy", "sprites", 2, 2, 2, 1, false, false);

        msr.newSprite("mineral", "sprites", 0, 3, 1, 1, false, false);
        msr.newSprite("herb", "sprites", 1, 3, 1, 1, false, false);
        msr.newSprite("eyeball", "sprites", 2, 3, 1, 1, false, false);
        msr.newSprite("worm", "sprites", 3, 3, 1, 1, false, false);
        msr.newSprite("dollar", "sprites", 4, 3, 1, 1, false, false);
        msr.newSprite("potion", "sprites", 5, 3, 1, 1, false, false);
        msr.newSprite("mana", "sprites", 6, 3, 1, 1, false, false);
        msr.newSprite("coffee", "sprites", 7, 3, 1, 1, false, false);

        msr.newSprite("redbar", "sprites", 0, 4, 1, 1, false, false);
        msr.newSprite("darkredbar", "sprites", 1, 4, 1, 1, false, false);
        msr.newSprite("bluebar", "sprites", 2, 4, 1, 1, false, false);
        msr.newSprite("darkbluebar", "sprites", 3, 4, 1, 1, false, false);
        msr.newSprite("yellowbar", "sprites", 4, 4, 1, 1, false, false);
        msr.newSprite("darkyellowbar", "sprites", 5, 4, 1, 1, false, false);
        msr.newSprite("uiback", "sprites", 6, 4, 1, 1, false, false);
        msr.newSprite("uislot", "sprites", 7, 4, 1, 1, false, false);
        msr.newSprite("cursor", "sprites", 8, 4, 1, 1, false, false);

        msr.newSprite("greenblock", "sprites", 0, 15, 1, 1, false, false);
        msr.newSprite("skyblueblock", "sprites", 1, 15, 1, 1, false, false);
        msr.newSprite("whiteblock", "sprites", 2, 15, 1, 1, false, false);
        msr.newSprite("greyblock", "sprites", 3, 15, 1, 1, false, false);

        msr.newAnim("newshot", new String[] {"shot0","shot1","shot2"}, 0.125f, Animation.PlayMode.NORMAL);
        msr.newAnim("shot", new String[] {"shot3","shot2"}, 0.01f, Animation.PlayMode.LOOP);
        msr.newAnim("paclf", new String[] {"pac0lf","pac1lf"}, 0.5f, Animation.PlayMode.LOOP);
        msr.newAnim("pacrt", new String[] {"pac0rt","pac1rt"}, 0.5f, Animation.PlayMode.LOOP);
    }
}
