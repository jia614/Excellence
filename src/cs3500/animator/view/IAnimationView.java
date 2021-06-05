package cs3500.animator.view;

import cs3500.animator.model.IReadOnlyAnimationModel;

/**
 * Represents the view (of the model-view-controller) implementation for the easy animator.
 * Holds one method, render(), that takes in a model and renders the model as a string or as
 * graphics. Is called by the controller and communicates to the model via the controller.
 */
public interface IAnimationView {

  /**
   * Renders the animation model (either as a textual or swing graphics output).
   */
  void render(IReadOnlyAnimationModel model);
}
