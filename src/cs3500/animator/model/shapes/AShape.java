package cs3500.animator.model.shapes;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * A class to abstract out the commonalities of the different kinds of shapes. Holds
 * the unique name of the shape, a shape type, a position, a color, a width, and a height.
 * Invariants:
 * - width and height must be non-negative.
 */
public abstract class AShape implements IShape {
  protected final String name;
  protected final ShapeType type;
  protected Point2D posn; // position at top-left corner of shape
  protected Color color;
  protected int width;
  protected int height;

  /**
   * Constructs an {@code AShape} object that represents an abstract shape.
   * @param name unique name of shape
   * @param type type of shape
   * @param posn 2D position of the shape
   * @param color color of the shape
   * @param width width of the shape
   * @param height height of the shape
   * @throws IllegalArgumentException if width or height is negative, or if any of
   *         name, posn, or color are null
   */
  public AShape(String name, ShapeType type, Point2D posn, Color color, int width, int height)
      throws IllegalArgumentException {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Invalid width or height!");
    }
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }
    if (posn == null) {
      throw new IllegalArgumentException("Position cannot be null!");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    }
    this.name = name;
    this.type = type;
    this.posn = posn;
    this.color = color;
    this.width = width;
    this.height = height;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public ShapeType getType() {
    return type;
  }

  @Override
  public Point2D getPosn() {
    return new Point2D.Double(posn.getX(), posn.getY());
  }

  @Override
  public Color getColor() {
    return new Color(color.getRGB());
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public void changePosn(double x, double y) {
    posn = new Point2D.Double(x, y);
  }

  @Override
  public void changeColor(int r, int g, int b) throws IllegalArgumentException {
    if (invalidIntensity(r) || invalidIntensity(g) || invalidIntensity(b)) {
      throw new IllegalArgumentException("Invalid intensity value!");
    }
    color = new Color(r, g, b);
  }

  /**
   * Determines if the given value is a valid intensity value.
   * @param value intensity value to check
   * @return if the given value falls between [0,255]
   */
  private boolean invalidIntensity(int value) {
    return value < 0 || value > 255;
  }

  @Override
  public void changeWidth(int width) throws IllegalArgumentException {
    if (width < 0) {
      throw new IllegalArgumentException("Width cannot be negative!");
    }
    this.width = width;
  }

  @Override
  public void changeHeight(int height) throws IllegalArgumentException {
    if (height < 0) {
      throw new IllegalArgumentException("Height cannot be negative!");
    }
    this.height = height;
  }
}
