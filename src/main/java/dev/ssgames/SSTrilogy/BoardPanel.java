package dev.ssgames.SSTrilogy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;


public class BoardPanel extends JPanel {
	

	private static final long serialVersionUID = -1102632585936750607L;
	public static final int COL_COUNT = 25;
	public static final int ROW_COUNT = 25;
	public static final int TILE_SIZE = 20;
	private static final int EYE_LARGE_INSET = TILE_SIZE / 3;
	private static final int EYE_SMALL_INSET = TILE_SIZE / 6;
	private static final int EYE_LENGTH = TILE_SIZE / 5;
	private static final Font FONT = new Font("Tahoma", Font.BOLD, 25);
	private SnakeGame game;
	private TileType[] tiles;
		

	public BoardPanel(SnakeGame game) {
		this.game = game;
		this.tiles = new TileType[ROW_COUNT * COL_COUNT];
		
		setPreferredSize(new Dimension(COL_COUNT * TILE_SIZE, ROW_COUNT * TILE_SIZE));
		setBackground(Color.BLACK);
	}
	

	public void clearBoard() {
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = null;
		}
	}
	

	public void setTile(Point point, TileType type) {
		setTile(point.x, point.y, type);
	}
	

	public void setTile(int x, int y, TileType type) {
		tiles[y * ROW_COUNT + x] = type;
	}
	

	public TileType getTile(int x, int y) {
		return tiles[y * ROW_COUNT + x];
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		

		for(int x = 0; x < COL_COUNT; x++) {
			for(int y = 0; y < ROW_COUNT; y++) {
				TileType type = getTile(x, y);
				if(type != null) {
					drawTile(x * TILE_SIZE, y * TILE_SIZE, type, g);
				}
			}
		}
		
/*
		g.setColor(Color.cyan);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		for(int x = 0; x < COL_COUNT; x++) {
			for(int y = 0; y < ROW_COUNT; y++) {
				g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, getHeight());
				g.drawLine(0, y * TILE_SIZE, getWidth(), y * TILE_SIZE);
			}
		}
*/

		if(game.isGameOver() || game.isNewGame() || game.isPaused()) {
			g.setColor(Color.WHITE);
			

			int centerX = getWidth() / 2;
			int centerY = getHeight() / 2;
			

			String largeMessage = null;
			String smallMessage = null;
			if(game.isNewGame()) {
				largeMessage = "Snake Game!";
				smallMessage = "Press Enter to Start, ESC to leave";
			} else if(game.isGameOver()) {
				largeMessage = "Game Over!";
				smallMessage = "Press Enter to Restart, ESC to leave";
			} else if(game.isPaused()) {
				largeMessage = "Paused";
				smallMessage = "Press P to Resume, ESC to leave";
			}
			

			g.setFont(FONT);
			g.drawString(largeMessage, centerX - g.getFontMetrics().stringWidth(largeMessage) / 2, centerY - 50);
			g.drawString(smallMessage, centerX - g.getFontMetrics().stringWidth(smallMessage) / 2, centerY + 50);
		}
	}
	

	private void drawTile(int x, int y, TileType type, Graphics g) {

		switch(type) {
		

		case Fruit:
			g.setColor(Color.RED);
			g.fillOval(x + 2, y + 2, TILE_SIZE - 4, TILE_SIZE - 4);
			break;
			

		case SnakeBody:
			g.setColor(Color.GREEN);
			g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
			break;
			

		case SnakeHead:

			g.setColor(Color.GREEN);
			g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
			

			g.setColor(Color.BLACK);
			

			switch(game.getDirection()) {
			case North: {
				int baseY = y + EYE_SMALL_INSET;
				g.drawLine(x + EYE_LARGE_INSET, baseY, x + EYE_LARGE_INSET, baseY + EYE_LENGTH);
				g.drawLine(x + TILE_SIZE - EYE_LARGE_INSET, baseY, x + TILE_SIZE - EYE_LARGE_INSET, baseY + EYE_LENGTH);
				break;
			}
				
			case South: {
				int baseY = y + TILE_SIZE - EYE_SMALL_INSET;
				g.drawLine(x + EYE_LARGE_INSET, baseY, x + EYE_LARGE_INSET, baseY - EYE_LENGTH);
				g.drawLine(x + TILE_SIZE - EYE_LARGE_INSET, baseY, x + TILE_SIZE - EYE_LARGE_INSET, baseY - EYE_LENGTH);
				break;
			}
			
			case West: {
				int baseX = x + EYE_SMALL_INSET;
				g.drawLine(baseX, y + EYE_LARGE_INSET, baseX + EYE_LENGTH, y + EYE_LARGE_INSET);
				g.drawLine(baseX, y + TILE_SIZE - EYE_LARGE_INSET, baseX + EYE_LENGTH, y + TILE_SIZE - EYE_LARGE_INSET);
				break;
			}
				
			case East: {
				int baseX = x + TILE_SIZE - EYE_SMALL_INSET;
				g.drawLine(baseX, y + EYE_LARGE_INSET, baseX - EYE_LENGTH, y + EYE_LARGE_INSET);
				g.drawLine(baseX, y + TILE_SIZE - EYE_LARGE_INSET, baseX - EYE_LENGTH, y + TILE_SIZE - EYE_LARGE_INSET);
				break;
			}
			
			}
			break;
		}
	}

}
