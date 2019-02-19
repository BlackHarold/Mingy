package logic;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Transaction;

import entity.OrderBook;
import utils.HibernateUtil;;

public class LogicMain extends HibernateUtil {

    Transaction tx;

    @SuppressWarnings({ "unchecked" })
    public void getDeal(OrderBook ob) throws SQLException {

	String forQuery = " WHERE deal = '" + dealType(ob.getDeal()) + "'";
	String sqlSelect = "from OrderBook" + forQuery;
	tx = session.getTransaction();

	tx.begin();
	List<OrderBook> result = session.createQuery(sqlSelect).list();
	// result.forEach((p) -> System.out.println(p.toString()));
	session.save(ob);
	tx.commit();

	if (!result.isEmpty()) {
	    // deals from min price
	    result.sort(Comparator.comparing(OrderBook::getPrice));
	    // result.forEach((p) -> System.out.println(p.toString()));

	    // calculations without profit !only quantity!
	    if (ob.getPrice() >= result.get(0).getPrice()) {
		int quantity = ob.getQuantity();

		while (ob.getQuantity() > 0 && !result.isEmpty()) {

		    quantity = quantity - result.get(0).getQuantity();

		    System.out.println("New execution with ID " + result.get(0).getId() + ": " + ob.getProduct() + " "
			    + result.get(0).getQuantity() + " @ " + ob.getQuantity() + "( orders "
			    + result.get(0).getId() + " AND " + ob.getId() + ")");

		    if (quantity > 0) {

			ob.setQuantity(quantity);
			tx.begin();
			session.update(ob);
			session.delete(result.get(0));
			tx.commit();

			result.remove(0);

		    } else if (quantity < 0) {

			result.get(0).setQuantity(Math.abs(quantity));

			tx.begin();
			session.update(result.get(0));
			session.delete(ob);
			tx.commit();
			break;

		    } else {

			tx.begin();
			session.delete(result.get(0));
			session.delete(ob);
			tx.commit();

			result = null;
			ob = null;
		    }
		}
	    } else if (ob.getPrice() < result.get(0).getPrice()) {

		int quantity = result.get(0).getQuantity();

		while (quantity > 0 && !result.isEmpty()) {

		    quantity = quantity - ob.getQuantity();

		    System.out.println("New execution with ID " + result.get(0).getId() + ": " + ob.getProduct() + " "
			    + result.get(0).getQuantity() + " @ " + ob.getQuantity() + "( orders "
			    + result.get(0).getId() + " AND " + ob.getId() + ")");

		    if (quantity > 0) {

			result.get(0).setQuantity(quantity);

			tx.begin();
			session.update(result.get(0));
			session.delete(ob);
			tx.commit();
			break;

		    } else if (quantity < 0) {

			ob.setQuantity(Math.abs(quantity));

			tx.begin();
			session.update(ob);
			session.delete(result.get(0));
			tx.commit();

			result.remove(0);

		    } else {

			tx.begin();
			session.delete(result.get(0));
			session.delete(ob);
			tx.commit();
		    }
		}

	    }
	} else {
	    session.save(ob);

	}

	if (session.isOpen()) {
	    session.clear();
	}
    }

    private String dealType(String obDeal) {
	if (obDeal.equals("B")) {
	    return "S";
	} else {
	    return "B";
	}

    }

}
