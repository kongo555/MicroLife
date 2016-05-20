package com.microlife.level;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.microlife.components.BoundsComponent;

import java.util.ArrayList;

/**
 * Created by kongo on 24.04.16.
 */
public class Grid {
    public int cellSize = Map.tileSize;
    public int width, height;
    public ArrayList<Entity>[] entitiesInCell;

    public Grid(int width, int height) {
        this.width = width / cellSize;
        this.height = height / cellSize;
        entitiesInCell = new ArrayList[this.width * this.height];
        for (int i = 0; i < this.width * this.height; i++) {
            entitiesInCell[i] = new ArrayList<Entity>();
        }
    }

    public void insertEntity(Entity entity, Vector2 position){
        insertEntity(entity,(int) position.x / cellSize, (int)position.y / cellSize);
    }

    public void insertEntity(Entity entity, float x, float y){
        insertEntity(entity,(int) x /  cellSize, (int) y / cellSize);
    }

    private void insertEntity(Entity entity, int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return;
        entitiesInCell[x + y * width].add(entity);
    }

    public void removeEntity(Entity entity, Vector2 position){
        removeEntity(entity, (int) position.x / cellSize, (int) position.y / cellSize);
    }

    public void removeEntity(Entity entity, int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return;
        boolean t = entitiesInCell[x + y * width].remove(entity);
        //if(t)
            //Gdx.app.log("grid","remove food");
    }

    public void move(Entity entity, Vector2 oldPosition, Vector2 newPosition) {
        // See which cell it was in.
        int oldCellX = (int) (oldPosition.x / cellSize);
        int oldCellY = (int) (oldPosition.y / cellSize);

        // See which cell it's moving to.
        int cellX = (int) (newPosition.x / cellSize);
        int cellY = (int) (newPosition.y / cellSize);

        // If it didn't change cells, we're done.
        if (oldCellX == cellX && oldCellY == cellY)
            return;

        // Unlink it from the list of its old cell.
        removeEntity(entity, oldCellX, oldCellY);

        // Add it back to the grid at its new cell.
        insertEntity(entity, cellX, cellY);
    }

    public ArrayList<Entity> getEntities(Vector2 position) {
        return getEntities(position.x, position.y);
    }

    public ArrayList<Entity> getEntities(float x, float y) {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        int cellX = (int) (x / cellSize);
        int cellY = (int) (y / cellSize);

        System.out.println(cellX + " " + cellY);

        entities.addAll(entitiesInCell[cellX + cellY * width]);
        /*if (position.x > 0 && position.y > 0)
            entities.addAll(entitiesInCell[cellX - 1 + (cellY - 1) * width]);
        if (position.x > 0)
            entities.addAll(entitiesInCell[cellX - 1 + cellY * width]);
        if (position.y > 0)
            entities.addAll(entitiesInCell[cellX + (cellY - 1) * width]);
        if (position.x > 0 && position.y < height * cellSize - 1)
            entities.addAll(entitiesInCell[cellX - 1 + (cellY + 1) * width]);*/

        return entities;
    }

    public <T extends Component> ArrayList<Entity> getEntitiesInRect(Rectangle rect, Class<T> type) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        int xt0 = (int) (rect.x / cellSize);
        int yt0 = (int) (rect.y / cellSize);
        int xt1 = (int) ((rect.x + rect.width) / cellSize);
        int yt1 = (int) ((rect.y + rect.height) / cellSize);
        for (int y = yt0; y <= yt1; y++) {
            for (int x = xt0; x <= xt1; x++) {
                if (x < 0 || y < 0 || x >= width || y >= height)
                    continue;
                for (Entity entity : entitiesInCell[x + y * width]) {
                    Rectangle entityRect = entity.getComponent(BoundsComponent.class).rect;
                    if (rect.overlaps(entityRect) && entity.getComponent(type) != null)
                        result.add(entity);
                }
            }
        }
        return result;
    }

   public <T extends Component> ArrayList<Entity> getEntitiesInCircle(int xCenter, int yCenter, int radius, ComponentMapper<T> componentMapper) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        int x0 = (int) (xCenter / cellSize);
        int y0 = (int) (yCenter / cellSize);
        float radius2 = radius * radius;
        for (int xCircle = -radius; xCircle <= radius; ++xCircle) {
            for (int yCircle = -radius; yCircle <= radius; ++yCircle) {
                int d = xCircle * xCircle + yCircle * yCircle;
                if (d <= radius2) {
                    int x = x0 + xCircle;
                    int y = y0 + yCircle;
                    if (x < 0 || y < 0 || x >= width || y >= height)
                        continue;
                    for (Entity entity : entitiesInCell[x + y * width]) {
                        if(componentMapper.get(entity) != null) {
                            result.add(entity);
                        }
                    }
                }
            }
        }
        return result;
    }
}
