package cs3500.animator.model;

import cs3500.animator.model.events.IEvent;
import cs3500.animator.model.shapes.IReadOnlyShape;
import cs3500.animator.model.shapes.IShape;
import java.util.List;
import java.util.Map;

/**
 * Maintains the state for an animation that can only be read from (i.e. only includes the
 * getters of the animation), and does not allow any new data to be written to the model.
 */
public interface IReadOnlyAnimationModel {

  /**
   * Gets all shapes currently in animation.
   * @return the list of shapes in the model
   */
  List<IReadOnlyShape> getShapes();

  /**
   * Retrieves the list of shapes in the Animation Model at the given tick.
   * @param tick the tick to retrieve the list of shapes at
   * @return the list of shapes at the given tick
   * @throws IllegalArgumentException if given tick produces no shapes in the list
   */
  List<IReadOnlyShape> getShapesAtTick(int tick);

  /**
   * Retrieves the shape by id.
   * @param id name of shape to be retrieved
   * @return the IShape
   * @throws IllegalArgumentException if no shape with given id exists
   */
  IReadOnlyShape getShapeByID(String id) throws IllegalArgumentException;

  /**
   * Retrieves the mapping of shape id to its events.
   * @return hashmap with string -> list of events
   */
  Map<String, List<IEvent>> getEvents() throws IllegalArgumentException;

  /**
   * Retrieves the events of the shape associated with the given id.
   * @param id of shape whose events need to be retrieved
   * @return the list of events associated with the given id
   * @throws IllegalArgumentException if the given id is null or doesn't exist, or if id does
   *         not correspond to an existing shape
   */
  List<IEvent> getEventsById(String id) throws IllegalArgumentException;

  /**
   * Retrieves the given shape's events.
   * @param shape whose events need to be retrieved
   * @return the list of events of the given shape
   * @throws IllegalArgumentException if given shape is null or doesn't exist
   */
  List<IEvent> getEventsByShape(IShape shape) throws IllegalArgumentException;

  /**
   * Retrieves the {@code Screen} of this animation.
   * @return the screen dimensions and origin point of this model's animation
   */
  Screen getScreen();
}
