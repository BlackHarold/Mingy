package domain;

import java.sql.SQLException;
import java.util.Scanner;

import entity.OrderBook;
import logic.LogicMain;
import utils.HibernateUtil;

public class App extends HibernateUtil {

    static LogicMain main = new LogicMain();

    public static void main(String[] args) throws SQLException {

	Scanner scanner = new Scanner(System.in);

	while (true) {
	    String in = scanner.nextLine().toUpperCase();
	    if (in.equals("QUIT"))
		break;
	    String[] list = new String[4];
	    list = in.split(" ");

	    if (list.length == 4) {
		OrderBook ob = new OrderBook(list[0], Integer.parseInt(list[1]), Integer.parseInt(list[2]), list[3]);

		System.out.print("add ");
		for (String string : list) {
		    System.out.print(string + " ");
		}
		System.out.println();

		main.getDeal(ob);
	    } else {
		System.out.println("e.g.: GOOGLE 100 50 B");
	    }
	}
	if (session.isOpen()) {
	    session.close();
	}
	System.out.print("buybuy");
	scanner.close();

	System.exit(0);

    }
}
