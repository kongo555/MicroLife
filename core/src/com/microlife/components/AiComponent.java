package com.microlife.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.microlife.level.astarUtils.NodePath;

/**
 * Created by kongo on 14.05.16.
 */
public class AiComponent implements Component{
     public BehaviorTree<AiComponent> btree = new BehaviorTree<AiComponent>();
     public boolean pickedUp = false;
     public NodePath path = new NodePath();
     public int pathIndex = 1;

     public void resetPath(){
          path.clear();
          pathIndex = 1;
     }
}
