package net.jorhlok.oops

/**
 * Some sort of message between entities. Don't put things that must be disposed here.
 * @author Jorhlok
 */
class Postage {
    var ReturnAddress: Entity? = null
    var Name: String = ""
    var Type: String = ""
    var iValue: Int = 0
    var fValue: Float = 0f
    var sValue: String = ""
    var Contents: List<Any>? = null //if the above doesn't suffice


    constructor(e: Entity?, name: String, type: String = "", i: Int = 0, f: Float = 0f, s: String = "", c: List<Any>? = null) {
        ReturnAddress = e
        Name = name
        Type = type
        iValue = i
        fValue = f
        sValue = s
        Contents = c
    }

    constructor(e: Entity?, name: String, type: String, c: List<Any>) {
        ReturnAddress = e
        Name = name
        Type = type
        Contents = c
    }
}
