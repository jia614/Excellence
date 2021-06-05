package cs3500.animator.controller;

/**
 * Represents a controller for producing and interacting with an animation/animator. Delegates
 * between the model and the view by either feeding all frames of the animation at once, or by
 * feeding one frame of the animation at a time from the model to the view to be rendered.
 */
public interface IAnimationController {

  /**
   * Starts the animator.
   */
  void play();
}
