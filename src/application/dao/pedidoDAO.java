package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.model.funcionarioModel;
import application.model.itemModel;
import application.model.pedidoModel;
import application.model.relatorioVendaModel;
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
			String sql = "INSERT INTO itens_pedido (id_pedido, id_produto , quantidade, preco_unitario, desconto ,data_cadastro,data_alteracao) "+
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
			
			String sql="select i.id_item,i.id_pedido,i.id_produto,p.codbarras,p.nome,p.descricao,i.quantidade,REPLACE(FORMAT(i.preco_unitario, 2), '.', ',') AS preco_unitario,  REPLACE(FORMAT(i.desconto, 2), '.', ',') AS desconto,"
					+ "  REPLACE(FORMAT(i.valor_total, 2), '.', ',') AS valor_total, i.data_cadastro,i.data_alteracao from itens_pedido i inner join produtos p on p.id_produto=i.id_produto where i.id_pedido= ?";
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
						resultado.getString("nome"),
						resultado.getString("preco_unitario"),
						resultado.getString("desconto"),
						resultado.getString("valor_total"),
						resultado.getDate("data_cadastro"),
						resultado.getDate("data_alteracao")	
						);
				i.setIdItem(resultado.getInt("id_item"));
				i.setIdPedido(resultado.getInt("id_pedido"));
				i.setIdProduto(resultado.getInt("id_produto"));
				i.setQuantidade(resultado.getInt("quantidade"));
				i.setCodBarras(resultado.getString("codBarras"));
				i.setDescricao(resultado.getString("nome"));
				i.setPrecoUnitario(resultado.getString("preco_unitario"));
				i.setDesconto(resultado.getString("desconto"));
				i.setValorTotal(resultado.getString("valor_total"));
				i.setDataCadastro(resultado.getDate("data_cadastro"));
				i.setDataAlteracao(resultado.getDate("data_alteracao"));
				itens.add(i);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return itens;
	}
	
	public static List<pedidoModel> resumoPedido(int codPedido){
		Connection conn = null;
		PreparedStatement query=null;
		ResultSet resultado=null;
		
		List <pedidoModel> pedido = new ArrayList <pedidoModel>();
		try {
			conn=conexao.getConnection();
			if(conn==null) return pedido;			
			
			String sql="select count(*) Quantidade, sum(i.quantidade) Volumes, sum(i.valor_total) Vlr_Total from pedidos p inner join itens_pedido i on p.id_pedido=i.id_pedido where p.id_pedido=?";
			query=conn.prepareStatement(sql);
			query.setInt(1, codPedido);
			 
			
			resultado = query.executeQuery();
			
			while(resultado.next()) {
				pedidoModel p = new pedidoModel(
						resultado.getInt("Quantidade"),
						resultado.getInt("Volumes"),
						resultado.getInt("Vlr_Total")	
						);
				p.setQuantidade(resultado.getInt("Quantidade"));
				p.setVolume(resultado.getInt("Volumes"));
				p.setValorTotal(resultado.getDouble("Vlr_Total"));
				pedido.add(p);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return pedido;
	}

	public static List<relatorioVendaModel> relatorioVenda(String desc, LocalDate dataInicio, LocalDate dataFinal){
		Connection conn = null;
		PreparedStatement query=null;
		ResultSet resultado=null;
		
		List <relatorioVendaModel> relatorio = new ArrayList <relatorioVendaModel>();
		try {
			String sqlBusca="";
			
			conn=conexao.getConnection();
			
			if(conn==null) return relatorio;	
			
			if(desc!=null && !desc.isEmpty()) {
				//sqlBusca="and (c.nome like ? or f.nome like ?)";
				
				sqlBusca="and ( (CASE WHEN IFNULL(p.id_cliente, 0) > 0 THEN CONCAT(p.id_cliente, '-', c.nome) ELSE 'Frente de Caixa' END) like ? or "+
						 "(CASE WHEN IFNULL(p.id_funcionario, 0) > 0 THEN CONCAT(p.id_cliente, '-', c.nome) ELSE 'Frente de Caixa' END) like ?)";
					}
			
			String sql = "SELECT p.id_pedido, DATE_FORMAT(p.data_pedido, '%d/%m/%Y') AS data_pedido, " +
                    "(CASE WHEN IFNULL(p.id_cliente, 0) > 0 THEN CONCAT(p.id_cliente, '-', c.nome) ELSE 'Frente de Caixa' END) AS cliente, " +
                    "(CASE WHEN IFNULL(p.id_funcionario, 0) > 0 THEN CONCAT(p.id_funcionario, '-', f.nome) ELSE 'Frente de Caixa' END) AS funcionario, " +
                    "(CASE WHEN p.valor_total > 0 THEN 'Fechado' ELSE 'Aberto' END) AS status, " +
                    "REPLACE(FORMAT(p.valor_pedido, 2), '.', ',') AS valor_pedido, " +
                    "REPLACE(FORMAT(p.desconto, 2), '.', ',') AS desconto, " +
                    "REPLACE(FORMAT(p.valor_total, 2), '.', ',') AS valor_total " +
                    "FROM pedidos p " +
                    "LEFT JOIN clientes c ON c.id_cliente = p.id_cliente " +
                    "LEFT JOIN funcionarios f ON f.id_funcionario = p.id_funcionario " +
                    "WHERE  (p.data_pedido BETWEEN ? AND ? )"+ sqlBusca;
			
			query=conn.prepareStatement(sql);
			query.setDate(1, java.sql.Date.valueOf(dataInicio));
			query.setDate(2, java.sql.Date.valueOf(dataFinal));
			
			if (sqlBusca!="") {
				query.setString(3, "%"+desc+"%");
				query.setString(4, "%"+desc+"%");				
			}
			 
			
			resultado = query.executeQuery();
			
			while (resultado.next()) {
	            relatorioVendaModel r = new relatorioVendaModel(
	            		resultado.getInt("id_pedido"),
	    	            resultado.getString("data_pedido"),
	    	            resultado.getString("cliente"),
	    	            resultado.getString("funcionario"),
	    	            resultado.getString("status"),
	    	            resultado.getString("valor_pedido"),
	    	            resultado.getString("desconto"),
	    	            resultado.getString("valor_total")
	            		);
	            r.setID(resultado.getInt("id_pedido"));
	            r.setData(resultado.getString("data_pedido"));
	            r.setCliente(resultado.getString("cliente"));
	            r.setVendedor(resultado.getString("funcionario"));
	            r.setStatus(resultado.getString("status"));
	            r.setVlrPedido(resultado.getString("valor_pedido"));
	            r.setDesconto(resultado.getString("desconto"));
	            r.setVlrTotal(resultado.getString("valor_total"));

	            relatorio.add(r);
	        }
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return relatorio;
		
	}

	public boolean atualizarPedido(int idPedido) {
		Connection conn=null;
		PreparedStatement query = null;
		try {
		conn=conexao.getConnection();
		String sql="update clientes set nome=?, cpf=?,telefone=?, email=?,"+
		" data_alteracao=now() where id_cliente=?";
		
		query=conn.prepareStatement(sql);
		/*
		query.setString(1, c.getNome());
		query.setString(2, c.getCpf());
		query.setString(3, c.getTelefone());
		query.setString(4, c.getEmail());
		query.setInt(5, c.getID());*/
		
		int update = query.executeUpdate();
		
		return update>0;
		
		}catch(Exception e ) {
			e.printStackTrace();
		return false;
		}
	}
}
