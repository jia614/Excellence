import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModel.Builder;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.events.Event;
import cs3500.animator.model.events.IEvent;
import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Plus;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.text.SVGView;
import cs3500.animator.view.text.TextualView;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the SVG View methods.
 */
public class SVGViewTest {

  Builder b;
  IAnimationModel testAModel;
  IAnimationView view;
  Appendable out;
  IShape e1;
  IShape r1;
  IShape e2;
  IShape p1;
  IShape e2Tick10;
  IEvent e1ev1;
  IEvent e1ev2;
  IEvent e1ev13;
  IEvent e1ev3;
  IEvent r1ev1;
  IEvent e2ev1;
  IEvent p1ev1;
  IEvent p1ev2;

  /**
   * Initialize some data.
   */
  @Before
  public void initData() {
    b = new AnimationModel.Builder();
    testAModel = b.setBounds(100, 150, 0, 0).build();
    out = new StringBuilder();
    view = new SVGView(out, 120);

    e1 = new Ellipse("e1", new Double(0.0, 0.0), Color.BLACK, 10, 5);
    r1 = new Rectangle("r1", new Double(1.0, 0.0), Color.BLUE, 5, 10);
    e2 = new Ellipse("e2", new Double(2.0, 0.0), Color.RED, 10, 10);
    p1 = new Plus("p1", new Double(2.0, 2.0), Color.GREEN, 10, 10);
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
    p1ev1 = new Event(0, new Double(0.0, 0.0), Color.BLACK,
        10, 5, 20, new Double(20.0, 0.0), Color.BLACK,
        10, 5);
    p1ev2 = new Event(20, new Double(20.0, 0.0), Color.BLACK,
        10, 5, 30, new Double(20.0, 0.0), Color.BLACK,
        10, 15);
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
    assertEquals("<svg height=\"0\" width=\"0\" version=\"1.1\""
            + " xmlns=\"http://www.w3.org/2000/svg\">\n\n"
            + "</svg>",
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
    assertEquals("<svg height=\"0\" width=\"0\" version=\"1.1\""
            + " xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "\n"
            + "<ellipse id=\"e1\" cx=\"0.0\" cy=\"0.0\" rx=\"5\" ry=\"2\" fill=\"rgb(0,0,0)\" "
            + "visibility=\"visible\" >\n"
            + "</ellipse>\n"
            + "<rect id=\"r1\" x=\"1.0\" y=\"0.0\" width=\"5\" height=\"10\" fill=\"rgb(0,0,255)\" "
            + "visibility=\"visible\" >\n"
            + "</rect>\n</svg>",
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
    assertEquals("<svg height=\"0\" width=\"0\" version=\"1.1\""
            + " xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "\n"
            + "<ellipse id=\"e1\" cx=\"0.0\" cy=\"0.0\" rx=\"5\" ry=\"2\" fill=\"rgb(0,0,0)\" "
            + "visibility=\"visible\" >\n"
            + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"166.66666666666666ms\" "
            + "attributeName=\"cx\" from=\"-100.0\" to=\"-80.0\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"166.66666666666666ms\" "
            + "attributeName=\"cy\" from=\"-150.0\" to=\"-150.0\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"166.66666666666666ms\" "
            + "attributeName=\"rx\" from=\"5\" to=\"5\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"166.66666666666666ms\" "
            + "attributeName=\"ry\" from=\"2\" to=\"2\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"166.66666666666666ms\" "
            + "attributeName=\"fill\" from=\"rgb(0,0,0)\" to=\"rgb(0,0,0)\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"166.66666666666666ms\" "
            + "dur=\"83.33333333333333ms\" attributeName=\"cx\" from=\"-80.0\" to=\"-80.0\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"166.66666666666666ms\" "
            + "dur=\"83.33333333333333ms\" attributeName=\"cy\" from=\"-150.0\" to=\"-150.0\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"166.66666666666666ms\" "
            + "dur=\"83.33333333333333ms\" attributeName=\"rx\" from=\"5\" to=\"5\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"166.66666666666666ms\" "
            + "dur=\"83.33333333333333ms\" attributeName=\"ry\" from=\"2\" to=\"7\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"166.66666666666666ms\" "
            + "dur=\"83.33333333333333ms\" attributeName=\"fill\" from=\"rgb(0,0,0)\" "
            + "to=\"rgb(0,0,0)\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"250.0ms\" dur=\"83.33333333333333ms\" "
            + "attributeName=\"cx\" from=\"-80.0\" to=\"-80.0\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"250.0ms\" dur=\"83.33333333333333ms\" "
            + "attributeName=\"cy\" from=\"-150.0\" to=\"-140.0\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"250.0ms\" dur=\"83.33333333333333ms\" "
            + "attributeName=\"rx\" from=\"5\" to=\"5\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"250.0ms\" dur=\"83.33333333333333ms\" "
            + "attributeName=\"ry\" from=\"7\" to=\"17\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"250.0ms\" dur=\"83.33333333333333ms\" "
            + "attributeName=\"fill\" from=\"rgb(0,0,0)\" to=\"rgb(0,0,0)\" />\n"
            + "</ellipse>\n"
            + "<rect id=\"r1\" x=\"1.0\" y=\"0.0\" width=\"5\" height=\"10\" fill=\"rgb(0,0,255)\" "
            + "visibility=\"visible\" >\n"
            + "\t<animate attributeType=\"xml\" begin=\"125.0ms\" dur=\"125.0ms\" "
            + "attributeName=\"x\" from=\"-99.0\" to=\"-84.0\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"125.0ms\" dur=\"125.0ms\" "
            + "attributeName=\"y\" from=\"-150.0\" to=\"-150.0\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"125.0ms\" dur=\"125.0ms\" "
            + "attributeName=\"width\" from=\"10\" to=\"10\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"125.0ms\" dur=\"125.0ms\" "
            + "attributeName=\"height\" from=\"5\" to=\"5\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"125.0ms\" dur=\"125.0ms\" "
            + "attributeName=\"fill\" from=\"rgb(0,0,0)\" to=\"rgb(0,0,0)\" />\n"
            + "</rect>\n</svg>",
        out.toString());
  }

  @Test
  public void testRenderPlus() {
    initData();
    testAModel.addShape(p1);
    testAModel.addEvent("p1", p1ev1);
    testAModel.addEvent("p1", p1ev2);
    try {
      view.render(testAModel);
    }
    catch (IllegalStateException e) {
      // do nothing
    }
    assertEquals("<svg height=\"0\" width=\"0\" version=\"1.1\""
        + " xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "<polygon id =\"p1\" points=\"2.0,4.5 4.5,4.5 4.5,2.0 9.0,2.0 9.0,4.5 12.0,4.5 12.0,9.0 "
        + "9.0,9.0 9.0,12.0 4.5,12.0 4.5,9.0 2.0,9.0\" fill=\"rgb(0,255,0)\" visibility=\"visible\" "
        + ">\n"
        + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"166.66666666666666ms\" "
        + "attributeName=\"points\" from=\"-100.0,-149.0 -98.0,-149.0 -98.0,-150.0 -93.0,-150.0 "
        + "-93.0,-149.0 -90.0,-149.0 -90.0,-147.0 -93.0,-147.0 -93.0,-145.0 -98.0,-145.0 -98.0,"
        + "-147.0 -100.0,-147.0\" to=\"-80.0,-149.0 -78.0,-149.0 -78.0,-150.0 -73.0,-150.0 -73.0,"
        + "-149.0 -70.0,-149.0 -70.0,-147.0 -73.0,-147.0 -73.0,-145.0 -78.0,-145.0 -78.0,-147.0 "
        + "-80.0,-147.0\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"166.66666666666666ms\" "
        + "attributeName=\"fill\" from=\"rgb(0,0,0)\" to=\"rgb(0,0,0)\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"166.66666666666666ms\" "
        + "dur=\"83.33333333333333ms\" attributeName=\"points\" from=\"-80.0,-149.0 -78.0,-149.0 "
        + "-78.0,-150.0 -73.0,-150.0 -73.0,-149.0 -70.0,-149.0 -70.0,-147.0 -73.0,-147.0 -73.0,"
        + "-145.0 -78.0,-145.0 -78.0,-147.0 -80.0,-147.0\" to=\"-80.0,-147.0 -78.0,-147.0 -78.0,"
        + "-150.0 -73.0,-150.0 -73.0,-147.0 -70.0,-147.0 -70.0,-140.0 -73.0,-140.0 -73.0,-135.0 "
        + "-78.0,-135.0 -78.0,-140.0 -80.0,-140.0\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"166.66666666666666ms\" "
        + "dur=\"83.33333333333333ms\" attributeName=\"fill\" from=\"rgb(0,0,0)\" to=\"rgb(0,0,0)\" "
        + "/>\n"
        + "</polygon>\n"
        + "</svg>", out.toString());
  }
}
