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
import net.jorhlok.multiav.MultiAVRegister;



public class ShootyShoot extends ApplicationAdapter {
    SpriteBatch batch;
    BitmapFont font;
    MultiAVRegister mav;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Vector2 campos = new Vector2();
    
    float time = 0;
    float sfxtime = 0f;

    @Override
    public void create () {
        mav = new MultiAVRegister();
        mkav();
        mav.Generate();
        batch = new SpriteBatch();
        mav.setBatch(batch);
        camera.setToOrtho(false, 640/16, 360/16);
        camera.position.x = campos.x = 640/32;
        camera.position.y = campos.y = 360/32;
        mav.setScale( new Vector2(1f/16,1f/16) );
        mav.setCamPos(campos);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        float deltatime = Gdx.graphics.getDeltaTime();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        mav.draw("_greenblock", time, 320/16, 180/16, 640/16, 1, time*30);
        mav.drawString("||Hello, World||\n||\t||", 320/16, 180/16, 2, 1, time*30);
        mav.draw("_greyblock", 0, 1, 1, 1, 1);
        mav.draw("_alchemy", 0, 2, 2, 2, 2);
        mav.draw("pacrt", time, 6, 2, 1, 1);
        time += deltatime;
        batch.end();
        
        if (sfxtime >= 1) {
            mav.playSFX("dash");
            sfxtime = 0;
        }
        else sfxtime += deltatime;
    }
    
    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void dispose () {
            batch.dispose();
    }

    public void mkav() {
        mav.newSFX("pew", "sfx/pew.wav");
        mav.newSFX("jump", "sfx/jump.wav");
        mav.newSFX("dash", "sfx/dash.wav");
        mav.newSFX("growl", "sfx/growl.wav");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("gfx/libmono.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14*6;
        parameter.genMipMaps = true;
        parameter.magFilter = Texture.TextureFilter.Nearest;
        parameter.minFilter = Texture.TextureFilter.Linear;
        //parameter.characters = parameter.characters + ",▄■";
        parameter.hinting = Hinting.Full;
        mav.setFont(font = generator.generateFont(parameter), parameter.characters);
        mav.setFontSampling(6f);
        generator.dispose();

        mav.newImage("sprites", "gfx/sprites.png", 16, 16);

        mav.newSprite("guyrt", "sprites", 0, 0, 1, 1, false, false);
        mav.newSprite("guylf", "sprites", 0, 0, 1, 1, true, false);
        mav.newSprite("gunrt", "sprites", 1, 0, 1, 1, false, false);
        mav.newSprite("gunlf", "sprites", 1, 0, 1, 1, true, false);
        mav.newSprite("pac0lf", "sprites", 14, 0, 1, 1, false, false);
        mav.newSprite("pac0rt", "sprites", 14, 0, 1, 1, true, false);
        mav.newSprite("pac1lf", "sprites", 15, 0, 1, 1, false, false);
        mav.newSprite("pac1rt", "sprites", 15, 0, 1, 1, true, false);

        mav.newSprite("shot0", "sprites", 0, 1, 1, 1, false, false);
        mav.newSprite("shot1", "sprites", 1, 1, 1, 1, false, false);
        mav.newSprite("shot2", "sprites", 2, 1, 1, 1, false, false);
        mav.newSprite("shot3", "sprites", 3, 1, 1, 1, false, false);

        mav.newSprite("door", "sprites", 0, 2, 1, 1, false, false);
        mav.newSprite("pedestal", "sprites", 1, 2, 1, 1, false, false);
        mav.newSprite("alchemy", "sprites", 2, 2, 2, 1, false, false);

        mav.newSprite("mineral", "sprites", 0, 3, 1, 1, false, false);
        mav.newSprite("herb", "sprites", 1, 3, 1, 1, false, false);
        mav.newSprite("eyeball", "sprites", 2, 3, 1, 1, false, false);
        mav.newSprite("worm", "sprites", 3, 3, 1, 1, false, false);
        mav.newSprite("dollar", "sprites", 4, 3, 1, 1, false, false);
        mav.newSprite("potion", "sprites", 5, 3, 1, 1, false, false);
        mav.newSprite("mana", "sprites", 6, 3, 1, 1, false, false);
        mav.newSprite("coffee", "sprites", 7, 3, 1, 1, false, false);

        mav.newSprite("redbar", "sprites", 0, 4, 1, 1, false, false);
        mav.newSprite("darkredbar", "sprites", 1, 4, 1, 1, false, false);
        mav.newSprite("bluebar", "sprites", 2, 4, 1, 1, false, false);
        mav.newSprite("darkbluebar", "sprites", 3, 4, 1, 1, false, false);
        mav.newSprite("yellowbar", "sprites", 4, 4, 1, 1, false, false);
        mav.newSprite("darkyellowbar", "sprites", 5, 4, 1, 1, false, false);
        mav.newSprite("uiback", "sprites", 6, 4, 1, 1, false, false);
        mav.newSprite("uislot", "sprites", 7, 4, 1, 1, false, false);
        mav.newSprite("cursor", "sprites", 8, 4, 1, 1, false, false);

        mav.newSprite("greenblock", "sprites", 0, 15, 1, 1, false, false);
        mav.newSprite("skyblueblock", "sprites", 1, 15, 1, 1, false, false);
        mav.newSprite("whiteblock", "sprites", 2, 15, 1, 1, false, false);
        mav.newSprite("greyblock", "sprites", 3, 15, 1, 1, false, false);

        mav.newAnim("newshot", new String[] {"shot0","shot1","shot2"}, 0.125f, Animation.PlayMode.NORMAL);
        mav.newAnim("shot", new String[] {"shot3","shot2"}, 0.01f, Animation.PlayMode.LOOP);
        mav.newAnim("paclf", new String[] {"pac0lf","pac1lf"}, 0.5f, Animation.PlayMode.LOOP);
        mav.newAnim("pacrt", new String[] {"pac0rt","pac1rt"}, 0.5f, Animation.PlayMode.LOOP);
    }
}
