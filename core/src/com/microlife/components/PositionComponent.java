package com.microlife.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by kongo on 25.04.16.
 */
public class PositionComponent implements Component{
    public Vector2 vector;

    public PositionComponent(Vector2 position){
        this.vector = position;
    }
}
