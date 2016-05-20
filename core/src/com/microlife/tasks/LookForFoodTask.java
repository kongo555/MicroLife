package com.microlife.tasks;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.microlife.components.*;
import com.microlife.level.Astar;
import com.microlife.level.Grid;
import com.microlife.level.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by kongo on 14.05.16.
 */
public class LookForFoodTask extends LeafTask<AiComponent> {
    private final Vector2 position;
    private final Rectangle bounds;
    private final AiComponent aiComponent;
    private final Astar astar;
    private final Grid grid;
    private final Engine engine;
    public boolean goingForfood = false;
    private final PositionComparator positionComparator;

    private static final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private static final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    private static final ComponentMapper<EdibleComponent> edibleMapper = ComponentMapper.getFor(EdibleComponent.class);

    public LookForFoodTask(Entity entity, Engine engine, Astar astar, Grid grid) {
        this.engine = engine;
        this.position = positionMapper.get(entity).vector;
        this.bounds = boundsMapper.get(entity).rect;
        this.aiComponent = entity.getComponent(AiComponent.class);
        this.astar = astar;
        this.grid = grid;
        positionComparator = new PositionComparator();
    }
    @Override
    public Status execute () {
        searachForFood();
        return Status.SUCCEEDED;
    }

    public void searachForFood() {
        ArrayList<Entity> foods = grid.getEntitiesInCircle((int)position.x, (int)position.y, 10, edibleMapper);
        if(!foods.isEmpty()){
            //Gdx.app.log("mob","food");
            positionComparator.position = position;
            Collections.sort(foods, positionComparator);
            Entity food = foods.get(0);
            foods.clear();
            if(bounds.overlaps(boundsMapper.get(food).rect)){
                grid.removeEntity(food, positionMapper.get(food).vector);
                engine.removeEntity(food);
            }
            else {
                Vector2 foodPosition = positionMapper.get(food).vector;
                int x = (int)foodPosition.x / Map.tileSize;
                int y = (int)foodPosition.y / Map.tileSize;
                buildPath(aiComponent, position, x, y);
                goingForfood = true;
            }
        }
        else if(goingForfood){
            aiComponent.resetPath();
            goingForfood = false;
        }
    }

    private void buildPath(AiComponent astarComponent, Vector2 position, int x, int y){
        astar.updatePath(position, x, y, astarComponent.path, true);
        astarComponent.pathIndex = 1;
    }

    private class PositionComparator implements Comparator<Entity> {
        public Vector2 position;

        @Override
        public int compare(Entity e1, Entity e2) {
            return (int)Math.signum(positionMapper.get(e1).vector.dst(position) - positionMapper.get(e2).vector.dst(position));
        }
    }

    @Override
    protected Task<AiComponent> copyTo (Task<AiComponent> task) {
        return task;
    }
}
