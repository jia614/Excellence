import static org.junit.Assert.assertEquals;

import cs3500.animator.model.Screen;
import java.awt.geom.Point2D.Double;
import org.junit.Test;

/**
 * Tests for Screen methods.
 */
public class ScreenTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructScreenNegWidth() {
    new Screen(-4, 10, new Double(0.0, 0.0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructScreenNegHeight() {
    new Screen(5, -5, new Double(0.0, 0.0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructScreenNullOrigin() {
    new Screen(5, 10, null);
  }

  @Test
  public void testGetWidth() {
    Screen screen = new Screen(10, 15, new Double(0.0, 0.0));
    assertEquals(10, screen.getWidth());
  }

  @Test
  public void testGetHeight() {
    Screen screen = new Screen(10, 15, new Double(0.0, 0.0));
    assertEquals(15, screen.getHeight());
  }

  @Test
  public void testGetOrigin() {
    Screen screen = new Screen(10, 15, new Double(0.0, 0.0));
    assertEquals(new Double(0.0, 0.0), screen.getOrigin());
  }

  @Test
  public void testEquals() {
    Screen testScreen1 = new Screen(8, 7, new Double(0.0, 0.0));
    Screen testScreen2 = new Screen(8, 7, new Double(0.0, 0.0));
    assertEquals(testScreen1, testScreen2);
  }

  @Test
  public void testHashCode() {
    Screen testScreen1 = new Screen(8, 7, new Double(0.0, 0.0));
    assertEquals(37696, testScreen1.hashCode());
  }
}
