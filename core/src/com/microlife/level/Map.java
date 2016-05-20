package com.microlife.level;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.microlife.Main;
import com.microlife.level.astarUtils.DungeonUtils;
import com.microlife.level.astarUtils.Node;
import com.microlife.level.astarUtils.NodeConnection;

/**
 * Created by kongo on 25.04.16.
 */
public class Map implements IndexedGraph<Node> {
    public static final int TILE_EMPTY = 0;
    public static final int TILE_FLOOR = 1;
    public static final int TILE_WALL = 2;

    public static final int tileSize = 8;
    public static final int width =  Main.width / tileSize;
    public static final int height = Main.height / tileSize;

    private Array<Node> nodes;

    public Map(){
        int roomCount = MathUtils.random(80, 150);// 100, 260);//70, 120);
        int roomMinSize = 3;
        int roomMaxSize = 15;
        int squashIterations = 100;

        this.nodes = new Array<Node>(width * height);
        init(roomCount, roomMinSize, roomMaxSize, squashIterations);
    }

    public void init (int roomCount, int roomMinSize, int roomMaxSize, int squashIterations) {
        int map[][] = DungeonUtils.generate(width, height, roomCount, roomMinSize, roomMaxSize, squashIterations);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodes.add(new Node(x, y, map[x][y], 4));
            }
        }

        // Each node has up to 4 neighbors, therefore no diagonal movement is possible
        for (int x = 0; x < width; x++) {
            int idx = x * height;
            for (int y = 0; y < height; y++) {
                Node n = nodes.get(idx + y);
                if (x > 0) addConnection(n, -1, 0);
                if (y > 0) addConnection(n, 0, -1);
                if (x < width - 1) addConnection(n, 1, 0);
                if (y < height - 1) addConnection(n, 0, 1);
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                switch (getNode(x, y).type) {
                    case TILE_FLOOR:
                        shapeRenderer.setColor(Color.WHITE);
                        break;
                    case TILE_WALL:
                        shapeRenderer.setColor(Color.GRAY);
                        break;
                    default:
                        shapeRenderer.setColor(Color.BLACK);
                        break;
                }
                shapeRenderer.rect(x*tileSize, y*tileSize, tileSize, tileSize);
            }
        }
        shapeRenderer.end();
    }

    private void addConnection (Node n, int xOffset, int yOffset) {
        Node target = getNode(n.x + xOffset, n.y + yOffset);
        if (target.type == Map.TILE_FLOOR) n.getConnections().add(new NodeConnection(n, target));
    }

    public Node getNode (int x, int y) {
        return nodes.get(x * height + y);
    }

    @Override
    public int getIndex(Node node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return fromNode.getConnections();
    }
}
