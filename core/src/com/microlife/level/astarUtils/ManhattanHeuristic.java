package com.microlife.level.astarUtils;

import com.badlogic.gdx.ai.pfa.Heuristic;

/**
 * Created by kongo on 26.04.16.
 */
public class ManhattanHeuristic implements Heuristic<Node> {

    public ManhattanHeuristic () {
    }

    @Override
    public float estimate (Node node, Node endNode) {
        return Math.abs(endNode.x - node.x) + Math.abs(endNode.y - node.y);
    }
}
