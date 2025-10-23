package application.view;

import java.util.Date;
import java.util.List;

import application.dao.clienteDAO;
import application.model.clienteModel;
import application.util.metodo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class clienteController extends formularioController {
	@FXML protected TextField txtBuscar;
	@FXML protected TextField txtNome;
    @FXML protected TextField txtCPF;
    @FXML protected TextField txtTelefone;
    @FXML protected TextField txtDataCadastro;
    @FXML protected TextField txtEmail;
    @FXML protected TableView<clienteModel> tabDados;
    @FXML protected TableColumn<clienteModel, String> colDescricao;
    @FXML protected TableColumn<clienteModel, Integer> colID;
    private ObservableList<clienteModel> clienteList;
    
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
						txtCPF.setText(novaSelecao.getCpf());
						txtTelefone.setText(novaSelecao.getTelefone());
						txtEmail.setText(novaSelecao.getEmail());
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
		
		clienteDAO dao = new clienteDAO();
		List<clienteModel> clientes= dao.listarClientes(desc);
		clienteList=FXCollections.observableArrayList(clientes);
		tabDados.setItems(clienteList);
		
		if (!clienteList.isEmpty()) {
			tabDados.getSelectionModel().selectFirst();
			tabDados.getFocusModel();
			
			clienteModel cliente = tabDados.getSelectionModel().getSelectedItem();
			if(cliente != null) {
				txtNome.setText(cliente.getNome());
				txtCPF.setText(cliente.getCpf());
				txtTelefone.setText(cliente.getTelefone());
				txtEmail.setText(cliente.getEmail());
				txtDataCadastro.setText(cliente.getDataCadastro().toString());
			}
		}
	}
	
	@FXML protected void Salvar() {
		clienteDAO dao = new clienteDAO();
	try {	
		String nome=txtNome.getText();
		String cpf=txtCPF.getText();
		String telefone=txtTelefone.getText();
		String email=txtEmail.getText();
		Date data=new Date();
		
		if(statusForm==1) {
			clienteModel novoCliente=
					new clienteModel(0,nome,cpf,telefone,email,data,data);
		boolean ok = dao.inserirCliente(novoCliente);
		
		if (ok) {
			//MENAGEM DE CADASTRO CONCLUIDO
			metodo.mensagem("Confirmado", null, "Cliente salvo com sucesso.", "0");
		} else {
			//MENSAGEM DE ERRO AO CADASTRAR
			metodo.mensagem("Erro", null, "Erro ao salvar Cliente.", "0");
		}
			
			
	} else if(statusForm==2) {
			int id=tabDados.getSelectionModel().getSelectedItem().getID();
			clienteModel atualizaCliente = new clienteModel(id,nome,cpf,
					telefone,email,null,null);
			boolean ok = dao.atualizarCliente(atualizaCliente);
					if (ok) {
						//MENSAGEM DE ALTERAÇÃO BEM SUCEDIDO
						metodo.mensagem("Confirmado", null, "Cliente alterado com sucesso.", "0");
					} else {
						//MENSAGEM DE ERRO AO ATUALIZAR
						metodo.mensagem("Erro", null, "Erro ao alterar cliente.", "2");
					}
		} else {return;}
	}catch (Exception e) {
		e.printStackTrace();
		
	} finally {
		carregaDados(null);
	}
	}
	
	@FXML protected void Excluir() {
		clienteDAO dao = new clienteDAO();
		try {
		int id=tabDados.getSelectionModel().getSelectedItem().getID();
		boolean ok = dao.excluirCliente(id);
		
		if(ok) {
			//MENSAGEM DE EXCLUIDO COM SUCESSO
			metodo.mensagem("Confirmado", null, "Cliente excluído com sucesso.", "0");
			
		} else {
			//MENSAGEM DE ERRO AO EXCLUIR
			metodo.mensagem("Erro", null, "Erro ao excluir cliente.", "0");
		}
		}finally {
			carregaDados(null);
		}
	}

}
