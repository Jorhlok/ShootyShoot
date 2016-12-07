package net.jorhlok.multiav;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/**
 *
 * @author joshm
 */
public class SEffect {
    protected String Name;
    protected Sound Data;
    protected float GlobalVolume = 1;
    
    public SEffect(String key, String uri) {
        Name = key;
        Data = Gdx.audio.newSound(new FileHandle(uri));
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
        long id = Data.play(volume*GlobalVolume, speed, pan);
        Data.setLooping(id, loop);
        return id;
    }
    
    public void stop() {
        Data.stop();
    }
    
    public void stop(long id) {
        Data.stop(id);
    }
    
    public void dispose() {
        Data.dispose();
    }
}
