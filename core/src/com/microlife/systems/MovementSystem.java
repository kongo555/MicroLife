package com.microlife.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.microlife.components.*;
import com.microlife.level.Astar;
import com.microlife.level.Grid;
import com.microlife.level.Map;
import com.microlife.level.astarUtils.Node;

/**
 * Created by kongo on 26.04.16.
 */
public class MovementSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> velocityMapper = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);

    private Map map;
    private Grid grid;
    private Vector2 newPosition = new Vector2();

    public MovementSystem(Map map, Grid grid) {
        super(Family.all(VelocityComponent.class, PositionComponent.class, BoundsComponent.class).get());
        this.map = map;
        this.grid = grid;
    }


    @Override
    public void processEntity(Entity entity, float delta) {
    /*    Vector2 position = positionMapper.get(entity).vector;
        Vector2 velocity = velocityMapper.get(entity).vector;
        Rectangle bounds = boundsMapper.get(entity).rect;


        newPosition.set(position);
        newPosition.add(velocity);
        grid.move(entity, position, newPosition);
        position.set(newPosition);
        bounds.setPosition(position);*/
    }
}