package com.microlife.level;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.microlife.EntityFactor;
import com.microlife.Main;
import com.microlife.Player;
import com.microlife.level.astarUtils.Node;
import com.microlife.systems.*;

/**
 * Created by kongo on 25.04.16.
 */
public class Level {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    private Engine engine;
    private Map map;
    private Grid grid;
    private Astar astar;
    private EntityFactor entityFactor;
    private Player player;

    private FPSLogger fpsLogger = new FPSLogger();

    public Level(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Main.width, Main.height);
        shapeRenderer = new ShapeRenderer();

        map = new Map();
        astar = new Astar(map);
        grid = new Grid(Main.width, Main.height);

        engine = new Engine();
        engine.addSystem(new RenderSystem(camera, shapeRenderer, map));
        engine.addSystem(new DebugSystem(shapeRenderer));
        engine.addSystem(new AiSystem());
        engine.addSystem(new MovementSystem(map, grid));

        entityFactor = new EntityFactor(engine, grid, astar);
        int mobsSize = 1;
        for (int i = 0; i < mobsSize; i++) {
            while(true) {
                int x = MathUtils.random(Map.width-1);
                int y = MathUtils.random(Map.height-1);
                Node startNode = map.getNode(x, y);
                if (startNode.type == Map.TILE_FLOOR) {
                    entityFactor.addMob(x * Map.tileSize, y * Map.tileSize, Map.tileSize, Map.tileSize);
                    break;
                }
            }
        }
        int foodSize = 20;
        for (int i = 0; i < foodSize; i++) {
            while(true) {
                int x = MathUtils.random(Map.width-1);
                int y = MathUtils.random(Map.height-1);
                Node startNode = map.getNode(x, y);
                if (startNode.type == Map.TILE_FLOOR) {
                    entityFactor.addFood(x * Map.tileSize, y * Map.tileSize, Map.tileSize, Map.tileSize);
                    break;
                }
            }
        }

        player = new Player(camera, map, grid);
        Gdx.input.setInputProcessor(player);
    }

    public void update(float delta){
        engine.update(delta);
        player.render(shapeRenderer);
        fpsLogger.log();
    }

    public Map getMap() {
        return map;
    }

    public Grid getGrid() {
        return grid;
    }

    public Astar getAstar() {
        return astar;
    }
}
