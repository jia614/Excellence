package cs3500.animator.model.shapes;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Represents a read-only shape accessible by the client. Does not allow new data to be written
 * to any of its type.
 */
public interface IReadOnlyShape {
  /**
   * Returns the unique name of the shape.
   * @return the name of the shape
   */
  String getName();

  /**
   * Returns the type of the shape.
   * @return the shape type
   */
  ShapeType getType();

  /**
   * Returns the position of the shape.
   * @return the shape's position
   */
  Point2D getPosn();

  /**
   * Returns the color of the shape.
   * @return the shape's color
   */
  Color getColor();

  /**
   * Returns the width of the shape.
   * @return the shape's width
   */
  int getWidth();

  /**
   * Returns the height of the shape.
   * @return the shape's height
   */
  int getHeight();

  /**
   * Returns a copy of the shape with exact same fields.
   * @return a copy of the shape
   */
  IShape copy();

}
