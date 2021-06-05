package cs3500.animator.view.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Represents a plus shape to be rendered on the drawing panel.
 */
public class DrawingPanelPlus extends Rectangle implements IDrawingPanelShape {
  private final Color color;

  /**
   * Constructs a {@code DrawingPanelPlus} object that represents a plus
   * shape on a drawing panel.
   * @param x the X coordinate of the upper-left corner of the plus
   * @param y the Y coordinate of the upper-left corner of the plus
   * @param width width of the plus
   * @param height height of the plus
   * @param color color of the rectangle to be plus
   */
  public DrawingPanelPlus(int x, int y, int width, int height, Color color)
      throws IllegalArgumentException {
    super(x, y, width, height);
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    }
    this.color = color;
  }

  @Override
  public void draw(Graphics g) throws IllegalArgumentException {
    if (g == null) {
      throw new IllegalArgumentException("Graphics cannot be null!");
    }
    g.setColor(color);
    g.fillRect(x + width / 4, y, width / 2, height);
    g.fillRect(x, y + height / 4, width, height / 2);
  }
}