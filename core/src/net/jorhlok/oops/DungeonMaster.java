package net.jorhlok.oops;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import net.jorhlok.multiav.MultiAVRegister;

/**
 * The master script in a room.
 * @author Jorhlok
 */
public class DungeonMaster {
    //setup
    public Map<Integer,String> TileEntityMapping;
    public String MapName;
    
    //runtime
    Map<String, Class<? extends Entity> > eTypes;
    public TiledMap Level;
    public Map<String, List<Entity> > Living;
    
    public DungeonMaster(String mapname, Map<Integer,String> temap) {
        Living = new HashMap<String, List<Entity> >();
        MapName = mapname;
        TileEntityMapping = temap;
    }
    
    public void create(Map<String,TiledMap> maps, Map<String, Class<? extends Entity> > etypes) {
        
    }
    
    public void begin() {
        
    }
    
    public void end() {
        
    }
    
    public void dispose() {
        
    }

    public void prestep(float deltatime) {
        
    }
    
    public void step(float deltatime) {
        
    }

    public void poststep(float deltatime) {
        
    }
    
    public void draw(float deltatime, MultiAVRegister msr) {
        
    }
    
    public void CorporealCollisions(List<String> cw, Queue<Corporeal> q, Rectangle aabb) {
        
    }
    
    public void PhysicalCollisions(Queue<TMPCO> q, Rectangle aoi) {
        
    }
}
