package cs3500.animator.view.interactive.outline;

import cs3500.animator.view.interactive.IInteractiveView;
// TODO: 4/22/2021 NEW
/**
 * Represents the existing interactive view with a new functionality: enabling/disabling outline
 * mode, which allows animation to be rendered with outlined shapes. This functionality should be
 * able to be turned on and off, so plain shape rendering should be retained, as well as all of the
 * existing functionality.
 */
public interface IOutlineInteractiveView extends IInteractiveView {

  /**
   * Disables outline mode if it is currently enabled, and enables outline mode if it is
   * currently disabled.
   */
  void setOutline();
}
