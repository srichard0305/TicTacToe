package io.github.StevenRichard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    GameScreen gameScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen(this);
        this.setScreen(gameScreen);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
        
    }
}
