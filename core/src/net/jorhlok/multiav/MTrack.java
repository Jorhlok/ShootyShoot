package net.jorhlok.multiav;

import com.badlogic.gdx.audio.Music;

/**
 *
 * @author joshm
 */
public class MTrack implements Music.OnCompletionListener {
    //setup
    protected String Name;
    protected String URIIntro;
    protected String URILoop;
    
    //runtime
    protected Music DataIntro;
    protected Music DataBody;
    protected float GlobalVolume = 1;
    protected int State = 0;
    
    public MTrack(String key, String uri) {
        Name = key;
        URIIntro = null;
        URILoop = uri;
    }
        
    public MTrack(String key, String intro, String body) {
        Name = key;
        URIIntro = intro;
        URILoop = body;
    }
    
    public String getName() {
        return Name;
    }
    
    public void setVolume(float v) {
        GlobalVolume = Math.max(v, 0);
    }
    
    public void Generate() {
        
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
            State = 1;
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
        switch (State) {
            case 1: //single playthrough done with intro
                m.stop();
                if (DataBody != null) {
                    DataBody.setLooping(false);
                    DataBody.play();
                    State = 2;
                }
                else State = 0;
                break;
            case 2: //single playthrough done with loop
                m.stop();
                State = 0;
                break;
            case 3: //looping done with intro
                m.stop();
                if (DataBody != null) {
                    DataBody.setLooping(true);
                    DataBody.play();
                    State = 4;
                }
                else State = 0;
                break;
            default:
                //nothing?
        }
    }
}
