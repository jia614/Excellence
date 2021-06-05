package cs3500.animator.model.shapes;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Objects;

/**
 * Represents an ellipse shape with a unique name, position on the x,y-coordinate system,
 * a color, and a width and height.
 */
public class Ellipse extends AShape {

  /**
   * Constructs a {@code Ellipse} object that represents a ellipse.
   * @param name unique identifier
   * @param posn position
   * @param color color in rgb [0-255]
   * @param width width of ellipse
   * @param height height of ellipse
   */
  public Ellipse(String name, Point2D posn, Color color, int width, int height) {
    super(name, ShapeType.ELLIPSE, posn, color, width, height);
  }

  /**
   * Convenience constructor that constructs a {@code Rectangle} object that represents a
   * rectangle.
   * @param name unique identifier
   */
  public Ellipse(String name) {
    super(name, ShapeType.ELLIPSE, new Double(0.0, 0.0), Color.WHITE, 0, 0);
  }

  @Override
  public boolean equals(Object obj) {
    if (! (obj instanceof Ellipse)) {
      return false;
    }
    Ellipse that = (Ellipse) obj;
    return this.name.equals(that.name)
        && this.posn.equals(that.posn)
        && this.color.equals(that.color)
        && this.width == that.width
        && this.height == that.height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.posn, this.color,
        this.width, this.height);
  }

  @Override
  public IShape copy() {
    return new Ellipse(name, posn, color, width, height);
  }
}
