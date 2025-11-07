package application.view;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import application.dao.clienteDAO;
import application.dao.pedidoDAO;
import application.model.clienteModel;
import application.model.produtoModel;
import application.model.relatorioVendaModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class relatorioVendaController {
    
    @FXML private TextField txtBusca;
    @FXML private DatePicker dtFinal;
    @FXML private DatePicker dtInicio;
    @FXML private Button btnBuscar;
    @FXML private TableView<relatorioVendaModel> tabRelatorio;
    @FXML private TableColumn<relatorioVendaModel, String> colID;
    @FXML private TableColumn<relatorioVendaModel, String> colData;
    @FXML private TableColumn<relatorioVendaModel, String> colStatus;
    @FXML private TableColumn<relatorioVendaModel, String> colCliente;
    @FXML private TableColumn<relatorioVendaModel, String> colVendedor;
    @FXML private TableColumn<relatorioVendaModel, String> ColVlrPedido;
    @FXML private TableColumn<relatorioVendaModel, String> colDesconto;
    @FXML private TableColumn<relatorioVendaModel, String> colVlrTotal;
    private LocalDate hoje,primeiroDia,ultimoDia;
    private ObservableList<relatorioVendaModel> relatorioVenda;
    
    @FXML
    public void initialize() {
    		// Data atual
        hoje = LocalDate.now();
        // Primeiro dia do mês
         primeiroDia = hoje.withDayOfMonth(1);
        // Último dia do mês
         ultimoDia = hoje.withDayOfMonth(hoje.lengthOfMonth());
         
         dtInicio.setValue(primeiroDia);
         dtFinal.setValue(ultimoDia);
        
    	 	colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
    	    colData.setCellValueFactory(new PropertyValueFactory<>("Data"));
    	    colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
    	    colCliente.setCellValueFactory(new PropertyValueFactory<>("Cliente"));
    	    colVendedor.setCellValueFactory(new PropertyValueFactory<>("Vendedor"));
    	    ColVlrPedido.setCellValueFactory(new PropertyValueFactory<>("VlrPedido"));
    	    colDesconto.setCellValueFactory(new PropertyValueFactory<>("Desconto"));
    	    colVlrTotal.setCellValueFactory(new PropertyValueFactory<>("VlrTotal"));
    
    	    //REALIZA A BUSCA DO RELATORIO
    	    btnBuscar.setOnAction(e->{
    	    	buscarRelatorio(txtBusca.getText(),dtInicio.getValue(),dtFinal.getValue());
    	    });
    	
    }
    
    private void buscarRelatorio(String Desc, LocalDate dtInicio, LocalDate dtFinal) {
    		pedidoDAO dao = new pedidoDAO();
		List<relatorioVendaModel> realtorio= dao.relatorioVenda(Desc, dtInicio, dtFinal);
		relatorioVenda=FXCollections.observableArrayList(realtorio);
		tabRelatorio.setItems(relatorioVenda);
    }
    
}
