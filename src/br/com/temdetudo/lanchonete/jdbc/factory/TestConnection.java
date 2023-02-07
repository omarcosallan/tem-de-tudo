package br.com.temdetudo.lanchonete.jdbc.factory;
public class TestConnection {

	public static void main(String[] args) {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.getConnection();
	}
}
