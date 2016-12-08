package net.jorhlok.multiav;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 *
 * @author joshm
 */
public class SEffect {
    //setup
    protected String Name;
    protected String URI;
    protected float GlobalVolume = 1;
    
    //runtime
    protected Sound Data;
    
    public SEffect(String key, String uri) {
        Name = key;
//        Data = Gdx.audio.newSound(new FileHandle(uri));
        URI = uri;
    }
    
    public void Generate() {
        Data = Gdx.audio.newSound(Gdx.files.internal(URI));
    }
    
    public String getName() {
        return Name;
    }
    
    public void setVolume(float v) {
        GlobalVolume = Math.max(v, 0);
    }
    
    public long play() {
        return play(1,1,0,false);
    }
    public long play(boolean loop) {
        return play(1,1,0,loop);
    }
    
    public long play(float volume) {
        return play(volume,1,0,false);
    }
    
    public long play(float volume, boolean loop) {
        return play(volume,1,0,loop);
    }
    
    public long play(float volume, float speed) {
        return play(volume,speed,0,false);
    }
    
    public long play(float volume, float speed, boolean loop) {
        return play(volume,speed,0,loop);
    }
    
    public long play(float volume, float speed, float pan) {
        return play(volume,speed,pan,false);
    }

    public long play(float volume, float speed, float pan, boolean loop) {
        try {
            long id = Data.play(volume*GlobalVolume, speed, pan);
            Data.setLooping(id, loop);
            return id;
        } catch (Exception e) {
            return -1;
        }
    }
    
    public void stop() {
        try {
            Data.stop();
        } catch (Exception e) {
            //nothing
        }
    }
    
    public void stop(long id) {
        try {
            Data.stop(id);
        } catch (Exception e) {
            //nothing
        }
    }
    
    public void dispose() {
        try {
            Data.dispose();
        } catch (Exception e) {
            //nothing
        }
    }
}
