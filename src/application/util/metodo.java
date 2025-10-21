package application.util;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class metodo {

private static final Map<String, Stage> janelasAbertas = new HashMap<>(); // 
	
    public static void abrirJanela(String fxml, String titulo) {    	
        if (janelasAbertas.containsKey(fxml)) {// Verifica se a janela já está aberta
            Stage janelaExistente = janelasAbertas.get(fxml);
            if (janelaExistente.isShowing()) {
                janelaExistente.toFront(); // Traz a janela para frente
                return;
            } else {
                janelasAbertas.remove(fxml); // Remove se não estiver mais visível
            }
        }
        try {
            FXMLLoader loader = new FXMLLoader(metodo.class.getResource("/application/view/" + fxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(metodo.class.getResourceAsStream("/application/util/icon_2.png")));
            stage.centerOnScreen();
            stage.setMaximized(true);
            stage.show();
            
            janelasAbertas.put(fxml, stage);// Adiciona ao mapa de janelas abertas
            
            stage.setOnCloseRequest(event -> janelasAbertas.remove(fxml));// Remove do mapa quando a janela for fechada

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void abrirJanelaModal(String fxml, String titulo, Stage janelaPrincipal) {
        if (janelasAbertas.containsKey(fxml)) {
            Stage janelaExistente = janelasAbertas.get(fxml);
            if (janelaExistente.isShowing()) {
                janelaExistente.toFront(); // Traz para frente se já estiver aberta
                return;
            } else {
            	janelasAbertas.remove(fxml); // Remove se não estiver mais visível
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(metodo.class.getResource("/application/view/" + fxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            //stage.getIcons().add(new Image(metodo.class.getResourceAsStream("/application/util/icon.png")));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.setMaximized(true); //  Abre a tela já maximizada

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(janelaPrincipal);

            janelasAbertas.put(fxml, stage); // Registra a janela antes de abrir

            stage.setOnHidden(event -> janelasAbertas.remove(fxml));// Remove do mapa ao fechar

            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public static void mensagem(String titulo, String cabecalho, String texto, String tipo) {
		AlertType type = AlertType.CONFIRMATION;
		
		switch(tipo) {
		case "0":
			type=AlertType.CONFIRMATION;
			break;
		case "1":
			type=AlertType.WARNING;
			break;
		case "2":
			type=AlertType.ERROR;
			break;
		default :
			break;		
			
		
		}
		
		Alert alert = new Alert(type);//CRIA UMA NOVA MENSAGEM
		alert.setTitle(titulo);//INFORMA O TITULO DA MENSAGEM
		alert.setHeaderText(cabecalho);//REMOVE O CABEÇALHO DA MENSAGEM
		alert.setContentText(texto);//TEXTO DO CORPO DA MENSAGEM
		alert.showAndWait();//MOSTRA A MENSAGEM*/
	}
	
	public static int strToIntDef(String x, int defaultValue) {
        try {
            return Integer.parseInt(x);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
