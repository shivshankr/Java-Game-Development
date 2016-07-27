package dev.ssgames.SSTrilogy;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;

public class SnakeGame extends JFrame {
		

	//private static final long serialVersionUID = 6678292058307426314L;
	private static final long FRAME_TIME = 1000L / 50L;
	private static final int MIN_SNAKE_LENGTH = 5;
	private static final int MAX_DIRECTIONS = 3;
	private BoardPanel board;
	private SidePanel side;
	private Random random;
	private Clock logicTimer;
	private boolean isNewGame;
	private boolean isGameOver;
	private boolean isPaused;
	private LinkedList<Point> snake;
	private LinkedList<Direction> directions;

	private int score;
	private int fruitsEaten;
	private int nextFruitScore;
	

	public SnakeGame() {
		super("Snake Remake");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(900,600);


		this.board = new BoardPanel(this);
		this.side = new SidePanel(this);
        board.setBackground(new Color(0,0,102));
        side.setBackground(Color.red);

		add(board, BorderLayout.CENTER);
		add(side, BorderLayout.EAST);
		

		addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {


				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					if(!isPaused && !isGameOver) {
						if(directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if(last != Direction.South && last != Direction.North) {
								directions.addLast(Direction.North);
							}
						}
					}
					break;


				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					if(!isPaused && !isGameOver) {
						if(directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if(last != Direction.North && last != Direction.South) {
								directions.addLast(Direction.South);
							}
						}
					}
					break;
				

				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					if(!isPaused && !isGameOver) {
						if(directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if(last != Direction.East && last != Direction.West) {
								directions.addLast(Direction.West);
							}
						}
					}
					break;
			

				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					if(!isPaused && !isGameOver) {
						if(directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if(last != Direction.West && last != Direction.East) {
								directions.addLast(Direction.East);
							}
						}
					}
					break;
				

				case KeyEvent.VK_P:
					if(!isGameOver) {
						isPaused = !isPaused;
						logicTimer.setPaused(isPaused);
					}
					break;
				

				case KeyEvent.VK_ENTER:
					if(isNewGame || isGameOver) {
						resetGame();

					}
					break;
                    case KeyEvent.VK_ESCAPE:
                        Login.display.getFrame().setVisible(true);
                        dispose();
                        break;
				}
			}
			
		});
		

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	

	public void startGame() {

		this.random = new Random();
		this.snake = new LinkedList<>();
		this.directions = new LinkedList<>();
		this.logicTimer = new Clock(9.0f);
		this.isNewGame = true;
		

		logicTimer.setPaused(true);


		while(true) {

			long start = System.nanoTime();
			logicTimer.update();
			if(logicTimer.hasElapsedCycle()) {
				updateGame();
			}
			board.repaint();
			side.repaint();
			

			long delta = (System.nanoTime() - start) / 1000000L;
			if(delta < FRAME_TIME) {
				try {
					Thread.sleep(FRAME_TIME - delta);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	private void updateGame() {

		TileType collision = updateSnake();
		

		if(collision == TileType.Fruit) {
			fruitsEaten++;
			score += nextFruitScore;
			spawnFruit();
		} else if(collision == TileType.SnakeBody) {
			isGameOver = true;
			logicTimer.setPaused(true);
		} else if(nextFruitScore > 10) {
			nextFruitScore--;
		}
	}
	

	private TileType updateSnake() {


		Direction direction = directions.peekFirst();
				

		Point head = new Point(snake.peekFirst());
		switch(direction) {
		case North:
			head.y--;
			break;
			
		case South:
			head.y++;
			break;
			
		case West:
			head.x--;
			break;
			
		case East:
			head.x++;
			break;
		}
		

		if(head.x < 0 || head.x >= BoardPanel.COL_COUNT || head.y < 0 || head.y >= BoardPanel.ROW_COUNT) {
			return TileType.SnakeBody;
		}
		

		TileType old = board.getTile(head.x, head.y);
		if(old != TileType.Fruit && snake.size() > MIN_SNAKE_LENGTH) {
			Point tail = snake.removeLast();
			board.setTile(tail, null);
			old = board.getTile(head.x, head.y);
		}
		

		if(old != TileType.SnakeBody) {
			board.setTile(snake.peekFirst(), TileType.SnakeBody);
			snake.push(head);
			board.setTile(head, TileType.SnakeHead);
			if(directions.size() > 1) {
				directions.poll();
			}
		}
				
		return old;
	}
	

	private void resetGame() {

		this.score = 0;
		this.fruitsEaten = 0;
		

		this.isNewGame = false;
		this.isGameOver = false;
		

		Point head = new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2);


		snake.clear();
		snake.add(head);
		

		board.clearBoard();
		board.setTile(head, TileType.SnakeHead);
		

		directions.clear();
		directions.add(Direction.North);
		

		logicTimer.reset();
		

		spawnFruit();
	}
	

	public boolean isNewGame() {
		return isNewGame;
	}
	

	public boolean isGameOver() {
		return isGameOver;
	}
	

	public boolean isPaused() {
		return isPaused;
	}
	

	private void spawnFruit() {

		this.nextFruitScore = 100;


		int index = random.nextInt(BoardPanel.COL_COUNT * BoardPanel.ROW_COUNT - snake.size());
		

		int freeFound = -1;
		for(int x = 0; x < BoardPanel.COL_COUNT; x++) {
			for(int y = 0; y < BoardPanel.ROW_COUNT; y++) {
				TileType type = board.getTile(x, y);
				if(type == null || type == TileType.Fruit) {
					if(++freeFound == index) {
						board.setTile(x, y, TileType.Fruit);
						break;
					}
				}
			}
		}
	}

	public int getScore() {
		return score;
	}
	public int getFruitsEaten() {
		return fruitsEaten;
	}
	public int getNextFruitScore() {
		return nextFruitScore;
	}
	public Direction getDirection() {
		return directions.peek();
	}

}
