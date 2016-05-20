package com.microlife.level.astarUtils;

import com.badlogic.gdx.ai.pfa.DefaultConnection;

/**
 * Created by kongo on 26.04.16.
 */
public class NodeConnection extends DefaultConnection<Node> {

    public NodeConnection (Node fromNode, Node toNode) {
        super(fromNode, toNode);
    }

    /*@Override
    public float getCost () {
        return 1;
    }*/
}
