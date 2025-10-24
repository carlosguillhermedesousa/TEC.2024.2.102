package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.util.conexao;

public class pedidoDAO {
	
	public int criarPedido() {
		Connection conn=null;
		PreparedStatement query = null;
		try {
			conn=conexao.getConnection();
			String sql = "INSERT INTO pedido (total) VALUES (0)";
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


	public static boolean inserirItemPedido( int pedidoId, String produto, int quantidade, double precoUnitario) {
		Connection conn=null;
		PreparedStatement query = null;
		try {
			conn=conexao.getConnection();
			String sql = "INSERT INTO item_pedido (pedido_id, produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
		    query = conn.prepareStatement(sql);
		    query.setInt(1, pedidoId);
		    query.setString(2, produto);
		    query.setInt(3, quantidade);
		    query.setDouble(4, precoUnitario);
		    //query.executeUpdate();
		    int insert = query.executeUpdate();
			
			return insert>0;
		}catch(Exception e ) {
			e.printStackTrace();
			return false;
		}
	}
}
