package net.jorhlok.oops

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import java.util.ArrayList
import java.util.LinkedList
import java.util.Queue

/**
 * An entity that exists in the worldspace. Not subject to physics.
 * @author Jorhlok
 */
open class Corporeal : Entity() {
    //properties
    var AABB = Rectangle() //relative to the bottom left corner of the sprite
    var CollidesWith: List<String> = ArrayList() //types of corporeal entities this can collide with
    var Physics = true

    //runtime
    var Position = Vector2() //in world space
    var Rotation = 0f //counterclockwise in degrees
    var Velocity = Vector2() //world units per second
    var Friction = Vector2() //clamps velocity to zero
    var CollideQueue: Queue<Corporeal> = LinkedList()

    override fun update(deltatime: Float) {
        prestep(deltatime)
        if (Physics) doCorporealPhysics(deltatime)
        step(deltatime)
        //collect collisions
        Maestro!!.CorporealCollisions(CollidesWith, CollideQueue, AABB)
        poststep(deltatime)
        //clear queues
        Mailbox.clear()
        CollideQueue.clear()
    }

    protected fun doCorporealPhysics(deltatime: Float) {
        //apply velocity
        Position.add(Velocity.cpy().scl(deltatime))
        //apply friction
        if (Velocity.x > 0) {
            Velocity.x -= Friction.x * deltatime
            if (Velocity.x < 0) Velocity.x = 0f
        } else if (Velocity.x < 0) {
            Velocity.x += Friction.x * deltatime
            if (Velocity.x > 0) Velocity.x = 0f
        }
        if (Velocity.y > 0) {
            Velocity.y -= Friction.y * deltatime
            if (Velocity.y < 0) Velocity.y = 0f
        } else if (Velocity.y < 0) {
            Velocity.y += Friction.y * deltatime
            if (Velocity.y > 0) Velocity.y = 0f
        }
    }
}
