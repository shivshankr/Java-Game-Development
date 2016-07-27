package dev.ssgames.SSTrilogy;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;


public class TTTGamePlay extends JFrame {

    public static final int ROWS = 2;
    public static final int COLS = 2;



    public static final int CELL_SIZE = 100;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;

    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;


    public enum GameState {
        PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }

    private GameState currentState;


    public enum Seed {
        EMPTY, CROSS, NOUGHT
    }

    private Seed currentPlayer;

    private Seed[][] board   ;
    private DrawCanvas canvas;
    private JLabel statusBar;
    private int vertical;
    private int horizontal;
    private boolean setWin=false;
    private int winX1;
    private int winY1;
    private int winX2;
    private int winY2;
    private int vertical;
    private int horizontal;
    boolean computerPlay=false;
    JLabel PlayerXCount;
    JLabel PlayerOCount;
    int rowSelected;
    int colSelected;
    int playerXscore;
    int playerOscore;


    public TTTGamePlay(boolean player) {
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(400, 200));


        PlayerXCount = new JLabel("Player X: " + String.valueOf(playerXscore));
        PlayerXCount.setBounds(620,120,260,100);
        add(PlayerXCount);

        PlayerOCount = new JLabel("Player O: " + String.valueOf(playerOscore));
        PlayerOCount.setBounds(620,200,260,100);
        add(PlayerOCount);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                rowSelected = mouseY / CELL_SIZE;
                colSelected = mouseX / CELL_SIZE;

                if (currentState == GameState.PLAYING) {
                    if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0
                            && colSelected < COLS && board[rowSelected][colSelected] == Seed.EMPTY) {
                        board[rowSelected][colSelected] = currentPlayer;
                        updateGame(currentPlayer, rowSelected, colSelected);

                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                    }
                } else {
                    initGame();
                }

                repaint();
                if(computerPlay && (currentPlayer == Seed.NOUGHT)){
                    computerPlay();
                    updateGame(currentPlayer, rowSelected, colSelected);
                    currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                    repaint();
                }

            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {

                    case KeyEvent.VK_ESCAPE:
                        Login.display.getFrame().setVisible(true);
                        dispose();
                        break;
                }
            }
        });


        statusBar = new JLabel("  ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(statusBar, BorderLayout.PAGE_END);

        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
        board = new Seed[ROWS][COLS];
        initGame();
    }


    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = Seed.EMPTY;
            }
        }
        PlayerXCount.setText("Player X: " + String.valueOf(playerXscore));
        PlayerOCount.setText("Player O: " + String.valueOf(playerOscore));

        setWin=false;
        currentState = GameState.PLAYING;
        currentPlayer = Seed.CROSS;
    }


    public void updateGame(Seed theSeed, int rowSelected, int colSelected) {
        if (hasWon(theSeed, rowSelected, colSelected)) {
            if(theSeed == Seed.CROSS) {
                currentState = GameState.CROSS_WON;
                playerXscore++;
            }
            else {
                currentState = GameState.NOUGHT_WON;
                playerOscore++;
            }
        } else if (isDraw()) {
            currentState = GameState.DRAW;
            playerXscore++;
            playerOscore++;
        }

    }


    public boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == Seed.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean hasWon(Seed theSeed, int rowSelected, int colSelected) {

        if(board[rowSelected][0] == theSeed
                && board[rowSelected][1] == theSeed
                && board[rowSelected][2] == theSeed){

            setWin=true;
            winX1=0;
            winY1=rowSelected;
            winX2=2;
            winY2=rowSelected;
            vertical=0;
            horizontal=1;

            return true;
        }
        else if( board[0][colSelected] == theSeed
                && board[1][colSelected] == theSeed
                && board[2][colSelected] == theSeed){

            setWin=true;
            winX1=colSelected;
            winY1=0;
            winX2=colSelected;
            winY2=2;
            vertical=1;
            horizontal=0;

            return true;
        }
        else if(rowSelected == colSelected
                && board[0][0] == theSeed
                && board[1][1] == theSeed
                && board[2][2] == theSeed){

            return true;
        }
        else if(rowSelected + colSelected == 2
                && board[0][2] == theSeed
                && board[1][1] == theSeed
                && board[2][0] == theSeed){

            return true;
        }
        return (false);
    }

    void computerPlay()
    {
        Seed t[]=new Seed[3];
        int a,b;

        for(a=0;a<3;a++)
            if(Arrays.deepEquals(board[a],new Seed[]{Seed.NOUGHT,Seed.NOUGHT,Seed.EMPTY}))
            {board[a][2]=Seed.NOUGHT;rowSelected=a;colSelected=2;return;}
            else if(Arrays.deepEquals(board[a],new Seed[]{Seed.NOUGHT,Seed.EMPTY,Seed.NOUGHT}))
            {board[a][1]=Seed.NOUGHT;rowSelected=a;colSelected=1;return;}
            else if(Arrays.deepEquals(board[a],new Seed[]{Seed.EMPTY,Seed.NOUGHT,Seed.NOUGHT}))
            {board[a][0]=Seed.NOUGHT;rowSelected=a;colSelected=0;return;}

            for(a=0;a<3;a++)
            {
                for(b=0;b<3;b++)
                    t[b]=board[b][a];

                if(Arrays.deepEquals(t,new Seed[]{Seed.NOUGHT,Seed.NOUGHT,Seed.EMPTY}))
                {board[2][a]=Seed.NOUGHT;rowSelected=2;colSelected=a;return;}
                else if(Arrays.deepEquals(t,new Seed[]{Seed.NOUGHT,Seed.EMPTY,Seed.NOUGHT}))
                {board[1][a]=Seed.NOUGHT;rowSelected=1;colSelected=a;return;}
                else if(Arrays.deepEquals(t,new Seed[]{Seed.EMPTY,Seed.NOUGHT,Seed.NOUGHT}))
                {board[0][a]=Seed.NOUGHT;rowSelected=0;colSelected=a;return;}
            }

            for(a=0;a<3;a++)
                t[a]=board[a][a];
            if(Arrays.deepEquals(t,new Seed[]{Seed.NOUGHT,Seed.NOUGHT,Seed.EMPTY}))
            {board[2][2]=Seed.NOUGHT;rowSelected=2;colSelected=2;return;}
            else if(Arrays.deepEquals(t,new Seed[]{Seed.NOUGHT,Seed.EMPTY,Seed.NOUGHT}))
            {board[1][1]=Seed.NOUGHT;rowSelected=1;colSelected=1;return;}
            else if(Arrays.deepEquals(t,new Seed[]{Seed.EMPTY,Seed.NOUGHT,Seed.NOUGHT}))
            {board[0][0]=Seed.NOUGHT;rowSelected=0;colSelected=0;return;}

            t[0]=board[0][2];
            t[2]=board[2][0];
            if(Arrays.deepEquals(t,new Seed[]{Seed.NOUGHT,Seed.NOUGHT,Seed.EMPTY}))
            {board[2][0]=Seed.NOUGHT;rowSelected=2;colSelected=0;return;}
            else if(Arrays.deepEquals(t,new Seed[]{Seed.NOUGHT,Seed.EMPTY,Seed.NOUGHT}))
            {board[1][1]=Seed.NOUGHT;rowSelected=1;colSelected=1;return;}
            else if(Arrays.deepEquals(t,new Seed[]{Seed.EMPTY,Seed.NOUGHT,Seed.NOUGHT}))
            {board[0][2]=Seed.NOUGHT;rowSelected=0;colSelected=2;return;}

            for(a=0;a<3;a++)
                if(Arrays.deepEquals(board[a],new Seed[]{Seed.CROSS,Seed.CROSS,Seed.EMPTY}))
                {board[a][2]=Seed.NOUGHT;rowSelected=a;colSelected=2;return;}
                else if(Arrays.deepEquals(board[a],new Seed[]{Seed.CROSS,Seed.EMPTY,Seed.CROSS}))
                {board[a][1]=Seed.NOUGHT;rowSelected=a;colSelected=1;return;}
                else if(Arrays.deepEquals(board[a],new Seed[]{Seed.EMPTY,Seed.CROSS,Seed.CROSS}))
                {board[a][0]=Seed.NOUGHT;rowSelected=a;colSelected=0;return;}

            for(a=0;a<3;a++)
                {
                    for(b=0;b<3;b++)
                        t[b]=board[b][a];

                    if(Arrays.deepEquals(t,new Seed[]{Seed.CROSS,Seed.CROSS,Seed.EMPTY}))
                    {board[2][a]=Seed.NOUGHT;rowSelected=2;colSelected=a;return;}
                    else if(Arrays.deepEquals(t,new Seed[]{Seed.CROSS,Seed.EMPTY,Seed.CROSS}))
                    {board[1][a]=Seed.NOUGHT;rowSelected=1;colSelected=a;return;}
                    else if(Arrays.deepEquals(t,new Seed[]{Seed.EMPTY,Seed.CROSS,Seed.CROSS}))
                    {board[0][a]=Seed.NOUGHT;rowSelected=0;colSelected=a;return;}
                }

                for(a=0;a<3;a++)
                    t[a]=board[a][a];
                if(Arrays.deepEquals(t,new Seed[]{Seed.CROSS,Seed.CROSS,Seed.EMPTY}))
                {board[2][2]=Seed.NOUGHT;rowSelected=2;colSelected=2;return;}
                else if(Arrays.deepEquals(t,new Seed[]{Seed.CROSS,Seed.EMPTY,Seed.CROSS}))
                {board[1][1]=Seed.NOUGHT;rowSelected=1;colSelected=1;return;}
                else if(Arrays.deepEquals(t,new Seed[]{Seed.EMPTY,Seed.CROSS,Seed.CROSS}))
                {board[0][0]=Seed.NOUGHT;rowSelected=0;colSelected=0;return;}

                t[0]=board[0][2];
                t[2]=board[2][0];
                if(Arrays.deepEquals(t,new Seed[]{Seed.CROSS,Seed.CROSS,Seed.EMPTY}))
                {board[2][0]=Seed.NOUGHT;rowSelected=2;colSelected=0;return;}
                else if(Arrays.deepEquals(t,new Seed[]{Seed.CROSS,Seed.EMPTY,Seed.CROSS}))
                {board[1][1]=Seed.NOUGHT;rowSelected=1;colSelected=1;return;}
                else if(Arrays.deepEquals(t,new Seed[]{Seed.EMPTY,Seed.CROSS,Seed.CROSS}))
                {board[0][2]=Seed.NOUGHT;rowSelected=0;colSelected=2;return;}

                if(board[1][1]==Seed.EMPTY)
                {board[1][1]=Seed.NOUGHT;rowSelected=1;colSelected=1;return;}

                if(board[0][1]==Seed.CROSS)
                    if((board[1][0]==Seed.CROSS || board[2][0]==Seed.CROSS) && board[0][0]==Seed.EMPTY)
                    {board[0][0]=Seed.NOUGHT;rowSelected=0;colSelected=0;return;}
                    else if((board[1][2]==Seed.CROSS || board[2][2]==Seed.CROSS) && board[0][2]==Seed.EMPTY)
                    {board[0][2]=Seed.NOUGHT;rowSelected=0;colSelected=2;return;}

                if(board[1][0]==Seed.CROSS)
                    if((board[0][1]==Seed.CROSS || board[0][2]==Seed.CROSS) && board[0][0]==Seed.EMPTY)
                    {board[0][0]=Seed.NOUGHT;rowSelected=0;colSelected=0;return;}
                    else if((board[2][1]==Seed.CROSS || board[2][2]==Seed.CROSS) && board[2][0]==Seed.EMPTY)
                    {board[2][0]=Seed.NOUGHT;rowSelected=2;colSelected=0;return;}

                if(board[2][1]==Seed.CROSS)
                    if((board[0][0]==Seed.CROSS || board[1][0]==Seed.CROSS) && board[2][0]==Seed.EMPTY)
                    {board[2][0]=Seed.NOUGHT;rowSelected=2;colSelected=0;return;}
                    else if((board[0][2]==Seed.CROSS || board[1][2]==Seed.CROSS) && board[2][2]==Seed.EMPTY)
                    {board[2][2]=Seed.NOUGHT;rowSelected=2;colSelected=2;return;}


                if(board[1][2]==Seed.CROSS)
                    if((board[0][0]==Seed.CROSS || board[0][1]==Seed.CROSS) && board[0][2]==Seed.EMPTY)
                    {board[0][2]=Seed.NOUGHT;rowSelected=0;colSelected=2;return;}
                    else if((board[2][0]==Seed.CROSS || board[2][1]==Seed.CROSS) && board[2][2]==Seed.EMPTY)
                    {board[2][2]=Seed.NOUGHT;rowSelected=2;colSelected=2;return;}

                    if(board[1][1]==Seed.CROSS)
                    {
                        if(board[2][2]==Seed.EMPTY)
                        {board[2][2]=Seed.NOUGHT;rowSelected=2;colSelected=2;return;}
                        else if(board[0][2]==Seed.EMPTY)
                        {board[0][2]=Seed.NOUGHT;rowSelected=0;colSelected=2;return;}
                        else if(board[2][0]==Seed.EMPTY)
                        {board[2][0]=Seed.NOUGHT;rowSelected=2;colSelected=0;return;}
                    }
                    else
                    {
                        if(board[2][1]==Seed.EMPTY)
                        {board[2][1]=Seed.NOUGHT;rowSelected=2;colSelected=1;return;}
                        else if(board[0][1]==Seed.EMPTY)
                        {board[0][1]=Seed.NOUGHT;rowSelected=0;colSelected=1;return;}
                        else if(board[1][0]==Seed.EMPTY)
                        {board[1][0]=Seed.NOUGHT;rowSelected=1;colSelected=0;return;}
                        else if(board[1][2]==Seed.EMPTY)
                        {board[1][2]=Seed.NOUGHT;rowSelected=1;colSelected=2;return;}
                    }

                    for(a=0;a<3;a++)
                        for(b=0;b<3;b++)
                            if(board[a][b]==Seed.EMPTY)
                            {board[a][b]=Seed.NOUGHT;rowSelected=a;colSelected=b;return;}

    }

    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);


            g.setColor(Color.LIGHT_GRAY);
            for (int row = 1; row < ROWS; ++row) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                        CANVAS_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < COLS; ++col) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                        GRID_WIDTH, CANVAS_HEIGHT-1, GRID_WIDTH, GRID_WIDTH);
            }



            Graphics2D g2d = (Graphics2D)g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (board[row][col] == Seed.CROSS) {
                        g2d.setColor(Color.RED);
                        int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                        int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                        g2d.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    } else if (board[row][col] == Seed.NOUGHT) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }

                }
            }


            if (currentState == GameState.PLAYING) {
                statusBar.setForeground(Color.BLACK);
                if (currentPlayer == Seed.CROSS) {
                    statusBar.setText("X's Turn");
                } else {
                    statusBar.setText("O's Turn");
                }
            } else if (currentState == GameState.DRAW) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("It's a Draw! Click to play again.");
            } else if (currentState == GameState.CROSS_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("'X' Won! Click to play again.");
            } else if (currentState == GameState.NOUGHT_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("'O' Won! Click to play again.");
            }
        }
    }


}
