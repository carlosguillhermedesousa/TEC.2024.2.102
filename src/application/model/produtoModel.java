package application.model;

import java.util.Date;

public class produtoModel {
	private int id_produto;
    private String nome;
    private String descricao;
    private String preco;
    private int estoque;
    private String codBarra;
    private Date data_cadastro;
    private Date data_alteracao;

    public produtoModel(
            int id_produto,
            String nome,
            String descricao,
            String codBarra,
            String preco,
            int estoque,
            Date data_cadastro,
            Date data_alteracao
    ) {
        this.id_produto = id_produto;
        this.nome = nome;
        this.descricao = descricao;
        this.codBarra=codBarra;
        this.preco = preco;
        this.estoque = estoque;
        this.data_cadastro = data_cadastro;
        this.data_alteracao = data_alteracao;
    }

    // GETTERS E SETTERS

    public int getID() { return id_produto;}
    public void setID(int id_produto) {this.id_produto = id_produto;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getDescricao() {return descricao; }
    public void setDescricao(String descricao) {this.descricao = descricao;}
    
    public String getCodBarra() {return codBarra; }
    public void setCodBarra(String codBarra) {this.codBarra = codBarra;}

    public String getPreco() {return preco;}
    public void setPreco(String preco) {this.preco = preco;}

    public int getEstoque() {return estoque;}
    public void setEstoque(int estoque) {this.estoque = estoque;}

    public Date getDataCadastro() {return data_cadastro;}
    public void setDataCadastro(Date data_cadastro) {this.data_cadastro = data_cadastro;}

    public Date getDataAlteracao() {return data_alteracao;}
    public void setDataAlteracao(Date data_alteracao) {this.data_alteracao = data_alteracao;}
}
