package test;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Player {
	X_PLAYER(new ImageView(new Image("http://i.imgur.com/HY6GeUY.png"))), O_PLAYER(new ImageView(
			new Image("http://i.imgur.com/KgHHxL6.png")));

	private final ImageView view;

	Player(ImageView view) {
		this.view = view;
	}

	public Image marker() {
		return view.getImage();
	}

	@Override
	public String toString() {
		return name().charAt(0) + name().substring(2).toLowerCase();
	}
}