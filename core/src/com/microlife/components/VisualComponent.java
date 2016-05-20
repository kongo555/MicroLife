package com.microlife.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by kongo on 26.04.16.
 */
public class VisualComponent implements Component {
    public Color color;

    public VisualComponent(Color color) {
        this.color = color;
    }
}
