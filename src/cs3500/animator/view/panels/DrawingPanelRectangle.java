package cs3500.animator.view.panels;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

/**
 * Represents a rectangle shape to be rendered on the drawing panel. Holds its color as a field,
 * and has a method draw() that renders the rectangle onto the panel.
 */
public class DrawingPanelRectangle extends Rectangle implements IDrawingPanelShape {
  private final Color color;

  /**
   * Constructs a {@code DrawingPanelRectangle} object that represents a rectangle
   * shape on a drawing panel.
   * @param x the X coordinate of the upper-left corner of the rectangle
   * @param y the Y coordinate of the upper-left corner of the rectangle
   * @param width width of the rectangle
   * @param height height of the rectangle
   * @param color color of the rectangle to be rendered
   */
  public DrawingPanelRectangle(int x, int y, int width, int height, Color color)
      throws IllegalArgumentException {
    super(x, y, width, height);
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    }
    this.color = color;
  }

  @Override
  public void draw(Graphics g) {
    if (g == null) {
      throw new IllegalArgumentException("Graphics cannot be null!");
    }
    g.setColor(color);
    g.fillRect(x, y, width, height);
  }


}
