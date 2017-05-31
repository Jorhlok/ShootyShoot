package net.jorhlok.shootyshoot

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import net.jorhlok.multiav.MultiGfxRegister
import net.jorhlok.oops.ObjectOrientedPlaySet


class ShootyShoot : ApplicationAdapter() {
//    private val camera = OrthographicCamera()
//    private val campos = Vector2()
    private var mav: MultiGfxRegister? = null
    private var oops: ObjectOrientedPlaySet? = null
    var fb: FrameBuffer? = null
    var fbtr: TextureRegion? = null
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
        mav = MultiGfxRegister()
        mav!!.palette = bigpal
        mkav()
        mav!!.Generate()
        fb = FrameBuffer(Pixmap.Format.RGB888,640,360,false)
        fb?.colorBufferTexture?.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Nearest)
        fbtr = TextureRegion(fb?.colorBufferTexture)
        fbtr?.flip(false,true)
        var cam = OrthographicCamera()
        cam.setToOrtho(false,640f,360f)
        cam.update()
        mav?.batch?.projectionMatrix = cam.combined



//        mav.setMusVolume(0.5f)

        oops = ObjectOrientedPlaySet()
        oops!!.setMAV(mav)
        oops!!.addTileMap("test0", TmxMapLoader(InternalFileHandleResolver()).load("map/test0.tmx"))
        oops!!.addTileMap("test1", TmxMapLoader(InternalFileHandleResolver()).load("map/test1.tmx"))
        oops!!.addEntityType("testplat", TestPlatformer::class.java)

        val dm = TestDM("test1", null)
        dm.cam = cam
        oops!!.addMasterScript("testdm", dm)
        oops!!.launchScript("testdm")
    }


    override fun render() {

        val deltatime = Gdx.graphics.deltaTime
        statetime += deltatime
        if (statetime >= 4) {
            System.out.println("${Gdx.graphics.framesPerSecond} FPS")
            statetime -= 4f
            val f = 1f
            mav!!.palette[2].set(Math.round(Math.random()*f).toFloat()/f,Math.round(Math.random()*f).toFloat()/f,Math.round(Math.random()*f).toFloat()/f,1f)
        }

        fb!!.begin()
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        //game logic
        oops!!.step(deltatime)
        try {
            oops!!.draw(deltatime)
        } catch (e: Exception) {
            System.err.println("Error drawing!")
            e.printStackTrace()
        }

        mav!!.batch!!.begin()

//        mav!!.drawString("hullo",0f,346f)
//        mav!!.Font!!.draw(mav!!.batch!!,"hullo\nbye",0f,358f)
//        mav!!.frame.get("girl")?.drawPal(mav!!.batch!!,pal,bigpal,Math.sin(statetime*Math.PI/2).toFloat()*64f,Math.cos(statetime*Math.PI/2).toFloat()*64f,1f,1f)
        mav!!.drawPal("_girl",4,0f,72f,72f,2f,2f,statetime*90, Vector2())
        mav!!.drawPal("_girl",0,0f,8f,8f,2f,2f,statetime*90)
        mav!!.drawPal("_girl",0,0f,40f,40f,2f,2f,statetime*90)
        mav!!.drawString("libmono","wubba lubba dub dub",320f,180f)
        mav!!.drawRgb("pacrt",statetime,320f,180f,1f,1f,0f,Vector2())
        mav!!.batch!!.end()
        fb!!.end()
        mav!!.batch!!.begin()
        mav!!.batch!!.draw(fbtr,0f,0f)
        mav!!.batch!!.end()



    }


    override fun resize(width: Int, height: Int) {

    }


    override fun dispose() {
        oops?.dispose()
        mav?.dispose()
    }


    fun mkav() {

        mav?.newImagePal("imgmap","gfx/imgmap.png",16,16)
        mav?.newSpritePal("girl","imgmap",0,0)

//        mav.newMusic("frcasio", "bgm/FriendlyCasiotone.ogg")
//        mav.newMusic("mkds", "bgm/mkdsintro.ogg", "bgm/mkds.ogg")
//
//        mav.newSFX("pew", "sfx/pew.wav")
//        mav.newSFX("jump", "sfx/jump.wav")
//        mav.newSFX("dash", "sfx/dash.wav")
//        mav.newSFX("growl", "sfx/growl.wav")
//
        val generator = FreeTypeFontGenerator(Gdx.files.internal("gfx/libmono.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = 14 * 1
        parameter.kerning = true
        parameter.genMipMaps = true
        parameter.magFilter = Texture.TextureFilter.Nearest
        parameter.minFilter = Texture.TextureFilter.Linear
        //parameter.characters = parameter.characters + "▄■";
        parameter.hinting = FreeTypeFontGenerator.Hinting.Full
        mav?.newFont("libmono",generator.generateFont(parameter))
//        mav?.setFont(generator.generateFont(parameter), parameter.characters)
//        mav?.fontSampling = 1f
        generator.dispose()

        mav?.newImageRgb("sprites", "gfx/sprites.png", 16, 16)

        mav?.newSpriteRgb("guyrt", "sprites", 0, 0, 1, 1, false, false)
        mav?.newSpriteRgb("guylf", "sprites", 0, 0, 1, 1, true, false)
        mav?.newSpriteRgb("gunrt", "sprites", 1, 0, 1, 1, false, false)
        mav?.newSpriteRgb("gunlf", "sprites", 1, 0, 1, 1, true, false)
        mav?.newSpriteRgb("pac0lf", "sprites", 14, 0, 1, 1, false, false)
        mav?.newSpriteRgb("pac0rt", "sprites", 14, 0, 1, 1, true, false)
        mav?.newSpriteRgb("pac1lf", "sprites", 15, 0, 1, 1, false, false)
        mav?.newSpriteRgb("pac1rt", "sprites", 15, 0, 1, 1, true, false)

        mav?.newSpriteRgb("shot0", "sprites", 0, 1, 1, 1, false, false)
        mav?.newSpriteRgb("shot1", "sprites", 1, 1, 1, 1, false, false)
        mav?.newSpriteRgb("shot2", "sprites", 2, 1, 1, 1, false, false)
        mav?.newSpriteRgb("shot3", "sprites", 3, 1, 1, 1, false, false)

        mav?.newSpriteRgb("door", "sprites", 0, 2, 1, 1, false, false)
        mav?.newSpriteRgb("pedestal", "sprites", 1, 2, 1, 1, false, false)
        mav?.newSpriteRgb("alchemy", "sprites", 2, 2, 2, 1, false, false)

        mav?.newSpriteRgb("mineral", "sprites", 0, 3, 1, 1, false, false)
        mav?.newSpriteRgb("herb", "sprites", 1, 3, 1, 1, false, false)
        mav?.newSpriteRgb("eyeball", "sprites", 2, 3, 1, 1, false, false)
        mav?.newSpriteRgb("worm", "sprites", 3, 3, 1, 1, false, false)
        mav?.newSpriteRgb("dollar", "sprites", 4, 3, 1, 1, false, false)
        mav?.newSpriteRgb("potion", "sprites", 5, 3, 1, 1, false, false)
        mav?.newSpriteRgb("mana", "sprites", 6, 3, 1, 1, false, false)
        mav?.newSpriteRgb("coffee", "sprites", 7, 3, 1, 1, false, false)

        mav?.newSpriteRgb("redbar", "sprites", 0, 4, 1, 1, false, false)
        mav?.newSpriteRgb("darkredbar", "sprites", 1, 4, 1, 1, false, false)
        mav?.newSpriteRgb("bluebar", "sprites", 2, 4, 1, 1, false, false)
        mav?.newSpriteRgb("darkbluebar", "sprites", 3, 4, 1, 1, false, false)
        mav?.newSpriteRgb("yellowbar", "sprites", 4, 4, 1, 1, false, false)
        mav?.newSpriteRgb("darkyellowbar", "sprites", 5, 4, 1, 1, false, false)
        mav?.newSpriteRgb("uiback", "sprites", 6, 4, 1, 1, false, false)
        mav?.newSpriteRgb("uislot", "sprites", 7, 4, 1, 1, false, false)
        mav?.newSpriteRgb("cursor", "sprites", 8, 4, 1, 1, false, false)

        mav?.newSpriteRgb("greenblock", "sprites", 0, 15, 1, 1, false, false)
        mav?.newSpriteRgb("skyblueblock", "sprites", 1, 15, 1, 1, false, false)
        mav?.newSpriteRgb("whiteblock", "sprites", 2, 15, 1, 1, false, false)
        mav?.newSpriteRgb("greyblock", "sprites", 3, 15, 1, 1, false, false)

        mav?.newAnimRgb("newshot", Array<String>(arrayOf("shot0", "shot1", "shot2")), 0.125f, Animation.PlayMode.NORMAL)
        mav?.newAnimRgb("shot", Array<String>(arrayOf("shot3", "shot2")), 0.01f, Animation.PlayMode.LOOP)
        mav?.newAnimRgb("paclf", Array<String>(arrayOf("pac0lf", "pac1lf")), 0.5f, Animation.PlayMode.LOOP)
        mav?.newAnimRgb("pacrt", Array<String>(arrayOf("pac0rt", "pac1rt")), 0.5f, Animation.PlayMode.LOOP)
    }
}
