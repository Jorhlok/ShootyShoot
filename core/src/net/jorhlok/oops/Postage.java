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
    public List<Object> Contents; //if the above doesn't suffice
}
