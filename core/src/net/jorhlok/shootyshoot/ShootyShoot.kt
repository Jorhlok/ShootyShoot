package net.jorhlok.shootyshoot

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import net.jorhlok.multiav.MultiAudioRegister
import net.jorhlok.multiav.MultiGfxRegister
import net.jorhlok.oops.LabelledObject
import net.jorhlok.oops.ObjectOrientedPlaySet


class ShootyShoot : ApplicationAdapter() {
    private var mgr: MultiGfxRegister? = null
    private var audio: MultiAudioRegister? = null
    private var oops: ObjectOrientedPlaySet? = null
    var statetime = 0f


    override fun create() {
        var bigpal = Array<Color>()
        bigpal.add(Color(0f,0f,0f,0f))
        bigpal.add(Color(0f,0f,0f,1f))
        bigpal.add(Color(4/15f,12/15f,2/15f,1f))
        bigpal.add(Color(13/15f,13/15f,13/15f,1f))
        bigpal.add(Color(7/15f,8/15f,1f,1f))
        bigpal.add(Color(0f,0f,0f,1f))
        bigpal.add(Color(4/15f,12/15f,2/15f,1f))
        bigpal.add(Color(13/15f,13/15f,13/15f,1f))
        mgr = MultiGfxRegister()
        mgr!!.palette = bigpal
        audio = MultiAudioRegister()
        mkav()
        mgr!!.Generate()
        mgr!!.camera.setToOrtho(false,640f,360f)
        mgr!!.camera.update()
        audio!!.Generate()

        oops = ObjectOrientedPlaySet()
        oops!!.addTileMap("test0", TmxMapLoader(InternalFileHandleResolver()).load("map/test0.tmx"))
        oops!!.addTileMap("test1", TmxMapLoader(InternalFileHandleResolver()).load("map/test1.tmx"))
        oops!!.addTileMap("test2", TmxMapLoader(InternalFileHandleResolver()).load("map/test2.tmx"))

        val dm = TestDM("test2",mgr!!,audio!!)
        oops!!.addMasterScript("testdm", dm)
        oops!!.launchScript("testdm")
    }


    override fun render() {

        val deltatime = Gdx.graphics.deltaTime
        statetime += deltatime
        if (statetime >= 4) {
            System.out.println("${Gdx.graphics.framesPerSecond} FPS")
            statetime -= 4f
        }

//        val c = mgr!!.getBufCam("main")
//        c!!.translate(Math.sin(statetime*Math.PI/2).toFloat(),Math.cos(statetime*Math.PI/2).toFloat())
//        c!!.rotate(Math.sin(statetime*Math.PI/2).toFloat())
//        c!!.zoom = 0.5f
//        c!!.update()



//        mgr!!.fillShapes()
//        mgr!!.drawCircle(320f,180f,Math.sin(statetime*Math.PI/2).toFloat()*4+12,Color(0.2f,0.3f,1f,1f))

        //game logic
        oops!!.step(deltatime)
        try {
            oops!!.draw(deltatime)
        } catch (e: Exception) {
            System.err.println("Error drawing!")
            e.printStackTrace()
        }

//        mgr!!.drawPal("_girl",4,0f,64f,64f,2f,2f,statetime*90, Vector2())
//        mgr!!.drawPal("_girl",0,0f,16f,16f,2f,2f,statetime*90)
//        mgr!!.drawPal("_girl",0,0f,48f,48f,2f,2f,statetime*90)
//        mgr!!.drawString("libmono","wubba lubba dub dub",Math.round(Math.sin(statetime*Math.PI/2)*64f+320f).toFloat(),Math.round(Math.cos(statetime*Math.PI/2)*64f+180f).toFloat(),1f,1f,0f,Vector2(0.5f,0.5f), mgr!!.palette[2])
//        mgr!!.drawString("libmono","wubba lubba dub dub\n\n  grass tastes bad",Math.sin(statetime*Math.PI/2).toFloat()*64f+320f,Math.cos(statetime*Math.PI/2).toFloat()*64f+180f,2f,1f,statetime*-90,Vector2(0.5f,0.5f), mgr!!.palette[2])
//        mgr!!.drawRgb("pacrt",statetime*3,320f,180f,1f,1f,statetime*-90f+90f)

    }


    override fun resize(width: Int, height: Int) {

    }


    override fun dispose() {
        oops?.dispose()
        mgr?.dispose()
        audio?.dispose()
    }


    fun mkav() {

        mgr?.newBuffer("main",640,360,640f,360f)

        mgr?.newImagePal("imgmap","gfx/imgmap.png",8,8)
        mgr?.newSpritePal("girl","imgmap",0,0,2,2)

        mgr?.newSpritePal("column","imgmap",30,4,2,2)


        mgr?.newMapTileRgb("sprites",241,"_greenblock")
        mgr?.newMapTileRgb("sprites",242,"_skyblueblock")
        mgr?.newMapTileRgb("sprites",243,"_whiteblock")
        mgr?.newMapTileRgb("sprites",244,"_greyblock")

        audio?.newMusic("frcasio", "bgm/FriendlyCasiotone.ogg")
        audio?.newMusic("mkds", "bgm/mkdsintro.ogg", "bgm/mkds.ogg")

        audio?.newSFX("pew", "sfx/pew.wav")
        audio?.newSFX("jump", "sfx/jump.wav")
        audio?.newSFX("dash", "sfx/dash.wav")
        audio?.newSFX("growl", "sfx/growl.wav")


        val generator = FreeTypeFontGenerator(Gdx.files.internal("gfx/libmono.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = 14 * 1
        parameter.magFilter = Texture.TextureFilter.Linear
        parameter.minFilter = Texture.TextureFilter.Linear
        parameter.hinting = FreeTypeFontGenerator.Hinting.Full
        mgr?.newFont("libmono",generator.generateFont(parameter),1f)
        generator.dispose()

        mgr?.newImageRgb("sprites", "gfx/sprites.png", 16, 16)

        mgr?.newSpriteRgb("guyrt", "sprites", 0, 0, 1, 1, false, false)
        mgr?.newSpriteRgb("guylf", "sprites", 0, 0, 1, 1, true, false)
        mgr?.newSpriteRgb("gunrt", "sprites", 1, 0, 1, 1, false, false)
        mgr?.newSpriteRgb("gunlf", "sprites", 1, 0, 1, 1, true, false)
        mgr?.newSpriteRgb("pac0lf", "sprites", 14, 0, 1, 1, false, false)
        mgr?.newSpriteRgb("pac0rt", "sprites", 14, 0, 1, 1, true, false)
        mgr?.newSpriteRgb("pac1lf", "sprites", 15, 0, 1, 1, false, false)
        mgr?.newSpriteRgb("pac1rt", "sprites", 15, 0, 1, 1, true, false)

        mgr?.newSpriteRgb("shot0", "sprites", 0, 1, 1, 1, false, false)
        mgr?.newSpriteRgb("shot1", "sprites", 1, 1, 1, 1, false, false)
        mgr?.newSpriteRgb("shot2", "sprites", 2, 1, 1, 1, false, false)
        mgr?.newSpriteRgb("shot3", "sprites", 3, 1, 1, 1, false, false)

        mgr?.newSpriteRgb("door", "sprites", 0, 2, 1, 1, false, false)
        mgr?.newSpriteRgb("pedestal", "sprites", 1, 2, 1, 1, false, false)
        mgr?.newSpriteRgb("alchemy", "sprites", 2, 2, 2, 1, false, false)

        mgr?.newSpriteRgb("mineral", "sprites", 0, 3, 1, 1, false, false)
        mgr?.newSpriteRgb("herb", "sprites", 1, 3, 1, 1, false, false)
        mgr?.newSpriteRgb("eyeball", "sprites", 2, 3, 1, 1, false, false)
        mgr?.newSpriteRgb("worm", "sprites", 3, 3, 1, 1, false, false)
        mgr?.newSpriteRgb("dollar", "sprites", 4, 3, 1, 1, false, false)
        mgr?.newSpriteRgb("potion", "sprites", 5, 3, 1, 1, false, false)
        mgr?.newSpriteRgb("mana", "sprites", 6, 3, 1, 1, false, false)
        mgr?.newSpriteRgb("coffee", "sprites", 7, 3, 1, 1, false, false)

        mgr?.newSpriteRgb("redbar", "sprites", 0, 4, 1, 1, false, false)
        mgr?.newSpriteRgb("darkredbar", "sprites", 1, 4, 1, 1, false, false)
        mgr?.newSpriteRgb("bluebar", "sprites", 2, 4, 1, 1, false, false)
        mgr?.newSpriteRgb("darkbluebar", "sprites", 3, 4, 1, 1, false, false)
        mgr?.newSpriteRgb("yellowbar", "sprites", 4, 4, 1, 1, false, false)
        mgr?.newSpriteRgb("darkyellowbar", "sprites", 5, 4, 1, 1, false, false)
        mgr?.newSpriteRgb("uiback", "sprites", 6, 4, 1, 1, false, false)
        mgr?.newSpriteRgb("uislot", "sprites", 7, 4, 1, 1, false, false)
        mgr?.newSpriteRgb("cursor", "sprites", 8, 4, 1, 1, false, false)

        mgr?.newSpriteRgb("greenblock", "sprites", 0, 15, 1, 1, false, false)
        mgr?.newSpriteRgb("skyblueblock", "sprites", 1, 15, 1, 1, false, false)
        mgr?.newSpriteRgb("whiteblock", "sprites", 2, 15, 1, 1, false, false)
        mgr?.newSpriteRgb("greyblock", "sprites", 3, 15, 1, 1, false, false)

        mgr?.newAnimRgb("newshot", Array<String>(arrayOf("shot0", "shot1", "shot2")), 0.125f, Animation.PlayMode.NORMAL)
        mgr?.newAnimRgb("shot", Array<String>(arrayOf("shot3", "shot2")), 0.01f, Animation.PlayMode.LOOP)
        mgr?.newAnimRgb("paclf", Array<String>(arrayOf("pac0lf", "pac1lf")), 0.5f, Animation.PlayMode.LOOP)
        mgr?.newAnimRgb("pacrt", Array<String>(arrayOf("pac0rt", "pac1rt")), 0.5f, Animation.PlayMode.LOOP)
    }
}
