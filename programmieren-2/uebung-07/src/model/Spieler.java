package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Spieler {
	SPIELER_X(new ImageView((new Image("file:resources/images/x.png")))), SPIELER_O(new ImageView(
			(new Image("file:resources/images/o.png"))));

	private final ImageView view;

	Spieler(ImageView view) {
		this.view = view;
	}

	public Image sign() {
		return view.getImage();
	}
}
