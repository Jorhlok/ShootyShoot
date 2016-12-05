package net.jorhlok.oops;

import com.badlogic.gdx.maps.tiled.TiledMap;
import java.util.List;
import java.util.Map;

/**
 * A whole 'nother world!
 * @author Jorhlok
 */
public class World {
    //setup
    public Map<Integer,String> TileEntityMapping;
    public Map<String, Class<? extends Entity> > EntityTypes;
    public DungeonMaster Maestro;
    public TiledMap Level;
    
    //runtime
    public Map<String,List<Entity> > Living;
    
    /**
     * Polymorphism to the max!
     */
    public Entity mkentity(String key) {
        try {
            return EntityTypes.get(key).newInstance();
        } catch (Exception e) {
            System.err.println("Unable to make a new " + key + " because:\n" + e.toString());
            return null;
        }
    }
}
