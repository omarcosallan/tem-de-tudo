package br.com.temdetudo.lanchonete.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.temdetudo.lanchonete.modelo.Order;
import br.com.temdetudo.lanchonete.modelo.OrderItem;
import br.com.temdetudo.lanchonete.modelo.Product;
import br.com.temdetudo.lanchonete.modelo.enums.Category;

public class TestOrder {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		List<Product> products = new ArrayList<>();
		products.add(new Product("Pizza de Frango Catupiry", 37.0, Category.PIZZA));
		products.add(new Product("Pizza de Calabresa com Catupiry", 34.0, Category.PIZZA));
		products.add(new Product("Pizza Portuguesa", 37.0, Category.PIZZA));
		products.add(new Product("Pastel de Frango Catupiry", 2.5, Category.PASTEL));
		products.add(new Product("Pastel de Queijo", 2.0, Category.PASTEL));
		
		System.out.println("Lanches disponíveis");
		products.forEach(p -> System.out.println(p));
		
		System.out.print("\nQuantos produtos deseja comprar? ");
		int qtdd = sc.nextInt();
		System.out.println("=============================");
		
		Order order = new Order();
		for (int i = 0; i < qtdd; i++) {
			System.out.print("Informe o código do Lanche: ");
			Product product = products.get(sc.nextInt() - 1);
			System.out.println(product);
			System.out.print("Quantos deseja comprar? ");
			int quantidade = sc.nextInt();
			order.addItem(new OrderItem(product, quantidade));
			System.out.println();
		}
		
		System.out.println(order);
		
		sc.close();
	}
}
