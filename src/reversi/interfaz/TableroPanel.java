package reversi.interfaz;

import ia.Move;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.JPanel;

import reversi.Tablero;
import reversi.TableroListener;
import reversi.GameController;
import reversi.GameView;
import reversi.EstadoTablero;
import reversi.Auxiliar;

public class TableroPanel extends JPanel implements TableroListener, GameView,
		MouseMotionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color borderColor;
	private Color boardBgColor;
	private Color gridColor;
	private Color whitePlayerColor;
	private Color blackPlayerColor;
	private Color textColor;
	private Color textBgColor1;
	private Color textBgColor2;
	private Color validWhiteMovesColor;
	private Color validBlackMovesColor;
	private Color highlightColor;
	private Color highlightInvalidColor;
	private Font textFont;
	private int minGap;
	private Rectangle gameArea;
	private Tablero board;
	private GameController controller;
	private Point highlightedSquare;
	private int currentPlayerColour;
	private boolean drawValidMoves;
	private boolean AIIsPlaying;
	private boolean playerHasNoValidMoves;
	private String playerWithNoMoves;
	private long lastClickTime;

	public TableroPanel() {
		init();
	}

	private void init() {

		this.minGap = 20;
		this.gameArea = null;
		this.highlightedSquare = null;

		this.drawValidMoves = true;
		this.AIIsPlaying = false;
		this.playerHasNoValidMoves = false;
		this.playerWithNoMoves = "";

		this.borderColor = new Color(0, 0, 0); // black
		this.boardBgColor = new Color(52, 52, 119);
		this.gridColor = new Color(0, 0, 0); // black
		this.whitePlayerColor = new Color(255, 255, 255); // white
		this.blackPlayerColor = new Color(0, 0, 0); // black
		this.textColor = new Color(66, 66, 66); // dark grey
		this.textBgColor1 = new Color(218, 214, 221);
		this.textBgColor2 = new Color(113, 122, 113);
		this.validWhiteMovesColor = new Color(66, 59, 73);
		this.validBlackMovesColor = new Color(132, 132, 133);
		this.highlightColor = new Color(0, 30, 100, 155);
		this.highlightInvalidColor = new Color(71, 108, 108, 100);

		this.textFont = new Font("SansSerif", Font.BOLD, 12);
		this.board = null;

		this.controller = null;
		this.currentPlayerColour = Auxiliar.BLACK;

		this.lastClickTime = 0;

		// Muestra el cursor
		this.addMouseMotionListener(this);
		// Gneral el evento al presionar el cursor
		this.addMouseListener(this);

	}

	public void setDrawValidMoves(boolean drawValidMoves) {
		this.drawValidMoves = drawValidMoves;

	}

	public void setAIIsPlaying(boolean AIIsPlaying) {
		this.AIIsPlaying = AIIsPlaying;

	}

	public void setPlayerHasNoMovesAvailable(boolean playerHasNoValidMoves, String player) {
		this.playerHasNoValidMoves = playerHasNoValidMoves;
		this.playerWithNoMoves = player;
	}

	public boolean getAIIsPlaying() {
		return this.AIIsPlaying;
	}

	// Remueve la ficha y estable otra y pinta de nuevo el tablero
	public void setBoard(Tablero b) {
		if (this.board != null) {
			this.board.removeView(this);
		}
		this.board = b;
		if (this.board != null) {
			this.board.addView(this);
		}
		this.repaint();
	}

	public Tablero getBoard() {
		return this.board;
	}

	// Estable un objeto el cual genera una repaint al generar un tiro
	public void setController(GameController gc) {
		this.init();
		this.controller = gc;
		this.currentPlayerColour = gc.getCurrentPlayerColour();
	}

	// Guarda y notifica el cambio que se genero en la grilla
	public void notifyGameChanged(GameController g) {
		this.currentPlayerColour = g.getCurrentPlayerColour();
		this.repaint();
	}

	public void forceUpdate() {
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		this.draw(arg0);
	}

	private void draw(Graphics g) {

		// La grilla toma el ancho y el alto de la pantalla
		int width = this.getWidth();
		int height = this.getHeight();

		BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) bi.getGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setFont(this.textFont);
		FontMetrics fm = g2d.getFontMetrics();
		int gap = Math.max(Math.max(this.minGap, fm.getMaxAdvance() + 10),
				fm.getHeight() + 10);

		int rectDimension = Math.min(width, height) - (2 * gap) - 50;
		int rectLeft = (width - rectDimension) / 2;
		int rectTop = (height - rectDimension) / 2;

		this.gameArea = new Rectangle(rectLeft, rectTop, rectDimension,
				rectDimension);

		g2d.setBackground(this.textBgColor1);
		g2d.setPaint(new GradientPaint(0, (float) (height * 0.5),
				this.textBgColor1, 0, (float) (height * 1.25),
				this.textBgColor2));
		g2d.fillRect(0, 0, width, height);

		this.drawGameArea(g2d, rectLeft, rectTop, rectDimension, fm);
		this.drawBorder(g2d, width, height);
		if (this.board != null) {
			this.drawDiscs(g2d, rectLeft, rectTop, rectDimension);
			if (this.drawValidMoves) {
				this.drawValidMoves(g2d, rectLeft, rectTop, rectDimension);
			}
			if (this.playerHasNoValidMoves) {
				this.drawNoMovesAlert(g2d, this.gameArea, this.playerWithNoMoves);
			}
			this.drawGameScore(g2d, width, height);
			if (this.board.isGameOver()) {
				drawGameOver(g2d, this.gameArea);
			} else {
				if (this.AIIsPlaying) {
					this.drawAIIsPlaying(g2d, width, height);
				}
			}
		}

		if (this.highlightedSquare != null) {
			this.highlightSquare(this.highlightedSquare.x,
					this.highlightedSquare.y, g2d, rectLeft, rectTop,
					rectDimension);
		}

		Graphics2D g2d2 = (Graphics2D) g;
		g2d2.drawImage(bi, null, 0, 0);

		g2d.dispose();
		g2d2.dispose();

	}

	private void drawBorder(Graphics2D g2d, int width, int height) {
		g2d.setPaint(this.borderColor);
		g2d.drawRect(0, 0, width - 1, height - 1);
	}

	private void drawAIIsPlaying(Graphics2D g2d, int width, int height) {
		if (this.board != null) {
			g2d.setPaint(new Color(255, 255, 255));
			g2d.setFont(this.textFont);
			String str = "Estoy Pensando...";
			FontMetrics fm = g2d.getFontMetrics();
			int textWidth = fm.charsWidth(str.toCharArray(), 0, str.length());
			float textX = width - textWidth - 5;
			float textY = height - 5;
			g2d.drawString(str, textX, textY);
		}
	}

	private void drawGameScore(Graphics2D g2d, int width, int height) {
		if (this.board != null) {
			Point score = this.board.getPuntaje();
			String str1 = "Jugador1:  " + score.x;
			String str2 = "Jugador2:  " + score.y;
			
			float textX = 5;
			float textY = height - 5;
			g2d.setPaint(new Color(0,0,0));
			g2d.setFont(this.textFont);
			g2d.drawString(str1, textX, textY - 20);
			
			g2d.setPaint(new Color(255,255,255));
			g2d.drawString(str2, textX, textY);
		}
	}

	private void drawNoMovesAlert(Graphics2D g2d, Rectangle2D gameArea, String player) {
		int margin = 10;
		double x = gameArea.getX() + margin;
		double w = gameArea.getWidth() - 2 * margin;
		double h = w / 2;
		double y = gameArea.getY() + (gameArea.getHeight() - h) / 2;
		String str = player + " No tiene movimientos validos...";
		int fontSize = (int) Math.max(12, (w - 20) / str.length());

		Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
		g2d.setPaint(new Color(255, 255, 255, 210));
		g2d.fill(rect);
		g2d.setPaint(new Color(0, 0, 0));
		g2d.draw(rect);
		g2d.setFont(new Font("Monospaced", Font.BOLD, fontSize));
		FontMetrics fm = g2d.getFontMetrics();
		int textHeight = fm.getHeight();
		int textWidth = fm.charsWidth(str.toCharArray(), 0, str.length());
		float textY = (float) (y + (h - textHeight) / 2 + fm.getMaxAscent());
		float textX = (float) (x + (w - textWidth) / 2);
		g2d.drawString(str, textX, textY);
	}

	private void drawGameArea(Graphics2D g2d, int rectLeft, int rectTop,
			int rectDimension, FontMetrics fm) {

		double squareWidth = rectDimension / 8;
		double squareHeight = squareWidth;

		int dimension = (int) squareWidth * 8;

		Rectangle gameAreaRect = new Rectangle(rectLeft, rectTop, dimension,
				dimension);

		g2d.setBackground(this.boardBgColor);
		g2d.setPaint(this.boardBgColor);
		g2d.fill(gameAreaRect);

		// Draw the grid:
		g2d.setStroke(new BasicStroke(1));

		g2d.setPaint(this.gridColor);
		g2d.draw(gameAreaRect);

		double y1 = rectTop;
		double y2 = rectTop + gameAreaRect.getHeight();
		for (int i = 1; i < 8; i++) {
			double x = rectLeft + i * squareWidth;
			g2d.draw(new Line2D.Double(x, y1, x, y2));
		}

		double x1 = rectLeft;
		double x2 = rectLeft + gameAreaRect.getWidth();
		for (int i = 1; i < 8; i++) {
			double y = rectTop + i * squareHeight;
			g2d.draw(new Line2D.Double(x1, y, x2, y));
		}

		// Dibujar numeros columnas:
		g2d.setPaint(this.textColor);
		g2d.setFont(this.textFont);
		float y3 = rectTop - 10;
		float y4 = rectTop + dimension + fm.getHeight() + 5;
		// Genera las letras que estan ubicadas en el plano
		char c = '0';
		for (int i = 0; i < 8; i++) {
			int charWidth = fm.charWidth(c);
			float x = (float) ((rectLeft + i * squareWidth) + (squareWidth - charWidth) / 2);
			g2d.drawString(String.valueOf(c), x, y3);
			g2d.drawString(String.valueOf(c), x, y4);
			c++;
		}

		// Dibujar filas:
		g2d.setPaint(this.textColor);
		g2d.setFont(this.textFont);
		float x3 = rectLeft - 10;
		float x4 = rectLeft + dimension + 10;
		char c2 = '0';
		int charHeight = fm.getHeight();
		for (int i = 0; i < 8; i++) {
			int charWidth = fm.charWidth(c2);
			x3 = rectLeft - 10 - charWidth;
			float y = (float) ((rectTop + i * squareHeight) + (squareHeight + charHeight) / 2);
			g2d.drawString(String.valueOf(c2), x3, y);
			g2d.drawString(String.valueOf(c2), x4, y);
			c2++;
		}
	}

	private void drawDiscs(Graphics2D g2d, int rectLeft, int rectTop,
			int rectDimension) {

		int black = this.board.getBlackValue();
		int white = this.board.getWhiteValue();
		int empty = this.board.getEmptyValue();

		double squareDimension = rectDimension / 8;
		int margin = 2;
		double boundingBoxDimension = squareDimension - 2 * margin;

		EstadoTablero state = this.board.getState();
		int value = empty;
		for (int i = 0; i < this.board.getBoardDimension(); i++) {
			for (int j = 0; j < this.board.getBoardDimension(); j++) {
				value = state.get(i, j);
				if (value != empty) {
					// Ubica las fichas en el centro de la grilla
					double x = rectLeft + i * squareDimension + margin;
					double y = rectTop + j * squareDimension + margin;
					// Pinta las fichas ( color)
					if (value == black) {
						this.drawBlackDisc(g2d, x, y, boundingBoxDimension);
					} else if (value == white) {
						this.draWhiteDisc(g2d, x, y, boundingBoxDimension);
					}
				}
			}
		}

	}

	// Cambia el color de fichas negras
	private void drawBlackDisc(Graphics2D g2d, double x, double y,
			double dimension) {
		g2d.setPaint(this.blackPlayerColor);
		Ellipse2D disc = new Ellipse2D.Double(x, y, dimension, dimension);
		g2d.fill(disc);
	}

	// Cambia el color de fichas blancas

	private void draWhiteDisc(Graphics2D g2d, double x, double y,
			double dimension) {
		g2d.setPaint(this.whitePlayerColor);
		Ellipse2D disc = new Ellipse2D.Double(x, y, dimension, dimension);
		g2d.fill(disc);
	}

	private void drawValidMoves(Graphics2D g2d, int rectLeft, int rectTop,
			int rectDimension) {

		int playerColour = this.currentPlayerColour;
		Color highlight = null;
		if (playerColour == Auxiliar.BLACK) {
			// Muestra los movimientos validos del jugador
			highlight = this.validBlackMovesColor;
		} else {
			// Muestra los movimientos validos de la maquina
			highlight = this.validWhiteMovesColor;
		}
		double squareDimension = rectDimension / 8;
		// Margen de las posiciones validas en el tablero
		int margin = 2;
		// Tamaño de la posiciones validas
		double boundingBoxDimension = squareDimension - 2 * margin;

		Vector<Move> valid = this.board.getMovimientosValidos(playerColour);
		for (Iterator<Move> it = valid.iterator(); it.hasNext();) {
			Move move = it.next();
			// Obtiene la coordenada de las posiciones validas
			int i = move.getX();
			int j = move.getY();

			double x = rectLeft + i * squareDimension + margin;
			double y = rectTop + j * squareDimension + margin;
			g2d.setPaint(highlight);			
	
			Rectangle2D rect = new Rectangle2D.Double(x, y,
					boundingBoxDimension, boundingBoxDimension);
			g2d.fill(rect);
		}

	}

	private void highlightSquare(int i, int j, Graphics2D g2d, int rectLeft,
			int rectTop, int rectDimension) {
		double squareDimension = rectDimension / 8;
		// Establece el ovalo en la posicion valida
		double x = rectLeft + i * squareDimension + 2;
		double y = rectTop + j * squareDimension + 2;
		g2d.setPaint(this.highlightInvalidColor);
		Ellipse2D disc = new Ellipse2D.Double(x, y, squareDimension - 4,
				squareDimension - 4);
		if (this.board != null) {
			Vector<Move> moves = this.board
					.getMovimientosValidos(this.currentPlayerColour);
			Move m = new Move(i, j, this.currentPlayerColour);
			if ((moves.contains(m)) && (!this.AIIsPlaying)) {
				g2d.setPaint(this.highlightColor);
			}
		}
		g2d.fill(disc);
	}

	private void drawGameOver(Graphics2D g2d, Rectangle2D gameArea) {
		int margin = 10;
		double x = gameArea.getX() + margin;
		double w = gameArea.getWidth() - 2 * margin;
		double h = w / 2;
		double y = gameArea.getY() + (gameArea.getHeight() - h) / 2;
		int fontSize = (int) Math.max(10, (w - 40) / 9);

		Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
		g2d.setPaint(new Color(255, 255, 255, 210));
		g2d.fill(rect);
		g2d.setPaint(new Color(0, 0, 0));
		g2d.draw(rect);
		
		String str = "";
		Point score = this.board.getPuntaje();
		if (this.board.isGameOver()) {
			if (score.x > score.y) {
				str += "Jugador1 Gana!";
			} else if (score.y > score.x) {
				str += "Jugador2 Gana!";
			} else {
				str += "Empate!";
			}
		}
		
		g2d.setFont(new Font("Monospaced", Font.BOLD, fontSize));
		FontMetrics fm = g2d.getFontMetrics();
		int textHeight = fm.getHeight();
		int textWidth = fm.charsWidth(str.toCharArray(), 0, str.length());
		float textY = (float) (y + (h - textHeight) / 2 + fm.getMaxAscent());
		float textX = (float) (x + (w - textWidth) / 2);
		g2d.drawString(str, textX, textY);
	}

	public void notifyBoardChanged(Tablero b) {
		if (b.equals(this.board)) {
			this.currentPlayerColour = this.controller.getCurrentPlayerColour();
		}
	}

	private Point getSquareUnderCursor(int x, int y, int rectLeft, int rectTop,
			int rectDimension) {
		int squareDimension = rectDimension / 8;
		int rectRight = rectLeft + rectDimension;
		int rectBottom = rectTop + rectDimension;
		int max = 7;
		if (this.board != null) {
			max = this.board.getBoardDimension() - 1;
		}
		if ((x > rectLeft) && (y > rectTop) && (x < rectRight)
				&& (y < rectBottom)) {
			int x1 = x - rectLeft;
			int y1 = y - rectTop;
			int i = Math.min((int) (x1 / squareDimension), max);
			int j = Math.min((int) (y1 / squareDimension), max);
			return new Point(i, j);
		} else {
			return null;
		}
	}	

	public void mouseMoved(MouseEvent e) {
		if ((this.gameArea != null) && (this.board != null)) {
			if ((!this.board.isGameOver()) && (!this.playerHasNoValidMoves)) {
				Point square = getSquareUnderCursor(e.getX(), e.getY(), this.gameArea.x, this.gameArea.y, this.gameArea.width);
				if (square != this.highlightedSquare) {
					this.repaint();
				}
				this.highlightedSquare = square;
			} else {
				this.highlightedSquare = null;
				this.repaint();
			}
		} else {
			this.highlightedSquare = null;
		}
	}

	public void mouseClicked(MouseEvent e) {
		long currentTime = System.currentTimeMillis();
		long diff = currentTime - this.lastClickTime;
		if (diff > 1000) {
			this.lastClickTime = currentTime;
			if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
				if ((this.gameArea != null) && (!this.AIIsPlaying) && (!this.playerHasNoValidMoves)) {
					Point square = getSquareUnderCursor(e.getX(), e.getY(),
							this.gameArea.x, this.gameArea.y, this.gameArea.width);
					if ((this.controller != null) && (square != null)) {
						this.controller.play(square.x, square.y);
					}
				}
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
