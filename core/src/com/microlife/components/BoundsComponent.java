package com.microlife.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by kongo on 26.04.16.
 */
public class BoundsComponent implements Component {
    public Rectangle rect;

    public BoundsComponent(Rectangle rect) {
        this.rect = rect;
    }
}
