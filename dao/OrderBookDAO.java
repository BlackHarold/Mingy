package dao;

import java.util.List;

import entity.OrderBook;

public interface OrderBookDAO {

    void add(OrderBook ob);

    List<OrderBook> getAll();

    OrderBook getById(int id);

    void update(OrderBook ob);

    void remove(OrderBook ob);
}
