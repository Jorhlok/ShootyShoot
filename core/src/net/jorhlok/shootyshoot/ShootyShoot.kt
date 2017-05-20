package net.jorhlok.shootyshoot

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import net.jorhlok.multiav.MultiAVRegister
import net.jorhlok.oops.ObjectOrientedPlaySet


class ShootyShoot : ApplicationAdapter() {
    private val camera = OrthographicCamera()
    private val campos = Vector2()
    private var mav: MultiAVRegister? = null
    private var oops: ObjectOrientedPlaySet? = null


    override fun create() {
        mav = MultiAVRegister()
        mkav()
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

        oops = ObjectOrientedPlaySet()
//        oops.setMAV(mav)
//        oops.addTileMap("test0", TmxMapLoader(InternalFileHandleResolver()).load("map/test0.tmx"))
//        oops.addTileMap("test1", TmxMapLoader(InternalFileHandleResolver()).load("map/test1.tmx"))
//        oops.addEntityType("testplat", TestPlatformer::class.java)

//        val dm = TestDM("test1", null)
//        dm.cam = camera
//        oops.addMasterScript("testdm", dm)
//        oops.launchScript("testdm")

    }


    override fun render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val deltatime = Gdx.graphics.deltaTime

        //game logic
//        oops.step(deltatime)

//        camera.update()
//        mav.batch?.projectionMatrix = camera.combined
//        try {
//            oops.draw(deltatime)
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
//        mav.newMusic("frcasio", "bgm/FriendlyCasiotone.ogg")
//        mav.newMusic("mkds", "bgm/mkdsintro.ogg", "bgm/mkds.ogg")
//
//        mav.newSFX("pew", "sfx/pew.wav")
//        mav.newSFX("jump", "sfx/jump.wav")
//        mav.newSFX("dash", "sfx/dash.wav")
//        mav.newSFX("growl", "sfx/growl.wav")
//
//        val generator = FreeTypeFontGenerator(Gdx.files.internal("gfx/libmono.ttf"))
//        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
//        parameter.size = 14 * 6
//        parameter.genMipMaps = true
//        parameter.magFilter = Texture.TextureFilter.Nearest
//        parameter.minFilter = Texture.TextureFilter.Linear
//        //parameter.characters = parameter.characters + "▄■";
//        parameter.hinting = Hinting.Full
//        mav.setFont(generator.generateFont(parameter), parameter.characters)
//        mav.fontSampling = 6f
//        generator.dispose()
//
//        mav.newImage("sprites", "gfx/sprites.png", 16, 16)
//
//        mav.newSprite("guyrt", "sprites", 0, 0, 1, 1, false, false)
//        mav.newSprite("guylf", "sprites", 0, 0, 1, 1, true, false)
//        mav.newSprite("gunrt", "sprites", 1, 0, 1, 1, false, false)
//        mav.newSprite("gunlf", "sprites", 1, 0, 1, 1, true, false)
//        mav.newSprite("pac0lf", "sprites", 14, 0, 1, 1, false, false)
//        mav.newSprite("pac0rt", "sprites", 14, 0, 1, 1, true, false)
//        mav.newSprite("pac1lf", "sprites", 15, 0, 1, 1, false, false)
//        mav.newSprite("pac1rt", "sprites", 15, 0, 1, 1, true, false)
//
//        mav.newSprite("shot0", "sprites", 0, 1, 1, 1, false, false)
//        mav.newSprite("shot1", "sprites", 1, 1, 1, 1, false, false)
//        mav.newSprite("shot2", "sprites", 2, 1, 1, 1, false, false)
//        mav.newSprite("shot3", "sprites", 3, 1, 1, 1, false, false)
//
//        mav.newSprite("door", "sprites", 0, 2, 1, 1, false, false)
//        mav.newSprite("pedestal", "sprites", 1, 2, 1, 1, false, false)
//        mav.newSprite("alchemy", "sprites", 2, 2, 2, 1, false, false)
//
//        mav.newSprite("mineral", "sprites", 0, 3, 1, 1, false, false)
//        mav.newSprite("herb", "sprites", 1, 3, 1, 1, false, false)
//        mav.newSprite("eyeball", "sprites", 2, 3, 1, 1, false, false)
//        mav.newSprite("worm", "sprites", 3, 3, 1, 1, false, false)
//        mav.newSprite("dollar", "sprites", 4, 3, 1, 1, false, false)
//        mav.newSprite("potion", "sprites", 5, 3, 1, 1, false, false)
//        mav.newSprite("mana", "sprites", 6, 3, 1, 1, false, false)
//        mav.newSprite("coffee", "sprites", 7, 3, 1, 1, false, false)
//
//        mav.newSprite("redbar", "sprites", 0, 4, 1, 1, false, false)
//        mav.newSprite("darkredbar", "sprites", 1, 4, 1, 1, false, false)
//        mav.newSprite("bluebar", "sprites", 2, 4, 1, 1, false, false)
//        mav.newSprite("darkbluebar", "sprites", 3, 4, 1, 1, false, false)
//        mav.newSprite("yellowbar", "sprites", 4, 4, 1, 1, false, false)
//        mav.newSprite("darkyellowbar", "sprites", 5, 4, 1, 1, false, false)
//        mav.newSprite("uiback", "sprites", 6, 4, 1, 1, false, false)
//        mav.newSprite("uislot", "sprites", 7, 4, 1, 1, false, false)
//        mav.newSprite("cursor", "sprites", 8, 4, 1, 1, false, false)
//
//        mav.newSprite("greenblock", "sprites", 0, 15, 1, 1, false, false)
//        mav.newSprite("skyblueblock", "sprites", 1, 15, 1, 1, false, false)
//        mav.newSprite("whiteblock", "sprites", 2, 15, 1, 1, false, false)
//        mav.newSprite("greyblock", "sprites", 3, 15, 1, 1, false, false)
//
//        mav.newAnim("newshot", arrayOf("shot0", "shot1", "shot2"), 0.125f, Animation.PlayMode.NORMAL)
//        mav.newAnim("shot", arrayOf("shot3", "shot2"), 0.01f, Animation.PlayMode.LOOP)
//        mav.newAnim("paclf", arrayOf("pac0lf", "pac1lf"), 0.5f, Animation.PlayMode.LOOP)
//        mav.newAnim("pacrt", arrayOf("pac0rt", "pac1rt"), 0.5f, Animation.PlayMode.LOOP)
    }
}
