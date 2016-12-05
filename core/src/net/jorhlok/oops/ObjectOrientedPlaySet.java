package net.jorhlok.oops;

import com.badlogic.gdx.maps.tiled.TiledMap;
import java.util.Map;
import net.jorhlok.multisprite.MultiSpriteRegister;

/**
 * Object Oriented Gameplay Setup
 * Registers maps, levels, and kinds of entities
 * Meant to be used as-is
 * @author Jorhlok
 */
public class ObjectOrientedPlaySet {
    //setup
    public Map<String,TiledMap> TileMap;
    public Map<String, Class<? extends Entity> > EntityType;
    public Map<String,DungeonMaster> MasterScript;
    
    //runtime
    public MultiSpriteRegister MSR;
    public DungeonMaster Here;
        //something about input here
    
    public void addTileMap(String key, TiledMap map) {
        
    }
    
    public void addEntityType(String key, Class<? extends Entity> clazz) {
        
    }
    
    public void addMasterScript(String key, DungeonMaster script) {
        
    }
    
    public void step(float deltatime) {
        
    }
    
    public void draw(float deltatime) {
        
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
