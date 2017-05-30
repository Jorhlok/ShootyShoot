package net.jorhlok.shootyshoot

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import net.jorhlok.multiav.MultiAVRegister


class ShootyShoot : ApplicationAdapter() {
//    private val camera = OrthographicCamera()
//    private val campos = Vector2()
    private var mav: MultiAVRegister? = null
//    private var oops: ObjectOrientedPlaySet? = null
    var fb: FrameBuffer? = null
    var fbtr: TextureRegion? = null
    var statetime = 0f


    override fun create() {
//        mkav()
//        mav.Generate()
//        mav.batch = SpriteBatch()
//        camera.setToOrtho(false, (640 / 16).toFloat(), (360 / 16).toFloat())
//        campos.x = (640 / 32).toFloat()
//        camera.position.x = campos.x
//        campos.y = (360 / 32).toFloat()
//        camera.position.y = campos.y
//        mav.scale = Vector2(1f / 16, 1f / 16)
//        mav.camPos = campos
//        mav.setMusVolume(0.5f)

//        oops = ObjectOrientedPlaySet()
//        oops.setMAV(mav)
//        oops.addTileMap("test0", TmxMapLoader(InternalFileHandleResolver()).load("map/test0.tmx"))
//        oops.addTileMap("test1", TmxMapLoader(InternalFileHandleResolver()).load("map/test1.tmx"))
//        oops.addEntityType("testplat", TestPlatformer::class.java)

//        val dm = TestDM("test1", null)
//        dm.cam = camera
//        oops.addMasterScript("testdm", dm)
//        oops.launchScript("testdm")
        var bigpal = Array<Color>()
        bigpal.add(Color(0f,0f,0f,0f))
        bigpal.add(Color(0f,0f,0f,1f))
        bigpal.add(Color(4/15f,12/15f,2/15f,1f))
        bigpal.add(Color(13/15f,13/15f,13/15f,1f))
        bigpal.add(Color(7/15f,8/15f,1f,1f))
        bigpal.add(Color(0f,0f,0f,1f))
        bigpal.add(Color(4/15f,12/15f,2/15f,1f))
        bigpal.add(Color(13/15f,13/15f,13/15f,1f))
        mav = MultiAVRegister()
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
        mav!!.batch!!.begin()
        mav!!.drawString("hullo",0f,346f)
//        mav!!.Font!!.draw(mav!!.batch!!,"hullo\nbye",0f,358f)
//        mav!!.frame.get("girl")?.drawPal(mav!!.batch!!,pal,bigpal,Math.sin(statetime*Math.PI/2).toFloat()*64f,Math.cos(statetime*Math.PI/2).toFloat()*64f,1f,1f)
        mav!!.drawPal("_girl",4,0f,72f,72f,2f,2f,statetime*90, Vector2())
        mav!!.drawPal("_girl",0,0f,8f,8f,2f,2f,statetime*90)
        mav!!.drawPal("_girl",0,0f,40f,40f,2f,2f,statetime*90)
        mav!!.batch!!.end()
        fb!!.end()
        mav!!.batch!!.begin()
        mav!!.batch!!.draw(fbtr,0f,0f)
        mav!!.batch!!.end()

        //game logic
//        oops.step(deltatime)

//        camera.update()
//        mav.batch?.projectionMatrix = camera.combined
//        try {
//            oops.drawPal(deltatime)
//        } catch (e: Exception) {
//            System.err.println("Error drawing!")
//            e.printStackTrace()
//        }

    }


    override fun resize(width: Int, height: Int) {

    }


    override fun dispose() {
//        oops.dispose()
//        mav.dispose()
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
        mav?.setFont(generator.generateFont(parameter), parameter.characters)
        mav?.fontSampling = 1f
        generator.dispose()
//
//        mav.newImagePal("sprites", "gfx/sprites.png", 16, 16)
//
//        mav.newSpritePal("guyrt", "sprites", 0, 0, 1, 1, false, false)
//        mav.newSpritePal("guylf", "sprites", 0, 0, 1, 1, true, false)
//        mav.newSpritePal("gunrt", "sprites", 1, 0, 1, 1, false, false)
//        mav.newSpritePal("gunlf", "sprites", 1, 0, 1, 1, true, false)
//        mav.newSpritePal("pac0lf", "sprites", 14, 0, 1, 1, false, false)
//        mav.newSpritePal("pac0rt", "sprites", 14, 0, 1, 1, true, false)
//        mav.newSpritePal("pac1lf", "sprites", 15, 0, 1, 1, false, false)
//        mav.newSpritePal("pac1rt", "sprites", 15, 0, 1, 1, true, false)
//
//        mav.newSpritePal("shot0", "sprites", 0, 1, 1, 1, false, false)
//        mav.newSpritePal("shot1", "sprites", 1, 1, 1, 1, false, false)
//        mav.newSpritePal("shot2", "sprites", 2, 1, 1, 1, false, false)
//        mav.newSpritePal("shot3", "sprites", 3, 1, 1, 1, false, false)
//
//        mav.newSpritePal("door", "sprites", 0, 2, 1, 1, false, false)
//        mav.newSpritePal("pedestal", "sprites", 1, 2, 1, 1, false, false)
//        mav.newSpritePal("alchemy", "sprites", 2, 2, 2, 1, false, false)
//
//        mav.newSpritePal("mineral", "sprites", 0, 3, 1, 1, false, false)
//        mav.newSpritePal("herb", "sprites", 1, 3, 1, 1, false, false)
//        mav.newSpritePal("eyeball", "sprites", 2, 3, 1, 1, false, false)
//        mav.newSpritePal("worm", "sprites", 3, 3, 1, 1, false, false)
//        mav.newSpritePal("dollar", "sprites", 4, 3, 1, 1, false, false)
//        mav.newSpritePal("potion", "sprites", 5, 3, 1, 1, false, false)
//        mav.newSpritePal("mana", "sprites", 6, 3, 1, 1, false, false)
//        mav.newSpritePal("coffee", "sprites", 7, 3, 1, 1, false, false)
//
//        mav.newSpritePal("redbar", "sprites", 0, 4, 1, 1, false, false)
//        mav.newSpritePal("darkredbar", "sprites", 1, 4, 1, 1, false, false)
//        mav.newSpritePal("bluebar", "sprites", 2, 4, 1, 1, false, false)
//        mav.newSpritePal("darkbluebar", "sprites", 3, 4, 1, 1, false, false)
//        mav.newSpritePal("yellowbar", "sprites", 4, 4, 1, 1, false, false)
//        mav.newSpritePal("darkyellowbar", "sprites", 5, 4, 1, 1, false, false)
//        mav.newSpritePal("uiback", "sprites", 6, 4, 1, 1, false, false)
//        mav.newSpritePal("uislot", "sprites", 7, 4, 1, 1, false, false)
//        mav.newSpritePal("cursor", "sprites", 8, 4, 1, 1, false, false)
//
//        mav.newSpritePal("greenblock", "sprites", 0, 15, 1, 1, false, false)
//        mav.newSpritePal("skyblueblock", "sprites", 1, 15, 1, 1, false, false)
//        mav.newSpritePal("whiteblock", "sprites", 2, 15, 1, 1, false, false)
//        mav.newSpritePal("greyblock", "sprites", 3, 15, 1, 1, false, false)
//
//        mav.newAnimPal("newshot", arrayOf("shot0", "shot1", "shot2"), 0.125f, Animation.PlayMode.NORMAL)
//        mav.newAnimPal("shot", arrayOf("shot3", "shot2"), 0.01f, Animation.PlayMode.LOOP)
//        mav.newAnimPal("paclf", arrayOf("pac0lf", "pac1lf"), 0.5f, Animation.PlayMode.LOOP)
//        mav.newAnimPal("pacrt", arrayOf("pac0rt", "pac1rt"), 0.5f, Animation.PlayMode.LOOP)
    }
}
