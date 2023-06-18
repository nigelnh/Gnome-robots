import javafx.scene.shape.Circle;

abstract class GameObject extends Circle{
	
	public GameObject( double x, double y) {
		setCenterX(x);
		setCenterY(y);
		setRadius(8);
	}
	
	abstract void moveTo(int rows, int cols);
	
	public void setX( double x ) {
		setCenterX(x);
	}
	
	public double getX() {
		return getCenterX();
	}
	
	public void setY( double y ) {
		setCenterY(y);
	}
	
	public double getY() {
		return getCenterY();
	}
}


