package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.model.funcionarioModel;
import application.model.itemModel;
import application.util.conexao;

public class pedidoDAO {
	
	public int criarPedido() {
		Connection conn=null;
		PreparedStatement query = null;
		try {
			conn=conexao.getConnection();
			String sql = "INSERT INTO pedidos (data_pedido,id_cliente,id_funcionario,id_empresa,desconto,valor_pedido,valor_total,data_cadastro,data_alteracao) VALUES (now(),null,null,null,0,0,0,now(),null)";
			query = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			query.executeUpdate();

		    ResultSet rs = query.getGeneratedKeys();
		    if (rs.next()) {
		        return rs.getInt(1); // retorna o ID do pedido criado
		    }
		}catch(Exception e ) {
			e.printStackTrace();
			return 0;
		}
		 return 0; // retorna 0 se falhar
	}


	public boolean inserirItemPedido( int idPedido, int id_produto, int quantidade, 
											double precoUnitario, double desconto ,double valorTotal) {
		Connection conn=null;
		PreparedStatement query = null;
		try {
			conn=conexao.getConnection();
			String sql = "INSERT INTO itens_pedido (id_pedido, id_produto	, quantidade, preco_unitario, desconto ,data_cadastro,data_alteracao) "+
			"VALUES (?, ?, ?,?, ?, now(), null)";
			/*(select id_produto from produtos where codbarras=?)*/
		    query = conn.prepareStatement(sql);
		    query.setInt(1, idPedido);
		    query.setInt(2, id_produto);
		    query.setInt(3, quantidade);
		    query.setDouble(4, precoUnitario);
		    query.setDouble(5, desconto);
		    //query.setDouble(6, valorTotal);
		    //query.executeUpdate();
		    int insert = query.executeUpdate();
			
			return insert>0;
		}catch(Exception e ) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<itemModel> listarItensPedido(int codPedido){
		Connection conn = null;
		PreparedStatement query=null;
		ResultSet resultado=null;
		
		List <itemModel> itens = new ArrayList <itemModel>();
		try {
			conn=conexao.getConnection();
			if(conn==null) return itens;			
			
			String sql="select i.*,p.* from itens_pedido i inner join produtos p on p.id_produto=i.id_produto where i.id_pedido= ?";
			query=conn.prepareStatement(sql);
			query.setInt(1, codPedido);
			 
			
			resultado = query.executeQuery();
			
			while(resultado.next()) {
				itemModel i = new itemModel(
						resultado.getInt("id_item"),
						resultado.getInt("id_pedido"),
						resultado.getInt("id_produto"),
						resultado.getInt("quantidade"),
						resultado.getString("codBarras"),
						resultado.getString("Descricao"),
						resultado.getDouble("preco_unitario"),
						resultado.getDouble("desconto"),
						resultado.getDouble("valor_total"),
						resultado.getDate("data_cadastro"),
						resultado.getDate("data_alteracao")	
						);
				i.setIdItem(resultado.getInt("id_item"));
				i.setIdPedido(resultado.getInt("id_pedido"));
				i.setIdProduto(resultado.getInt("id_produto"));
				i.setQuantidade(resultado.getInt("quantidade"));
				i.setCodBarras(resultado.getString("codBarras"));
				i.setDescricao(resultado.getString("Descricao"));
				i.setPrecoUnitario(resultado.getDouble("preco_unitario"));
				i.setDesconto(resultado.getDouble("desconto"));
				i.setValorTotal(resultado.getDouble("valor_total"));
				i.setDataCadastro(resultado.getDate("data_cadastro"));
				i.setDataAlteracao(resultado.getDate("data_alteracao"));
				itens.add(i);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return itens;
	}
}
