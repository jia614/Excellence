package cs3500.animator.view.visual;

import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.view.IAnimationView;
import java.awt.Color;

/**
 * Represents a visual view. Inherits the render() method from IAnimationView,
 * and renders the input model using Java Swing graphics.
 */
public interface IVisualAnimationView extends IAnimationView {

  /**
   * Draws a shape with the given type and parameters.
   * @param type shape type
   * @param x initial x-coordinate of shape
   * @param y initial y-coordinate of shape
   * @param w initial width of shape
   * @param h initial height of shape
   * @param color initial color of shape
   * @throws IllegalArgumentException if shape type not supported, width or height are negative, or
   *         if color is null
   */
  void drawShape(ShapeType type, double x, double y, int w, int h, Color color)
      throws IllegalArgumentException;

  /**
   * Repaints the view.
   */
  void refresh();
}
