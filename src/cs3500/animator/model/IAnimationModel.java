package cs3500.animator.model;

import cs3500.animator.model.events.IEvent;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.ShapeType;
import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Maintains the state for an animation. Inherits all of the methods defined by the
 * IReadOnlyAnimationModel, and adds methods that allow for writing back into the fields
 * of the model implementation.
 */
public interface IAnimationModel extends IReadOnlyAnimationModel {

  /**
   * Adds the given shape to the Animation Model's list of shapes.
   * @param shape to be added to the list of shapes
   * @throws IllegalArgumentException if the given shape already exists
   */
  void addShape(IShape shape) throws IllegalArgumentException;

  /**
   * Adds a shape with the given parameters to the Animation Model's list of shapes if it does
   * not already exist.
   * @param id unique identifier
   * @param type shape type
   * @param posn initial position of shape
   * @param color color of shape
   * @param width width of shape
   * @param height height of shape
   * @throws IllegalArgumentException if width or height are negative, id, posn, or color are null,
   *         or if shape with given id already exists
   */
  void addShape(String id, ShapeType type, Point2D posn, Color color, int width, int height)
      throws IllegalArgumentException;

  /**
   * Adds a new event to the correct shape by id.
   * @param id shape to add event to
   * @param event event to be added
   * @throws IllegalArgumentException if shape or id is null, or if shape
   *         is invalid, or if starting parameters of given event don't match those of the
   *         previous event.
   */
  void addEvent(String id, IEvent event);

  /**
   * Adds a new event to the correct shape by id using the given parameters.
   * @param id unique identifier
   * @param startTime starting time of event
   * @param endTime ending time of event
   * @param endPosn final position of shape after event
   * @param endColor final color of shape after event
   * @param endWidth final width of shape after event
   * @param endHeight final height of shape after event
   * @throws IllegalArgumentException if id, endPosn, or endColor are null, or if endWidth,
   *         endHeight, startTime, or endTime are negative, or if startTime is greater than
   *         endTime
   */
  void addEvent(String id, int startTime, int endTime, Point2D endPosn,
      Color endColor, int endWidth, int endHeight) throws IllegalArgumentException;

  /**
   * Inserts an event frame with the given parameters into the list of events associated with the
   * shape corresponding to the given id.
   * @param id unique shape identifier
   * @param tick tick of frame
   * @param posn position of shape at the given tick
   * @param color color of shape at the given tick
   * @param width width of shape at given tick
   * @param height height of shape at given tick
   * @throws IllegalArgumentException if id, posn, or color are null, or if tick is out of bounds,
   *         or if width or height are negative
   */
  void insertFrame(String id, int tick, Point2D posn, Color color, int width, int height)
      throws IllegalArgumentException;

  /**
   * Removes the given shape from this model.
   * @param shape shape to be removed
   * @throws IllegalArgumentException if shape is null or doesn't exist
   */
  void removeShape(IShape shape);

  /**
   * Removes the shape with the given id from this model.
   * @param id name of shape to be deleted
   * @throws IllegalArgumentException if id is null or shape associated with id doesn't exist
   */
  void removeShapeByID(String id);

  /**
   * Removes the given event from the list of events associated with the shape of the given id.
   * @param id unique identifier
   * @param event event to remove
   * @throws IllegalArgumentException if the id or event is null, shape associated with id doesn't
   *         exist, or if events list is empty
   */
  void removeEvent(String id, IEvent event);

  /**
   * Removes the event at the given tick from the list of events associated with the shape of the
   * given id.
   * @param id unique identifier
   * @param tick tick in event to be removed
   * @throws IllegalArgumentException if the id is null, id doesn't exist, or if tick falls out
   *         of bounds
   */
  void removeEventAtTick(String id, int tick);
}
