package application.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.util.List;

import application.dao.produtoDAO;
import application.model.itemModel;
import application.model.produtoModel;
import application.util.metodo;

public class frenteCaixaController {

    @FXML private AnchorPane formFrenteCaixa;    
    @FXML private TextField txtQuantidade;    
    @FXML private TextField txtBusca;
    @FXML private Label lblTipoBusca;
    //TABLE VIEW PARA BUSCAR OS PRODUTOS
    @FXML private TableView<produtoModel> tabItem;
    @FXML private TableColumn<produtoModel, String> tabDescricao;
    @FXML private TableColumn<produtoModel, String> tabID;
    private ObservableList<produtoModel> produtoList;
    
    //TABLE VIEW PARA INSERIR OS ITENS DO PEDIDO
    @FXML private TableView<itemModel> tabItemPedido;
    @FXML private TableColumn<itemModel, Integer> colQuantidade;
    @FXML private TableColumn<itemModel, String> colCodBarra;
    @FXML private TableColumn<itemModel, String> colDescricao;
    @FXML private TableColumn<itemModel, Double> colValorTotal;
    @FXML private TableColumn<itemModel, Double> colValorUn;
    private ObservableList<itemModel> itemList;
    
    private boolean buscaDescricao=false;

    @FXML
    private void initialize() {
    	/*newScene.setOnKeyTyped(event -> {
	    if ("*".equals(event.getCharacter())) {
	    	edtQuantidade.requestFocus();
	        //System.out.println("* pressionado");
	        // Aqui você pode executar qualquer ação desejada
	    }
	});*/
    	
    	tabID.setCellValueFactory(new PropertyValueFactory<>("CodBarra"));
	tabDescricao.setCellValueFactory(new PropertyValueFactory<>("Nome"));
	
	colCodBarra.setCellValueFactory(new PropertyValueFactory<>("CodBarras"));
	colDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
	colQuantidade.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));
	colValorUn.setCellValueFactory(new PropertyValueFactory<>("PrecoUnitario"));
	colValorTotal.setCellValueFactory(new PropertyValueFactory<>("ValorTotal"));
		
    	
    	txtQuantidade.setText("1");
    	
        formFrenteCaixa.sceneProperty().addListener((obs, oldScene, newScene) -> {// Adiciona listener depois que a cena estiver carregada
            if (newScene != null) {          	         	
                newScene.setOnKeyPressed(event -> {
                    KeyCode key = event.getCode();
                    Stage stage = (Stage) formFrenteCaixa.getScene().getWindow();

                    switch (key) {
                        case F1:
                            System.out.println("F1 pressionado ");
                            break;
                        case F2:
                            System.out.println("F2 pressionado");
                            break;
                        case F3:
                            System.out.println("F3 pressionado ");
                            break;
                        case F10:
                        case ESCAPE:
                            //System.out.println("Fechando formulário...");
                            stage.close();
                            break;
                        case MULTIPLY: // Teclado numérico (*)
                        		txtQuantidade.requestFocus(); 
                        	//System.out.println("Teclado numérico * pressionado");
                            break;
                        default:
                        	
                            break;
                    }
                });
            }
        });
    
        txtQuantidade.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if ("*".equals(event.getCharacter())) {
                event.consume(); // Bloqueia o * dentro do edtQuantidade
            }
        });
        
        txtQuantidade.setOnAction(e-> {
        	if(txtQuantidade.getText().trim().isEmpty()) { 	
        		txtQuantidade.setText("1");
        		txtBusca.requestFocus();
        		
        	} else {
        		txtQuantidade.setText(String.valueOf(metodo.strToIntDef(txtQuantidade.getText(),1)));
        		//edtQuantidade.setText(Integer.toString(WindowHelper.strToIntDef(edtQuantidade.getText(),1)));
        		txtBusca.requestFocus();
        	}
        	
        });
        
        txtBusca.setOnAction(e->{
        	//inserir o produto 
        	
        	if (txtBusca.getText().equals("*")) {
        		txtBusca.setText(null);
        		txtQuantidade.requestFocus(); 
                txtQuantidade.setText("");
                return;
        	};
        	
        	
        	
        	
        });
        
        txtBusca.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            
            if ((key == KeyCode.MULTIPLY) || ("*".equals(event.getText()))) {// Se for o * do teclado numérico
                //System.out.println("Detectado * do teclado numérico no edtBusca");
            	event.consume();
                txtQuantidade.requestFocus(); 
                txtQuantidade.setText("");
            }
            
            if (txtBusca.getText()=="") {
	            	lblTipoBusca.setText("Código de Barras Produto");
	            	buscaDescricao=false;
	            	tabItemVisualizacao(buscaDescricao);            	
            }
            
            if (key == KeyCode.DIGIT5 && event.isShiftDown()) {  
	            	if (!buscaDescricao) {
	            		buscaDescricao=true;
	            		lblTipoBusca.setText("Descrição Produto");
	            		txtBusca.setText("");
	            	}
            }
            
            if (key == KeyCode.DOWN) {
            	Platform.runLater(() -> tabItem.requestFocus());
            }
        	
            txtBusca.textProperty().addListener((observable, oldValue, newValue) -> {
	    		    if(buscaDescricao) {
		            	if (newValue.length() >= 3) {
		            		tabItemVisualizacao(buscaDescricao);
		            		buscaItemDescricao();
		    		    } 
	            	}
    			});
            
           /* if ("*".equals(event.getText())) {// Se for o * do teclado normal (Shift + 8)
                //System.out.println("Detectado * do teclado normal no edtBusca");
                event.consume();
            }*/
            
        });
    }
        
    private void tabItemVisualizacao(boolean status) {
	        	tabItem.setVisible(status);
	    		tabItem.setManaged(status);
	    		//percentual=status;
	        	if (status) {}        
    }
        
        public void buscaItemDescricao(){
	        	produtoDAO dao = new produtoDAO();
	    		List<produtoModel> produtos= dao.listarProdutos(txtBusca.getText().replace("%", ""));
	    		produtoList=FXCollections.observableArrayList(produtos);
	    		tabItem.setItems(produtoList);
	    		
        }
}
