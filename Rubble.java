
import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Rubble extends GameObject {
	
	public Rubble(double x, double y) {
		super(x, y);
		setFill(Color.ORANGE);
	}

	@Override
	void moveTo(int rows, int cols) {
	}
	
	static boolean rubbleLeft(ArrayList<GameObject> listRobots) {
		for (int i = 0; i < listRobots.size(); i++) {
			if (listRobots.get(i) instanceof Robot) {
				return false;
			}
		}
		return true;
	}
}
