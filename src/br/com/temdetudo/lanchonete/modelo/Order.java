package br.com.temdetudo.lanchonete.modelo;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.temdetudo.lanchonete.modelo.enums.OrderStatus;

public class Order {

	private LocalDateTime moment;
	private OrderStatus status;

	private List<OrderItem> orderItem = new ArrayList<>();

	public Order() {
		this.moment = LocalDateTime.now();
		this.status = OrderStatus.PENDING_PAYMENT;
	}
	
	public void addItem(OrderItem item) {
		orderItem.add(item);
	}

	public void removeItem(OrderItem item) {
		orderItem.remove(item);
	}

	public Double total() {
		return orderItem.stream().mapToDouble(OrderItem::getTotal).sum();
	}
	
	public boolean makeOrder() {
		return true;
	}

	@Override
	public String toString() {
		return "<< Resumo do Pedido >>\n"
				+ "Horário: "
				+ this.moment.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
				+ "\nStatus: " + this.status
				+ "\nItens:\n"+ printItens()
				+ "\nValor total ........................................."
				+ NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(this.total());
	}
	
	private StringBuilder printItens() {
		StringBuilder itens = new StringBuilder();
		this.orderItem.forEach(p -> {
			itens.append(p + "\n");
		});
		return itens;
	}
}
