package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class funcionarioController extends formularioController {
	@FXML private TextField txtBuscar;
    @FXML private TextField txtCPF;
    @FXML private TextField txtCargo;
    @FXML private TextField txtDataCadastro;
    @FXML private TextField txtNome;
    @FXML private TextField txtRG;
    @FXML private TextField txtSalario;
    @FXML private TextField txtSenha;
    @FXML private TextField txtUsuario;
    
	@FXML
	public void initialize() {
		super.init();
		
	}

}
