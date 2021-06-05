package cs3500.animator.view.interactive;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.model.events.IEvent;
import cs3500.animator.model.shapes.IReadOnlyShape;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.view.panels.OutlineDrawingPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

/**
 * An interactive representation of the animator. Its setListener() method allows the interactive
 * controller to set itself as the interactive view's listener. The actionPerformed() method allows
 * the view to emit its own events to enable drawing on the panel. Buttons for PLAY, PAUSE, RESTART,
 * SPEED-UP, SLOW-DOWN, and ENABLE/DISABLE LOOPING were created and are responded to by the
 * controller.
 */
public class InteractiveView extends JFrame implements IInteractiveView, ActionListener {
  private final int origTempo;
  private int tempo;
  private boolean loop;
  protected int tick;
  protected int maxTick;
  protected final Timer timer;

  protected IReadOnlyAnimationModel model;
  private double mx;
  private double my;
  private int width;
  private int height;

  // TODO: 4/22/2021 MODIFIED
  // drawing panel -> outline drawing panel
  // panel, button panel, tick, max tick, timer, model, and buttons made protected
  protected OutlineDrawingPanel panel; // the animation container
  protected JPanel buttonPanel = new JPanel();
  protected List<JButton> buttons;
  private final JScrollPane scroll;

  /**
   * Constructs a {@code InteractiveView} object.
   * @param tempo initial tempo of animation
   * @throws IllegalArgumentException if tempo is negative
   */
  public InteractiveView(int tempo) throws IllegalArgumentException {
    if (tempo < 0) {
      throw new IllegalArgumentException("Tempo cannot be null!");
    }
    this.origTempo = tempo;
    this.tempo = tempo;
    this.loop = false;
    this.tick = 1;
    this.timer = new Timer(tempo, this);
    this.scroll = new JScrollPane(panel,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.initButtons("PLAY", "PAUSE", "RESTART", "SPEED-UP", "SLOW-DOWN", "ENABLE/DISABLE LOOPING");
  }

  /**
   * Initializes the buttons.
   * @param buttons names of buttons to initialize
   */
  private void initButtons(String... buttons) {
    List<JButton> result = new ArrayList<>();
    for (String s : buttons) {
      JButton button = new JButton(s);
      result.add(button);
      button.setActionCommand(s);
    }
    this.buttons = result;
  }

  @Override
  public void render(IReadOnlyAnimationModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    this.mx = model.getScreen().getOrigin().getX();
    this.my = model.getScreen().getOrigin().getY();
    this.model = model;
    this.width = model.getScreen().getWidth();
    this.height = model.getScreen().getHeight();

    Map<String, List<IEvent>> events = model.getEvents();
    int max = 0;
    for (String key : events.keySet()) {
      List<IEvent> eventsAtKey = model.getEventsById(key);
      max = Math.max(max, eventsAtKey.get(eventsAtKey.size() - 1).getEndTime());
    }
    this.maxTick = max;

    this.setTitle("Excellence");
    this.setBounds((int) mx, (int) my, width, height);
    this.setLayout(new BorderLayout());
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.initDrawingPanel();
    this.pack();
    this.initButtonsPanel();
    this.initEditPanel();
    this.pack();

    setVisible(true);
  }

  /**
   * Initializes the drawing panel that holds the animation.
   */
  private void initDrawingPanel() {
    this.panel = new OutlineDrawingPanel();
    this.panel.setPreferredSize(new Dimension(width, height));
    this.panel.setBounds((int) mx, (int) my, width, height);
    this.panel.add(scroll);
    this.add(panel, BorderLayout.CENTER);
  }

  /**
   * Initializes the panel that contains the buttons that delegate the
   * PLAY, PAUSE, RESTART, SPEED-UP, SLOW-DOWN, and ENABLE/DISABLE LOOPING buttons.
   */
  private void initButtonsPanel() {
    // the container holding buttons
    for (JButton button : buttons) {
      buttonPanel.add(button);
    }
    this.add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Initializes the panel that allows the user to add shapes, remove shapes, and edit events.
   * Not fully implemented, might implement in future.
   */
  private void initEditPanel() {
    // maybe want to add shapes / events
    JTabbedPane editPane = new JTabbedPane();
    editPane.setPreferredSize(new Dimension(300,
        this.model.getScreen().getHeight()));
    this.add(editPane, BorderLayout.EAST);
  }

  @Override
  public void play() {
    this.timer.start();
  }

  @Override
  public void pause() {
    this.timer.stop();
  }

  @Override
  public void restart() {
    this.tick = 0;
    this.timer.restart();
    this.tempo = this.origTempo;
  }

  @Override
  public void speedUp() {
    this.setTempo(this.tempo - 1);
  }

  @Override
  public void slowDown() {
    this.setTempo(this.tempo + 1);
  }

  /**
   * Sets the tempo if the given tempo is positive, sets tempo = 1 otherwise.
   * @param tempo tempo to set
   */
  private void setTempo(int tempo) {
    if (this.tempo < 1) {
      this.tempo = 1;
    }
    this.tempo = tempo;
    this.timer.setDelay(this.tempo);
  }

  @Override
  public void setLoop() {
    this.loop = !this.loop;
  }

  @Override
  public void setListener(ActionListener listener) throws IllegalArgumentException {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null!");
    }
    for (JButton button : buttons) {
      button.addActionListener(listener);
    }
  }

  @Override
  public void drawShape(ShapeType type, double x, double y, int w, int h, Color color)
      throws IllegalArgumentException {
    if (w < 0) {
      throw new IllegalArgumentException("Width cannot be negative!");
    }
    if (h < 0) {
      throw new IllegalArgumentException("Height cannot be negative!");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    }
    switch (type) {
      case RECTANGLE:
        panel.drawRect(x - mx, y - my, w, h, color);
        break;
      case ELLIPSE:
        panel.drawEllipse(x - mx, y - my, w, h, color);
        break;
      case PLUS:
        panel.drawPlus(x - mx, y - my, w, h, color);
        break;
      default:
        throw new IllegalArgumentException("Shape type not supported!");
    }
  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    List<IReadOnlyShape> toRender = new ArrayList<>(model.getShapesAtTick(tick));
    for (IReadOnlyShape shape : toRender) {
      ShapeType type = shape.getType();
      drawShape(type,
          (int) shape.getPosn().getX(),
          (int) shape.getPosn().getY(),
          shape.getWidth(),
          shape.getHeight(),
          shape.getColor());
    }
    this.loopBack();
    refresh();
    tick++;
  }

  /**
   * Checks if the current tick is past the maximum tick and if looping is enabled.
   */
  protected void loopBack() {
    if (this.tick > maxTick && this.loop) {
      this.tick = 1;
    }
  }
}
