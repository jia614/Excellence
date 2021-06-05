package cs3500.animator.model;

import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * Represents a rectangular landscape with width, height, and origin coordinates (top-left).
 * Invariants:
 * - width is always non-negative
 * - height is always non-negative
 */
public class Screen {
  private final int width;
  private final int height;
  private final Point2D origin;

  /**
   * Constructs a {@code Screen} object.
   * @param width desired width of screen
   * @param height desired height of screen
   * @param origin desired origin point of screen (top left)
   * @throws IllegalArgumentException if width or height are negative, or if origin is null
   */
  public Screen(int width, int height, Point2D origin) throws IllegalArgumentException {
    if (width < 0) {
      throw new IllegalArgumentException("Width cannot be negative!");
    }
    if (height < 0) {
      throw new IllegalArgumentException("Height cannot be negative!");
    }
    if (origin == null) {
      throw new IllegalArgumentException("Origin point cannot be null!");
    }
    this.width = width;
    this.height = height;
    this.origin = origin;
  }

  /**
   * Returns the width of this screen.
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns the height of this screen.
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns the origin point of this screen.
   * @return a copy of the origin point
   */
  public Point2D getOrigin() {
    return new Point2D.Double(origin.getX(), origin.getY());
  }

  @Override
  public boolean equals(Object obj) {
    if (! (obj instanceof Screen)) {
      return false;
    }
    Screen that = (Screen) obj;
    return this.width == that.width
        && this.height == that.height
        && this.origin.equals(that.origin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.width, this.height, this.origin);
  }
}
