package cs3500.animator.model.shapes;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Objects;

/**
 * Represents a plus shape with a unique name, position on the x,y-coordinate system,
 * a color, and a width and height.
 */
public class Plus extends AShape {
  // TODO: 4/16/2021 NEW
  /**
   * Creates an event that resizes the height of the shape to the new given height.
   * @param name   unique name of shape
   * @param posn   2D position of the shape
   * @param color  color of the shape
   * @param width  width of the shape
   * @param height height of the shape
   * @throws IllegalArgumentException if width or height is negative, or if any of name, posn, or
   *                                  color are null
   */
  public Plus(String name, Point2D posn, Color color,
      int width, int height) throws IllegalArgumentException {
    super(name, ShapeType.PLUS, posn, color, width, height);
  }

  /**
   * Convenience constructor that constructs a {@code Rectangle} object that represents a
   * rectangle.
   * @param name unique identifier
   */
  public Plus(String name) {
    super(name, ShapeType.PLUS, new Double(0.0, 0.0), Color.WHITE, 0, 0);
  }

  @Override
  public boolean equals(Object obj) {
    if (! (obj instanceof Plus)) {
      return false;
    }
    Plus that = (Plus) obj;
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
    return new Plus(this.name, this.posn, this.color, this.width, this.height);
  }
}
