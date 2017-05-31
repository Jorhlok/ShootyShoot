package net.jorhlok.oops;

import java.util.LinkedList;
import java.util.Queue;
import net.jorhlok.multiav.MultiGfxRegister;

/**
 * Some sort of entity. May or may not exist in worldspace.
 * @author Jorhlok
 */
public class Entity {
    public String Name = "";
    public String Type = "";
    public Queue<Postage> Mailbox = new LinkedList<Postage>();
    public DungeonMaster Maestro;
    
    public void update(float deltatime) {
        prestep(deltatime);
        step(deltatime);
        poststep(deltatime);
        Mailbox.clear();
    }
    
    public void create() {}
    public void prestep(float deltatime) {}
    public void step(float deltatime) {}
    public void poststep(float deltatime) {}
    public void draw(MultiGfxRegister mav) {}
}
