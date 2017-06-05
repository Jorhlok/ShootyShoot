package net.jorhlok.oops

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import java.util.LinkedList
import java.util.Queue

/**
 * Some sort of entity. May or may not exist in worldspace.
 * @author Jorhlok
 */
open class Entity {
    //properties
    var Name = ""
    var Type = "Entity"
    var AABB = Rectangle()
    var Physics = false
    var CollEntities = false
    var CollEntWhite: Set<String>? = null
    var CollEntGray: Set<String>? = null
    var CollEntAsTile: Set<String>? = null
    var CollTiles = false
    var CollTileWhite: Set<Int>? = null
    var CollTileGray: Set<Int>? = null
    var Tolerance = Vector2() //the extra space of each axis for tile collisions
    var VerticalFirst = true //order each axis is checked

    //runtime
    var Parent: DungeonMaster? = null
    var Mailbox: Queue<LabelledObject> = LinkedList()
    var CollQueue: Queue<LabelledObject> = LinkedList()
    var Position = Vector2() //in world space
    var Velocity = Vector2() //world units per second
    var Friction = Vector2() //clamps velocity to zero
    var PrePosition = Vector2() //before simple physics
    var PreVelocity = Vector2()
    var AOI = Rectangle()

    open fun begin() {}
    open fun prestep(deltatime: Float) {}
    open fun checkCollEntity(deltatime: Float, e: Entity) = false
    open fun checkCollTile(deltatime: Float, c: TiledMapTileLayer.Cell, x: Int, y: Int) = false
    open fun poststep(deltatime: Float) {}
    open fun draw(obj: LabelledObject) {}
    open fun end() {}

    fun doSimplePhysics(deltatime: Float) {
        PrePosition.set(Position)
        if (Physics) {
            PreVelocity.set(Velocity)
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
        AOI.setPosition(PrePosition).setSize(AABB.width,AABB.height)
    }

    fun collideWithTiles(deltaTime: Float) {
        if (Physics) {
            //do tile collisions
//            var len = CollisionTiles.size
//            CollisionFlags = 0
            val vRect = Rectangle(AABB.x + Position.x + AABB.width * Tolerance.x / 2, AABB.y + Position.y,
                    AABB.width * Tolerance.x, AABB.height)
            val hRect = Rectangle(AABB.x + Position.x, AABB.y + Position.y + AABB.height * Tolerance.y / 2,
                    AABB.width, AABB.height * Tolerance.y)

            //first pass
//            for (i in 0..len - 1) {
//                val t = CollisionTiles.poll()
//                t.CollisionFlags = 0
//                if (t.AABB.overlaps(vRect)) {
//                    //check if it's a bottom hit or a top hit
//                    val tHi = t.AABB.y + t.AABB.height
//                    val pHi = vRect.y + vRect.height
//                    val up = pHi - tHi
//                    val down = t.AABB.y - vRect.y
//                    if (getVelocity().y < 0 || getVelocity().y >= 0 && up >= 0) t.CollisionFlags = t.CollisionFlags or 8
//                    if (getVelocity().y > 0 || getVelocity().y <= 0 && down >= 0) t.CollisionFlags = t.CollisionFlags or 4
//                    if (!t.CollisionUp() && !t.CollisionDown()) t.CollisionFlags = t.CollisionFlags or 8 + 4
//                    CollisionFlags = CollisionFlags or t.CollisionFlags
//                }
//                if (t.AABB.overlaps(hRect)) {
//                    //check if it's a left hit or a right hit
//                    val tRi = t.AABB.x + t.AABB.width
//                    val pRi = hRect.x + hRect.width
//                    val left = t.AABB.x - hRect.x
//                    val right = pRi - tRi
//                    if (getVelocity().x > 0 || getVelocity().x <= 0 && left >= 0) t.CollisionFlags = t.CollisionFlags or 2
//                    if (getVelocity().x < 0 || getVelocity().x >= 0 && right >= 0) t.CollisionFlags = t.CollisionFlags or 1
//                    if (!t.CollisionLeft() && !t.CollisionRight()) t.CollisionFlags = t.CollisionFlags or 2 + 1
//                    CollisionFlags = CollisionFlags or t.CollisionFlags
//                }
//                CollisionTiles.add(t)
//            }

            //second pass
//            for (j in 0..1) {
//                len = CollisionTiles.size
//                for (i in 0..len - 1) {
//                    //this removes non-colliders and finds where things are pushing this
//
//                    val t = CollisionTiles.poll()
//                    if (t.CollisionFlags !== 0) {
//                        CollisionTiles.add(t)
//                        //adjust position if it still overlaps
//                        if (t.AABB.overlaps(Rectangle(getAABB()).setPosition(getPosition().cpy().add(getAABB().x, getAABB().y)))) {
//                            if (j == 0 && VerticalFirst || j == 1 && !VerticalFirst) {
//                                //vertical shunting
//                                if (t.CollisionUp() && !t.CollisionDown()) {
//                                    //top of block
//                                    getPosition().y += t.AABB.y + t.AABB.height - (getPosition().y + getAABB().y)
//                                    getVelocity().y = 0
//                                } else if (t.CollisionDown() && !t.CollisionUp()) {
//                                    //bottom of block
//                                    getPosition().y -= getPosition().y + getAABB().y + getAABB().height - t.AABB.y
//                                    getVelocity().y = 0
//                                }
//                            } else {
//                                //horizontal shunting
//                                if (t.CollisionRight() && !t.CollisionLeft()) {
//                                    //right side of block
//                                    getPosition().x += t.AABB.x + t.AABB.width - (getPosition().x + getAABB().x)
//                                    getVelocity().x = 0
//                                } else if (t.CollisionLeft() && !t.CollisionRight()) {
//                                    //left side of block
//                                    getPosition().x -= getPosition().x + getAABB().x + getAABB().width - t.AABB.x
//                                    getVelocity().x = 0
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }
    }
}
