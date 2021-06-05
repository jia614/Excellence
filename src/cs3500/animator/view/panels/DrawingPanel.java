package cs3500.animator.view.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Represents the component inside the view window that displays the actual animation (and
 * nothing else). Extends the {@code JPanel} class and overrides its paintComponent() method. It
 * holds the list of drawing panel shapes to be rendered, and contains a drawRect() and
 * drawEllipse() method that is called by the controller to enable rendering between the model
 * and view.
 */
public class DrawingPanel extends JPanel {
  protected final List<IDrawingPanelShape> shapes;

  /**
   * Constructs a {@code DrawingPanel} object.
   */
  public DrawingPanel() {
    super();
    setBackground(Color.WHITE);
    shapes = new ArrayList<>();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (IDrawingPanelShape toDraw : shapes) {
      toDraw.draw(g);
    }
    shapes.clear();
  }

  /**
   * Adds a new {@code DrawingPanelRectangle} to be rendered.
   * @param x x-coordinate of rectangle
   * @param y y-coordinate of rectangle
   * @param w width of rectangle
   * @param h height of rectangle
   * @param color color of rectangle
   */
  public void drawRect(double x, double y, int w, int h, Color color) {
    shapes.add(new DrawingPanelRectangle((int) x, (int) y, w, h, color));
  }

  /**
   * Adds a new {@code DrawingPanelEllipse} to be rendered.
   * @param x x-coordinate of ellipse
   * @param y y-coordinate of ellipse
   * @param w width of ellipse
   * @param h height of ellipse
   * @param color color of ellipse
   */
  public void drawEllipse(double x, double y, int w, int h, Color color) {
    shapes.add(new DrawingPanelEllipse((int) x, (int) y, w, h, color));
  }

  // TODO: 4/22/2021 MODIFIED 
  /**
   * Adds a new {@code DrawingPanelPlus} to be rendered.
   * @param x x-coordinate of plus
   * @param y y-coordinate of plus
   * @param w width of plus
   * @param h height of plus
   * @param color color of plus
   */
  public void drawPlus(double x, double y, int w, int h, Color color) {
    shapes.add(new DrawingPanelPlus((int) x, (int) y, w, h, color));
  }
}
