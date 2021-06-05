package cs3500.animator.model.events;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * Represents an animation event for a shape. Holds the start time, initial position, initial color,
 * initial width, initial height, end time, final position, final color, final width, and final
 * height of the shape.
 * Invariants:
 * - startTime and endTime must be non-negative.
 * - All widths and heights must be non-negative.
 * - starTime must be less than or equal to endTime.
 */
public class Event implements IEvent {
  private final int startTime;
  private final Point2D startPosn;
  private final Color startColor;
  protected final int startWidth;
  private final int startHeight;
  private final int endTime;
  private final Point2D endPosn;
  private final Color endColor;
  private final int endWidth;
  private final int endHeight;


  /**
   * Constructs a {@code Event} object which represents an animation event for a shape.
   * @param startTime start time of event
   * @param startPosn start position of shape before event
   * @param startColor start color of shape before event
   * @param startWidth start width of shape before event
   * @param startHeight start height of shape before event
   * @param endTime end time of event
   * @param endPosn resulting position of shape after event
   * @param endColor resulting color of shape after event
   * @param endWidth resulting width of shape after event
   * @param endHeight resulting height of shape after event
   * @throws IllegalArgumentException if start time, end time, widths, or heights are negative, or
   *         if end time is before start time, or if positions or colors are null
   */
  public Event(int startTime, Point2D startPosn, Color startColor, int startWidth, int startHeight,
      int endTime, Point2D endPosn, Color endColor, int endWidth, int endHeight)
      throws IllegalArgumentException {
    if (startTime < 0 || endTime < 0) {
      throw new IllegalArgumentException("Start time and end time cannot be negative!");
    }
    if (startTime > endTime) {
      throw new IllegalArgumentException("You must start before you can end!");
    }
    if (startWidth < 0 || endWidth < 0) {
      throw new IllegalArgumentException("Widths cannot be negative!");
    }
    if (startHeight < 0 || endHeight < 0) {
      throw new IllegalArgumentException("Heights cannot be negative!");
    }
    if (startPosn == null || endPosn == null) {
      throw new IllegalArgumentException("Positions cannot be null!");
    }
    if (startColor == null || endColor == null) {
      throw new IllegalArgumentException("Colors cannot be null!");
    }
    this.startTime = startTime;
    this.startPosn = startPosn;
    this.startColor = startColor;
    this.startWidth = startWidth;
    this.startHeight = startHeight;
    this.endTime = endTime;
    this.endPosn = endPosn;
    this.endColor = endColor;
    this.endWidth = endWidth;
    this.endHeight = endHeight;
  }

  @Override
  public int getStartTime() {
    return startTime;
  }

  @Override
  public Point2D getStartPosn() {
    return new Point2D.Double(startPosn.getX(), startPosn.getY());
  }

  @Override
  public Color getStartColor() {
    return new Color(startColor.getRGB());
  }

  @Override
  public int getStartWidth() {
    return startWidth;
  }

  @Override
  public int getStartHeight() {
    return startHeight;
  }

  @Override
  public int getEndTime() {
    return endTime;
  }

  @Override
  public Point2D getEndPosn() {
    return new Point2D.Double(endPosn.getX(), endPosn.getY());
  }

  @Override
  public Color getEndColor() {
    return new Color(endColor.getRGB());
  }

  @Override
  public int getEndWidth() {
    return endWidth;
  }

  @Override
  public int getEndHeight() {
    return endHeight;
  }

  @Override
  public IEvent copy() {
    return new Event(startTime, startPosn, startColor, startWidth, startHeight,
        endTime, endPosn, endColor, endWidth, endHeight);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (! (obj instanceof Event)) {
      return false;
    }
    Event that = (Event) obj;
    return this.startTime == that.startTime
        && this.startPosn.equals(that.startPosn)
        && this.startColor.equals(that.startColor)
        && this.startWidth == that.startWidth
        && this.startHeight == that.startHeight
        && this.endTime == that.endTime
        && this.endPosn.equals(that.endPosn)
        && this.endColor.equals(that.endColor)
        && this.endWidth == that.endWidth
        && this.endHeight == that.endHeight;
  }

  @Override
  public int hashCode() {
    return Objects.hash(startTime, startPosn, startColor, startWidth, startHeight, endTime,
        endPosn, endColor, endWidth, endHeight);
  }

}
