package com.contact;

import com.contact.contact.IRepository;
import com.contact.controller.FrameController;
import com.contact.contact.SQLRepository;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		String dbURL = "jdbc:sqlite:contacts.db";

		URL fxmlPath = getClass().getResource("/fxml/Frame.fxml");

		FXMLLoader loader = new FXMLLoader(fxmlPath);

		final IRepository repository = new SQLRepository(dbURL);

		FrameController controller = new FrameController(repository);

		loader.setController(controller);

		Parent root = loader.load();
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
