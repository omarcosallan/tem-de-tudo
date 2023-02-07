package br.com.temdetudo.lanchonete.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.temdetudo.lanchonete.modelo.Product;
import br.com.temdetudo.lanchonete.modelo.enums.Category;

public class ProdutoDAO {

	private Connection connection;

	public ProdutoDAO(Connection connection) {
		this.connection = connection;
	}

	public Set<Product> list() {
		Set<Product> products = new HashSet<>();
		try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM produtos")) {
			pstm.execute();
			try (ResultSet result = pstm.getResultSet()) {
				while (result.next()) {
					Product product = new Product(result.getString(2), result.getDouble(3),
							Category.valueOf(result.getString(4)));
					product.setId(result.getInt(1));
					products.add(product);
				}
			}
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void save(Product product) {
		String sql = "INSERT INTO produtos (produto, valor, categoria) VALUES (?, ?, ?)";
		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setString(1, product.getName());
			pstm.setDouble(2, product.getPrice());
			pstm.setString(3, product.getCategory().toString());
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(int id) {
		try (PreparedStatement pstm = connection.prepareStatement("DELETE FROM produtos WHERE ID = ?")) {
			pstm.setInt(1, id);
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(Product product) {
		try (PreparedStatement pstm = connection
				.prepareStatement("UPDATE produtos SET produto = ?, valor = ?, categoria = ? WHERE ID = ?")) {
			pstm.setString(1, product.getName());
			pstm.setDouble(2, product.getPrice());
			pstm.setString(3, product.getCategory().toString());
			pstm.setInt(4, product.getId());

			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
