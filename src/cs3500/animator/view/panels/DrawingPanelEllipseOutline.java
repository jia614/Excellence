package cs3500.animator.view.panels;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents an outlined ellipse shape to be rendered on the drawing panel.
 */
public class DrawingPanelEllipseOutline extends DrawingPanelEllipse implements IDrawingPanelShape {

  /**
   * Constructs a {@code DrawingPanelEllipse} object that represents an ellipse shape on a drawing
   * panel.
   *
   * @param x      the X coordinate of the upper-left corner of the specified rectangular area of the
   *               ellipse
   * @param y      the Y coordinate of the upper-left corner of the specified rectangular area of the
   *               ellipse
   * @param width  the width of the specified rectangular area of the ellipse
   * @param height the height of the specified rectangular area of the ellipse
   * @param color  the color of the ellipse to be rendered
   */
  public DrawingPanelEllipseOutline(int x, int y, int width, int height, Color color) {
    super(x, y, width, height, color);
  }

  @Override
  public void draw(Graphics g) throws IllegalArgumentException {
    super.draw(g);
    g.setColor(Color.BLACK);
    g.drawOval((int) x, (int) y, (int) width, (int) height);
  }
}
