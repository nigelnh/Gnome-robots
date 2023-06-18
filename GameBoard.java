import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameBoard extends Application {
	public static final double WIDTH = 820;
	public static final double HEIGHT = 600;
	public static final int ROWS = 30;
	public static final int COLS = 41;
	public static final double SIZEWIDTH = WIDTH / COLS;
	public static final double SIZEHEIGHT = HEIGHT / ROWS;

	public ArrayList<GameObject> listRobots;
	public Player player;
	public int numRobots = 14;
	public static IntegerProperty numScores = new SimpleIntegerProperty(0);
	public static Pane gamePlay;
	public static boolean gameOver = false;
	public Text score;
	public Stage primaryStage;
	public Scene scene1;
	public Scene scene2;
	public BorderPane mainPane2;

	public void start(Stage primaryStage) throws Exception {
		try {
			this.primaryStage = primaryStage;

			Random rand = new Random();

			BorderPane mainPane1 = new BorderPane();
			mainPane2 = new BorderPane();
			gamePlay = new Pane();

			// score board and teleport control
			VBox scoreBoard = new VBox();
			HBox teleportBoard = new HBox();
			Text scoreText = new Text("Scores");
			score = new Text("");
			score.textProperty().bind(numScores.asString());

			scoreBoard.getChildren().addAll(scoreText, score);
			scoreBoard.setAlignment(Pos.CENTER);
			mainPane1.setTop(scoreBoard);

			mainPane1.setBottom(teleportBoard);
			mainPane1.setCenter(gamePlay);

			scene1 = new Scene(mainPane1, WIDTH + 90, HEIGHT + 57);
			scene2 = new Scene(mainPane2, WIDTH, HEIGHT + 57);

			buildGamePlay();

			// add player
			Player player = new Player(SIZEWIDTH * COLS / 2, SIZEHEIGHT * ROWS / 2 + SIZEHEIGHT / 2);

			gamePlay.getChildren().add(player);
			// add robots
			listRobots = new ArrayList<GameObject>();

			for (int i = 0; i < numRobots; i++) {
				Robot robot = new Robot(rand.nextInt(COLS) * SIZEWIDTH + SIZEWIDTH / 2,
						rand.nextInt(ROWS) * SIZEHEIGHT + SIZEHEIGHT / 2);
				listRobots.add(robot);
			}
			gamePlay.getChildren().addAll(listRobots);

			// add teleport button
			Button teleport = new Button("Teleport");
			teleport.setOnAction(e -> {
				player.teleportTo(ROWS, COLS, gameOver);
				updateRobots(listRobots, player);
			});

			// add safe teleport button
			Button safeTeleport = new Button("Safe Teleport");
			safeTeleport.setOnAction(e -> {
				player.safeTeleport(listRobots, ROWS, COLS, gameOver);
				updateRobots(listRobots, player);
			});

			teleportBoard.getChildren().addAll(teleport, safeTeleport);

			teleport.setMaxWidth(Double.MAX_VALUE);
			safeTeleport.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(teleport, Priority.ALWAYS);
			HBox.setHgrow(safeTeleport, Priority.ALWAYS);

			// control player using button
			ControlButtons buttonControl = new ControlButtons(gamePlay, this);
			mainPane1.setRight(buttonControl);

			buttonControl.run(listRobots, player);

			// control player using keyboard
			scene1.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.D && player.getX() + SIZEWIDTH < WIDTH && gameOver == false) {
					player.moveTo(0, 1);
					updateRobots(listRobots, player);
				}

				if (e.getCode() == KeyCode.A && player.getX() - SIZEWIDTH > 0 && gameOver == false) {
					player.moveTo(0, -1);
					updateRobots(listRobots, player);
				}

				if (e.getCode() == KeyCode.W && player.getY() - SIZEHEIGHT > 0 && gameOver == false) {
					player.moveTo(-1, 0);
					updateRobots(listRobots, player);
				}

				if (e.getCode() == KeyCode.S && player.getY() + SIZEHEIGHT < HEIGHT && gameOver == false) {
					player.moveTo(1, 0);
					updateRobots(listRobots, player);
				}

				if (e.getCode() == KeyCode.Q && player.getX() - SIZEWIDTH > 0 && player.getY() - SIZEHEIGHT > 0
						&& gameOver == false) {
					player.moveTo(-1, -1);
					updateRobots(listRobots, player);
				}

				if (e.getCode() == KeyCode.E && player.getY() - SIZEHEIGHT > 0 && player.getX() + SIZEWIDTH < WIDTH
						&& gameOver == false) {
					player.moveTo(-1, 1);
					updateRobots(listRobots, player);
				}

				if (e.getCode() == KeyCode.Z && player.getX() - SIZEWIDTH > 0 && player.getY() + SIZEHEIGHT < HEIGHT
						&& gameOver == false) {
					player.moveTo(1, -1);
					updateRobots(listRobots, player);
				}

				if (e.getCode() == KeyCode.C && player.getX() + SIZEWIDTH < WIDTH && player.getY() + SIZEHEIGHT < HEIGHT
						&& gameOver == false) {
					player.moveTo(1, 1);
					updateRobots(listRobots, player);
				}

				if (e.getCode() == KeyCode.X && gameOver == false) {
					player.moveTo(0, 0);
					updateRobots(listRobots, player);
				}

				if (Rubble.rubbleLeft(listRobots) == true) {
					updateRobots(listRobots, player);
				}
			});

			primaryStage.setTitle("Game Play");
			primaryStage.setScene(scene1);
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}	

	void checkCollision(ArrayList<GameObject> listRobots) {
		for (int i = 0; i < listRobots.size(); i++) {
			for (int j = i + 1; j < listRobots.size(); j++) {
				if (listRobots.get(i).getX() == listRobots.get(j).getX()
				&& listRobots.get(i).getY() == listRobots.get(j).getY()) {
					Rubble rubble = new Rubble(listRobots.get(i).getX(), listRobots.get(i).getY());
					listRobots.set(i, rubble);
					numScores.set(numScores.get() + 10);
					gamePlay.getChildren().add(rubble);
					listRobots.remove(j);
				}
			}
		}
	}

	boolean isGameOver(ArrayList<GameObject> listRobots, Player player) {
		for (int i = 0; i < listRobots.size(); i++) {
			if (player.getX() == listRobots.get(i).getX() && player.getY() == listRobots.get(i).getY()) {
				return true;
			}
		}

		return false;
	}

	void updateRobots(ArrayList<GameObject> listRobots, Player player) {
		Robot.robotsFollow(listRobots, player);
		checkCollision(listRobots);
		if (isGameOver(listRobots, player) == true) {
			gameOver = true;

			primaryStage.setScene(scene2);
			score = new Text("");
			VBox setting = new VBox();
			Text textScore = new Text("Scores");
			Button replay = new Button("Try Again");
			setting.getChildren().addAll(textScore, score, replay);
			score.textProperty().bind(numScores.asString());
			setting.setAlignment(Pos.CENTER);

			replay.setOnAction(r -> {
				gameOver = false;
				player.safeTeleport(listRobots, ROWS, COLS, gameOver);
				primaryStage.setScene(scene1);
			});
			mainPane2.setCenter(setting);
		}
		else if (Rubble.rubbleLeft(listRobots) == true) {
			primaryStage.setScene(scene2);
			score = new Text("");
			VBox setting = new VBox();
			Text win = new Text("You win!");
			Text textScore = new Text("Scores");
			Button replay = new Button("Try Again");
			setting.getChildren().addAll(win, textScore, score, replay);
			score.textProperty().bind(numScores.asString());
			setting.setAlignment(Pos.CENTER);

			replay.setOnAction(r -> {
				gamePlay.getChildren().clear();
				buildGamePlay();

				numScores.setValue(0);
				player.setX(SIZEWIDTH * COLS / 2);
				player.setY(SIZEHEIGHT * ROWS / 2 + SIZEHEIGHT / 2);
				gamePlay.getChildren().add(player);

				Random rand = new Random();

				// clear rubble
				for (int i = listRobots.size() - 1; i >= 0; i--) {
					listRobots.remove(i);
				}

				// add robots
				for (int i = 0; i < numRobots; i++) {
					Robot robot = new Robot(rand.nextInt(COLS) * SIZEWIDTH + SIZEWIDTH / 2,
							rand.nextInt(ROWS) * SIZEHEIGHT + SIZEHEIGHT / 2);
					listRobots.add(robot);
				}
				gamePlay.getChildren().addAll(listRobots);

				primaryStage.setScene(scene1);
			});
			mainPane2.setCenter(setting);
		}
	}

	void buildGamePlay() {
		// rectangle location
		double x = 0;
		double y = 0;

		// decide with color to use
		int j = 0;
		ArrayList<Color> listColor = new ArrayList<Color>(2);
		listColor.add(Color.BEIGE);
		listColor.add(Color.CADETBLUE);

		// draw game board
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				Rectangle rectangle = new Rectangle(x, y, SIZEWIDTH, SIZEHEIGHT);
				rectangle.setFill(listColor.get(j));
				rectangle.setStroke(Color.BLACK);
				gamePlay.getChildren().addAll(rectangle);

				if (j == 0) {
					j++;
				}
				else {
					j--;
				}

				x += SIZEWIDTH;
			}
			x = 0;
			y += SIZEHEIGHT;
		}
	}
}
