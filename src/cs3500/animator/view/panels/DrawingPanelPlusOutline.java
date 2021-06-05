package cs3500.animator.view.panels;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents an outlined plus shape to be rendered on the drawing panel.
 */
public class DrawingPanelPlusOutline extends DrawingPanelPlus implements IDrawingPanelShape {

  /**
   * Constructs a {@code DrawingPanelPlusOutline} object that represents an outline plus shape on a
   * drawing panel.
   * @param x the X coordinate of the upper-left corner of the plus
   * @param y the Y coordinate of the upper-left corner of the plus
   * @param width width of the plus
   * @param height height of the plus
   * @param color color of the plus to be rendered
   */
  public DrawingPanelPlusOutline(int x, int y, int width, int height, Color color) {
    super(x, y, width, height, color);
  }

  @Override
  public void draw(Graphics g) throws IllegalArgumentException {
    super.draw(g);
    int[] xp = new int[13];
    int[] yp = new int[13];

    int x1 = x;
    int x2 = x + width / 4;
    int x3 = x + width / 4 + width / 2;
    int x4 = x + width;

    int y1 = y;
    int y2 = y + height / 4;
    int y3 = y + height / 4 + height / 2;
    int y4 = y + height;

    xp[0] = x1;
    xp[1] = x2;
    xp[2] = x2;
    xp[3] = x3;
    xp[4] = x3;
    xp[5] = x4;
    xp[6] = x4;
    xp[7] = x3;
    xp[8] = x3;
    xp[9] = x2;
    xp[10] = x2;
    xp[11] = x1;
    xp[12] = x1;

    yp[0] = y2;
    yp[1] = y2;
    yp[2] = y1;
    yp[3] = y1;
    yp[4] = y2;
    yp[5] = y2;
    yp[6] = y3;
    yp[7] = y3;
    yp[8] = y4;
    yp[9] = y4;
    yp[10] = y3;
    yp[11] = y3;
    yp[12] = y2;

    g.setColor(Color.BLACK);
    g.drawPolyline(xp, yp, 13);
  }
}
