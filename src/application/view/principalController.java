package application.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class principalController {

    @FXML
    private AnchorPane form;

	@FXML
	private void initialize() {
		
	}
	
	/*METODO PARA CARREGAR AS TELAS NO ANCHOR PANE FORM*/
	
	private void carregaTelas(String fxmlFile, String tituloFuncionalidade) {
		try {
		Parent fxml = FXMLLoader.load(getClass().getResource(fxmlFile));
		form.getChildren().clear();// LIMPA O FORM PARA RECEBER NOVA TELA
		form.getChildren().add(fxml);// ADICIONA NOVA TELA AO FORM
		
		/*AJUSTA O CONTEUDO PARA OCUPAR/AJUSTAR TODO O FORMULARIO OU TELA*/
		form.setTopAnchor(fxml, 0.0);
		form.setBottomAnchor(fxml, 0.0);
		form.setLeftAnchor(fxml, 0.0);
		form.setRightAnchor(fxml, 0.0);
		/*FIM AJUSTA TELA*/
		Scene cena =form.getScene();
		if(cena!=null) {
			Stage stage= (Stage) cena.getWindow();
			stage.setTitle(tituloFuncionalidade);
		}
		} catch(Exception e){e.printStackTrace();}
	}
	
	public void close() {System.exit(0);}
	
	public void loggout() {
		//FECHAR A TELA ATUAL
		Stage stageAtual = (Stage) form.getScene().getWindow();
		stageAtual.close();
		//CARREGA A TELA DE LOGIN NOVAMENTE
		try {
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		Stage stageLogin = new Stage();
		stageLogin.setScene(new Scene(root));
		stageLogin.centerOnScreen();
		stageLogin.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void abrirCadastro() {
		carregaTelas("formulario.fxml","Cadastro");
	}

}
