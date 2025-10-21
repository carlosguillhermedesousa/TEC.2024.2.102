package application.view;

import java.util.Date;
import java.util.List;

import application.dao.produtoDAO;
import application.model.produtoModel;
import application.util.metodo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class produtoController extends formularioController {
	  

	@FXML protected TextField txtData;
	@FXML protected TextField txtDescricao;
	@FXML protected TextField txtEstoque;
	@FXML protected TextField txtNome;
	@FXML protected TextField txtPreco;
	 
	@FXML protected TableView<produtoModel> tabDados;
	@FXML protected TableColumn<produtoModel, Integer> colID;
	@FXML protected TableColumn<produtoModel, String> colDescricao;
	private ObservableList<produtoModel> produtoList;

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
						txtDescricao.setText(novaSelecao.getDescricao());						
						txtEstoque.setText(String.valueOf(novaSelecao.getEstoque()));
						txtPreco.setText(novaSelecao.getPreco().toString());
						txtData.setText(novaSelecao.getDataCadastro().toString());
						
					}
				});
		//tabDados.setFocusTraversable(true);
		//tabDados.requestFocus();
		Platform.runLater(() -> tabDados.requestFocus());
		
	}
	
	protected void carregaDados(String desc) {
		emEdicao(false);
		habilitaCampos(false);
		
		produtoDAO dao = new produtoDAO();
		List<produtoModel> produtos= dao.listarProdutos(desc);
		produtoList=FXCollections.observableArrayList(produtos);
		tabDados.setItems(produtoList);
		
		if (!produtoList.isEmpty()) {
			tabDados.getSelectionModel().selectFirst();
			tabDados.getFocusModel();
			
			produtoModel produto = tabDados.getSelectionModel().getSelectedItem();
			if(produto != null) {
				txtNome.setText(produto.getNome());
				txtDescricao.setText(produto.getDescricao());
				txtEstoque.setText(String.valueOf(produto.getEstoque()));
				txtPreco.setText(produto.getPreco().toString());
				txtData.setText(produto.getDataCadastro().toString());
			}
		}
	}
	
	@FXML protected void Salvar() {
		produtoDAO dao = new produtoDAO();
	try {	
		String nome=txtNome.getText();
		String descricao=txtDescricao.getText();
		Integer estoque=Integer.valueOf(txtEstoque.getText());
		Double preco=Double.valueOf(txtPreco.getText());
		Date data=new Date();
		
		if(statusForm==1) {
			produtoModel novoProduto=
					new produtoModel(0,nome,descricao,preco,estoque,data,data);
		boolean ok = dao.inserirProduto(novoProduto);
		
		if (ok) {
			//MENAGEM DE CADASTRO CONCLUIDO
			metodo.mensagem("Confirmado", null, "Produto salvo com sucesso.", "0");
		} else {
			//MENSAGEM DE ERRO AO CADASTRAR
			metodo.mensagem("Erro", null, "Erro ao salvar Produto.", "0");
		}
			
			
	} else if(statusForm==2) {
			int id=tabDados.getSelectionModel().getSelectedItem().getID();
			produtoModel atualizaProduto = new produtoModel(id,nome,descricao,
					preco,estoque,null,null);
			boolean ok = dao.atualizarProduto(atualizaProduto);
					if (ok) {
						//MENSAGEM DE ALTERAÇÃO BEM SUCEDIDO
						metodo.mensagem("Confirmado", null, "Produto alterado com sucesso.", "0");
					} else {
						//MENSAGEM DE ERRO AO ATUALIZAR
						metodo.mensagem("Erro", null, "Erro ao alterar Produto.", "2");
					}
		} else {return;}
	}catch (Exception e) {
		e.printStackTrace();
		
	} finally {
		carregaDados(null);
	}
	}
	
	@FXML protected void Excluir() {
		produtoDAO dao = new produtoDAO();
		try {
		int id=tabDados.getSelectionModel().getSelectedItem().getID();
		boolean ok = dao.excluirProduto(id);
		
		if(ok) {
			//MENSAGEM DE EXCLUIDO COM SUCESSO
			metodo.mensagem("Confirmado", null, "Produto excluído com sucesso.", "0");
			
		} else {
			//MENSAGEM DE ERRO AO EXCLUIR
			metodo.mensagem("Erro", null, "Erro ao excluir funcionário.", "0");
		}
		}finally {
			carregaDados(null);
		}
	}

}
