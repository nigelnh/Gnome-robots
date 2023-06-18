import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ControlButtons extends GridPane {
	public static final double WIDTH = 820;
	public static final double HEIGHT = 600;
	public static final int ROWS = 30;
	public static final int COLS = 41;
	public static final double SIZEWIDTH = WIDTH / COLS;
	public static final double SIZEHEIGHT = HEIGHT / ROWS;
	
	public Button up = new Button("U");
	public Button down = new Button("D");
	public Button left = new Button(" L ");
	public Button right = new Button(" R ");
	public Button upRight = new Button("UR");
	public Button upLeft = new Button("UL");
	public Button downRight = new Button("DR");
	public Button downLeft = new Button("DL");
	public Button stay = new Button("S");
	public GameBoard gameboard;

	public ControlButtons(Pane gameplay, GameBoard gameboard) {
		this.gameboard = gameboard;
		
		// setting up buttons
		setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		setAlignment(Pos.CENTER);

		ArrayList<Button> controlButtons = new ArrayList<Button>();

		controlButtons.add(upLeft);
		controlButtons.add(up);
		controlButtons.add(upRight);
		controlButtons.add(left);
		controlButtons.add(stay);
		controlButtons.add(right);
		controlButtons.add(downLeft);
		controlButtons.add(down);
		controlButtons.add(downRight);

		int count = 0;
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				add(controlButtons.get(count), c, r);
				count++;
			}
		}
	}
	 
	// control player using buttons
	void run(ArrayList<GameObject> listRobots, Player player) {
		right.setOnAction(e -> {
			if (player.getX() + SIZEWIDTH < WIDTH && GameBoard.gameOver == false) {
				player.moveTo(0, 1);
				gameboard.updateRobots(listRobots, player);
			}
			e.consume();
		});

		left.setOnAction(e -> {
			if (player.getX() - SIZEWIDTH > 0 && GameBoard.gameOver == false) {
				player.moveTo(0, -1);
				gameboard.updateRobots(listRobots, player);
			}
			e.consume();
		});

		up.setOnAction(e -> {
			if (player.getY() - SIZEHEIGHT > 0 && GameBoard.gameOver == false) {
				player.moveTo(-1, 0);
				gameboard.updateRobots(listRobots, player);
			}
			e.consume();
		});

		down.setOnAction(e -> {
			if (player.getY() + SIZEHEIGHT < HEIGHT && GameBoard.gameOver == false) {
				player.moveTo(1, 0);
				gameboard.updateRobots(listRobots, player);
			}
			e.consume();
		});

		upLeft.setOnAction(e -> {
			if (player.getX() - SIZEWIDTH > 0 && player.getY() - SIZEHEIGHT > 0 && GameBoard.gameOver == false) {
				player.moveTo(-1, -1);
				gameboard.updateRobots(listRobots, player);
			}
			e.consume();
		});

		upRight.setOnAction(e -> {
			if (player.getY() - SIZEHEIGHT > 0 && player.getX() + SIZEWIDTH < WIDTH && GameBoard.gameOver == false) {
				player.moveTo(-1, 1);
				gameboard.updateRobots(listRobots, player);
			}
			e.consume();
		});

		downLeft.setOnAction(e -> {
			if (player.getX() - SIZEWIDTH > 0 && player.getY() + SIZEHEIGHT < HEIGHT && GameBoard.gameOver == false) {
				player.moveTo(1, -1);
				gameboard.updateRobots(listRobots, player);
			}
			e.consume();
		});

		downRight.setOnAction(e -> {
			if (player.getX() + SIZEWIDTH < WIDTH && player.getY() + SIZEHEIGHT < HEIGHT && GameBoard.gameOver == false) {
				player.moveTo(1, 1);
				gameboard.updateRobots(listRobots, player);
			}
			e.consume();
		});

		stay.setOnAction(e -> {
			if (GameBoard.gameOver == false) {
				player.moveTo(0, 0);
				gameboard.updateRobots(listRobots, player);
			}
			e.consume();
		});
	}
}
