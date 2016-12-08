package net.jorhlok.multiav;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 *
 * @author joshm
 */
public class MTrack implements Music.OnCompletionListener {
    //setup
    protected String Name;
    protected String URIIntro;
    protected String URIBody;
    
    //runtime
    protected Music DataIntro;
    protected Music DataBody;
    protected float GlobalVolume = 1;
    protected int State = 0;
    
    public MTrack(String key, String uri) {
        Name = key;
        URIIntro = null;
        URIBody = uri;
    }
        
    public MTrack(String key, String intro, String body) {
        Name = key;
        URIIntro = intro;
        URIBody = body;
    }
    
    public String getName() {
        return Name;
    }
    
    public void setVolume(float v) {
        GlobalVolume = Math.max(v, 0);
    }
    
    public void Generate() {
        dispose();
        try {
            DataIntro = Gdx.audio.newMusic(Gdx.files.internal(URIIntro));
        } catch (Exception e) {
            DataIntro = null;
        }
        try {
            DataBody = Gdx.audio.newMusic(Gdx.files.internal(URIBody));
        } catch (Exception e) {
            DataBody = null;
        }
    }
    
    public void dispose() {
        try {
            DataIntro.dispose();
        } catch (Exception e) {
            //nothing
        }
        try {
            DataBody.dispose();
        } catch (Exception e) {
            //nothing
        }
    }
    
    public void play() {
        play(false);
    }
    
    public void play(boolean loop) {
        stop();
        try {
            DataIntro.setLooping(false);
            DataIntro.setOnCompletionListener(this);
            DataIntro.setVolume(GlobalVolume);
            if (loop) State = 3;
            else State = 1;
            if (DataBody != null) DataBody.setLooping(loop);
            DataIntro.play();
        } catch (Exception e) {
            playNoIntro(loop);
        }
    }
    
    public void playNoIntro() {
        playNoIntro(false);
    }
    
    public void playNoIntro(boolean loop) {
        stop();
        try {
            DataBody.setLooping(loop);
            DataBody.setVolume(GlobalVolume);
            DataBody.play();
        } catch (Exception e) {
            //nothing
        }
    }
    
    public void stop() {
        try {
            DataIntro.stop();
        } catch (Exception e) {
            //nothing
        }
        try {
            DataIntro.stop();
        } catch (Exception e) {
            //nothing
        }
        State = 0;
    }

    @Override
    public void onCompletion(Music m) {
        try {
            switch (State) {
                case 1: //single playthrough done with intro
                    DataBody.play();
                    State = 2;
                    break;
                case 2: //single playthrough done with loop
                    State = 0;
                    break;
                case 3: //looping done with intro
                    DataBody.play();
                    State = 4;
                    break;
                default:
                    //nothing?
            }
        } catch (Exception e) {
            //nothing
        }
    }
}
