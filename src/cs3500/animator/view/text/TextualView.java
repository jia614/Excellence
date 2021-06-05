package cs3500.animator.view.text;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.model.events.IEvent;
import cs3500.animator.model.shapes.IReadOnlyShape;
import cs3500.animator.view.IAnimationView;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A textual representation of an animation. Renders the events in an animation as states
 * (start and end) in a table with start time, initial coordinates, initial width, initial height,
 * and initial color, as well as end time, final coordinates, final width, final height, and final
 * color.
 */
public class TextualView implements IAnimationView {
  private final Appendable out;
  private final int tempo;

  /**
   * Constructs a {@code AbstractTextualView} object.
   * @param out appendable to append to
   * @param tempo ticks per second
   */
  public TextualView(Appendable out, int tempo) throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Appendable cannot be null!");
    }
    if (tempo < 0) {
      throw new IllegalArgumentException("Tempo cannot be negative!");
    }
    this.out = out;
    this.tempo = tempo;
  }

  /**
   * Constructs a {@code TextualView} object.
   * @throws IllegalArgumentException if model is null
   */
  public TextualView(int tempo) throws IllegalArgumentException {
    if (tempo < 0) {
      throw new IllegalArgumentException("Tempo cannot be negative!");
    }
    this.out = new StringBuilder();
    this.tempo = tempo;
  }

  /**
   * Overrides render() in IAnimationView.
   * @throws IllegalStateException if rendering fails for any reason.
   */
  @Override
  public void render(IReadOnlyAnimationModel model) throws IllegalStateException {
    try {
      out.append(animationToText(model));
    }
    catch (IOException e) {
      throw new IllegalStateException("Rendering failed!");
    }
  }

  /**
   * Returns a string representation of the animation model.
   * @return the string representation of the animation
   */
  private String animationToText(IReadOnlyAnimationModel model) {
    StringBuilder sb = new StringBuilder();
    sb.append("# describes the motions of a shape between two moments of animation:\n");
    sb.append("# t == tick\n");
    sb.append("# (x,y) == position\n");
    sb.append("# (w,h) == dimensions\n");
    sb.append("# (r,g,b) == color (with values between 0 and 255)\n");
    List<IReadOnlyShape> shapes = new ArrayList<>(model.getShapes());
    for (IReadOnlyShape shape : shapes) {
      sb.append("shape ").append(shape.getName()).append(" ")
          .append(shape.getType().toString()).append("\n");
    }
    sb.append("#                               start                  "
        + "                  end\n");
    sb.append("#                ------------------------------------  "
        + "  -------------------------------------\n");
    sb.append("#                t    x    y    w    h    r    g    b  "
        + "   t    x    y    w    h    r    g    b\n");

    for (IReadOnlyShape shape : shapes) {
      List<IEvent> events = new ArrayList<>(model.getEventsById(shape.getName()));
      for (IEvent event : events) {
        sb.append("motion ");
        sb.append(String.format("%-10s", shape.getName()));
        sb.append(String.format("%-4s", ticksToSeconds(event.getStartTime()))).append(" ");
        sb.append(String.format("%-4s", event.getStartPosn().getX())).append(" ");
        sb.append(String.format("%-4s", event.getStartPosn().getY())).append(" ");
        sb.append(String.format("%-4s", event.getStartWidth())).append(" ");
        sb.append(String.format("%-4s", event.getStartHeight())).append(" ");
        sb.append(String.format("%-4s", event.getStartColor().getRed())).append(" ");
        sb.append(String.format("%-4s", event.getStartColor().getGreen())).append(" ");
        sb.append(String.format("%-4s", event.getStartColor().getBlue())).append(" ");
        sb.append(" ");
        sb.append(String.format("%-4s", ticksToSeconds(event.getEndTime()))).append(" ");
        sb.append(String.format("%-4s", event.getEndPosn().getX())).append(" ");
        sb.append(String.format("%-4s", event.getEndPosn().getY())).append(" ");
        sb.append(String.format("%-4s", event.getEndWidth())).append(" ");
        sb.append(String.format("%-4s", event.getEndHeight())).append(" ");
        sb.append(String.format("%-4s", event.getEndColor().getRed())).append(" ");
        sb.append(String.format("%-4s", event.getEndColor().getGreen())).append(" ");
        sb.append(String.format("%-4s", event.getEndColor().getBlue())).append(" ");
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  /**
   * Converts the given number of ticks to seconds and renders as a string.
   * @param tick tick to be converted
   * @return ticks in seconds
   */
  private String ticksToSeconds(int tick) {
    double result = (double) tick / tempo;
    return new DecimalFormat("#.##").format(result);
  }
}
