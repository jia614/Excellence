package cs3500.animator.controller;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.view.interactive.discrete.IDiscreteInteractiveView;
import cs3500.animator.view.interactive.IInteractiveView;
import cs3500.animator.view.interactive.outline.IOutlineInteractiveView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the implementation for the interactive Java Swing graphics controller. Delegates
 * between its model and view by sending the model to the view via the view's render() method.
 * Implements the ActionListener interface and allows it to take cues from the view and call
 * certain view methods depending on how the user interacts with the view.
 */
public class AnimationInteractiveController implements IAnimationController, ActionListener {
  private final IReadOnlyAnimationModel model;
  private final IInteractiveView view;

  /**
   * Constructs a {@code AnimationInteractiveController} object.
   * @param model animation model
   * @param view view to render
   * @throws IllegalArgumentException if model or view are null.
   */
  public AnimationInteractiveController(IReadOnlyAnimationModel model, IInteractiveView view)
      throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null!");
    }
    this.model = model;
    this.view = view;
    this.view.setListener(this);
  }

  @Override
  public void play() {
    view.render(model);
  }

  // TODO: 4/22/2021 MODIFIED
  @Override
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    switch (cmd) {
      case "PLAY":
        view.play();
        break;
      case "PAUSE":
        view.pause();
        break;
      case "RESTART":
        view.restart();
        break;
      case "SPEED-UP":
        view.speedUp();
        break;
      case "SLOW-DOWN":
        view.slowDown();
        break;
      case "ENABLE/DISABLE LOOPING":
        view.setLoop();
        break;
      case "ENABLE/DISABLE OUTLINE":
        if (view instanceof IOutlineInteractiveView) {
          ((IOutlineInteractiveView) view).setOutline();
        }
        else {
          throw new IllegalArgumentException("Unsupported command!");
        }
        break;
      case "ENABLE/DISABLE DISCRETE MODE":
        if (view instanceof IDiscreteInteractiveView) {
          ((IDiscreteInteractiveView) view).setDiscrete();
        }
        else {
          throw new IllegalArgumentException("Unsupported command!");
        }
        break;
      default:
        throw new IllegalArgumentException("Unsupported command!");
    }
  }
}
