package cs3500.animator.view.panels;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents an outlined rectangle shape to be rendered on the drawing panel.
 */
public class DrawingPanelRectangleOutline extends DrawingPanelRectangle
    implements IDrawingPanelShape {

  /**
   * Constructs a {@code DrawingPanelRectangle} object that represents a rectangle shape on a drawing
   * panel.
   * @param x      the X coordinate of the upper-left corner of the rectangle
   * @param y      the Y coordinate of the upper-left corner of the rectangle
   * @param width  width of the rectangle
   * @param height height of the rectangle
   * @param color  color of the rectangle to be rendered
   */
  public DrawingPanelRectangleOutline(int x, int y, int width, int height, Color color) {
    super(x, y, width, height, color);
  }

  @Override
  public void draw(Graphics g) throws IllegalArgumentException {
    super.draw(g);
    g.setColor(Color.BLACK);
    g.drawRect(x, y, width, height);
  }
}
