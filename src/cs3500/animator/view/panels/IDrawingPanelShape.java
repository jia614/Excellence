package cs3500.animator.view.panels;

import java.awt.Graphics;

/**
 * Represents a shape to be rendered onto the drawing panel. Holds a method draw() that renders
 * the shape onto the panel using the given {@code Graphics} object.
 */
public interface IDrawingPanelShape {

  /**
   * Renders the shape onto the drawing panel using the given graphics.
   * @param g graphics
   * @throws IllegalArgumentException if g is null
   */
  void draw(Graphics g) throws IllegalArgumentException ;
}
