package cs3500.animator.view.panels;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;


/**
 * Represents an ellipse shape to be rendered on the drawing panel. Holds its color as a field,
 * and has a method draw() that renders the ellipse onto the panel.
 */
public class DrawingPanelEllipse extends Ellipse2D.Double implements IDrawingPanelShape {
  private final Color color;

  /**
   * Constructs a {@code DrawingPanelEllipse} object that represents an ellipse
   * shape on a drawing panel.
   * @param x the X coordinate of the upper-left corner of the specified rectangular
   *          area of the ellipse
   * @param y the Y coordinate of the upper-left corner of the specified rectangular
   *          area of the ellipse
   * @param width the width of the specified rectangular area of the ellipse
   * @param height the height of the specified rectangular area of the ellipse
   * @param color the color of the ellipse to be rendered
   */
  public DrawingPanelEllipse(int x, int y, int width, int height, Color color)
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
    g.fillOval((int) x, (int) y, (int) width, (int) height);

  }
}
