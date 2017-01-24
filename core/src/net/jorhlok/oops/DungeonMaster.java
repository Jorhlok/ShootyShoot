package net.jorhlok.oops;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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
    public String StrTerrain = "Terrain";
    public String StrTileObjects = "TileObjects";
    public String StrNonTiles = "NonTiles";
    
    //runtime
    public Map<String, Class<? extends Entity> > eTypes;
    public TiledMap Level;
    public TiledMapTileLayer LyrTerrain;
    public TiledMapTileLayer LyrTileObjects;
    public MapLayer LyrNonTiles;
    public Map<String, List<Entity> > Living;
    
    public DungeonMaster(String mapname, Map<Integer,String> temap) {
        Living = new HashMap<String, List<Entity> >();
        MapName = mapname;
        TileEntityMapping = temap;
    }
    
    public void create(Map<String,TiledMap> maps, Map<String, Class<? extends Entity> > etypes) {
        eTypes = etypes;
        try {
            Level = new TmxMapLoader(new InternalFileHandleResolver()).load(MapName);
            try {
                LyrTerrain = (TiledMapTileLayer) Level.getLayers().get(StrTerrain);
            } catch (Exception e) {
                //nothing
            }
            try {
                LyrTileObjects = (TiledMapTileLayer) Level.getLayers().get(StrTileObjects);
            } catch (Exception e) {
                //nothing
            }
            try {
                LyrNonTiles = Level.getLayers().get(StrNonTiles);
            } catch (Exception e) {
                //nothing
            }
        } catch (Exception e) {
            e.printStackTrace();//nothing
        }
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
    
    public void PhysicalCollisions(Queue<TMPCO> q, Rectangle aoi, Rectangle projected) {
        int x1 = (int) Math.floor(aoi.x);
        int y1 = (int) Math.floor(aoi.y);
        int x2 = (int) Math.ceil(aoi.x+aoi.width);
        int y2 = (int) Math.ceil(aoi.y+aoi.height);
        //collide with tiles
        for (int y=y1; y<=y2; ++y) {
            for (int x=x1; x<=x2; ++x) {
                Cell c;
                try {
                    c = LyrTerrain.getCell(x, y);
                    if (c != null && c.getTile() != null) {
                        TMPCO tmp = new TMPCO();
                        tmp.AABB = new Rectangle(x,y,1,1);
                        tmp.cell = c;
                        Vector2 tcenter = tmp.AABB.getCenter(new Vector2());
                        Vector2 pcenter = projected.getCenter(new Vector2());
                        if (pcenter.y > tcenter.y) tmp.CollisionFlags += 8;
                        else if (pcenter.y < tcenter.y) tmp.CollisionFlags += 4;
                        if (pcenter.x > tcenter.x) tmp.CollisionFlags += 2;
                        else if (pcenter.x < tcenter.x) tmp.CollisionFlags += 1;
                        q.add(tmp);
                    }
                } catch (Exception e) {
                    //nothing
                }
                try {
                    c = LyrTileObjects.getCell(x, y);
                    if (c != null && c.getTile() != null) {
                        TMPCO tmp = new TMPCO();
                        tmp.AABB = new Rectangle(x,y,1,1);
                        tmp.cell = c;
                        Vector2 tcenter = tmp.AABB.getCenter(new Vector2());
                        Vector2 pcenter = projected.getCenter(new Vector2());
                        if (pcenter.y > tcenter.y) tmp.CollisionFlags += 8;
                        else if (pcenter.y < tcenter.y) tmp.CollisionFlags += 4;
                        if (pcenter.x > tcenter.x) tmp.CollisionFlags += 2;
                        else if (pcenter.x < tcenter.x) tmp.CollisionFlags += 1;
                        q.add(tmp);
                    }
                } catch (Exception e) {
                    //nothing
                }
            }
        }
        //collide with non-tiles
        try {
            Array<RectangleMapObject> mapobj = LyrNonTiles.getObjects().getByType(RectangleMapObject.class);
            for (RectangleMapObject r : mapobj) {
                if (aoi.overlaps(r.getRectangle())) {
                    TMPCO tmp = new TMPCO();
                    tmp.AABB = r.getRectangle();
                    tmp.NonTile = r;
                    Vector2 tcenter = tmp.AABB.getCenter(new Vector2());
                    Vector2 pcenter = projected.getCenter(new Vector2());
                    if (pcenter.y > tcenter.y) tmp.CollisionFlags += 8;
                    else if (pcenter.y < tcenter.y) tmp.CollisionFlags += 4;
                    if (pcenter.x > tcenter.x) tmp.CollisionFlags += 2;
                    else if (pcenter.x < tcenter.x) tmp.CollisionFlags += 1;
                    q.add(tmp);
                }
            }
        } catch (Exception e) {
            //nothing
        }
    }
}
