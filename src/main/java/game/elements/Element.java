package game.elements;

import java.awt.*;

public abstract class Element {
    protected int x;
    protected int y;

    public abstract void render(Graphics g);
    public abstract void update();
}
