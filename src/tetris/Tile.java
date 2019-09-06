package tetris;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author ItsNorin
 * @see <a href="http://github.com/ItsNorin">Github</a>
 */
public class Tile {
	private Color color;
	private boolean exists;
	
	public static final Color BACKDROP_COLOR = Color.BLACK;
	public static final Color BORDER_COLOR = Color.DARK_GRAY;
	public static final Color HIGHLIGHT_COLOR = Color.WHITE;
	
	
	public Tile() {
		clear();
	}
	
	public Tile(Color color) {
		set(color);
	}
	
	
	/** Makes tile exist
	 * @param color Color of tile
	 */
	public void set(Color color) {
		this.color = color;
		this.exists = true;
	}
	
	
	/**
	 * Removes tile from existence
	 */
	public void clear() {
		this.color = BACKDROP_COLOR;
		this.exists = false;
	}
	
	
	/**
	 * @return true if tile exists
	 */
	public boolean exists() {
		return exists;
	}
	
	/**
	 * Draws in a tile at the given position. If tile doesn't exist, draws backdrop instead.
	 * @param tileSize Size of tile in pixels
	 * @param xPos x index in game field
	 * @param yPos y index in game field
	 * @param g Graphics
	 */
	public void paint(Graphics g, int tileSize, int xPos, int yPos) {
		if(this.exists) {
			//fill with tile
			g.setColor(color);
			g.fillRect(xPos*tileSize, yPos*tileSize, tileSize, tileSize);
			//highlight
			g.setColor(HIGHLIGHT_COLOR);
			g.fillRect(xPos*tileSize + tileSize/12, yPos*tileSize + tileSize/12, tileSize/3, tileSize/6);
			g.fillRect(xPos*tileSize + tileSize/12, yPos*tileSize + tileSize/12, tileSize/6, tileSize/3);
			//border
			g.setColor(BORDER_COLOR);
			g.drawRect(xPos*tileSize, yPos*tileSize, tileSize, tileSize);
		}
		else {
			// fill with empty space
			g.setColor(BACKDROP_COLOR);
			g.fillRect(xPos*tileSize, yPos*tileSize, tileSize, tileSize);
			g.setColor(BORDER_COLOR);
			g.drawRect(xPos*tileSize, yPos*tileSize, tileSize, tileSize);
		}
	}
}