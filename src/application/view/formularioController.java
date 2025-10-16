package application.view;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class formularioController {
	@FXML protected Button btnCancelar;
    @FXML protected Button btnEditar;
    @FXML protected Button btnExcluir;
    @FXML protected Button btnNovo;
    @FXML protected Button btnSair;
    @FXML protected Button btnSalvar;
    @FXML protected AnchorPane formCadastro;    
    @FXML protected TextField txtBuscar;
    protected int statusForm;
    
    @FXML
    protected void Sair() {
    		try {
    			/*PEGA A CENA DA JANELA ATUAL*/
    			Stage stage = (Stage) btnSair.getScene().getWindow();
    			/*LOCALIZA O FORM*/
    			AnchorPane form = (AnchorPane) stage.getScene().lookup("#form");
    			
    			/*CARREGA INICIO SISTEMA*/
    			Parent fxml=FXMLLoader.load(getClass().getResource("planoFundo.fxml"));
    			form.getChildren().setAll(fxml);
    			
    			form.setTopAnchor(fxml, 0.0);
    			form.setBottomAnchor(fxml, 0.0);
    			form.setLeftAnchor(fxml, 0.0);
    			form.setRightAnchor(fxml, 0.0);
    			
    			stage.setTitle("Sistema | Página Inicial");
    			
    		}catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    }
    
    @FXML
    protected void init() {//Metodo initialize();
    		carregaDados(null);
    		
    		txtBuscar.setOnAction(e->{carregaDados(txtBuscar.getText());});
    		
		btnSair.setOnAction(e-> {Sair();});
		
		btnNovo.setOnAction(e->{
			statusForm=0;
			emEdicao(true);
			habilitaCampos(true);
			limpaCampos();
		});
		
		btnEditar.setOnAction(e->{
			statusForm=1;
			emEdicao(true);
			habilitaCampos(true);
		});
		
		btnSalvar.setOnAction(e->{Salvar();});
		
		btnCancelar.setOnAction(e->{carregaDados(null);});
		
		btnExcluir.setOnAction(e->{Excluir();});
		
		formCadastro.sceneProperty().addListener((obs, oldScene, newScene)->{
			if (newScene!=null) {
				newScene.setOnKeyPressed(e->{
					KeyCode key = e.getCode();
					
					switch (key) {
					case ESCAPE:
						Sair();
						break;
					default:
						break;
					}
				});
			}
		});
			
	}
    
    protected void habilitaCampos(boolean status) {
	    	for(javafx.scene.Node node : formCadastro.getChildren()) {
	    		if(node instanceof TextField) {
	    			((TextField) node).setDisable(!status);
	    			
	    		}
	    	}
    }
    
    protected void limpaCampos() {
    		for(javafx.scene.Node node : formCadastro.getChildren()) {
    		if(node instanceof TextField) {
    			((TextField) node).clear();
    			
    			}
    		}
    }
    
    protected void emEdicao(boolean status) {
    		if(!status) {
    			statusForm=0;
    			
    		}
    		
    		btnNovo.setDisable(status);
    		btnEditar.setDisable(status);
    		btnExcluir.setDisable(status);
    		btnCancelar.setDisable(!status);
    		btnSalvar.setDisable(!status);
    }
    
    protected void carregaDados(String id) {
    	
    }
    
    protected void Salvar() {
    	
    }
    
    protected void Excluir() {
    	
    }
}
