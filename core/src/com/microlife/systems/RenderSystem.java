package com.microlife.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.microlife.components.BoundsComponent;
import com.microlife.components.VisualComponent;
import com.microlife.level.Map;

/**
 * Created by kongo on 25.04.16.
 */
public class RenderSystem extends EntitySystem {
    private ComponentMapper<VisualComponent> visualMapper = ComponentMapper.getFor(VisualComponent.class);
    private ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    private ImmutableArray<Entity> entities;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Map map;

    public RenderSystem(OrthographicCamera camera, ShapeRenderer shapeRenderer, Map map){
        this.camera = camera;
        this.shapeRenderer = shapeRenderer;
        this.map = map;
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(VisualComponent.class, BoundsComponent.class).get());
    }

    public void update(float deltaTime) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        map.render(shapeRenderer);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            Rectangle bounds = boundsMapper.get(entity).rect;
            shapeRenderer.setColor(visualMapper.get(entity).color);
            shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
        shapeRenderer.end();
    }
}
