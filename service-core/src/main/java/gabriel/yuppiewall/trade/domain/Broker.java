package gabriel.yuppiewall.trade.domain;

public interface Broker {

	OrderStatus placeOrder(Order order);
}
