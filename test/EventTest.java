import static org.junit.Assert.assertEquals;

import cs3500.animator.model.events.Event;
import cs3500.animator.model.events.IEvent;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Event methods.
 */
public class EventTest {
  Point2D pi;
  Point2D pf;
  IEvent e;
  IEvent eMove;
  IEvent eColor;
  IEvent eWidth;
  IEvent eHeight;

  @Before
  public void initData() {
    pi = new Double(-1.0, 2.0);
    pf = new Double(3.5, 4.0);
    e = new Event(1, pi, Color.BLUE, 10, 15,
        3, pf, Color.RED, 20, 30);
    eMove = new Event(1, pi, Color.BLUE, 10, 15,
        3, pi, Color.BLUE, 10, 15);
    eColor = new Event(1, pi, Color.BLUE, 10, 15,
        3, pf, Color.RED, 10, 15);
    eWidth = new Event(1, pi, Color.BLUE, 10, 15,
        3, pf, Color.BLUE, 20, 15);
    eHeight = new Event(1, pi, Color.BLUE, 10, 15,
        3, pf, Color.BLUE, 10, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructNegStartTime() {
    initData();
    new Event(-1, pi, Color.BLUE, 10, 15,
        3, pf, Color.RED, 10, 15);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructNegEndTime() {
    initData();
    new Event(1, pi, Color.BLUE, 10, 15,
        -5, pf, Color.RED, 10, 15);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructBadTimeOrder() {
    initData();
    new Event(5, pi, Color.BLUE, 10, 15,
        3, pf, Color.RED, 10, 15);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructNegStartWidth() {
    new Event(1, pi, Color.BLUE, -10, 15,
        3, pf, Color.RED, 10, 15);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructNegEndWidth() {
    new Event(1, pi, Color.BLUE, 10, 15,
        3, pf, Color.RED, -10, 15);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructNegStartHeight() {
    new Event(1, pi, Color.BLUE, 10, -15,
        3, pf, Color.RED, 10, 15);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructNegEndHeight() {
    new Event(1, pi, Color.BLUE, 10, 15,
        3, pf, Color.RED, 10, -15);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullStartPosn() {
    new Event(1, null, Color.BLUE, 10, 15,
        3, pf, Color.RED, 10, 15);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullEndPosn() {
    new Event(1, pi, Color.BLUE, 10, 15,
        3, null, Color.RED, 10, 15);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullStartColor() {
    new Event(1, pi, null, 10, 15,
        3, pf, Color.RED, 10, 15);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullEndColor() {
    new Event(1, pi, Color.BLUE, 10, 15,
        3, pf, null, 10, 15);
  }

  @Test
  public void testGetStartTime() {
    assertEquals(1, e.getStartTime());
  }

  @Test
  public void testGetStartPosn() {
    assertEquals(pi, e.getStartPosn());
  }

  @Test
  public void testGetStartColor() {
    assertEquals(Color.BLUE, e.getStartColor());
  }

  @Test
  public void testGetStartWidth() {
    assertEquals(10, e.getStartWidth());
  }

  @Test
  public void testGetStartHeight() {
    assertEquals(15, e.getStartHeight());
  }

  @Test
  public void testGetEndTime() {
    assertEquals(3, e.getEndTime());
  }

  @Test
  public void testGetEndPosn() {
    assertEquals(pf, e.getEndPosn());
  }

  @Test
  public void testGetEndColor() {
    assertEquals(Color.RED, e.getEndColor());
  }

  @Test
  public void testGetEndWidth() {
    assertEquals(20, e.getEndWidth());
  }

  @Test
  public void testGetEndHeight() {
    assertEquals(30, e.getEndHeight());
  }

  @Test
  public void testCopy() {
    assertEquals(new Event(1, pi, Color.BLUE, 10, 15,
        3, pf, Color.RED, 20, 30), e.copy());
  }

  @Test
  public void testEquals() {
    assertEquals(e.copy(), e);
  }

  @Test
  public void testHashCode() {
    assertEquals(1190011113, e.hashCode());
  }
}
