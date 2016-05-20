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
public class AiSystem extends IteratingSystem {
    private ComponentMapper<AiComponent> aiMapper = ComponentMapper.getFor(AiComponent.class);

    public AiSystem() {
        super(Family.all(AiComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float delta) {
        AiComponent aiComponent = aiMapper.get(entity);
        aiComponent.btree.step();
    }

}