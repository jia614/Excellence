package cs3500.animator.view.interactive.slomo;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.model.shapes.IReadOnlyShape;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.view.interactive.IInteractiveView;
import cs3500.animator.view.interactive.InteractiveView;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
// TODO: 4/22/2021 NEW
/**
 * An interactive representation with the ability to show parts of the animation in slomo mode.
 * Retains the abilities of the original interactive view. Takes in a slomo tempo as well as a list
 * of intervals that were parsed in the main class. Plays the animation in those intervals at
 * the specified slomo tempo.
 */
public class SlomoInteractiveView extends InteractiveView implements IInteractiveView,
    ActionListener {
  private final int slomo;
  private final List<Point> intervals;
  private final int origDelay;

  /**
   * Constructs a {@code InteractiveView} object.
   *
   * @param tempo initial tempo of animation
   * @throws IllegalArgumentException if tempo is negative
   */
  public SlomoInteractiveView(int tempo, int slomo, List<Point> intervals)
      throws IllegalArgumentException {
    super(tempo);
    if (slomo < 0) {
      throw new IllegalArgumentException("Slomo tempo cannot be negative!");
    }
    if (intervals == null) {
      throw new IllegalArgumentException("Times cannot be null!");
    }
    this.slomo = slomo;
    this.intervals = new ArrayList<>(intervals);
    this.origDelay = super.timer.getDelay();
  }

  @Override
  public void render(IReadOnlyAnimationModel model) {
    super.render(model);
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
    super.speedUp();
  }

  @Override
  public void slowDown() {
    super.slowDown();
  }

  @Override
  public void setLoop() {
    super.setLoop();
  }

  @Override
  public void setListener(ActionListener listener) throws IllegalArgumentException {
    super.setListener(listener);
  }

  @Override
  public void drawShape(ShapeType type, double x, double y, int w, int h, Color color)
      throws IllegalArgumentException {
    super.drawShape(type, x, y, w, h, color);
  }

  @Override
  public void refresh() {
    super.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    List<IReadOnlyShape> toRender = new ArrayList<>(model.getShapesAtTick(tick));
    if (isSlomo(super.tick)) {
      super.timer.setDelay(slomo);
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
      tick++;
    }
    else {
      super.timer.setDelay(origDelay);
      super.actionPerformed(e);
    }
  }

  /**
   * Checks if the current tick is part of the slomo interval.
   * @param tick tick to check
   * @return true if given tick is in intervals, else return false
   */
  private boolean isSlomo(int tick) {
    boolean result = false;
    for (Point interval : intervals) {
      result |= (tick >= interval.getX() && tick <= interval.getY());
    }
    return result;
  }
}
