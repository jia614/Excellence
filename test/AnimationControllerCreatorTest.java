import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.AnimationInteractiveController;
import cs3500.animator.controller.AnimationSwingController;
import cs3500.animator.controller.AnimationTextualController;
import cs3500.animator.controller.AnimatorControllerCreator;
import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.view.AnimatorViewCreator;
import cs3500.animator.model.AnimationModel.Builder;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the AnimationControllerCreator methods.
 */
public class AnimationControllerCreatorTest {

  Builder b;
  IReadOnlyAnimationModel testAModel;
  Appendable out;
  IAnimationView tView;
  IAnimationView svgView;
  IAnimationView vView;
  IAnimationView iView;
  IAnimationView oView;
  IAnimationView dView;


  @Before
  public void initData() {
    b = new AnimationModel.Builder();
    testAModel = b.setBounds(0, 0, 100, 150).build();
    out = new StringBuilder();
    tView = AnimatorViewCreator.create("text", out, 8, 200, new ArrayList<>());
    svgView = AnimatorViewCreator.create("svg", out, 8, 200, new ArrayList<>());
    vView = AnimatorViewCreator.create("visual", out, 8, 200, new ArrayList<>());
    iView = AnimatorViewCreator.create("interactive", out, 8, 200, new ArrayList<>());
    oView = AnimatorViewCreator.create("outline", out, 8, 200, new ArrayList<>());
    dView = AnimatorViewCreator.create("discrete", out, 8, 200, new ArrayList<>());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testUnsupportedOption() {
    AnimatorControllerCreator.create("tomato", 8, testAModel, tView);
  }

  @Test
  public void testTextualController() {
    assertEquals(AnimationTextualController.class,
        AnimatorControllerCreator.create("text", 8, testAModel, tView).getClass());
  }

  @Test
  public void testSVGController() {
    assertEquals(AnimationTextualController.class,
        AnimatorControllerCreator.create("svg", 8, testAModel, svgView).getClass());
  }

  @Test
  public void testVisualController() {
    assertEquals(AnimationSwingController.class,
        AnimatorControllerCreator.create("visual", 8, testAModel, vView).getClass());
  }

  @Test
  public void testInteractiveController() {
    assertEquals(AnimationInteractiveController.class,
        AnimatorControllerCreator.create("interactive", 8, testAModel, iView).getClass());
  }

  @Test
  public void testSlomoController() {
    assertEquals(AnimationInteractiveController.class,
        AnimatorControllerCreator.create("slomo", 8, testAModel, iView).getClass());
  }

  @Test
  public void testOutlineController() {
    assertEquals(AnimationInteractiveController.class,
        AnimatorControllerCreator.create("outline", 8, testAModel, oView).getClass());
  }

  @Test
  public void testDiscreteController() {
    assertEquals(AnimationInteractiveController.class,
        AnimatorControllerCreator.create("discrete", 8, testAModel, dView).getClass());
  }
}
