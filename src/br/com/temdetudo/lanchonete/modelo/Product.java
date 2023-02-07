package br.com.temdetudo.lanchonete.modelo;

import java.util.Objects;

import br.com.temdetudo.lanchonete.modelo.enums.Category;

public class Product {

	private int id;
	private String name;
	private Double price;
	private Category category;
	private static int total;

	public Product(String name, Double price, Category category) {
		if (price <= 0.0) {
			throw new IllegalStateException("Preço não pode ser menor ou igual a 0.");
		}
		if ((name == null || name.equals("")) || price == null || category == null) {
			throw new IllegalStateException("Erro ao instanciar! Valores inválidos.");
		}
		this.name = name.toUpperCase().trim();
		this.price = price;
		this.category = category;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public Category getCategory() {
		return category;
	}

	public static int getTotal() {
		return total;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, name, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return category == other.category && Objects.equals(name, other.name) && Objects.equals(price, other.price);
	}

	@Override
	public String toString() {
		return name + ", " + price;
	}
}
