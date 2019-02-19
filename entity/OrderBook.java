package entity;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class OrderBook implements Comparator<OrderBook> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 10)
    private String product;
    @Column
    private int price;
    @Column
    private int quantity;
    @Column(length = 1)
    private String deal;

    public OrderBook() {
    }

    public OrderBook(String product, int price, int quantity, String deal) {
	this.product = product;
	this.price = price;
	this.quantity = quantity;
	this.deal = deal;
    }

    public OrderBook(int id, String product, int price, int quantity, String deal) {
	this.id = id;
	this.product = product;
	this.price = price;
	this.quantity = quantity;
	this.deal = deal;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getProduct() {
	return product;
    }

    public void setProduct(String product) {
	this.product = product;
    }

    public int getPrice() {
	return price;
    }

    public void setPrice(int price) {
	this.price = price;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    public String getDeal() {
	return deal;
    }

    public void setDeal(String deal) {
	this.deal = deal;
    }

    @Override
    public String toString() {
	return "OrderBook [id=" + id + ", product=" + product + ", price=" + price + ", quantity=" + quantity
		+ ", deal=" + deal + "]";
    }

    @Override
    public int compare(OrderBook o1, OrderBook o2) {
	int ob1price = o1.getPrice();
	int ob2price = o2.getPrice();

	if (ob1price > ob2price) {
	    return 1;
	} else if (ob1price < ob2price) {
	    return -1;
	} else {
	    return 0;
	}
    }
}
