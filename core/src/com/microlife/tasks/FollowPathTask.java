package com.microlife.tasks;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.microlife.components.AiComponent;
import com.microlife.components.BoundsComponent;
import com.microlife.components.PositionComponent;
import com.microlife.level.Grid;
import com.microlife.level.Map;
import com.microlife.level.astarUtils.Node;

/**
 * Created by kongo on 14.05.16.
 */
public class FollowPathTask extends LeafTask<AiComponent> {
    private final Entity entity;
    private final Vector2 position;
    private final Rectangle bounds;
    private final AiComponent aiComponent;
    private final Grid grid;

    public FollowPathTask(Entity entity, Grid grid) {
        this.entity = entity;
        this.grid = grid;
        this.position = entity.getComponent(PositionComponent.class).vector;
        this.aiComponent = entity.getComponent(AiComponent.class);
        this.bounds = entity.getComponent(BoundsComponent.class).rect;
    }

    @Override
    public Task.Status execute () {
        if(aiComponent.path.getCount() != 0) {
            followPath();
            return Status.RUNNING;
        }
        return Task.Status.SUCCEEDED;
    }

    @Override
    protected Task<AiComponent> copyTo (Task<AiComponent> task) {
        return task;
    }

    public void followPath() {
        Node target = aiComponent.path.get(aiComponent.pathIndex);
        int x = Math.abs(((int) position.x / Map.tileSize) - target.x);
        int y = Math.abs(((int) position.y / Map.tileSize) - target.y);
        if (x == 0.0f && y == 0.0f) {
            aiComponent.pathIndex++;
            if (aiComponent.pathIndex == aiComponent.path.getCount()) { // end of path
                aiComponent.resetPath();
            }
        }
        else {
            Vector2 targetDirection = new Vector2(target.x, target.y).sub(position.x / Map.tileSize, position.y / Map.tileSize);
            //fixme speed
            float speed = 1;
            targetDirection.nor();
            targetDirection.scl(speed);

            move(target, targetDirection.scl(speed));
        }
    }


    private void move(Node target, Vector2 shift){
        Vector2 newPosition = new Vector2(position).add(shift);
        if((shift.x > 0 && newPosition.x > target.x * Map.tileSize) || (shift.x < 0 && newPosition.x < target.x * Map.tileSize)) {
            newPosition.x = target.x * Map.tileSize;
        }

        if((shift.y > 0 && newPosition.y > target.y * Map.tileSize) || (shift.y < 0 && newPosition.y < target.y * Map.tileSize)) {
            newPosition.y = target.y * Map.tileSize;
        }

        grid.move(entity, position, newPosition);
        position.set(newPosition);
        bounds.setPosition(position);
    }
}
