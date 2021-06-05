import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModel.Builder;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.Screen;
import cs3500.animator.model.events.Event;
import cs3500.animator.model.events.IEvent;
import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.model.shapes.ShapeType;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the AnimationModel methods.
 */
public class AnimationModelTest {
  Builder b;
  IAnimationModel testAModel;
  IShape e1;
  IShape r1;
  IShape e2;
  IShape e2Tick10;
  IEvent e1ev1;
  IEvent e1ev2;
  IEvent e1ev13;
  IEvent e1ev3;
  IEvent r1ev1;
  IEvent e2ev1;

  @Before
  public void initData() {
    b = new AnimationModel.Builder();
    testAModel = b.setBounds(0, 0, 100, 150).build();

    e1 = new Ellipse("e1", new Double(0.0, 0.0), Color.BLACK, 10, 5);
    r1 = new Rectangle("r1", new Double(1.0, 0.0), Color.BLUE, 5, 10);
    e2 = new Ellipse("e2", new Double(2.0, 0.0), Color.RED, 10, 10);
    e2Tick10 = new Ellipse("e2", new Point2D.Double(7.0, 0.0),
        Color.BLACK, 10, 5);

    e1ev1 = new Event(0, new Double(0.0, 0.0), Color.BLACK,
        10, 5, 20, new Double(20.0, 0.0), Color.BLACK,
        10, 5);
    e1ev2 = new Event(20, new Double(20.0, 0.0), Color.BLACK,
        10, 5, 30, new Double(20.0, 0.0), Color.BLACK,
        10, 15);
    e1ev13 = new Event(0, new Double(0.0, 0.0), Color.BLACK,
        10, 5, 30, new Double(20.0, 0.0), Color.BLACK,
        10, 15);
    e1ev3 = new Event(30, new Double(20.0, 0.0), Color.BLACK,
        10, 15, 40, new Double(20.0, 10.0),
        Color.BLACK, 10, 35);
    r1ev1 = new Event(15, new Double(1.0, 0.0), Color.BLACK,
        10, 5, 30, new Double(16.0, 0.0),
        Color.BLACK, 10, 5);
    e2ev1 = new Event(5, new Double(2.0, 0.0), Color.BLACK,
        10, 5, 25, new Double(22.0, 0.0),
        Color.BLACK, 10, 5);
  }

  @Test
  public void testGetShapes() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape(r1);
    testAModel.addShape(e2);
    assertEquals(e1, testAModel.getShapes().get(0));
    assertEquals(r1, testAModel.getShapes().get(1));
    assertEquals(e2, testAModel.getShapes().get(2));
  }

  @Test
  public void testGetShapesAtTickNoShapes() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape(e2);
    testAModel.addShape(r1);
    testAModel.addEvent("e1", e1ev3);
    testAModel.addEvent("e2", e2ev1);
    assertEquals(0, testAModel.getShapesAtTick(2).size());
    assertEquals(0, testAModel.getShapesAtTick(50).size());
  }

  @Test
  public void testGetShapesAtTickOneShape() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape(e2);
    testAModel.addShape(r1);
    testAModel.addEvent("e1", e1ev3);
    testAModel.addEvent("e2", e2ev1);
    assertEquals(1, testAModel.getShapesAtTick(10).size());
    assertEquals(e2Tick10, testAModel.getShapesAtTick(10).get(0));
  }

  @Test
  public void testGetShapesAtTickMoreThanOne() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape(e2);
    testAModel.addShape(r1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e2", e2ev1);
    testAModel.addEvent("r1", r1ev1);
    assertEquals(2, testAModel.getShapesAtTick(25).size());

    assertEquals(new Ellipse("e1", e1ev2.getStartPosn(), e1ev2.getStartColor(),
        e1ev2.getStartWidth(), 10), testAModel.getShapesAtTick(25).get(0));

    assertEquals(new Rectangle("r1", new Point2D.Double(11, r1ev1.getStartPosn().getY()),
        r1ev1.getStartColor(), r1ev1.getStartWidth(), r1ev1.getStartHeight()),
        testAModel.getShapesAtTick(25).get(1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetShapeByIdNullID() {
    initData();
    testAModel.getShapeByID(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetShapeByIdInvalidID() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape(r1);
    testAModel.addShape(e2);
    testAModel.getShapeByID("asegaepgaejgaoi");
  }

  @Test
  public void testGetShapeById() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape(r1);
    testAModel.addShape(e2);
    assertEquals(r1, testAModel.getShapeByID("r1"));
  }

  @Test
  public void testGetEvents() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    Map<String, List<IEvent>> shapeEvents = testAModel.getEvents();
    assertEquals(e1ev1, shapeEvents.get("e1").get(0));
    assertEquals(e1ev2, shapeEvents.get("e1").get(1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetEventsByIdNullID() {
    initData();
    testAModel.addShape(e1);
    testAModel.getEventsById(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetEventsByIdInvalidID() {
    initData();
    testAModel.addShape(e1);
    testAModel.getEventsById("agaegaegae");
  }

  @Test
  public void testGetEventsById() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    assertEquals(e1ev1, testAModel.getEventsById("e1").get(0));
    assertEquals(e1ev2, testAModel.getEventsById("e1").get(1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetEventsByEventNullShape() {
    initData();
    testAModel.addShape(e1);
    testAModel.getEventsByShape(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetEventsByEventNullInvalidShape() {
    initData();
    testAModel.addShape(e1);
    testAModel.getEventsByShape(r1);
  }

  @Test
  public void testGetEventsByEvent() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    assertEquals(e1ev1, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(1));
  }

  @Test
  public void testGetScreen() {
    initData();
    assertEquals(new Screen(100, 150, new Double(0.0, 0.0)),
        testAModel.getScreen());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddShapeNullShape() {
    initData();
    testAModel.addShape(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddShapeDuplicateShape() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape(e1);
  }

  @Test
  public void testAddShapeEmptyList() {
    initData();
    testAModel.addShape(e1);
    assertEquals(1, testAModel.getShapes().size());
    assertEquals(e1, testAModel.getShapes().get(0));
  }

  @Test
  public void testAddShapeNotEmptyList() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape(r1);
    assertEquals(2, testAModel.getShapes().size());
    assertEquals(e1, testAModel.getShapes().get(0));
    assertEquals(r1, testAModel.getShapes().get(1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddShapeNegWidth() {
    initData();
    testAModel.addShape("e1", ShapeType.ELLIPSE,
        new Double(0.0, 0.0), Color.BLACK, -1, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddShapeNegHeight() {
    initData();
    testAModel.addShape("e1", ShapeType.ELLIPSE,
        new Double(0.0, 0.0), Color.BLACK, 2, -1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddShapeNullId() {
    initData();
    testAModel.addShape(null, ShapeType.ELLIPSE,
        new Double(0.0, 0.0), Color.BLACK, 2, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddShapeNullPosn() {
    initData();
    testAModel.addShape("e1", ShapeType.ELLIPSE, null, Color.BLACK, 2, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddShapeNullColor() {
    initData();
    testAModel.addShape("e1", ShapeType.ELLIPSE, new Double(0.0, 0.0),
        null, 2, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddShapeDuplicates() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape("e1", ShapeType.ELLIPSE, new Double(0.0, 0.0),
        Color.BLACK, 2, 2);
  }

  @Test
  public void testAddShapeToMT() {
    initData();
    testAModel.addShape("e1", ShapeType.ELLIPSE, new Double(0.0, 0.0),
        Color.BLACK, 10, 5);
    assertEquals(1, testAModel.getShapes().size());
    assertEquals(e1, testAModel.getShapes().get(0));
    assertEquals(0, testAModel.getEventsById("e1").size());
  }

  @Test
  public void testAddShapeToNotMT() {
    initData();
    testAModel.addShape(r1);
    testAModel.addShape("e1", ShapeType.ELLIPSE, new Double(0.0, 0.0),
        Color.BLACK, 10, 5);
    assertEquals(2, testAModel.getShapes().size());
    assertEquals(r1, testAModel.getShapes().get(0));
    assertEquals(e1, testAModel.getShapes().get(1));
    assertEquals(0, testAModel.getEventsById("r1").size());
    assertEquals(0, testAModel.getEventsById("e1").size());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEventNullID() {
    initData();
    testAModel.addEvent(null,e1ev1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEventNullEvent() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEventGap() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEventOverlap() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e2ev1);
  }

  @Test
  public void testAddEvent() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e1", e1ev3);
    assertEquals(3, testAModel.getEventsById("e1").size());
    assertEquals(e1ev1, testAModel.getEventsById("e1").get(0));
    assertEquals(e1ev2, testAModel.getEventsById("e1").get(1));
    assertEquals(e1ev3, testAModel.getEventsById("e1").get(2));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEvent2NullID() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent(null, 0, 10, new Double(0.0,0.0),
        Color.BLACK, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEvent2NullEndPosn() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", 0, 10, null,
        Color.BLACK, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEvent2NullEndColor() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", 0, 10, new Double(0.0,0.0),
        null, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEvent2NegEndWidth() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", 0, 10, new Double(0.0,0.0),
        Color.BLACK, -1, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEvent2NegEndHeight() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", 0, 10, new Double(0.0,0.0),
        Color.BLACK, 10, -1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEvent2NegStartTime() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", -1, 10, new Double(0.0,0.0),
        Color.BLACK, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEvent2NegEndTime() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", 0, -1, new Double(0.0,0.0),
        Color.BLACK, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEvent2StartAfterEnd() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", 10, 0, new Double(0.0,0.0),
        Color.BLACK, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEvent2ShapeDoesNotYetExist() {
    initData();
    testAModel.addEvent("e1", 0, 10, new Double(0.0,0.0),
        Color.BLACK, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEvent2Gap() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e1", 100, 110, new Double(0.0,0.0),
        Color.BLACK, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddEvent2Overlap() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", 5, 10, new Double(0.0,0.0),
        Color.BLACK, 10, 5);
  }

  @Test
  public void testAddEvent2ToMT() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", 0, 20, new Double(20.0,0.0),
        Color.BLACK, 10, 5);
    assertEquals(1, testAModel.getEventsByShape(e1).size());
    assertEquals(e1ev1, testAModel.getEventsByShape(e1).get(0));
  }

  @Test
  public void testAddEvent2NotMT() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", 0, 20, new Double(20.0,0.0),
        Color.BLACK, 10, 5);
    testAModel.addEvent("e1", 20, 30, new Double(20.0,0.0),
        Color.BLACK, 10, 15);
    assertEquals(2, testAModel.getEventsByShape(e1).size());
    assertEquals(e1ev1, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInsertFrameTickOutOfBoundsNegTick() {
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.insertFrame("e1", -1, new Double(10.0, 2.0), Color.CYAN, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInsertFrameTickOutOfBoundsHighTick() {
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.insertFrame("e1", 100, new Double(10.0, 2.0), Color.CYAN, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInsertFrameNullID() {
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.insertFrame(null, 3, new Double(10.0, 2.0), Color.CYAN, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInsertFrameNullPosn() {
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.insertFrame("e1", 3, null, Color.CYAN, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInsertFrameNullColor() {
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.insertFrame("e1", 3, new Double(10.0, 2.0), null, 10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInsertFrameNegWidth() {
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.insertFrame("e1", 3, new Double(10.0, 2.0), Color.CYAN, -10, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInsertFrameNegHeight() {
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.insertFrame("e1", 3, new Double(10.0, 2.0), Color.CYAN, 10, -5);
  }

  @Test
  public void testInsertFrameIntoMT() {
    testAModel.addShape(e1);
    IEvent testEvent = new Event(3, new Double(10.0, 2.0), Color.CYAN, 10, 5,
        3, new Double(10.0, 2.0), Color.CYAN, 10, 5);
    testAModel.insertFrame("e1", 3, new Double(10.0, 2.0), Color.CYAN, 10, 5);
    assertEquals(1, testAModel.getEventsById("e1").size());
    assertEquals(testEvent, testAModel.getEventsById("e1").get(0));
  }

  @Test
  public void testInsertFrameIntoNotMT() {
    testAModel.addShape(e1);
    testAModel.addEvent("e1",e1ev1);
    IEvent testEvent = new Event(0, new Double(0.0, 0.0), Color.BLACK, 10, 5,
        3, new Double(10.0, 2.0), Color.CYAN, 10, 5);
    IEvent testEvent2 = new Event(3, new Double(10.0, 2.0), Color.CYAN, 10, 5,
        20, new Double(20.0, 0.0), Color.BLACK, 10, 5);
    testAModel.insertFrame("e1", 3, new Double(10.0, 2.0), Color.CYAN, 10, 5);
    assertEquals(2, testAModel.getEventsById("e1").size());
    assertEquals(testEvent, testAModel.getEventsById("e1").get(0));
    assertEquals(testEvent2, testAModel.getEventsById("e1").get(1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveShapeNullShape() {
    initData();
    testAModel.removeShape(null);
  }

  @Test
  public void testRemoveShapeWithNoEvents() {
    initData();
    testAModel.addShape(e1);
    assertEquals(1, testAModel.getShapes().size());
    testAModel.removeShape(e1);
    assertEquals(0, testAModel.getShapes().size());
  }

  @Test
  public void testRemoveShapeWithEvents() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    assertEquals(1, testAModel.getShapes().size());
    testAModel.removeShape(e1);
    assertEquals(0, testAModel.getShapes().size());
  }

  @Test
  public void testRemoveShapeNotThereNoChange() {
    initData();
    testAModel.addShape(e1);
    assertEquals(1, testAModel.getShapes().size());
    testAModel.removeShape(e2);
    assertEquals(1, testAModel.getShapes().size());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveShapeByIDNullID() {
    initData();
    testAModel.removeShapeByID(null);
  }

  @Test
  public void testRemoveShapeByIDWithNoEvents() {
    initData();
    testAModel.addShape(e1);
    assertEquals(1, testAModel.getShapes().size());
    testAModel.removeShapeByID("e1");
    assertEquals(0, testAModel.getShapes().size());
  }

  @Test
  public void testRemoveShapeByIDWithEvents() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    assertEquals(1, testAModel.getShapes().size());
    testAModel.removeShapeByID("e1");
    assertEquals(0, testAModel.getShapes().size());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveEventNullID() {
    initData();
    testAModel.addShape(e1);
    testAModel.removeEvent(null, e1ev1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveEventNullEvent() {
    initData();
    testAModel.addShape(e1);
    testAModel.removeEvent("e1", null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveEventNoSuchID() {
    initData();
    testAModel.removeEvent("e1", e1ev1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveEventFromMTEvents() {
    initData();
    testAModel.addShape(e1);
    testAModel.removeEvent("e1", e1ev1);
  }

  @Test
  public void testRemoveEventMultipleEvents() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    assertEquals(2, testAModel.getEventsByShape(e1).size());
    testAModel.removeEvent("e1", e1ev1);
    assertEquals(1, testAModel.getEventsByShape(e1).size());
    testAModel.removeEvent("e1", e1ev2);
    assertEquals(0, testAModel.getEventsByShape(e1).size());
  }

  @Test
  public void testRemoveEventFirstEvent() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e1", e1ev3);
    assertEquals(e1ev1, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(1));
    assertEquals(e1ev3, testAModel.getEventsByShape(e1).get(2));
    testAModel.removeEvent("e1", e1ev1);
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev3, testAModel.getEventsByShape(e1).get(1));
  }

  @Test
  public void testRemoveEventLastEvent() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e1", e1ev3);
    assertEquals(e1ev1, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(1));
    assertEquals(e1ev3, testAModel.getEventsByShape(e1).get(2));
    testAModel.removeEvent("e1", e1ev3);
    assertEquals(e1ev1, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(1));
  }

  @Test
  public void testRemoveEventInMiddle() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e1", e1ev3);
    assertEquals(e1ev1, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(1));
    assertEquals(e1ev3, testAModel.getEventsByShape(e1).get(2));
    testAModel.removeEvent("e1", e1ev2);
    assertEquals(e1ev13, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev3, testAModel.getEventsByShape(e1).get(1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveEventAtTickNullID() {
    initData();
    testAModel.addShape(e1);
    testAModel.removeEventAtTick(null, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveEventAtTickNoSuchID() {
    initData();
    testAModel.removeEventAtTick("e1", 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveEventAtTickOutOfBoundsBeforeStart() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.removeEventAtTick("e1", -5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveEventAtTickOutOfBoundsAfterEnd() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.removeEventAtTick("e1", 50);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveEventAtTickMTEvents() {
    initData();
    testAModel.addShape(e1);
    testAModel.removeEventAtTick("e1", 2);
  }

  @Test
  public void testRemoveEventAtTickFirstEvent() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e1", e1ev3);
    assertEquals(e1ev1, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(1));
    assertEquals(e1ev3, testAModel.getEventsByShape(e1).get(2));
    testAModel.removeEventAtTick("e1", 1);
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev3, testAModel.getEventsByShape(e1).get(1));
  }

  @Test
  public void testRemoveEventAtTickLastEvent() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e1", e1ev3);
    assertEquals(e1ev1, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(1));
    assertEquals(e1ev3, testAModel.getEventsByShape(e1).get(2));
    testAModel.removeEventAtTick("e1", 35);
    assertEquals(e1ev1, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(1));
  }

  @Test
  public void testRemoveEventAtTickMiddleEvent() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e1", e1ev3);
    assertEquals(e1ev1, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev2, testAModel.getEventsByShape(e1).get(1));
    assertEquals(e1ev3, testAModel.getEventsByShape(e1).get(2));
    testAModel.removeEventAtTick("e1", 25);
    assertEquals(e1ev13, testAModel.getEventsByShape(e1).get(0));
    assertEquals(e1ev3, testAModel.getEventsByShape(e1).get(1));
  }

  @Test
  public void testInvariantUniqueShapes() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape(e2);
    testAModel.addShape(r1);
    assertEquals(3, testAModel.getShapes().size());
    assertNotEquals(testAModel.getShapes().get(0), testAModel.getShapes().get(1));
    assertNotEquals(testAModel.getShapes().get(1), testAModel.getShapes().get(2));
    assertNotEquals(testAModel.getShapes().get(0), testAModel.getShapes().get(2));
  }

  @Test
  public void testInvariantValidEventsStates() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e1", e1ev3);
    assertEquals(e1ev1.getEndPosn(), e1ev2.getStartPosn());
    assertEquals(e1ev1.getEndColor(), e1ev2.getStartColor());
    assertEquals(e1ev1.getEndWidth(), e1ev2.getStartWidth());
    assertEquals(e1ev1.getEndHeight(), e1ev2.getStartHeight());
    assertEquals(e1ev2.getEndPosn(), e1ev3.getStartPosn());
    assertEquals(e1ev2.getEndColor(), e1ev3.getStartColor());
    assertEquals(e1ev2.getEndWidth(), e1ev3.getStartWidth());
    assertEquals(e1ev2.getEndHeight(), e1ev3.getStartHeight());
  }

  @Test
  public void testInvariantValidEventsTimes() {
    initData();
    testAModel.addShape(e1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e1", e1ev3);
    assertEquals(e1ev1.getEndTime(), e1ev2.getStartTime());
    assertEquals(e1ev2.getEndTime(), e1ev3.getStartTime());
  }
}




