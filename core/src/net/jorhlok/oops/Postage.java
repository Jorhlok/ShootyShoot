package net.jorhlok.oops;

import java.util.List;

/**
 * Some sort of message between entities. Don't put things that must be disposed here.
 * @author Jorhlok
 */
public class Postage {
    public Entity ReturnAddress;
    public String Name;
    public String Type;
    public int iValue;
    public float fValue;
    public String sValue;
    public List<Object> Contents; //if the above doesn't suffice
    
    public Postage() {
        ReturnAddress = null;
        Name = null;
        Type = null;
        iValue = 0;
        fValue = 0;
        sValue = null;
        Contents = null;
    }
    
    public Postage(Entity e, String name, String type) {
        ReturnAddress = e;
        Name = name;
        Type = type;
        iValue = 0;
        fValue = 0;
        sValue = null;
        Contents = null;
    }
    
    public Postage(Entity e, String name, String type, int i) {
        ReturnAddress = e;
        Name = name;
        Type = type;
        iValue = i;
        fValue = 0;
        sValue = null;
        Contents = null;
    }
    
    public Postage(Entity e, String name, String type, int i, float f, String s) {
        ReturnAddress = e;
        Name = name;
        Type = type;
        iValue = i;
        fValue = f;
        sValue = s;
        Contents = null;
    }
    
    public Postage(Entity e, String name, String type, int i, float f, String s, List<Object> c) {
        ReturnAddress = e;
        Name = name;
        Type = type;
        iValue = i;
        fValue = f;
        sValue = s;
        Contents = c;
    }
    
    public Postage(Entity e, String name, String type, List<Object> c) {
        ReturnAddress = e;
        Name = name;
        Type = type;
        iValue = 0;
        fValue = 0;
        sValue = null;
        Contents = c;
    }
}
