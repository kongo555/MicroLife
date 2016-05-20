package com.microlife.level.astarUtils;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;
import com.microlife.level.Map;

/**
 * Created by kongo on 26.04.16.
 */
public class Node {
    public final int x;
    public final int y;
    public final int type;
    protected Array<Connection<Node>> connections;

    public Node (int x, int y, int type, int connectionCapacity) {
        this.x = x;
        this.y = y;
        this.type = type;
        connections = new Array<Connection<Node>>(connectionCapacity);
    }

    public Array<Connection<Node>> getConnections () {
        return connections;
    }

    public int getIndex () {
        return x * Map.height + y;
    }
}
