package application.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class metodo {
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
}
