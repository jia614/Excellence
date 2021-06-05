import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.view.AnimatorViewCreator;
import cs3500.animator.view.interactive.InteractiveView;
import cs3500.animator.view.interactive.discrete.DiscreteInteractiveView;
import cs3500.animator.view.interactive.outline.OutlineInteractiveView;
import cs3500.animator.view.interactive.slomo.SlomoInteractiveView;
import cs3500.animator.view.text.SVGView;
import cs3500.animator.view.text.TextualView;
import cs3500.animator.model.AnimationModel.Builder;
import cs3500.animator.view.visual.VisualView;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
// TODO: 4/23/2021 DO TESTS
/**
 * Tests for the AnimatorViewCreator methods.
 */
public class AnimatorViewCreatorTest {

  Builder b;
  IReadOnlyAnimationModel testAModel;
  Appendable out;

  @Before
  public void initData() {
    b = new AnimationModel.Builder();
    testAModel = b.setBounds(0, 0, 100, 150).build();
    out = new StringBuilder();
  }


  @Test (expected = IllegalArgumentException.class)
  public void testCreateUnsupportedView() {
    AnimatorViewCreator.create("lettuce", out, 10, 200, new ArrayList<>());
  }

  @Test
  public void testCreateTextualView() {
    assertEquals(TextualView.class, AnimatorViewCreator.create("text", out, 10,
        200, new ArrayList<>()).getClass());
  }

  @Test
  public void testCreateSVGView() {
    assertEquals(SVGView.class, AnimatorViewCreator.create("svg", out, 10,
        200, new ArrayList<>()).getClass());
  }

  @Test
  public void testCreateVisualView() {
    assertEquals(VisualView.class, AnimatorViewCreator.create("visual", out, 10,
        200, new ArrayList<>()).getClass());
  }

  @Test
  public void testCreateInteractiveView() {
    assertEquals(InteractiveView.class, AnimatorViewCreator.create("interactive", out, 10,
        200, new ArrayList<>()).getClass());
  }

  @Test
  public void testCreateOutlineView() {
    assertEquals(OutlineInteractiveView.class, AnimatorViewCreator.create("outline", out, 10,
        200, new ArrayList<>()).getClass());
  }

  @Test
  public void testCreateDiscreteView() {
    assertEquals(DiscreteInteractiveView.class, AnimatorViewCreator.create("discrete", out, 10,
        200, new ArrayList<>()).getClass());
  }

  @Test
  public void testCreateSlomoView() {
    assertEquals(SlomoInteractiveView.class, AnimatorViewCreator.create("slomo", out, 10,
        200, new ArrayList<>()).getClass());
  }
}
