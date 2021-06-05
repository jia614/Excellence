package cs3500.animator.controller;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.view.IAnimationView;

/**
 * Represents the implementation for the textual controller. Renders the view at once to produce
 * the animation's textual output.
 */
public class AnimationTextualController implements IAnimationController {
  private final IReadOnlyAnimationModel model;
  private final IAnimationView view;

  /**
   * Constructs a {@code AnimationTextualController} object.
   * @param view view to be rendered
   * @throws IllegalArgumentException if model or view are null
   */
  public AnimationTextualController(IReadOnlyAnimationModel model, IAnimationView view)
      throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null!");
    }
    this.model = model;
    this.view = view;
  }

  @Override
  public void play() {
    view.render(model);
  }
}
