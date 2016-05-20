package com.microlife.Test;


import java.util.ArrayList;

/**
 * Created by kongo on 19.04.16.
 */
public class Mob{
    /*private final Astar astar;

    private float speed = 1f;
    private TiledSmoothableGraphPath<FlatTiledNode> path;
    int pathIndex = 1;
    private float delay = 2;
    private float time = delay;
    private boolean pickedUp;
    private boolean goingForfood = false;

    public Mob(Color color, int x, int y, Astar astar, Grid grid, EntityManager entityManager){
        super(color, x, y, grid, entityManager);
        this.astar = astar;
        path = new TiledSmoothableGraphPath<FlatTiledNode>();
    }

    public void update(float delta){
        //todo optimize
        ArrayList<Food> foods = grid.getEntitiesInCircle((int)position.x, (int)position.y, 10, Food.class);
        if(!foods.isEmpty()){
            Gdx.app.log("mob","food");
            Food food = foods.get(0);
            foods.clear();
            if(bounds.overlaps(food.bounds)){
                entityManager.removeEntity(food);
                resetPath();
            }
            else {
                buildPath((int) (food.position.x / Main.width), (int) (food.position.y / Main.width));
                goingForfood = true;
            }
        }
        else if(goingForfood){
            resetPath();
        }

        if (!pickedUp && path.getCount() > 1)
            followPath(delta);
        else{
            time += delta;
            if (time >= delay) { // cooldown
                time = 0;
                newWanderPath();
            }
        }
    }

    public void followPath(float delta) {
        FlatTiledNode target = path.get(pathIndex);
        if ((Math.abs(((int) position.x / Main.width) - target.x) < 0.3f) && Math.abs(((int) position.y / Main.width) - target.y) < 0.3f) {
            position.set(target.x * Main.width, target.y * Main.width);
            pathIndex++;
            if (pathIndex == path.getCount()) { // end of path
                resetPath();
            }
        }
        Vector2 targetDirection = new Vector2(target.x, target.y).sub(position.x / Main.width, position.y / Main.width);
        targetDirection.nor();
        followPath(targetDirection.scl(speed));
    }

    private void newWanderPath(){
        while (true) { // search for new target tile
            int x = MathUtils.random(FlatTiledGraph.sizeX - 1);
            int y = MathUtils.random(FlatTiledGraph.sizeY - 1);
            FlatTiledNode startNode = astar.worldMap.getNode(x, y);
            if (startNode.type == FlatTiledNode.TILE_FLOOR) {
                buildPath(x, y);
                break;
            }
        }
    }

    private void buildPath(int x, int y){
        astar.updatePath(position, x, y, path, true);
        pathIndex = 1;
    }

    private void followPath(Vector2 shift){
        Vector2 newPosition = new Vector2(position).add(shift);
        grid.move(this, newPosition);
        position.set(newPosition);
        bounds.setPosition(position);
    }

    private void resetPath(){
        path.clear();
        goingForfood = false;
        pathIndex = 1;
    }

    public void pickUp(){
        pickedUp = true;
        grid.removeEntity(this);
    }

    public void drop(){
        pickedUp = false;
        resetPath();
        grid.insertEntity(this);
    }

    private void renderPath(ShapeRenderer shapeRenderer){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        int nodeCount = path.getCount();
        for (int i = 0; i < nodeCount; i++) {
            FlatTiledNode node = path.nodes.get(i);
            shapeRenderer.rect(node.x * Main.width, node.y * Main.width, Main.width, Main.width);
        }
        //if (smooth) {
        if (true) {
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            float hw = Main.width / 2f;
            if (nodeCount > 0) {
                FlatTiledNode prevNode = path.nodes.get(0);
                for (int i = 1; i < nodeCount; i++) {
                    FlatTiledNode node = path.nodes.get(i);
                    shapeRenderer.line(node.x * Main.width + hw, node.y * Main.width + hw, prevNode.x * Main.width + hw, prevNode.y * Main.width + hw);
                    prevNode = node;
                }
            }
        }
        shapeRenderer.end();
    }*/
}
