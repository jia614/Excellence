import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModel.Builder;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.events.Event;
import cs3500.animator.model.events.IEvent;
import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.text.TextualView;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Textual view methods.
 */
public class TextualViewTest {

  Builder b;
  IAnimationModel testAModel;
  IAnimationView view;
  Appendable out;
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

  /**
   * Initialize some data.
   */
  @Before
  public void initData() {
    b = new AnimationModel.Builder();
    testAModel = b.setBounds(100, 150, 0, 0).build();
    out = new StringBuilder();
    view = new TextualView(out, 120);

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
        10, 5);
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

  @Test (expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    view = new TextualView(null, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegTempo() {
    view = new TextualView(out, -1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegTempoConvenience() {
    view = new TextualView(-1);
  }

  @Test
  public void testViewNoShapes() {
    initData();
    try {
      view.render(testAModel);
    }
    catch (IllegalStateException e) {
      // do nothing
    }
    assertEquals("# describes the motions of a shape between two moments of animation:\n"
        + "# t == tick\n"
        + "# (x,y) == position\n"
        + "# (w,h) == dimensions\n"
        + "# (r,g,b) == color (with values between 0 and 255)\n"
        + "#                               start                  "
        + "                  end\n"
        + "#                ------------------------------------  "
        + "  -------------------------------------\n"
        + "#                t    x    y    w    h    r    g    b  "
        + "   t    x    y    w    h    r    g    b\n",
        out.toString());
  }

  @Test
  public void testViewShapesNoEvent() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape(r1);
    try {
      view.render(testAModel);
    }
    catch (IllegalStateException e) {
      // do nothing
    }
    assertEquals("# describes the motions of a shape between two moments of animation:\n"
        + "# t == tick\n"
        + "# (x,y) == position\n"
        + "# (w,h) == dimensions\n"
        + "# (r,g,b) == color (with values between 0 and 255)\n"
        + "shape e1 ELLIPSE\n"
        + "shape r1 RECTANGLE\n"
        + "#                               start                  "
        + "                  end\n"
        + "#                ------------------------------------  "
        + "  -------------------------------------\n"
        + "#                t    x    y    w    h    r    g    b  "
        + "   t    x    y    w    h    r    g    b\n",
        out.toString());
  }

  @Test
  public void testViewShapesWithEvents() {
    initData();
    testAModel.addShape(e1);
    testAModel.addShape(r1);
    testAModel.addEvent("e1", e1ev1);
    testAModel.addEvent("e1", e1ev2);
    testAModel.addEvent("e1", e1ev3);
    testAModel.addEvent("r1", r1ev1);
    try {
      view.render(testAModel);
    }
    catch (IllegalStateException e) {
      // do nothing
    }
    assertEquals("# describes the motions of a shape between two moments of animation:\n"
        + "# t == tick\n"
        + "# (x,y) == position\n"
        + "# (w,h) == dimensions\n"
        + "# (r,g,b) == color (with values between 0 and 255)\n"
        + "shape e1 ELLIPSE\n"
        + "shape r1 RECTANGLE\n"
        + "#                               start                  "
        + "                  end\n"
        + "#                ------------------------------------  "
        + "  -------------------------------------\n"
        + "#                t    x    y    w    h    r    g    b  "
        + "   t    x    y    w    h    r    g    b\n"
        + "motion e1        0    0.0  0.0  10   5    0    0    0  "
        + "   0.17 20.0 0.0  10   5    0    0    0    \n"
        + "motion e1        0.17 20.0 0.0  10   5    0    0    0  "
        + "   0.25 20.0 0.0  10   15   0    0    0    \n"
        + "motion e1        0.25 20.0 0.0  10   15   0    0    0  "
        + "   0.33 20.0 10.0 10   35   0    0    0    \n"
        + "motion r1        0.12 1.0  0.0  10   5    0    0    0  "
        + "   0.25 16.0 0.0  10   5    0    0    0    \n",
        out.toString());
  }
}
