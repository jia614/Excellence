package cs3500.animator.view;

import cs3500.animator.view.interactive.InteractiveView;
import cs3500.animator.view.interactive.discrete.DiscreteInteractiveView;
import cs3500.animator.view.interactive.outline.OutlineInteractiveView;
import cs3500.animator.view.interactive.slomo.SlomoInteractiveView;
import cs3500.animator.view.text.SVGView;
import cs3500.animator.view.text.TextualView;
import cs3500.animator.view.visual.VisualView;
import java.awt.Point;
import java.util.List;
// TODO: 4/21/2021 MODIFIED 
/**
 * Factory class with a single method that generates the correct view instance
 * depending on the view type.
 */
public final class AnimatorViewCreator {

  /**
   * Returns an instance of {@code IAnimationView} using the given parameters
   * depending on the view type.
   * @param type view type
   * @param tempo ticks per second
   * @return an instance of IAnimationView
   * @throws IllegalArgumentException if view type is not supported
   */
  public static IAnimationView create(String type, Appendable out, int tempo, int slomo,
      List<Point> intervals) throws IllegalArgumentException {
    switch (type) {
      case "text":
        return new TextualView(out, tempo);
      case "svg":
        return new SVGView(out, tempo);
      case "visual":
        return new VisualView();
      case "interactive":
        return new InteractiveView(tempo);
      case "outline":
        return new OutlineInteractiveView(tempo);
      case "discrete":
        return new DiscreteInteractiveView(tempo);
      case "slomo":
        return new SlomoInteractiveView(tempo, slomo, intervals);
      default:
        throw new IllegalArgumentException("View type not yet supported!");
    }
  }
}
