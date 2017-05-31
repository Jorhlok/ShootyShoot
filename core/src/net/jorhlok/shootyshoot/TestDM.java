package net.jorhlok.shootyshoot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import java.util.Map;
import net.jorhlok.multiav.MultiAVRegister;
import net.jorhlok.oops.DungeonMaster;
import net.jorhlok.oops.Entity;
import net.jorhlok.oops.Postage;

public class TestDM extends DungeonMaster {
    OrthogonalTiledMapRenderer render;
    OrthographicCamera cam;
    
    public TestDM(String mapname, Map<Integer, String> temap) {
        super(mapname, temap);
    }
    
    @Override
    public void begin() {
        render = new OrthogonalTiledMapRenderer(Level,1f,Parent.MAV.getBatch());
        render.setView(cam);
        
        Entity e = mkEntity("testplat","player","player");
    }
    
    @Override
    public void update(float deltatime) {
        //poll input
        try {
            if ((Gdx.input.isKeyPressed(Input.Keys.SPACE)) || (Gdx.input.isKeyPressed(Input.Keys.UP)) 
                    || (Gdx.input.isKeyPressed(Input.Keys.W))) {
                for ( Entity e : Living.get("player") ) {
                    e.Mailbox.add(new Postage(null,"jump","control",1));
                }
            } else {
                for ( Entity e : Living.get("player") ) {
                    e.Mailbox.add(new Postage(null,"jump","control",0));
                }
            }

            if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT)) || (Gdx.input.isKeyPressed(Input.Keys.D))) {
                for ( Entity e : Living.get("player") ) {
                    e.Mailbox.add(new Postage(null,"right","control",1));
                }
            } else {
                for ( Entity e : Living.get("player") ) {
                    e.Mailbox.add(new Postage(null,"right","control",0));
                }
            }

            if ((Gdx.input.isKeyPressed(Input.Keys.LEFT)) || (Gdx.input.isKeyPressed(Input.Keys.A))) {
                for ( Entity e : Living.get("player") ) {
                    e.Mailbox.add(new Postage(null,"left","control",1));
                }
            } else {
                for ( Entity e : Living.get("player") ) {
                    e.Mailbox.add(new Postage(null,"left","control",0));
                }
            }
            //update objects
            for ( Entity e : Living.get("player") ) {
                e.update(deltatime);
            }
        } catch (Exception e) {
            System.err.println("Unable to update in TestDM because:\n" + e.toString() + "\n" + e.getMessage());
        }
    }
    
    @Override
    public void draw(float deltatime, MultiAVRegister msr) {
        render.render();
        msr.getBatch().begin();
        try {
            for ( Entity e : Living.get("player") ) {
                e.draw(msr);
            }
        } catch (Exception e) {
            //meh
            e.printStackTrace();
        }
        msr.getBatch().end();
    }
}
