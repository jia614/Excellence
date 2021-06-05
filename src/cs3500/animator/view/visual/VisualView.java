package cs3500.animator.view.visual;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.view.panels.DrawingPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * A visual representation of the animation itself within the animator. Renders the shapes and
 * events on every tick fed by the controller, and operates on a frame-by-frame basis. Holds the
 * drawing panel, a scroll pane, and the top left x and y-coordinates (which are initialized in
 * render() using the model input).
 */
public class VisualView extends JFrame implements IVisualAnimationView {
  private final DrawingPanel panel;
  private final JScrollPane scroll;
  private double mx;
  private double my;

  /**
   * Constructs a {@code VisualView} object and initializes the drawing panel and scroll pane.
   * @throws IllegalArgumentException if given model is null
   */
  public VisualView() throws IllegalArgumentException {
    this.panel = new DrawingPanel();
    this.scroll = new JScrollPane(panel,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
  }

  @Override
  public void render(IReadOnlyAnimationModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    setTitle("Excellence Animator");
    mx = (int) model.getScreen().getOrigin().getX();
    my = (int) model.getScreen().getOrigin().getY();
    int width = model.getScreen().getWidth();
    int height = model.getScreen().getHeight();
    Dimension dimensions = new Dimension(width, height);

    setPreferredSize(dimensions);
    setBounds((int) mx, (int) my, width, height);

    panel.setPreferredSize(dimensions);
    panel.setBounds((int) mx, (int) my, width, height);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    panel.setLayout(new BorderLayout());
    add(scroll);
    setVisible(true);
  }

  @Override
  public void drawShape(ShapeType type, double x, double y, int w, int h, Color color)
      throws IllegalArgumentException {
    if (w < 0) {
      throw new IllegalArgumentException("Width cannot be negative!");
    }
    if (h < 0) {
      throw new IllegalArgumentException("Height cannot be negative!");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    }
    switch (type) {
      case RECTANGLE:
        panel.drawRect(x - mx, y - my, w, h, color);
        break;
      case ELLIPSE:
        panel.drawEllipse(x - mx, y - my, w, h, color);
        break;
      default:
        throw new IllegalArgumentException("Shape type not supported!");
    }
  }

  @Override
  public void refresh() {
    repaint();
  }
}
