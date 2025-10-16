package application.view;

import java.util.List;

import application.dao.funcionarioDAO;
import application.model.funcionarioModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class funcionarioController extends formularioController {
	@FXML protected TextField txtBuscar;
    @FXML protected TextField txtCPF;
    @FXML protected TextField txtCargo;
    @FXML protected TextField txtDataCadastro;
    @FXML protected TextField txtNome;
    @FXML protected TextField txtRG;
    @FXML protected TextField txtSalario;
    @FXML protected TextField txtSenha;
    @FXML protected TextField txtUsuario;
    @FXML protected TableView<funcionarioModel> tabDados;
    @FXML protected TableColumn<funcionarioModel, String> colDescricao;
    @FXML protected TableColumn<funcionarioModel, Integer> colID;
    private ObservableList<funcionarioModel> funcionarioList;
    
	@FXML
	public void initialize() {
		super.init();
		
		
		colID.setCellValueFactory(data-> new javafx.beans.property.SimpleIntegerProperty(
				data.getValue().getID()).asObject());
		
		colDescricao.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(
				data.getValue().getNome()));
		
		carregaDados(null);
		
		//IDENTIFICA A SELEÇÃO NA TABLE VIEW E ATUALIZA OS DADOS NOS CAMPOS DO FORMULARIO
		tabDados.getSelectionModel().selectedItemProperty().addListener(
				(obs, selecao , novaSelecao) ->{
					if (novaSelecao != null) {
						txtNome.setText(novaSelecao.getNome());
						txtCPF.setText(novaSelecao.getCPF());
						txtRG.setText(novaSelecao.getRG());
						txtCargo.setText(novaSelecao.getCargo());
						txtSalario.setText(novaSelecao.getSalario());
						txtUsuario.setText(novaSelecao.getUsuario());
						txtSenha.setText(novaSelecao.getSenha());
						txtDataCadastro.setText(novaSelecao.getDataCadastro().toString());
						
					}
				});
		//tabDados.setFocusTraversable(true);
		//tabDados.requestFocus();
		Platform.runLater(() -> tabDados.requestFocus());
		
	}
	
	protected void carregaDados(String desc) {
		emEdicao(false);
		habilitaCampos(false);
		
		funcionarioDAO dao = new funcionarioDAO();
		List<funcionarioModel> funcionarios= dao.listarFuncionarios(desc);
		funcionarioList=FXCollections.observableArrayList(funcionarios);
		tabDados.setItems(funcionarioList);
		
		if (!funcionarioList.isEmpty()) {
			tabDados.getSelectionModel().selectFirst();
			tabDados.getFocusModel();
			
			funcionarioModel funcionario = tabDados.getSelectionModel().getSelectedItem();
			if(funcionario != null) {
				txtNome.setText(funcionario.getNome());
				txtCPF.setText(funcionario.getCPF());
				txtRG.setText(funcionario.getRG());
				txtCargo.setText(funcionario.getCargo());
				txtSalario.setText(funcionario.getSalario());
				txtUsuario.setText(funcionario.getUsuario());
				txtSenha.setText(funcionario.getSenha());
				txtDataCadastro.setText(funcionario.getDataCadastro().toString());
			}
		}
	}

}
