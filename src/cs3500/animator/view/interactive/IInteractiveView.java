package cs3500.animator.view.interactive;

import cs3500.animator.view.visual.IVisualAnimationView;
import java.awt.event.ActionListener;

/**
 * Represents an interactive view. Inherits the render() method from IAnimationView, as well as
 * IVisualAnimationView's drawShape() and refresh() methods. Renders the model using Java Swing and
 * has functionality to play, pause, restart, speed up, slow down, and enable/disable looping. It
 * also has a method to set the given listener to respond to its events.
 */
public interface IInteractiveView extends IVisualAnimationView {

  /**
   * Starts the animation.
   */
  void play();

  /**
   * Stops the animation.
   */
  void pause();

  /**
   * Restarts the animation.
   */
  void restart();

  /**
   * Speeds up the animation.
   */
  void speedUp();

  /**
   * Slows down the animation.
   */
  void slowDown();

  /**
   * Disables looping if it is currently enabled, and enables looping if it is
   * currently disabled.
   */
  void setLoop();

  /**
   * Sets the given listener to respond to events.
   * @param listener listener to set
   */
  void setListener(ActionListener listener) throws IllegalArgumentException;
}
