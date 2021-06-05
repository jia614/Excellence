package cs3500.animator.view.interactive.discrete;

import cs3500.animator.view.interactive.IInteractiveView;
// TODO: 4/22/2021 NEW
/**
 * Represents the existing interactive view with a new functionality: enabling/disabling discrete
 * mode, which allows animation to be rendered in discrete time, and allows user to watch individual
 * frames of each motion go by. This functionality should be able to be turned on and off, so
 * continuous playing should be retained, as well as all of the existing functionality.
 */
public interface IDiscreteInteractiveView extends IInteractiveView {

  /**
   * Disables discrete mode if it is currently enabled, and enables looping if it is
   * currently disabled.
   */
  void setDiscrete();
}
