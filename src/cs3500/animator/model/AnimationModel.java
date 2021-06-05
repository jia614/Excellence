package cs3500.animator.model;

import cs3500.animator.model.events.Event;
import cs3500.animator.model.events.IEvent;
import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IReadOnlyShape;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Plus;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.model.shapes.ShapeType;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import cs3500.animator.utils.AnimationBuilder;

/**
 * Represents the model (of the model-view-controller) implementation for the easy animator.
 * Holds the list of shapes, maps each shape by its ID to its corresponding events, and holds
 * the dimensions and origin point of the animation.
 * Invariants:
 * - Events associated with each shape ID are ordered in increasing intervals.
 * - Intervals of events do not overlap and do not have gaps.
 * - Initial states of an event should agree with the final states of the previous event.
 */
public final class AnimationModel implements IAnimationModel {
  private final List<IShape> shapes;
  private final HashMap<String, List<IEvent>> shapeEvents;
  private final Screen dimensions;

  /**
   * Constructs an {@code AnimationModel} object.
   * @param shapes shapes stored in the model
   * @param shapeEvents events stored in the model
   * @param dimensions the {@code Screen} of the model (width, height, and origin)
   * @throws IllegalArgumentException if list of shapes or list of events are null/invalid, or
   *         if width and/or height are negative.
   */
  private AnimationModel(List<IShape> shapes, HashMap<String, List<IEvent>> shapeEvents,
      Screen dimensions) throws IllegalArgumentException {
    if (shapes == null || shapeEvents == null) {
      throw new IllegalArgumentException("Inputs cannot be null!");
    }
    Set<IReadOnlyShape> shapeSet = new HashSet<>(shapes);
    if (shapeSet.size() != shapes.size()) {
      throw new IllegalArgumentException("No duplicate shapes allowed!");
    }
    if (dimensions == null) {
      throw new IllegalArgumentException("Screen cannot be null!");
    }
    this.shapes = new ArrayList<>(shapes);
    this.shapeEvents = new HashMap<>(shapeEvents);
    this.dimensions = dimensions;
  }

  @Override
  public List<IReadOnlyShape> getShapes() {
    List<IReadOnlyShape> temp = new ArrayList<>(this.shapes);
    return temp;
  }

  @Override
  public List<IReadOnlyShape> getShapesAtTick(int tick) {
    List<IReadOnlyShape> result = new ArrayList<>();
    for (IShape shape : shapes) {
      List<IEvent> events = shapeEvents.get(shape.getName());
      if (events.size() != 0) {
        for (IEvent event : events) {
          if (tick >= event.getStartTime() && tick < event.getEndTime()) {
            result.add(getShapeAtTick(shape.getName(), event.copy(), tick));
          }
        }
      }
    }
    return result;
  }

  /**
   * Helper that retrieves the updated shape associated with the given id at the given tick.
   * @param id id of shape
   * @param tick tick to retrieve at
   * @return the shape at the given tick
   * @throws IllegalArgumentException if shape with given id doesn't exist
   */
  private IReadOnlyShape getShapeAtTick(String id, IEvent event, int tick) {
    List<IShape> copy = new ArrayList<>(shapes);
    IShape toUpdate = null;
    for (IShape shape : copy) {
      if (shape.getName().equals(id)) {
        toUpdate = shape.copy();
      }
    }
    if (toUpdate == null) {
      throw new IllegalArgumentException("Shape with the given ID does not exist!");
    }
    int start = event.getStartTime();
    int end = event.getEndTime();

    double adjustedX = valueAtTick(tick, start, end,
        event.getStartPosn().getX(), event.getEndPosn().getX());
    double adjustedY = valueAtTick(tick, start, end,
        event.getStartPosn().getY(), event.getEndPosn().getY());
    double adjustedR = valueAtTick(tick, start, end,
        event.getStartColor().getRed(), event.getEndColor().getRed());
    double adjustedG = valueAtTick(tick, start, end,
        event.getStartColor().getGreen(), event.getEndColor().getGreen());
    double adjustedB = valueAtTick(tick, start, end,
        event.getStartColor().getBlue(), event.getEndColor().getBlue());

    Point2D adjustedPosn = new Point2D.Double(adjustedX, adjustedY);
    Color adjustedColor = new Color((int) adjustedR, (int) adjustedG, (int) adjustedB);
    int adjustedWidth = (int) valueAtTick(tick, start, end,
        event.getStartWidth(), event.getEndWidth());
    int adjustedHeight = (int) valueAtTick(tick, start, end,
        event.getStartHeight(), event.getEndHeight());

    switch (toUpdate.getType()) {
      case RECTANGLE:
        return new Rectangle(toUpdate.getName(), adjustedPosn,
            adjustedColor, adjustedWidth, adjustedHeight);
      case ELLIPSE:
        return new Ellipse(toUpdate.getName(), adjustedPosn,
            adjustedColor, adjustedWidth, adjustedHeight);
      case PLUS:
        // TODO: 4/16/2021 MODIFIED 
        return new Plus(toUpdate.getName(), adjustedPosn, 
            adjustedColor, adjustedWidth, adjustedHeight);
      default:
        throw new IllegalArgumentException("Unsupported shape type!");
    }
  }

  /**
   * Helper method to determine the result value (in the range [oldValue,newValue]) that occurs
   * at the given time tick.
   * @param tick tick to get value at
   * @param startTime start time of event
   * @param endTime end time of event
   * @param oldValue original value
   * @param newValue final value
   * @return intermediary value at time tick
   */
  private double valueAtTick(int tick, int startTime, int endTime,
      double oldValue, double newValue) {
    double deltaValue = newValue - oldValue;
    double percent = (double) (tick - startTime) / (double) (endTime - startTime);
    double result = oldValue + deltaValue * percent;
    return Math.round((result * 100) / 100.0);
  }

  @Override
  public IReadOnlyShape getShapeByID(String id) throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null!");
    }
    List<IReadOnlyShape> copy = new ArrayList<>(shapes);
    for (IReadOnlyShape shape : copy) {
      if (shape.getName().equals(id)) {
        return shape;
      }
    }
    throw new IllegalArgumentException("Shape with given id does not exist!");
  }

  @Override
  public Map<String, List<IEvent>> getEvents() {
    return new HashMap<>(shapeEvents);
  }

  @Override
  public List<IEvent> getEventsById(String id) throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null!");
    }
    if (!shapeEvents.containsKey(id)) {
      throw new IllegalArgumentException("Shape with given ID does not exist!");
    }
    List<IEvent> copy = new ArrayList<>(shapeEvents.get(id));
    return copy;
  }

  @Override
  public List<IEvent> getEventsByShape(IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null!");
    }
    if (!shapes.contains(shape)) {
      throw new IllegalArgumentException("Shape does not exist!");
    }

    List<IEvent> copyEvents = new ArrayList<>(shapeEvents.get(shape.getName()));
    return copyEvents;
  }

  @Override
  public Screen getScreen() {
    return new Screen(dimensions.getWidth(), dimensions.getHeight(), dimensions.getOrigin());
  }

  @Override
  public void addShape(IShape shape) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null!");
    }
    List<IShape> copy = new ArrayList<>(shapes);
    if (copy.contains(shape)) {
      throw new IllegalArgumentException("Cannot have duplicate shapes!");
    }
    shapes.add(shape.copy());
    shapeEvents.putIfAbsent(shape.getName(), new ArrayList<>());
  }

  @Override
  public void addShape(String id, ShapeType type, Point2D posn,
      Color color, int width, int height) throws IllegalArgumentException {
    if (width < 0) {
      throw new IllegalArgumentException("Width cannot be negative!");
    }
    if (height < 0) {
      throw new IllegalArgumentException("Height cannot be negative!");
    }
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null!");
    }
    if (posn == null) {
      throw new IllegalArgumentException("Position cannot be null!");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    }
    for (IShape shape : shapes) {
      if (shape.getName().equals(id)) {
        throw new IllegalArgumentException("Cannot have duplicate shapes!");
      }
    }
    switch (type) {
      case RECTANGLE:
        shapes.add(new Rectangle(id, posn, color, width, height));
        break;
      case ELLIPSE:
        shapes.add(new Ellipse(id, posn, color, width, height));
        break;
      case PLUS:
        shapes.add(new Plus(id, posn, color, width, height));
        break;
        // TODO: 4/16/2021 MODIFIED 
      default:
        throw new IllegalArgumentException("Invalid type!"); // will never be reached
    }
    shapeEvents.putIfAbsent(id, new ArrayList<>());
  }

  @Override
  public void addEvent(String id, IEvent event) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null!");
    }
    if (event == null) {
      throw new IllegalArgumentException("Event cannot be null!");
    }

    List<IEvent> events = shapeEvents.get(id);
    if (events.size() != 0) {
      if (event.getStartTime() != events.get(events.size() - 1).getEndTime()) {
        throw new IllegalArgumentException("Events cannot overlap or have a gap!");
      }
      IEvent lastEvent = events.get(events.size() - 1).copy();
      if (!lastEvent.getEndPosn().equals(event.getStartPosn())
          || !lastEvent.getEndColor().equals(event.getStartColor())
          || lastEvent.getEndWidth() != event.getStartWidth()
          || lastEvent.getEndHeight() != event.getStartHeight()) {
        throw new IllegalArgumentException("Event states must match!");
      }
    }
    shapeEvents.get(id).add(event.copy());
  }

  @Override
  public void addEvent(String id, int startTime, int endTime, Point2D endPosn,
      Color endColor, int endWidth, int endHeight) throws IllegalArgumentException {
    if (shapes == null) {
      throw new IllegalArgumentException("List of shapes cannot be null!");
    }
    if (shapeEvents == null) {
      throw new IllegalArgumentException("Mapping of shape ID -> events cannot be null!");
    }
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null!");
    }
    if (endPosn == null) {
      throw new IllegalArgumentException("Position cannot be null!");
    }
    if (endColor == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    }
    if (endWidth < 0) {
      throw new IllegalArgumentException("Width cannot be negative!");
    }
    if (endHeight < 0) {
      throw new IllegalArgumentException("Height cannot be negative!");
    }
    if (startTime < 0 || endTime < 0) {
      throw new IllegalArgumentException("Times cannot be negative!");
    }
    if (startTime > endTime) {
      throw new IllegalArgumentException("Start must begin before end!");
    }
    IShape initShape = null;
    boolean contains = false;
    for (IShape shape : shapes) {
      if (shape.getName().equals(id)) {
        contains = true;
        initShape = shape.copy();
      }
    }
    if (!contains || (initShape == null)) {
      throw new IllegalArgumentException("Shape does not yet exist in model!");
    }
    List<IEvent> events = shapeEvents.get(id);
    int lastIdx = events.size() - 1;
    if (events.size() != 0) {
      IEvent lastEvent = events.get(lastIdx).copy();
      Point2D oldPosn = lastEvent.getEndPosn();
      Color oldColor = lastEvent.getEndColor();
      int oldWidth = lastEvent.getEndWidth();
      int oldHeight = lastEvent.getEndHeight();
      if (startTime != lastEvent.getEndTime()) {
        throw new IllegalArgumentException("New event start time must be equal to end time"
            + " of last event that occurred!");
      }
      events.add(new Event(startTime, oldPosn, oldColor, oldWidth, oldHeight,
          endTime, endPosn, endColor, endWidth, endHeight));
    }
    else {
      events.add(new Event(startTime, initShape.getPosn(), initShape.getColor(),
          initShape.getWidth(), initShape.getHeight(), endTime, endPosn,
          endColor, endWidth, endHeight));
    }
  }

  @Override
  public void insertFrame(String id, int tick, Point2D posn, Color color, int width, int height)
      throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null!");
    }
    if (posn == null) {
      throw new IllegalArgumentException("Position cannot be null!");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    }
    if (width < 0) {
      throw new IllegalArgumentException("Width cannot be negative!");
    }
    if (height < 0) {
      throw new IllegalArgumentException("Height cannot be negative!");
    }
    List<IEvent> events = shapeEvents.get(id);
    if (events.size() == 0) {
      events.add(new Event(tick, new Point2D.Double(posn.getX(), posn.getY()),
          new Color(color.getRGB()), width, height, tick, new Point2D.Double(posn.getX(),
          posn.getY()), new Color(color.getRGB()), width, height));
    }
    if (tick < events.get(0).getStartTime() || tick > events.get(events.size() - 1).getEndTime()) {
      throw new IllegalArgumentException("Tick out of bounds!");
    }
    else {
      List<IEvent> worklist = new ArrayList<>(events);
      for (IEvent event : worklist) {
        if (tick > event.getStartTime() && tick < event.getEndTime()) {
          int eventIdx = events.indexOf(event);
          events.remove(eventIdx);
          IEvent copy = event.copy();
          events.addAll(eventIdx, splice(copy, tick,
              new Double(posn.getX(), posn.getY()), new Color(color.getRGB()), width, height));
        }
      }
    }
  }

  /**
   * Helper that splits the given event at the given tick, and returns a list of the split events.
   * @param event event to edit
   * @param tick tick to split at
   * @param posn new position at tick
   * @param color new color at tick
   * @param width new width at tick
   * @param height new height at tick
   * @return a list of the resulting two split events
   */
  private List<IEvent> splice(IEvent event, int tick, Point2D posn,
      Color color, int width, int height) {
    IEvent upToTick = new Event(event.getStartTime(), event.getStartPosn(), event.getStartColor(),
        event.getStartWidth(), event.getStartHeight(), tick, posn, color, width, height);
    IEvent fromTick = new Event(tick, posn, color, width, height, event.getEndTime(),
        event.getEndPosn(), event.getEndColor(), event.getEndWidth(), event.getEndHeight());
    List<IEvent> splicedEvents = new ArrayList<>();
    splicedEvents.add(upToTick);
    splicedEvents.add(fromTick);
    return splicedEvents;
  }

  @Override
  public void removeShape(IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null!");
    }
    IShape copy = shape.copy();
    Set<String> ids = shapeEvents.keySet();
    Set<String> iter = new HashSet<>(ids);
    for (String s : iter) {
      if (s.equals(shape.getName())) {
        ids.remove(s);
      }
    }
    shapes.remove(copy);
  }

  @Override
  public void removeShapeByID(String id) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null!");
    }
    List<IShape> copy = new ArrayList<>(shapes);
    for (IShape s : copy) {
      if (s.getName().equals(id)) {
        shapes.remove(s);
      }
    }
    Set<String> ids = shapeEvents.keySet();
    Set<String> iter = new HashSet<>(ids);
    for (String s : iter) {
      if (s.equals(id)) {
        ids.remove(s);
      }
    }
  }

  @Override
  public void removeEvent(String id, IEvent event) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null!");
    }
    if (event == null) {
      throw new IllegalArgumentException("Event cannot be null!");
    }
    if (!shapeEvents.containsKey(id)) {
      throw new IllegalArgumentException("Given ID does not exist!");
    }
    IEvent copyEvent = event.copy();
    List<IEvent> events = shapeEvents.get(id);
    List<IEvent> copyEvents = new ArrayList<>(events);
    int eventIdx = copyEvents.indexOf(copyEvent);
    if (events.size() == 0) {
      throw new IllegalArgumentException("There are no events associated with this shape!");
    }
    if (eventIdx == 0 || eventIdx == events.size() - 1) {
      events.remove(copyEvent);
    }
    else {
      IEvent prev = copyEvents.get(eventIdx - 1);
      IEvent next = copyEvents.get(eventIdx + 1);
      IEvent alteredEvent = new Event(prev.getStartTime(), prev.getStartPosn(),
          prev.getStartColor(), prev.getStartWidth(), prev.getStartHeight(), next.getStartTime(),
          next.getStartPosn(), next.getStartColor(), next.getStartWidth(), next.getStartHeight());
      events.remove(copyEvent);
      events.remove(prev);
      events.add(eventIdx - 1, alteredEvent);
    }
  }

  @Override
  public void removeEventAtTick(String id, int tick) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null!");
    }
    if (!shapeEvents.containsKey(id)) {
      throw new IllegalArgumentException("Given ID does not exist!");
    }
    List<IEvent> events = new ArrayList<>(shapeEvents.get(id));
    int size = events.size();
    if (size == 0) {
      throw new IllegalArgumentException("No events for the given shape!");
    }
    if (tick < events.get(0).getStartTime() || tick > events.get(size - 1).getEndTime()) {
      throw new IllegalArgumentException("Tick out of bounds!");
    }
    for (IEvent event : events) {
      if (tick >= event.getStartTime() && tick <= event.getEndTime()) {
        removeEvent(id, event.copy());
      }
    }
  }

  /**
   * A builder class for constructing a model that ensures that there are no gaps/teleportations
   * in either the list of shapes or the hashmap of shapes -> events provided to the model. Inherits
   * the methods from {@code AnimationBuilder}.
   * Invariants: Identical to those of the AnimationModel class.
   */
  public static final class Builder implements AnimationBuilder<IAnimationModel> {
    private final List<IShape> shapes;
    private final HashMap<String, List<IEvent>> shapeEvents;
    private Screen dimensions;

    /**
     * Initial constructor that initializes our Builder fields.
     */
    public Builder() {
      shapes = new ArrayList<>();
      shapeEvents = new HashMap<>();
      dimensions = new Screen(0, 0, new Point2D.Double(0.0, 0.0));
    }

    @Override
    public AnimationBuilder<IAnimationModel> setBounds(int x, int y, int width, int height)
        throws IllegalArgumentException {
      if (width < 0) {
        throw new IllegalArgumentException("Width cannot be negative!");
      }
      if (height < 0) {
        throw new IllegalArgumentException("Height cannot be negative!");
      }
      dimensions = new Screen(width, height, new Point2D.Double(x, y));
      return this;
    }

    @Override
    public AnimationBuilder<IAnimationModel> declareShape(String id, String type)
        throws IllegalArgumentException {
      if (id == null) {
        throw new IllegalArgumentException("ID cannot be null!");
      }
      if (type == null) {
        throw new IllegalArgumentException("Shape type cannot be null!");
      }
      for (IShape shape : shapes) {
        if (shape.getName().equals(id)) {
          throw new IllegalArgumentException("Cannot have duplicate shapes!");
        }
      }
      switch (type) {
        case "rectangle":
          shapes.add(new Rectangle(id));
          break;
        case "ellipse":
          shapes.add(new Ellipse(id));
          break;
        case "plus":
          // TODO: 4/16/2021 MODIFIED 
          shapes.add(new Plus(id));
          break;
        default:
          throw new IllegalArgumentException("Invalid shape type!");
      }
      shapeEvents.putIfAbsent(id, new ArrayList<>());
      return this;
    }

    @Override
    public AnimationBuilder<IAnimationModel> addMotion(String name,
        int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
        int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
        throws IllegalArgumentException {
      if (t1 > t2) {
        throw new IllegalArgumentException("Start time must be less than end time!");
      }
      if (t1 < 0) {
        throw new IllegalArgumentException("Times cannot be negative!");
      }
      if (w1 < 0 || w2 < 0) {
        throw new IllegalArgumentException("Widths cannot be negative!");
      }
      if (h1 < 0 || h2 < 0) {
        throw new IllegalArgumentException("Heights cannot be negative!");
      }
      if (r1 < 0 || r1 > 255 || r2 < 0 || r2 > 255) {
        throw new IllegalArgumentException("Red intensity value must fall in range [0,255]!");
      }
      if (b1 < 0 || b1 > 255 || b2 < 0 || b2 > 255) {
        throw new IllegalArgumentException("Blue intensity value must fall in range [0,255]!");
      }
      if (g1 < 0 || g1 > 255 || g2 < 0 || g2 > 255) {
        throw new IllegalArgumentException("Green intensity value must fall in range [0,255]!");
      }

      IShape initShape = null;
      boolean contains = false;
      for (IShape shape : shapes) {
        if (shape.getName().equals(name)) {
          contains = true;
          initShape = shape.copy();
        }
      }
      if (!contains || (initShape == null)) {
        throw new IllegalArgumentException("Shape does not yet exist in model!");
      }
      List<IEvent> events = shapeEvents.get(name);
      int lastIdx = events.size() - 1;

      if (events.size() != 0) {
        IEvent lastEvent = events.get(lastIdx).copy();
        int oldEndTime = lastEvent.getEndTime();
        Point2D oldPosn = lastEvent.getEndPosn();
        Color oldColor = lastEvent.getEndColor();
        int oldWidth = lastEvent.getEndWidth();
        int oldHeight = lastEvent.getEndHeight();

        compareValues(t1, oldEndTime);
        compareValues(x1, (int) oldPosn.getX());
        compareValues(y1, (int) oldPosn.getY());
        compareValues(w1, oldWidth);
        compareValues(h1, oldHeight);
        compareValues(r1, oldColor.getRed());
        compareValues(g1, oldColor.getGreen());
        compareValues(b1, oldColor.getBlue());
      }
      events.add(new Event(t1, new Point2D.Double(x1, y1), new Color(r1, g1, b1), w1, h1,
          t2, new Point2D.Double(x2, y2), new Color(r2, g2, b2), w2, h2));
      return this;
    }

    /**
     * Helper method that compares two integers for equality.
     * @param v1 first value
     * @param v2 second value
     * @throws IllegalArgumentException if given values are not equal
     */
    private void compareValues(int v1, int v2) throws IllegalArgumentException {
      if (v1 != v2) {
        throw new IllegalArgumentException("New event initial value must be equal to previous"
            + "event's final value!");
      }
    }

    /**
     * Constructs the animation model.
     * @return the new animation model with the given parameters.
     */
    public IAnimationModel build() {
      return new AnimationModel(shapes, shapeEvents, dimensions);
    }
  }
}
