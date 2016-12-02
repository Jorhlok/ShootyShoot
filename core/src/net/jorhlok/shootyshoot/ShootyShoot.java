package net.jorhlok.shootyshoot;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import net.jorhlok.multisprite.MultiSpriteRegister;



public class ShootyShoot extends ApplicationAdapter {
    SpriteBatch batch;
    BitmapFont font;
    MultiSpriteRegister msr;
    float time = 0;

    @Override
    public void create () {
        msr = new MultiSpriteRegister();
        mkgfx();
        msr.Generate();
        batch = new SpriteBatch();
        msr.MyBatch = batch;
    }

    @Override
    public void render () {
            Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            msr.drawString("Monster Part\n\tUsed in crafting."
                    , 500/*+(float)(Math.sin(time)*-10.5f*4)*/
                    , 450+(float)Math.sin(time)*-10.5f*8
                    , 2, 2, (float)Math.sin(time)*30);
            System.out.println(Math.sin(time));
            msr.draw("_eyeball", time, 640-16, 360-16, 2, 2, time*-90);
            time += Gdx.graphics.getDeltaTime();
            batch.end();
    }

    @Override
    public void dispose () {
            batch.dispose();
    }

    public void mkgfx() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("gfx/libmono.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14;
//        parameter.genMipMaps = true;
        parameter.magFilter = Texture.TextureFilter.Nearest;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        //parameter.characters = parameter.characters + ",▄■";
        msr.Font = font = generator.generateFont(parameter);
        msr.DrawableChars = parameter.characters;
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
