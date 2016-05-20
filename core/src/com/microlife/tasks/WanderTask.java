package com.microlife.tasks;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.microlife.components.PositionComponent;
import com.microlife.level.Astar;
import com.microlife.components.AiComponent;

/**
 * Created by kongo on 14.05.16.
 */
public class WanderTask extends LeafTask<AiComponent> {
    private final Vector2 position;
    private final AiComponent aiComponent;
    private final Astar astar;

    public WanderTask(Entity entity, Astar astar){
        this.position = entity.getComponent(PositionComponent.class).vector;
        this.aiComponent = entity.getComponent(AiComponent.class);
        this.astar = astar;
    }

    @Override
    public Status execute () {
        astar.buildRandomPath(aiComponent, position);
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<AiComponent> copyTo (Task<AiComponent> task) {
        return task;
    }
}
