package application.model;

import java.time.LocalDate;
import java.util.Date;

public class clienteModel {
	private int idCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private Date dataCadastro;
    private Date dataAlteracao;

    // Construtores
   // public clienteModel() {}

    public clienteModel(int idCliente, String nome, String cpf, String telefone, String email,
                   Date dataCadastro, Date dataAlteracao) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
    }

    // Getters e Setters
    public int getID() {
        return idCliente;
    }

    public void setID(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
}
