package application.view;

import application.dao.funcionarioDAO;
import application.util.metodo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import application.util.metodo;

public class loginController {
/* ENCAPSULAMENTO DOS COMPONENTES DO FORMULÁRIO */
/* INICIO ENCAPSULAMENTO */
	
    @FXML private Button btnEntrar;
    @FXML private Button btnFechar;
    @FXML private PasswordField txtSenha;
    @FXML private TextField txtUsuario;
    
/*FINAL ENCAPSULAMENTO*/    
    
/*METODO PARA FECHAR O SISTEMA*/
    //public void close() {System.exit(0);}
	public void close() {
		if (metodo.mensagemConfirmacao("Fechar Sistema", null, "Deseja fechar o sistema?")) {
			System.exit(0);
		}		
	}

/*METODO DE ENTRAR OU LOGIN*/   
    public void login() {
    	String usuario=txtUsuario.getText();
    	String senha=txtSenha.getText();
    	
    	Alert mensagem;
    	
    	funcionarioDAO dao=new funcionarioDAO();
    	
    	if (dao.autenticar(usuario, senha)) {
    		//if(usuario.equals("admin") && senha.equals("123"))
    		/*mensagem= new Alert(Alert.AlertType.CONFIRMATION);//CRIA UMA NOVA MENSAGEM
    		mensagem.setTitle("Confirmação");//INFORMA O TITULO DA MENSAGEM
    		mensagem.setHeaderText(null);//REMOVE O CABEÇALHO DA MENSAGEM
    		mensagem.setContentText("Bem vindo ao Sistema "+usuario);//TEXTO DO CORPO DA MENSAGEM
    		mensagem.showAndWait();//MOSTRA A MENSAGEM*/
    		
    		metodo.mensagem("Confirmação", null, "Bem vindo Ao Sistema", "1");
    		
    		/*FECHAR TELA DE LOGIN*/
    		btnEntrar.getScene().getWindow().hide();
    		try {
    		Parent root = FXMLLoader.load(getClass().getResource("principal.fxml"));
    		
    		Stage stage = new Stage();
    		Scene scene = new Scene(root);
    		//metodo.cursorMouse(scene);
    		//metodo.ativarImagem(scene, stage);
    	    
    		/*CONFIGURA PROPIEDADES DA TELA OU FORMULÁRIO*/
    		stage.setScene(scene);
    		stage.setTitle("Sistema by Carlos Guilherme");//TITULO DO FORMULÁRIO
    		stage.getIcons().add(new Image(getClass().getResourceAsStream("/application/util/icon_2.png")));
    		stage.centerOnScreen();//ENTRALIZAR FORMULÁRIO
    		stage.setMaximized(true);//INICIALIZA FORMULÁRIO MAXIMIZADO
    		// Evento de fechamento
	        stage.setOnCloseRequest(e -> {
	            e.consume(); // Impede o fechamento automático
	            //confirmarSaida(primaryStage);
	            if(metodo.mensagemConfirmacao("Fechar Sistema", null, "Deseja fechar o sistema?")) {
	            	stage.close();
	            }
	        });
    		stage.show();//MOSTRA O FORMULÁRIO
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	} else {
    		/*mensagem= new Alert(Alert.AlertType.ERROR);//CRIA UMA NOVA MENSAGEM
    		mensagem.setTitle("Erro");//INFORMA O TITULO DA MENSAGEM
    		mensagem.setHeaderText(null);//REMOVE O CABEÇALHO DA MENSAGEM
    		mensagem.setContentText("Usuário ou Senha incorretos!");//TEXTO DO CORPO DA MENSAGEM
    		mensagem.showAndWait();*/
    		//MOSTRA A MENSAGEM
    		metodo.mensagem("Erro", null, "Usuário ou Senha incorretos", "3");
    	}
    }
    
/* @Override e @FXML INDICA QUE SERÁ CARREGADO 
   JUNTO COM A PAGINA OU FORMULÁRIO

 METODOS QUE SERÃO CARREGADOS JUNTO COM O FORMULÁRIO*/
    @FXML
    private void initialize() {
    	/*VINCULA A AÇÃO DO BOTÃO FECHAR AO METODO CLOSE*/
    	btnFechar.setOnAction(e->{close();});
    	
    	/*VINCULA A AÇÃO DO BOTÃO ENTRAR AO METODO LOGIN*/
    	btnEntrar.setOnAction(e->{login();});
    	
    	/*ACIONAR O ENTER NO TEXT FIEL USUARIO E ACESSAR O PASSWORD FILD DE SENHA*/
    	txtUsuario.setOnAction(e->{txtSenha.requestFocus();});
    	
    	/* ACIONAR O ENTER NO PASSWORD FIELD DE SENHA E ACESSAR O METODO DE LOGIN*/
    	txtSenha.setOnAction(e->{login();});
    	
    }
}

