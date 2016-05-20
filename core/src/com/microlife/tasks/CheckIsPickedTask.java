package com.microlife.tasks;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.microlife.components.AiComponent;

/**
 * Created by kongo on 14.05.16.
 */
public class CheckIsPickedTask extends LeafTask<AiComponent> {
    private final AiComponent aiComponent;

    public CheckIsPickedTask(Entity entity) {
        this.aiComponent = entity.getComponent(AiComponent.class);
    }

    @Override
    public Status execute() {
        if(aiComponent.pickedUp) {
            return Status.FAILED;
        }
        else
            return Status.SUCCEEDED;
    }

    @Override
    protected Task<AiComponent> copyTo(Task<AiComponent> task) {
        return task;
    }
}
