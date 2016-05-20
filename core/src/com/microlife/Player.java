package com.microlife;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.microlife.components.AiComponent;
import com.microlife.components.BoundsComponent;
import com.microlife.components.PositionComponent;
import com.microlife.level.Grid;
import com.microlife.level.Map;

import java.util.ArrayList;

/**
 * Created by kongo on 22.04.16.
 */
public class Player extends InputAdapter {
    private ComponentMapper<AiComponent> aiMapper = ComponentMapper.getFor(AiComponent.class);
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);

    private OrthographicCamera camera;
    private Map map;
    private Grid grid;
    private Vector3 mousePosition;
    private Entity pickedMob = null;
    private Rectangle rectangle = null;

    public Player(OrthographicCamera camera, Map map, Grid grid){
        this.camera = camera;
        this.map = map;
        this.grid = grid;
        mousePosition = new Vector3();
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            camera.unproject(mousePosition.set(screenX, screenY, 0));

            rectangle = new Rectangle(mousePosition.x - Map.tileSize, mousePosition.y - Map.tileSize, 2 * Map.tileSize, 2 * Map.tileSize);
            ArrayList<Entity> entities = grid.getEntitiesInRect(rectangle, AiComponent.class);

            for (Entity entity : entities) {
                //if (mob.getBounds().contains(mousePosition.x, mousePosition.y)) {
                //mob.pickUp();
                pickUp(entity);
                break;
                //}
            }
            return true;
        }
        return false;
    }

    public void pickUp(Entity entity){
        pickedMob = entity;
        aiMapper.get(pickedMob).pickedUp = true;

        grid.removeEntity(entity, positionMapper.get(pickedMob).vector);
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if(pickedMob != null){
                drop();
            }
            rectangle = null;
            return true;
        }
        return false;
    }

    public void drop(){
        aiMapper.get(pickedMob).pickedUp = false;
        aiMapper.get(pickedMob).resetPath();
        grid.insertEntity(pickedMob, positionMapper.get(pickedMob).vector);
        pickedMob = null;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        if(pickedMob != null){
            camera.unproject(mousePosition.set(screenX, screenY, 0));

            int x = (int)(mousePosition.x / Map.tileSize);
            if(x >= Map.width)
                x = Map.width-1;
            else if (x < 0)
                x = 0;
            int y = (int)(mousePosition.y / Map.tileSize);
            if(y >= Map.height)
                y = Map.height-1;
            else if (y < 0)
                y = 0;
            if (map.getNode(x, y).type == Map.TILE_FLOOR) {
                positionMapper.get(pickedMob).vector.set(mousePosition.x, mousePosition.y);
                boundsMapper.get(pickedMob).rect.setPosition(mousePosition.x, mousePosition.y);
            }
        }
        return false;
    }

    public void render(ShapeRenderer shapeRenderer){
        if (rectangle != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            shapeRenderer.end();
        }
    }
}
