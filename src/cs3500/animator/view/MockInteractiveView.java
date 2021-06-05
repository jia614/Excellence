package cs3500.animator.view;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.view.interactive.IInteractiveView;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Mock view for testing interaction between the interactive view and the interactive controller.
 */
public class MockInteractiveView implements IInteractiveView {
  private final Appendable out;

  /**
   * Constructs a {@code MockInteractiveView} object.
   * @param out appendable to append to
   * @throws IllegalArgumentException if given appendable is null
   */
  public MockInteractiveView(Appendable out) throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Appendable cannot be null!");
    }
    this.out = out;
  }

  @Override
  public void render(IReadOnlyAnimationModel model) throws IllegalArgumentException {
    try {
      String modelString = "Model...\n";
      out.append(modelString);
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Failed to render!");
    }
  }

  @Override
  public void play() throws IllegalArgumentException {
    try {
      out.append("Playing animation...\n");
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Failed to render!");
    }
  }

  @Override
  public void pause() throws IllegalArgumentException {
    try {
      out.append("Pausing animation...\n");
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Failed to render!");
    }
  }

  @Override
  public void restart() throws IllegalArgumentException {
    try {
      out.append("Restarting animation...\n");
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Failed to render!");
    }
  }

  @Override
  public void speedUp() throws IllegalArgumentException {
    try {
      out.append("Speeding up animation...\n");
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Failed to render!");
    }
  }

  @Override
  public void slowDown() throws IllegalArgumentException {
    try {
      out.append("Slowing down animation...\n");
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Failed to render!");
    }
  }

  @Override
  public void setLoop() throws IllegalArgumentException {
    try {
      out.append("Enabling/disabling looping of animation...\n");
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Failed to render!");
    }
  }

  @Override
  public void setListener(ActionListener listener) throws IllegalArgumentException {
    try {
      out.append("Setting listener...\n");
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Failed to render!");
    }
  }

  @Override
  public void drawShape(ShapeType type, double x, double y, int w, int h, Color color)
      throws IllegalArgumentException {
    // not called from controller
  }

  @Override
  public void refresh() {
    // not called from controller
  }
}
