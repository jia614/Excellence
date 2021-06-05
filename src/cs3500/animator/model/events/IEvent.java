package cs3500.animator.model.events;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Represents objects that are applied on shapes that change the shape's characteristics during an
 * interval in ticks of time. Should hold both the start time, end time, and the starting and ending
 * parameters.
 */
public interface IEvent {

  /**
   * Returns the starting time of the animation event.
   * @return the initial tick of the animation event
   */
  int getStartTime();

  /**
   * Returns the starting position of the shape before the animation event.
   * @return the initial posn of the shape
   */
  Point2D getStartPosn();

  /**
   * Returns the starting color of the shape before the animation event.
   * @return the initial color of the shape
   */
  Color getStartColor();

  /**
   * Returns the starting width of the shape before the animation event.
   * @return the initial width of the shape
   */
  int getStartWidth();

  /**
   * Returns the starting height of the shape before the animation event.
   * @return the initial height of the shape
   */
  int getStartHeight();

  /**
   * Returns the ending time of the animation event.
   * @return the final time of the animation event
   */
  int getEndTime();

  /**
   * Returns the resulting position of the shape after the animation event.
   * @return the final position of the shape
   */
  Point2D getEndPosn();

  /**
   * Returns the resulting color of the shape after the animation event.
   * @return the final color of the shape
   */
  Color getEndColor();

  /**
   * Returns the resulting width of the shape after the animation event.
   * @return the final width of the shape
   */
  int getEndWidth();

  /**
   * Returns the resulting height of the shape after the animation.
   * @return the final height of the shape
   */
  int getEndHeight();

  /**
   * Returns a copy of the Event with the exact same fields.
   * @return a copy of the Event
   */
  IEvent copy();
}
