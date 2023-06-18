import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Robot extends GameObject {
	public static final double COLSJUMP = 820 / 41;
	public static final double ROWSJUMP = 600 / 30;

	public Robot(double x, double y) {
		super(x, y);
		setFill(Color.RED);
	}

	@Override
	void moveTo(int rows, int cols) {
		this.setX(this.getX() + cols * COLSJUMP);
		this.setY(this.getY() + rows * ROWSJUMP);
	}

	// all robots follow
	static void robotsFollow(ArrayList<GameObject> list, Player player) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getX() < player.getX() && list.get(i).getY() < player.getY()) {
				list.get(i).moveTo(1, 1);
			}
			else if (list.get(i).getX() < player.getX() && list.get(i).getY() > player.getY()) {
				list.get(i).moveTo(-1, 1);
			}
			else if (list.get(i).getX() > player.getX() && list.get(i).getY() < player.getY()) {
				list.get(i).moveTo(1, -1);
			}
			else if (list.get(i).getX() > player.getX() && list.get(i).getY() > player.getY()) {
				list.get(i).moveTo(-1, -1);
			}
			else if (list.get(i).getX() == player.getX() && list.get(i).getY() < player.getY()) {
				list.get(i).moveTo(1, 0);
			}
			else if (list.get(i).getX() == player.getX() && list.get(i).getY() > player.getY()) {
				list.get(i).moveTo(-1, 0);
			}
			else if (list.get(i).getY() == player.getY() && list.get(i).getX() < player.getX()) {
				list.get(i).moveTo(0, 1);
			}
			else if (list.get(i).getY() == player.getY() && list.get(i).getX() > player.getX()) {
				list.get(i).moveTo(0, -1);
			}
		}
	}
}
