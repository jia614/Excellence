import cs3500.animator.model.AnimationModel.Builder;
import static org.junit.Assert.assertEquals;

import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.Screen;
import cs3500.animator.model.events.Event;
import cs3500.animator.model.events.IEvent;
import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IReadOnlyShape;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Rectangle;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for AnimationModel.Builder methods.
 */
public class BuilderTest {
  Builder b1;
  Screen s1;
  Point2D p1;
  Point2D pRec;
  Point2D pEl;
  IReadOnlyShape r1;
  IShape r1Mod;
  IReadOnlyShape e1;
  IShape e1Mod;
  IEvent evR1;
  IEvent evE1;
  IEvent evR2;


  @Before
  public void initData() {
    b1 = new Builder();
    p1 = new Double(0.0, 0.0);
    pRec = new Double(3.0, 4.0);
    pEl = new Double(4.0, -2.0);
    s1 = new Screen(10, 15, p1);
    r1 = new Rectangle("r1", p1, Color.WHITE, 0, 0);
    r1Mod = new Rectangle("r1", pRec, Color.BLUE, 10, 15);
    e1 = new Ellipse("e1", p1, Color.WHITE, 0, 0);
    e1Mod = new Ellipse("e1", pEl, Color.RED, 12, 50);
    evR1 = new Event(1, p1, Color.RED, 10, 10,
        3, pRec, Color.GREEN, 5, 5);
    evE1 = new Event(0, pEl, Color.RED, 12, 50,
        10, p1, Color.BLUE, 30, 30);
    evR2 = new Event(3, pRec, Color.GREEN, 5, 5,
        10, pEl, Color.GREEN, 1, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderSetBadWidth() {
    initData();
    b1.setBounds(0, 0, -1, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderSetBadHeight() {
    initData();
    b1.setBounds(0, 0, 10, -4);
  }

  @Test
  public void testBuilderSetGoodDimensions() {
    initData();
    IAnimationModel model = b1.setBounds(0, 0, 10, 15).build();
    assertEquals(s1, model.getScreen());
  }


  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddShapeNullID() {
    initData();
    b1.declareShape(null, "rectangle");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddShapeNullShapeType() {
    initData();
    b1.declareShape("r1", null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddShapeShapeTypeDoesNotExist() {
    initData();
    b1.declareShape("r1", "abadkfjs");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddExistingShape() {
    initData();
    b1.setBounds(10, 15, 0, 0)
        .declareShape("r1", "rectangle")
        .declareShape("r1", "rectangle")
        .build();
  }

  @Test
  public void testBuilderAddShapeToMTList() {
    initData();
    IAnimationModel model = b1.setBounds(10, 15, 0, 0)
        .declareShape("r1", "rectangle").build();
    assertEquals(r1, model.getShapes().get(0));
  }

  @Test
  public void testBuilderAddShapeToNonMTList() {
    initData();
    IAnimationModel model = b1.setBounds(10, 15, 0, 0)
        .declareShape("r1", "rectangle")
        .declareShape("e1", "ellipse").build();
    assertEquals(r1, model.getShapes().get(0));
    assertEquals(e1, model.getShapes().get(1));
  }


  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionNegStartWidth() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 0, 0, 0, -10, 10, 255, 0, 0,
        1, 3, 0, 15, 10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionNegEndWidth() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 0, 0, 0, 10, 10, 255, 0, 0,
        1, 3, 0, -15, 10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionNegStartHeight() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 0, 0, 0, 10, -10, 255, 0, 0,
        1, 3, 0, 15, 10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionNegEndHeight() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 0, 0, 0, 10, 10, 255, 0, 0,
        1, 3, 0, 15, -10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionLowStartRed() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 0, 0, 0, 10, 10, -255, 0, 0,
        1, 3, 0, 15, 10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionHighEndRed() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 0, 0, 0, 10, 10, 255, 0, 0,
        1, 3, 0, 15, 10, 5005, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionLowStartBlue() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 0, 0, 0, 10, 10, 255, 0, -10,
        1, 3, 0, 15, 10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionHighStartBlue() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 0, 0, 0, 10, 10, 255, 0, 10,
        1, 3, 0, 15, 10, 5, 40, 300);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionLowStartGreen() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 0, 0, 0, 10, 10, 255, -10, 10,
        1, 3, 0, 15, 10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionHighStartGreen() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 0, 0, 0, 10, 10, 255, 0, 10,
        1, 3, 0, 15, 10, 5, 400, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionNegStartTime() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", -3, 0, 0, 10, 10, 255, 0, 0,
        1, 3, 0, 15, 10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionNegEndTime() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 0, 0, 0, 10, 10, 255, 0, 0,
        -1, 3, 0, 15, 10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionStartAfterEnd() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("r1", 10, 0, 0, 10, 10, 255, 0, 0,
        3, 3, 0, 15, 10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddMotionNullID() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion(null, 0, 0, 0, 10, 10, 255, 0, 0,
        1, 3, 0, 15, 10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddEventNoSuchShape() {
    initData();
    b1.declareShape("r1", "rectangle");
    b1.addMotion("rectangle", 0, 0, 0, 10, 10, 255, 0, 0,
        1, 3, 0, 15, 10, 5, 40, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddEventOverlap() {
    initData();
    b1.declareShape("r1", "rectangle")
        .addMotion("r1", 0, 0, 0, 10, 10, 255, 0, 0,
            3, 3, 4, 5, 5, 0, 255, 0)
        .addMotion("r1", 2, 3, 4, 5, 5, 0, 255, 0,
            10, 6, 6, 3, 6, 0, 255, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuilderAddEventGap() {
    initData();
    b1.declareShape("r1", "rectangle")
        .addMotion("r1", 0, 0, 0, 10, 10, 255, 0, 0,
            3, 3, 4, 5, 5, 0, 255, 0)
        .addMotion("r1", 5, 3, 4, 5, 5, 0, 255, 0,
            10, 6, 6, 3, 6, 0, 255, 0);
  }

  @Test
  public void testBuilderAddEventToMT() {
    initData();
    IAnimationModel model = b1.setBounds(100, 200, 0, 0)
        .declareShape("r1", "rectangle")
        .addMotion("r1", 1, 0, 0, 10, 10, 255, 0, 0,
            3, 3, 4, 5, 5, 0, 255, 0)
        .build();
    assertEquals(evR1, model.getEventsById("r1").get(0));
  }

  @Test
  public void testBuilderAddEventToSameShape() {
    initData();
    IAnimationModel model = b1.setBounds(100, 200, 0, 0)
        .declareShape("r1", "rectangle")
        .addMotion("r1", 1, 0, 0, 10, 10, 255, 0, 0,
            3, 3, 4, 5, 5, 0, 255, 0)
        .addMotion("r1", 3, 3, 4, 5, 5, 0, 255, 0,
            10, 4, -2, 1, 2, 0, 255, 0)
        .build();
    assertEquals(evR1, model.getEventsById("r1").get(0));
    assertEquals(evR2, model.getEventsById("r1").get(1));
  }

  @Test
  public void testBuilderAddEventToDiffShape() {
    initData();
    IAnimationModel model = b1.setBounds(100, 200, 0, 0)
        .declareShape("r1", "rectangle")
        .declareShape("e1", "ellipse")
        .addMotion("r1", 1, 0, 0, 10, 10, 255, 0, 0,
            3, 3, 4, 5, 5, 0, 255, 0)
        .addMotion("r1", 3, 3, 4, 5, 5, 0, 255, 0,
            10, 4, -2, 1, 2, 0, 255, 0)
        .addMotion("e1", 0, 4, -2, 12, 50, 255, 0, 0,
            10, 0, 0, 30, 30, 0, 0, 255)
        .build();
    assertEquals(evR1, model.getEventsById("r1").get(0));
    assertEquals(evR2, model.getEventsById("r1").get(1));
    assertEquals(evE1, model.getEventsById("e1").get(0));
  }

  @Test
  public void testBuilderBuildMT() {
    initData();
    IAnimationModel model = b1.setBounds(100, 200, 0, 0).build();
    assertEquals(0, model.getShapes().size());
  }

  @Test
  public void testBuilderBuildNonMT() {
    initData();
    IAnimationModel model = b1.setBounds(10, 15, 0, 0)
        .declareShape("r1", "rectangle")
        .declareShape("e1", "ellipse").build();
    assertEquals(2, model.getShapes().size());
  }
}
