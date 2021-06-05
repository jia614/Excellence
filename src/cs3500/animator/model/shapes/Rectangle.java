package cs3500.animator.model.shapes;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Objects;

/**
 * Represents a rectangle shape with a unique name, position on the x,y-coordinate system,
 * a color, and a width and height.
 */
public class Rectangle extends AShape {

  /**
   * Constructs a {@code Rectangle} object that represents a rectangle.
   * @param name unique identifier
   * @param posn position
   * @param color color in rgb [0-255]
   * @param width width of rectangle
   * @param height height of rectangle
   */
  public Rectangle(String name, Point2D posn, Color color, int width, int height) {
    super(name, ShapeType.RECTANGLE, posn, color, width, height);
  }

  /**
   * Convenience constructor that constructs a {@code Rectangle} object that represents a
   * rectangle.
   * @param name unique identifier
   */
  public Rectangle(String name) {
    super(name, ShapeType.RECTANGLE, new Double(0.0, 0.0), Color.WHITE, 0, 0);
  }

  @Override
  public boolean equals(Object obj) {
    if (! (obj instanceof Rectangle)) {
      return false;
    }
    Rectangle that = (Rectangle) obj;
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
    return new Rectangle(name, posn, color, width, height);
  }
}
