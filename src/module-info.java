module sys {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.view to javafx.graphics, javafx.fxml;
}
