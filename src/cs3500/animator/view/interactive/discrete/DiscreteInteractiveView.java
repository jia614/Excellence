package cs3500.animator.view.interactive.discrete;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.model.events.IEvent;
import cs3500.animator.model.shapes.IReadOnlyShape;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.view.interactive.InteractiveView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
// TODO: 4/22/2021 NEW
/**
 * An interactive representation with the ability to show the animation in discrete mode. Renders
 * the animation in stop-motion style, and retains the abilities of the original interactive view.
 * Holds a boolean that toggles between discrete and continuous motion, a value to keep track of the
 * original delay, and a new JButton to set discrete mode on and off.
 */
public class DiscreteInteractiveView extends InteractiveView implements IDiscreteInteractiveView,
    ActionListener {
  private boolean discreteMode;
  private final int origDelay;
  private final static String DISCRETE = "ENABLE/DISABLE DISCRETE MODE";
  private JButton discreteBtn;

  /**
   * Constructs a {@code DiscreteInteractiveView} object.
   * @param tempo tempo of animation
   */
  public DiscreteInteractiveView(int tempo) {
    super(tempo);
    this.discreteMode = false;
    this.origDelay = super.timer.getDelay();
    initDiscreteButton();
  }

  /**
   * Initializes the toggle button for enabling/disable discrete view.
   */
  private void initDiscreteButton() {
    this.discreteBtn = new JButton(DISCRETE);
    this.discreteBtn.setActionCommand(DISCRETE);
  }

  @Override
  public void render(IReadOnlyAnimationModel model) {
    super.render(model);
    super.buttonPanel.add(discreteBtn);
  }

  @Override
  public void play() {
    super.play();
  }

  @Override
  public void pause() {
    super.pause();
  }

  @Override
  public void restart() {
    super.restart();
  }

  @Override
  public void speedUp() {
    if (!discreteMode) {
      super.speedUp();
    }
  }

  @Override
  public void slowDown() {
    if (!discreteMode) {
      super.slowDown();
    }
  }

  @Override
  public void setLoop() {
    super.setLoop();
  }

  @Override
  public void setListener(ActionListener listener) throws IllegalArgumentException {
    super.setListener(listener);
    this.discreteBtn.addActionListener(listener);
  }

  @Override
  public void drawShape(ShapeType type, double x, double y, int w, int h, Color color)
      throws IllegalArgumentException {
    super.drawShape(type, x, y, w, h, color);
  }

  @Override
  public void refresh() {
    super.refresh();
  }

  @Override
  public void setDiscrete() {
    this.discreteMode = !discreteMode;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    List<IReadOnlyShape> toRender = new ArrayList<>(model.getShapesAtTick(tick));
    if (discreteMode) {
      super.timer.setDelay(200);
      for (IReadOnlyShape shape : toRender) {
        ShapeType type = shape.getType();
        drawShape(type,
            (int) shape.getPosn().getX(),
            (int) shape.getPosn().getY(),
            shape.getWidth(),
            shape.getHeight(),
            shape.getColor());
      }
      refresh();
      tick = getNextTick();
    }
    else {
      super.timer.setDelay(origDelay);
      super.actionPerformed(e);
    }
  }

  /**
   * Gets the tick value of the next beginning or end of an event.
   * @return next significant tick value for discrete mode
   */
  private int getNextTick() {
    if (tick >= maxTick) {
      return 0;
    }
    int nextTick = maxTick;
    int temp = maxTick;
    for (String s : model.getEvents().keySet()) {
      for (IEvent e : model.getEventsById(s)) {
        if (this.tick < e.getStartTime()) {
          temp = e.getStartTime();
          break;
        } else if (this.tick < e.getEndTime()) {
          temp = e.getEndTime();
          break;
        }
      }
      nextTick = Math.min(nextTick, temp);
    }
    return nextTick;
  }
}
