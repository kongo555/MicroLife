package com.microlife.level;


import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.microlife.components.AiComponent;
import com.microlife.level.astarUtils.ManhattanHeuristic;
import com.microlife.level.astarUtils.Node;
import com.microlife.level.astarUtils.NodePath;
import com.microlife.level.astarUtils.NodeRaycastCollisionDetector;

/**
 * Created by kongo on 26.04.16.
 */
public class Astar {
    private Map map;
    private ManhattanHeuristic heuristic;
    private IndexedAStarPathFinder<Node> pathFinder;
    private PathSmoother<Node, Vector2> pathSmoother;

    public Astar(Map map){
        this.map = map;
        heuristic = new ManhattanHeuristic();
        pathFinder = new IndexedAStarPathFinder<Node>(map, true);
        pathSmoother = new PathSmoother<Node, Vector2>(new NodeRaycastCollisionDetector(map));
    }

    public void updatePath (Vector2 position, int targetX, int targetY, NodePath path, boolean smooth) {
        int startX = (int)(position.x / Map.tileSize);
        int startY = (int)(position.y / Map.tileSize);

        if (startX != targetX || startY != targetY) {
            Node startNode = map.getNode(startX, startY);
            Node endNode = map.getNode(targetX, targetY);
            if (endNode.type == Map.TILE_FLOOR) {
                path.clear();
                pathFinder.searchNodePath(startNode, endNode, heuristic, path);
                if (smooth) {
                    pathSmoother.smoothPath(path);
                }
            }
        }
    }

    public void buildRandomPath(AiComponent aiComponent, Vector2 position){
        while (true) { // search for new target tile
            int x = MathUtils.random(Map.width - 1);
            int y = MathUtils.random(Map.height - 1);
            Node startNode = map.getNode(x, y);
            if (startNode.type == Map.TILE_FLOOR) {
                updatePath(position, x, y, aiComponent.path, true);
                aiComponent.pathIndex = 1;
                break;
            }
        }
    }

}
