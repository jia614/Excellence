package cs3500.animator.controller;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.interactive.discrete.IDiscreteInteractiveView;
import cs3500.animator.view.interactive.IInteractiveView;
import cs3500.animator.view.interactive.outline.IOutlineInteractiveView;
import cs3500.animator.view.visual.IVisualAnimationView;

/**
 * Factory class with a single method that generates the correct controller instance
 * depending on the view type.
 */
public final class AnimatorControllerCreator {
// TODO: 4/22/2021 MODIFIED 
  /**
   * Returns an instance of {@code IAnimationController} using the given model and view
   * depending on the view type.
   * @param type view type
   * @param tempo ticks per second
   * @param model model to be passed into controller
   * @param view view to be passed into controller
   * @return an instance of IAnimationController
   * @throws IllegalArgumentException if view type is not supported
   */
  public static IAnimationController create(String type, int tempo, IReadOnlyAnimationModel model,
      IAnimationView view) throws IllegalArgumentException {
    switch (type) {
      case "text":
      case "svg":
        return new AnimationTextualController(model, view);
      case "visual":
        if (view instanceof IVisualAnimationView) {
          return new AnimationSwingController(model, (IVisualAnimationView) view, tempo);
        }
        throw new IllegalArgumentException("Invalid view!");
      case "interactive":
      case "slomo":
        if (view instanceof IInteractiveView) {
          return new AnimationInteractiveController(model, (IInteractiveView) view);
        }
        throw new IllegalArgumentException("Invalid view!");
      case "outline":
        if (view instanceof IOutlineInteractiveView) {
          return new AnimationInteractiveController(model, (IOutlineInteractiveView) view);
        }
        throw new IllegalArgumentException("Invalid view!");
      case "discrete":
        if (view instanceof IDiscreteInteractiveView) {
          return new AnimationInteractiveController(model, (IDiscreteInteractiveView) view);
        }
        throw new IllegalArgumentException("Invalid view!");
      default:
        throw new IllegalArgumentException("View type not supported!");
    }
  }
}
