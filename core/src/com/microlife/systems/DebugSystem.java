package com.microlife.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.microlife.components.AiComponent;
import com.microlife.components.PositionComponent;
import com.microlife.level.Map;
import com.microlife.level.astarUtils.Node;

/**
 * Created by kongo on 26.04.16.
 */
public class DebugSystem extends IteratingSystem {
    private ComponentMapper<AiComponent> aiMapper = ComponentMapper.getFor(AiComponent.class);
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private ShapeRenderer shapeRenderer;

    public DebugSystem(ShapeRenderer shapeRenderer) {
        super(Family.all(PositionComponent.class, AiComponent.class).get());
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void processEntity(Entity entity, float delta) {
        AiComponent astarComponent = aiMapper.get(entity);
        Vector2 position = positionMapper.get(entity).vector;

//        renderPath(astarComponent);
        //renderCircle((int)position.x, (int)position.y, 10);
    }

    private void renderPath(AiComponent astarComponent){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        int nodeCount = astarComponent.path.getCount();
        for (int i = 0; i < nodeCount; i++) {
            Node node = astarComponent.path.nodes.get(i);
            shapeRenderer.rect(node.x * Map.tileSize, node.y * Map.tileSize, Map.tileSize, Map.tileSize);
        }
        //if (smooth) {
        if (true) {
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            float hw = Map.tileSize / 2f;
            if (nodeCount > 0) {
                Node prevNode = astarComponent.path.nodes.get(0);
                for (int i = 1; i < nodeCount; i++) {
                    Node node = astarComponent.path.nodes.get(i);
                    shapeRenderer.line(node.x * Map.tileSize + hw, node.y * Map.tileSize + hw, prevNode.x * Map.tileSize + hw, prevNode.y * Map.tileSize + hw);
                    prevNode = node;
                }
            }
        }
        shapeRenderer.end();
    }

    private void renderCircle(int xCenter, int yCenter, int radius){
        int cellSize = Map.tileSize;
        int x0 = (int) (xCenter / cellSize);
        int y0 = (int) (yCenter / cellSize);
        float radius2 = radius * radius;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(Color.RED));
        for (int xCircle = -radius; xCircle <= radius; ++xCircle) {
            for (int yCircle = -radius; yCircle <= radius; ++yCircle) {
                int d = xCircle * xCircle + yCircle * yCircle;
                if (d <= radius2) {
                    int x = x0 + xCircle;
                    int y = y0 + yCircle;
                    shapeRenderer.rect(x* cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
        shapeRenderer.end();
    }
}
