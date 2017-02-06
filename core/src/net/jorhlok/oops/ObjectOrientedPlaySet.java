package net.jorhlok.oops;

import com.badlogic.gdx.maps.tiled.TiledMap;
import java.util.HashMap;
import java.util.Map;
import net.jorhlok.multiav.MultiAVRegister;

/**
 * Object Oriented Gameplay Setup
 * Registers maps, levels, and kinds of entities
 * Meant to be used as-is
 * @author Jorhlok
 */
public class ObjectOrientedPlaySet {
    //setup
    public Map<String,TiledMap> TileMap = new HashMap<String,TiledMap>();
    public Map<String, Class<? extends Entity> > EntityType = new HashMap<String, Class<? extends Entity> >();
    public Map<String,DungeonMaster> MasterScript = new HashMap<String,DungeonMaster>();
    
    //runtime
    public MultiAVRegister MAV;
    public DungeonMaster Here = null;
        //something about input here
    
    public void addTileMap(String key, TiledMap map) {
        TileMap.put(key, map);
    }
    
    public void addEntityType(String key, Class<? extends Entity> clazz) {
        EntityType.put(key, clazz);
    }
    
    public void addMasterScript(String key, DungeonMaster script) {
        script.Parent = this;
        MasterScript.put(key, script);
    }
    
    public void setMAV(MultiAVRegister mav) {
        MAV = mav;
    }
    
    public void step(float deltatime) {
        if (Here != null) Here.update(deltatime);
    }
    
    public void draw(float deltatime) {
        if (Here != null) Here.draw(deltatime, MAV);
    }
    
    public void launchScript(String key) {
        if (Here != null) {
            Here.end();
            Here.dispose();
        }
        Here = MasterScript.get(key);
        if (Here != null) {
            Here.create(TileMap, EntityType);
            Here.begin();
        }
    }
    
    /**
     * Polymorphism to the max!
     */
    public Entity mkentity(String key) {
        try {
            return EntityType.get(key).newInstance();
        } catch (Exception e) {
            System.err.println("Unable to make a new " + key + " because:\n" + e.toString());
            return null;
        }
    }
}
