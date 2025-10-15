package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import application.model.funcionarioModel;
import application.util.conexao;

public class funcionarioDAO {

	
	//LISTAR
	
	public List<funcionarioModel> listarFuncionarios(String desc){
		Connection conn = null;
		PreparedStatement query=null;
		ResultSet resultado=null;
		
		List <funcionarioModel> funcionarios = new ArrayList <funcionarioModel>();
		try {
			conn=conexao.getConnection();
			if(conn==null) return funcionarios;
			String sql="select *from funcionarios";
			
			if(desc!=null && !desc.isEmpty()) {
				sql="select *from funcionarios where nome like ?";
				query=conn.prepareStatement(sql);
				query.setString(1, "%"+sql+"%");
			} else {
				query=conn.prepareStatement(sql);
			}
			
			resultado = query.executeQuery();
			
			while(resultado.next()) {
				funcionarioModel f = new funcionarioModel(
						resultado.getInt("id_funcionario"),
						resultado.getString("nome"),
						resultado.getString("cpf"),
						resultado.getString("rg"),
						resultado.getString("cargo"),
						resultado.getString("salario"),
						resultado.getString("usuario"),
						resultado.getString("senha"),
						resultado.getDate("data_cadastro"),
						resultado.getDate("data_alteracao")				
						);
				f.setID(resultado.getInt("id_funcionario"));
				f.setNome(resultado.getString("nome"));
				f.setCPF(resultado.getString("cpf"));
				f.setRG(resultado.getString("rg"));
				f.setCargo(resultado.getString("cargo"));
				f.setSalario(resultado.getString("salario"));
				f.setUsuario(resultado.getString("usuario"));
				f.setSenha(resultado.getString("senha"));
				f.setDataCadastro(resultado.getDate("data_cadastro"));
				f.setDataAlteracao(resultado.getDate("data_alteracao"));
				funcionarios.add(f);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return funcionarios;
	}
	
	//INSERT
	
	//UPDATE
	
	//DELETE
	
	
}
