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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import application.dao.pedidoDAO;
import application.dao.produtoDAO;
import application.model.itemModel;
import application.model.pedidoModel;
import application.model.produtoModel;
import application.util.metodo;

public class frenteCaixaController {

    @FXML private AnchorPane formFrenteCaixa;    
    @FXML private TextField txtQuantidade;    
    @FXML private TextField txtBusca;
    @FXML private Label lblTipoBusca;
    @FXML private Label lblPedido;
    @FXML private Label lblQtdItens;
    @FXML private Label lblValorTotal;
    
    private int pedido=0;
    private boolean pedidoIniciado=false;
    
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
    private ObservableList<itemModel> itensList;
    
    private boolean buscaDescricao=false;
    
    DecimalFormat formatoMoeda = new DecimalFormat("R$ #,##0.00");
    //NumberFormat formatMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    NumberFormat formatMoeda = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

    @FXML
    private void initialize() {
    	/*newScene.setOnKeyTyped(event -> {
	    if ("*".equals(event.getCharacter())) {
	    	edtQuantidade.requestFocus();
	        //System.out.println("* pressionado");
	        // Aqui você pode executar qualquer ação desejada
	    }
	});*/
    	//PREPARA AS COLUNAS DA TABELA DE BUSCA DE PRODUTO PARA RECEBER OS DADOS
	    	tabID.setCellValueFactory(new PropertyValueFactory<>("CodBarra"));
		tabDescricao.setCellValueFactory(new PropertyValueFactory<>("Nome"));
	
	//PREPARA AS COLUNAS DA TABELA DE ITENS DO PEDIDO PARA RECEBER OS DADOS	
		colCodBarra.setCellValueFactory(new PropertyValueFactory<>("CodBarras"));
		colDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
		colQuantidade.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));
		
		 colValorUn.setCellValueFactory(new PropertyValueFactory<>("PrecoUnitario"));
		colValorTotal.setCellValueFactory(new PropertyValueFactory<>("ValorTotal"));
		 
			
	    //lblPedido.setText(String.valueOf(pedido));	
		lblPedido.setText(String.format("%06d",pedido));
	    	txtQuantidade.setText("1");
	    	
	    formFrenteCaixa.sceneProperty().addListener((obs, oldScene, newScene) -> {// Adiciona listener depois que a cena estiver carregada
	        if (newScene != null) {          	         	
	            newScene.setOnKeyPressed(event -> {
	                KeyCode key = event.getCode();
	                Stage stage = (Stage) formFrenteCaixa.getScene().getWindow();
	
	                switch (key) {
	                    case F1:
	                        	if (!pedidoIniciado){
	                        		pedidoDAO dao= new pedidoDAO();
	                            pedido=dao.criarPedido();
	                            //lblPedido.setText(String.valueOf(pedido));
	                            lblPedido.setText(String.format("%06d",pedido));
	                            if (pedido>0) {
	                            	pedidoIniciado=true;
	                            }
	                    		} else {
	                    			metodo.mensagem("Pedido", null, "Já existem um Pedido em aberto!", "1");
	                    		}
	                        break;
	                    case F2:
	                        System.out.println("F2 pressionado");
	                        break;
	                    case F3:
	                        System.out.println("F3 pressionado ");
	                        break;
	                    case F8:
	                        if(pedidoIniciado) {
	                        		pedidoIniciado=false;
	                        		pedido=0;
	                        } else {
	                        	metodo.mensagem("Atenção", null, "Nenhum pedido iniciado", "2");
	                        }
	                        break;
	                    case F10:
	                    case ESCAPE:
	                        //System.out.println("Fechando formulário...");
	                        stage.close();
	                        break;/*
	                    case MULTIPLY: // Teclado numérico (*)
	                    		event.consume();
	                    		txtQuantidade.requestFocus();
	                    		txtQuantidade.setText("");
	                    	//System.out.println("Teclado numérico * pressionado");
	                        break;*/
	                    default:
	                    	
	                        break;
	                }
	            });
	        }
	    });
	    /*
	    txtQuantidade.addEventFilter(KeyEvent.KEY_TYPED, event -> {
	        if ("*".equals(event.getCharacter())) {
	            event.consume(); // Bloqueia o * dentro do edtQuantidade
	        }
	    });
	        */
	    txtQuantidade.setOnAction(e-> {
	    String vlrQuantidade=txtQuantidade.getText().replace("*", "");
	    	if(vlrQuantidade.trim().isEmpty()) { 	
	    		txtQuantidade.setText("1");
	    		txtBusca.requestFocus();
	    		
	    	} else {
	    		txtQuantidade.setText(String.valueOf(metodo.strToIntDef(vlrQuantidade,1)));
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
	        	
	        	if (!pedidoIniciado) {
	        		metodo.mensagem("Incluir novo pedido", null,"Aperte 'F1' para iniciar um novo pedido" , "1");
	        	} else {
	        		String textoBusca= txtBusca.getText().replace("%", "");
	        		if(!textoBusca.isEmpty()) {
	        		//tabItemVisualizacao(true);
	        		buscaItem(2);
	        		tabItem.getSelectionModel().selectFirst();
	        		inserirNovoItem();
	        		}
	        	}
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
            /* if ("*".equals(event.getText())) {// Se for o * do teclado normal (Shift + 8)
            //System.out.println("Detectado * do teclado normal no edtBusca");
            event.consume();
        }*/
        
        });
        	
        txtBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		    if(buscaDescricao) {
	            	if (newValue.length() >= 3) {
	            		tabItemVisualizacao(buscaDescricao);
	            		buscaItem(1);
	    		    } 
            	}
			});
            

    
        	tabItem.setOnKeyPressed(event->{
        		 KeyCode key = event.getCode();
        		 if (key == KeyCode.ENTER) {
	        		if (pedidoIniciado && pedido>0){
	        			inserirNovoItem();
	        		}
        		}
        		
        	});
    
    }
        
	    private void tabItemVisualizacao(boolean status) {
		        	tabItem.setVisible(status);
		    		tabItem.setManaged(status);
		    		//percentual=status;
		        	if (status) {
		        		lblTipoBusca.setText("Descrição Produto");
		        	} else {
		        		lblTipoBusca.setText("Código de Barras Produto");
		        	}       
	    }
        
        public void buscaItem(int tipo){
	        	produtoDAO dao = new produtoDAO();
	    		List<produtoModel> produtos= dao.listarProdutos(txtBusca.getText().replace("%", ""),tipo);
	    		produtoList=FXCollections.observableArrayList(produtos);
	    		tabItem.setItems(produtoList);	    		
        }
        
        public void inserirNovoItem() {
        		pedidoDAO dao = new pedidoDAO();
			 boolean ok = dao.inserirItemPedido(pedido, 
					 tabItem.getSelectionModel().getSelectedItem().getID(), 
				 Integer.valueOf(txtQuantidade.getText()), 
				 tabItem.getSelectionModel().getSelectedItem().getPreco(), 
				 0, 
				 tabItem.getSelectionModel().getSelectedItem().getPreco()*Integer.valueOf(txtQuantidade.getText())
				);	 
		 
	        		if (ok) {      			
	        			List<itemModel> itens= dao.listarItensPedido(pedido);
	        			itensList=FXCollections.observableArrayList(itens);
		    	    		tabItemPedido.setItems(itensList);	        			
	        		}
	        		txtQuantidade.setText("1");
	        		txtBusca.setText("");
	        		txtBusca.requestFocus();
	        		buscaDescricao=false;
	        		tabItemVisualizacao(false);
	        		totalPedido();
        }
        
      private void totalPedido() {
    	  	//pedidoDAO dao = new pedidoDAO();
  		//List<pedidoModel> pedido = dao.resumoPedido(pedido);
    	  	List<pedidoModel> resumoPedido = pedidoDAO.resumoPedido(pedido);
    	  	pedidoModel resumo = resumoPedido.get(0);
    	  	lblValorTotal.setText(String.valueOf(resumo.getValorTotal()));
    	  	lblQtdItens.setText(String.valueOf(resumo.getQuantidade()));
    	  
      }  
}
