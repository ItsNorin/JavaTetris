package tetris;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author ItsNorin
 * @see <a href="http://github.com/ItsNorin">Github</a>
 */
public class Screen extends JFrame {
	private static final long serialVersionUID = 5695894168737296007L;

	static final Color BACKGROUND_COLOR = Color.black;

	Game g;

	public Screen(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(BACKGROUND_COLOR);

		g = new Game();

		// keep frames centered
		Box box = new Box(BoxLayout.X_AXIS);
		box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		box.add(Box.createHorizontalGlue());
		box.add(g);
		box.add(Box.createHorizontalGlue());

		add(box);
		pack();
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_Q:
					g.rotate(false);
					break;
				case KeyEvent.VK_E:
					g.rotate(true);
					break;
					
				case KeyEvent.VK_A:
					g.move(-1, 0);
					break;
				case KeyEvent.VK_D:
					g.move(1, 0);
					break;
					
				case KeyEvent.VK_S:
					g.move(0, 1);
				}
			}

			@Override public void keyReleased(KeyEvent e) {}
			@Override public void keyTyped(KeyEvent e) {}
		});
	}

	public void start() {
		setVisible(true);
		g.init();
	}

	

	public static void main(String[] args) {
		Screen s = new Screen("Shitty Tetris");

		s.start();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				s.g.resume();
			}
		});
	}

}
