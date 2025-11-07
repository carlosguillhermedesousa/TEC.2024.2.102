package application.model;

import java.util.Date;

public class itemModel {
	private int id_item;
    private int id_pedido;
    private int id_produto;
    private int quantidade;
    private String codBarras;
    private String Descricao;
    private String preco_unitario;
    private String desconto;
    private String valor_total;
    private Date data_cadastro;
    private Date data_alteracao;

    public itemModel(
            int id_item,
            int id_pedido,
            int id_produto,
            int quantidade,
            String codBarras,
            String Descricao,
            String preco_unitario,
            String desconto,
            String valor_total,
            Date data_cadastro,
            Date data_alteracao
    ) {
        this.id_item = id_item;
        this.id_pedido = id_pedido;
        this.id_produto = id_produto;
        this.quantidade = quantidade;
        this.codBarras=codBarras;
        this.Descricao=Descricao;
        this.preco_unitario = preco_unitario;
        this.desconto = desconto;
        this.valor_total = valor_total;
        this.data_cadastro = data_cadastro;
        this.data_alteracao = data_alteracao;
    }

    // GETTERS E SETTERS

    public int getIdItem() { return id_item; }
    public void setIdItem(int id_item) { this.id_item = id_item; }

    public int getIdPedido() { return id_pedido; }
    public void setIdPedido(int id_pedido) { this.id_pedido = id_pedido; }

    public int getIdProduto() { return id_produto; }
    public void setIdProduto(int id_produto) { this.id_produto = id_produto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    
    public String getCodBarras() { return codBarras; }
    public void setCodBarras(String codBarras) { this.codBarras = codBarras; }
    
    public String getDescricao() { return Descricao; }
    public void setDescricao(String Descricao) { this.Descricao = Descricao; }

    public String getPrecoUnitario() { return preco_unitario; }
    public void setPrecoUnitario(String preco_unitario) { this.preco_unitario = preco_unitario; }

    public String getDesconto() { return desconto; }
    public void setDesconto(String desconto) { this.desconto = desconto; }

    public String getValorTotal() { return valor_total; }
    public void setValorTotal(String valor_total) { this.valor_total = valor_total; }

    public Date getDataCadastro() { return data_cadastro; }
    public void setDataCadastro(Date data_cadastro) { this.data_cadastro = data_cadastro; }

    public Date getDataAlteracao() { return data_alteracao; }
    public void setDataAlteracao(Date data_alteracao) { this.data_alteracao = data_alteracao; }
}
