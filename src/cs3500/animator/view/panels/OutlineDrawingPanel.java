package cs3500.animator.view.panels;

import java.awt.Color;
// TODO: 4/23/2021 NEW 
/**
 * Represents the existing drawing panel with new functionality: the ability to draw outlined shapes
 * onto the panel. Extends the {@code DrawingPanel} class and inherits all of its functionality.
 * Declares new methods that draw outlined shapes.
 */
public class OutlineDrawingPanel extends DrawingPanel {

  /**
   * Constructs a {@code OutlineDrawingPanel} object.
   */
  public OutlineDrawingPanel() {
    super();
  }

  /**
   * Adds a new {@code DrawingPanelRectangleOutline} (an outlined rectangle) to be rendered.
   * @param x x-coordinate of rectangle
   * @param y y-coordinate of rectangle
   * @param w width of rectangle
   * @param h height of rectangle
   * @param color color of rectangle
   */
  public void drawRectOutline(double x, double y, int w, int h, Color color) {
    super.shapes.add(new DrawingPanelRectangleOutline((int) x, (int) y, w, h, color));
  }

  /**
   * Adds a new {@code DrawingPanelEllipseOutline} to be rendered.
   * @param x x-coordinate of ellipse
   * @param y y-coordinate of ellipse
   * @param w width of ellipse
   * @param h height of ellipse
   * @param color color of ellipse
   */
  public void drawEllipseOutline(double x, double y, int w, int h, Color color) {
    super.shapes.add(new DrawingPanelEllipseOutline((int) x, (int) y, w, h, color));
  }

  /**
   * Adds a new {@code DrawingPanelPlusOutline} to be rendered.
   * @param x x-coordinate of plus
   * @param y y-coordinate of plus
   * @param w width of plus
   * @param h height of plus
   * @param color color of plus
   */
  public void drawPlusOutline(double x, double y, int w, int h, Color color) {
    super.shapes.add(new DrawingPanelPlusOutline((int) x, (int) y, w, h, color));
  }
}
