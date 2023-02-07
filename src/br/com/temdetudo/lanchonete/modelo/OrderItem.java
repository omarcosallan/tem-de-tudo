package br.com.temdetudo.lanchonete.modelo;

import java.text.NumberFormat;
import java.util.Locale;

public class OrderItem {

	private Product product;
	private Integer quantity;

	public OrderItem(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Double getTotal() {
		return this.quantity * product.getPrice();
	}

	@Override
	public String toString() {
		return this.product.getName() + ", " + this.quantity + " unidades, Valor: "
				+ NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(this.getTotal());
	}
}
