package application.model;


public class relatorioVendaModel {
	
	private int ID;
	private String Data;
	private String Cliente;
    private String Vendedor;
    private String  Status;
    private String  VlrPedido;
    private String Desconto;
    private String VlrTotal;
    
    public relatorioVendaModel(int ID,
    		String Data, 
    		String Cliente, 
    		String Vendedor,
    		String Status, 
    		String VlrPedido, 
    		String Desconto, 
    		String VlrTotal) {
        this.ID = ID;
        this.Data = Data;
        this.Cliente = Cliente;
        this.Vendedor = Vendedor;
        this.Status = Status;
        this.VlrPedido = VlrPedido;
        this.Desconto = Desconto;
        this.VlrTotal = VlrTotal;
    }
    
    public int getID() {return ID;}
    public String getData() {return Data;}
    public String getCliente() {return Cliente;}
    public String getVendedor() {return Vendedor;}
    public String getStatus() {return Status;}
    public String getVlrPedido() {return VlrPedido;}
    public String getDesconto() {return Desconto;}
    public String getVlrTotal() {return VlrTotal;}


    public void setID(int ID) {this.ID = ID;}
    public void setData(String Data) {this.Data = Data;}
    public void setCliente(String Cliente) {this.Cliente = Cliente;}
    public void setVendedor(String Vendedor) {this.Vendedor = Vendedor;}
    public void setStatus(String Status) {this.Status = Status;}
    public void setVlrPedido(String VlrPedido) {this.VlrPedido = VlrPedido;}
    public void setDesconto(String Desconto) {this.Desconto = Desconto;}
    public void setVlrTotal(String VlrTotal) {this.VlrTotal = VlrTotal;}

}
