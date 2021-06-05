package cs3500.animator.model.shapes;


/**
 * Represents a shape object. Inherits the read-only getter and copy methods, and additionally
 * has methods that are capable of mutating a shape's data.
 */
public interface IShape extends IReadOnlyShape {
  /**
   * Sets the position of the shape to the given coordinates.
   * @param x the desired new X coordinate of the shape
   * @param y the desired new Y coordinate of the shape
   */
  void changePosn(double x, double y);

  /**
   * Sets the color of the shape to the given color.
   * @param r the red intensity value
   * @param g the green intensity value
   * @param b the blue intensity value
   * @throws IllegalArgumentException if r, g, or b values fall out of the range [0,255]
   */
  void changeColor(int r, int g, int b) throws IllegalArgumentException;

  /**
   * Sets the width of the shape to the given width.
   * @param width desired new width of the shape
   * @throws IllegalArgumentException if given width is negative
   */
  void changeWidth(int width) throws IllegalArgumentException;

  /**
   * Sets the height of the shape to the given height.
   * @param height desired new height of the shape
   * @throws IllegalArgumentException if given height is negative
   */
  void changeHeight(int height) throws IllegalArgumentException;
}
