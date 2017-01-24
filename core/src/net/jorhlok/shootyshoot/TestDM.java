package net.jorhlok.shootyshoot;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import java.util.Map;
import net.jorhlok.multiav.MultiAVRegister;
import net.jorhlok.oops.DungeonMaster;

public class TestDM extends DungeonMaster {
    OrthogonalTiledMapRenderer render;
    OrthographicCamera cam;
    
    public TestDM(String mapname, Map<Integer, String> temap) {
        super(mapname, temap);
    }
    
    @Override
    public void draw(float deltatime, MultiAVRegister msr) {
        render.render();
    }
}
