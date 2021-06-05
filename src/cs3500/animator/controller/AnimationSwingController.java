package cs3500.animator.controller;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.model.shapes.IReadOnlyShape;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.view.visual.IVisualAnimationView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;

/**
 * Represents the implementation for the Java Swing graphics controller. Delegates between its
 * view and model by feeding frames from the model (shapes at each tick) to the view to render
 * by keeping track of current tick via a timer.
 */
public class AnimationSwingController implements IAnimationController, ActionListener {
  private final IReadOnlyAnimationModel model;
  private final IVisualAnimationView view;
  private final int tempo;
  private int tick;

  /**
   * Constructs a {@code AnimationVisualController} object.
   * @param model animation model
   * @param view view to render
   * @param tempo delay
   * @throws IllegalArgumentException if given model or view is null, or if tempo is negative
   */
  public AnimationSwingController(IReadOnlyAnimationModel model, IVisualAnimationView view,
      int tempo) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null!");
    }
    if (tempo < 0) {
      throw new IllegalArgumentException("Tempo cannot be negative!");
    }
    this.model = model;
    this.view = view;
    this.tempo = tempo;
    this.tick = 0;
  }

  @Override
  public void play() {
    view.render(model);
    Timer timer = new Timer(tempo, this);
    timer.start();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    List<IReadOnlyShape> shapes = model.getShapesAtTick(tick);
    for (IReadOnlyShape shape : shapes) {
      ShapeType type = shape.getType();
      view.drawShape(type,
          (int) shape.getPosn().getX(),
          (int) shape.getPosn().getY(),
          shape.getWidth(),
          shape.getHeight(),
          shape.getColor());
    }
    view.refresh();
    tick++;
  }
}
