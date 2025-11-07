package application.view;

import java.util.Date;
import java.util.List;

import application.dao.funcionarioDAO;
import application.model.funcionarioModel;
import application.util.metodo;
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
						dtCadastro.setValue(((java.sql.Date) novaSelecao.getDataCadastro()).toLocalDate());
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
				dtCadastro.setValue(((java.sql.Date) funcionario.getDataCadastro()).toLocalDate());
			}
		}
	}
	
	@FXML protected void Salvar() {
		funcionarioDAO dao = new funcionarioDAO();
	try {	
		String nome=txtNome.getText();
		String cpf=txtCPF.getText();
		String rg=txtRG.getText();
		String cargo=txtCargo.getText();
		String salario=txtSalario.getText();
		String usuario=txtUsuario.getText();
		String senha=txtSenha.getText();
		Date data=new Date();
		
		if(statusForm==1) {
			funcionarioModel novoFuncionario=
					new funcionarioModel(0,nome,cpf,rg,cargo,
							salario,usuario,senha,data,data);
		boolean ok = dao.inserirFuncionario(novoFuncionario);
		
		if (ok) {
			//MENAGEM DE CADASTRO CONCLUIDO
			metodo.mensagem("Confirmado", null, "Funcionário salvo com sucesso.", "1");
		} else {
			//MENSAGEM DE ERRO AO CADASTRAR
			metodo.mensagem("Erro", null, "Erro ao salvar funcionário.", "3");
		}
			
			
	} else if(statusForm==2) {
			int id=tabDados.getSelectionModel().getSelectedItem().getID();
			funcionarioModel atualizaFuncionario = new funcionarioModel(id,nome,cpf,
					rg,cargo,salario,usuario,senha,null,null);
			boolean ok = dao.atualizarFuncionario(atualizaFuncionario);
					if (ok) {
						//MENSAGEM DE ALTERAÇÃO BEM SUCEDIDO
						metodo.mensagem("Confirmado", null, "Funcionário alterado com sucesso.", "1");
					} else {
						//MENSAGEM DE ERRO AO ATUALIZAR
						metodo.mensagem("Erro", null, "Erro ao alterar funcionário.", "3");
					}
		} else {return;}
	}catch (Exception e) {
		e.printStackTrace();
		
	} finally {
		carregaDados(null);
	}
	}
	
	@FXML protected void Excluir() {
		funcionarioDAO dao = new funcionarioDAO();
		try {
		int id=tabDados.getSelectionModel().getSelectedItem().getID();
		boolean ok = dao.excluirFuncionario(id);
		
		if(ok) {
			//MENSAGEM DE EXCLUIDO COM SUCESSO
			metodo.mensagem("Confirmado", null, "Funcionário excluído com sucesso.", "1");
			
		} else {
			//MENSAGEM DE ERRO AO EXCLUIR
			metodo.mensagem("Erro", null, "Erro ao excluir funcionário.", "3");
		}
		}finally {
			carregaDados(null);
		}
	}

}
