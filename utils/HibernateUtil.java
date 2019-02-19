package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    protected static SessionFactory sessionFactory;
    protected static Session session = setUp();

    protected static Session setUp() {
	// A SessionFactory is set up once for an application!
	StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
	
	sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	Session session = sessionFactory.openSession();
	    return session;

    }
}
