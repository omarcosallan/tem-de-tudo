package br.com.temdetudo.lanchonete.controller;

import java.sql.Connection;
import java.util.Set;

import br.com.temdetudo.lanchonete.jdbc.dao.ProdutoDAO;
import br.com.temdetudo.lanchonete.jdbc.factory.ConnectionFactory;
import br.com.temdetudo.lanchonete.modelo.Product;

public class ProductController {

	private Connection connection;
	private ProdutoDAO produtoDAO;

	public ProductController() {
		connection = new ConnectionFactory().getConnection();
		produtoDAO = new ProdutoDAO(connection);
	}

	public Set<Product> listar() {
		return produtoDAO.list();
	}

	public void delete(Integer id) {
		produtoDAO.delete(id);
	}

	public void update(Product product) {
		produtoDAO.update(product);
	}

	public void save(Product produto) {
		produtoDAO.save(produto);
	}
}
