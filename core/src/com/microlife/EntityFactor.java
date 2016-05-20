package com.microlife;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.BranchTask;
import com.badlogic.gdx.ai.btree.branch.DynamicGuardSelector;
import com.badlogic.gdx.ai.btree.branch.Parallel;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.microlife.components.*;
import com.microlife.level.Astar;
import com.microlife.level.Grid;
import com.microlife.tasks.CheckIsPickedTask;
import com.microlife.tasks.FollowPathTask;
import com.microlife.tasks.LookForFoodTask;
import com.microlife.tasks.WanderTask;

/**
 * Created by kongo on 26.04.16.
 */
public class EntityFactor {
    private Engine engine;
    private Grid grid;
    private Astar astar;

    public EntityFactor(Engine engine, Grid grid, Astar astar) {
        this.engine = engine;
        this.grid = grid;
        this.astar = astar;
    }

    public Entity addMob(float x, float y, float width, float height) {
        Entity entity = new Entity();
        engine.addEntity(entity);

        entity.add(new VisualComponent(Color.ROYAL));
        entity.add(new PositionComponent(new Vector2(x, y)));
        grid.insertEntity(entity, x, y);
        entity.add(new BoundsComponent(new Rectangle(x, y, width, height)));
        //entity.add(new VelocityComponent());

        AiComponent aiComponent = new AiComponent();
        entity.add(aiComponent);

        BranchTask<AiComponent> wanderPatch =new Sequence<AiComponent>();
        wanderPatch.addChild(new WanderTask(entity, astar));
        wanderPatch.addChild(new FollowPathTask(entity, grid));

        BranchTask<AiComponent> rightBranch =new Parallel<AiComponent>();
        rightBranch.addChild(new LookForFoodTask(entity, engine, astar, grid));
        rightBranch.addChild(wanderPatch);

        BranchTask<AiComponent> root = new DynamicGuardSelector<AiComponent>();
        rightBranch.setGuard(new CheckIsPickedTask(entity));
        root.addChild(rightBranch);
        aiComponent.btree = new BehaviorTree<AiComponent>(root, null);

        return entity;
    }

    public Entity addFood(float x, float y, float width, float height){
        Entity entity = new Entity();
        engine.addEntity(entity);

        entity.add(new VisualComponent(Color.ORANGE));
        PositionComponent positionComponent = new PositionComponent(new Vector2(x, y));
        entity.add(positionComponent);
        grid.insertEntity(entity, x, y);
        entity.add(new BoundsComponent(new Rectangle(x, y, width, height)));

        //entity.add(new PickableComponent());
        entity.add(new EdibleComponent());

        return entity;
    }
}
