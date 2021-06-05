import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.AnimationInteractiveController;
import cs3500.animator.model.AnimationModel.Builder;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.utils.AnimationBuilder;
import cs3500.animator.view.interactive.IInteractiveView;
import cs3500.animator.view.MockInteractiveView;
import java.awt.event.ActionEvent;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the interactive controller methods.
 */
public class InteractiveControllerTest {
  AnimationBuilder<IAnimationModel> builder;
  Appendable out;
  IReadOnlyAnimationModel model;
  IInteractiveView view;
  AnimationInteractiveController controller;
  ActionEvent play;
  ActionEvent pause;
  ActionEvent restart;
  ActionEvent speedUp;
  ActionEvent slowDown;
  ActionEvent looping;

  @Before
  public void initData() {
    out = new StringBuilder();
    builder = new Builder();
    model = builder.setBounds(0, 0, 50, 50).build();
    view = new MockInteractiveView(out);
    controller = new AnimationInteractiveController(model, view);
    play = new ActionEvent(view, 1, "PLAY");
    pause = new ActionEvent(view, 2, "PAUSE");
    restart = new ActionEvent(view, 3, "RESTART");
    speedUp = new ActionEvent(view, 4, "SPEED-UP");
    slowDown = new ActionEvent(view, 5, "SLOW-DOWN");
    looping = new ActionEvent(view, 6, "ENABLE/DISABLE LOOPING");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructNullModel() {
    initData();
    new AnimationInteractiveController(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructNullView() {
    initData();
    new AnimationInteractiveController(model, null);
  }

  @Test
  public void testPlay() {
    initData();
    controller.play();
    assertEquals("Setting listener...\n"
        + "Model...\n", out.toString());
  }

  @Test
  public void testActionPerformedPlay() {
    initData();
    controller.actionPerformed(play);
    assertEquals("Setting listener...\n"
        + "Playing animation...\n", out.toString());
  }

  @Test
  public void testActionPerformedPause() {
    initData();
    controller.actionPerformed(pause);
    assertEquals("Setting listener...\n"
        + "Pausing animation...\n", out.toString());
  }

  @Test
  public void testActionPerformedRestart() {
    initData();
    controller.actionPerformed(restart);
    assertEquals("Setting listener...\n"
        + "Restarting animation...\n", out.toString());
  }

  @Test
  public void testActionPerformedSpeedUp() {
    initData();
    controller.actionPerformed(speedUp);
    assertEquals("Setting listener...\n"
        + "Speeding up animation...\n", out.toString());
  }

  @Test
  public void testActionPerformedSlowDown() {
    initData();
    controller.actionPerformed(slowDown);
    assertEquals("Setting listener...\n"
        + "Slowing down animation...\n", out.toString());
  }

  @Test
  public void testActionPerformedLooping() {
    initData();
    controller.actionPerformed(looping);
    assertEquals("Setting listener...\n"
        + "Enabling/disabling looping of animation...\n", out.toString());
  }
}
