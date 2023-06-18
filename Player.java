import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;

public class Player extends GameObject {
	public static final double COLSJUMP = 820 / 41;
	public static final double ROWSJUMP = 600 / 30;

	public Player(double x, double y) {
		super(x, y);
		setFill(Color.GREEN);
	}

	@Override
	void moveTo(int rows, int cols) {
		this.setX(this.getX() + cols * COLSJUMP);
		this.setY(this.getY() + rows * ROWSJUMP);
	}

	void teleportTo(int rows, int cols, boolean gameOver) {
		if (gameOver == false) {
			Random rand = new Random();
			setX(rand.nextInt(cols) * COLSJUMP + COLSJUMP / 2);
			setY(rand.nextInt(rows) * ROWSJUMP + ROWSJUMP / 2);
		}
	}

	boolean isNextTo(ArrayList<GameObject> listRobots) {
		for (int i = 0; i < listRobots.size(); i++) {
			if (getX() + COLSJUMP == listRobots.get(i).getX() || getX() - COLSJUMP == listRobots.get(i).getX()
					|| getY() - ROWSJUMP == listRobots.get(i).getY() || getY() + ROWSJUMP == listRobots.get(i).getY()
					|| (getX() + COLSJUMP == listRobots.get(i).getX() && getY() - ROWSJUMP == listRobots.get(i).getY())
					|| (getX() + COLSJUMP == listRobots.get(i).getX() && getY() + ROWSJUMP == listRobots.get(i).getY())
					|| (getX() - COLSJUMP == listRobots.get(i).getX() && getY() - ROWSJUMP == listRobots.get(i).getY())
					|| (getX() - COLSJUMP == listRobots.get(i).getX()
							&& getY() + ROWSJUMP == listRobots.get(i).getY())) {
				return true;
			}
		}

		return false;
	}

	void safeTeleport(ArrayList<GameObject> listRobots, int rows, int cols, boolean gameOver) {
		if (gameOver == false) {
			teleportTo(rows, cols, gameOver);

			while (isNextTo(listRobots) == true) {
				teleportTo(rows, cols, gameOver);
			}
		}
	}
}
