package net.jorhlok.oops;

import java.util.Queue;
import net.jorhlok.multisprite.MultiSpriteRegister;

/**
 * Some sort of entity. May or may not exist in worldspace.
 * @author Jorhlok
 */
abstract public class Entity {
    public String Name;
    public String Type;
    public Queue<Integer> Events;
    
    public void update(float deltatime) {
        //semiauto do something about events
        prestep(deltatime);
        step(deltatime);
        poststep(deltatime);
    }
    
    abstract public void prestep(float deltatime);
    abstract public void step(float deltatime);
    abstract public void poststep(float deltatime);
    abstract public void draw(MultiSpriteRegister msr);
}
