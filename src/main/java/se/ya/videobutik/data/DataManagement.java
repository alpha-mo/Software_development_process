package se.ya.videobutik.data;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import se.ya.videobutik.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class DataManagement {

    private SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Actor.class)
            .addAnnotatedClass(Address.class)
            .addAnnotatedClass(Customer.class)
            .addAnnotatedClass(Film.class)
            .addAnnotatedClass(Staff.class)
            .addAnnotatedClass(Store.class)
            .addAnnotatedClass(City.class)
            .addAnnotatedClass(Country.class)
            .addAnnotatedClass(Language.class)
            .addAnnotatedClass(Payment.class)
            .addAnnotatedClass(Rental.class)
            .addAnnotatedClass(Inventory.class)
            .addAnnotatedClass(FilmActor.class)
            .addAnnotatedClass(FilmCategory.class)
            .addAnnotatedClass(Category.class)
            .buildSessionFactory();
    private Session session = null;

    public void setData(Object object){

        try {
            session = factory.openSession();
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }

    public void setData(Object[] objectData){

        try {
            session = factory.openSession();
            session.beginTransaction();
            NativeQuery<?> nativeQuery = session.createSQLQuery("CALL sakila.addNewAddress(?, ?, ?, ?, ?)")
                    .addEntity(Address.class)
                    .setParameter(1, objectData[0])
                    .setParameter(2, objectData[1])
                    .setParameter(3, objectData[2])
                    .setParameter(4, objectData[3])
                    .setParameter(5, objectData[4]);
            nativeQuery.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
    public void setAddress(){

        try {
            session = factory.openSession();
            session.beginTransaction();
            NativeQuery<?> nativeQuery = session.createNativeQuery("insert into sakila.address(address, district,city_id, postal_code,phone, location, last_update)\n" +
                            "values('Hermelinvägen 15', 'Västernorrland', 601, '86232', '0730972488', (ST_GeomFromText('point(17.36316 62.28842)')),current_timestamp());")

           .addEntity(Address.class);

            nativeQuery.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }

    }
    public void createAddress(Object [] objectData){

        try {
            session = factory.openSession();
            session.beginTransaction();
            NativeQuery<?> nativeQuery = session.createNativeQuery("insert into sakila.address(address, district,city_id, postal_code,phone, location, last_update)\n" +
                            "values(?, ?, ?, ?, ?, (ST_GeomFromText('point(17.36316 62.28842)')),current_timestamp());")
                    .setParameter(1, objectData[0])
                    .setParameter(2, objectData[1])
                    .setParameter(3, objectData[2])
                    .setParameter(4, objectData[3])
                    .setParameter(5, objectData[4])

                    .addEntity(Address.class);

            nativeQuery.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }

    }

    public Object getData(Class<?> tableClass, int id){
        Object output = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            output = session.find(tableClass, id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
        return output;
    }

    public Object getAddress(Class<?> tableClass, int id){
        Object output = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            NativeQuery nativeQuery = session.createNativeQuery("SELECT ST_X(location) AS Latitude, ST_Y(location) AS Longitude FROM sakila.address WHERE address_id = ?");
           nativeQuery.setParameter(1,id);
            output=nativeQuery.getSingleResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
        return output;
    }

    public void removeData(Object object){
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }

    public void updateData(Object object){
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }

    public Collection<Object> getDataList(Class<?> tableClass){

        String queryString = "";
        switch (tableClass.getSimpleName().toLowerCase(Locale.ROOT)){
            case "actor" -> queryString = "SELECT * FROM actor";
            case  "address" -> queryString = "SELECT * FROM address";
            case  "customer" -> queryString = "SELECT * FROM customer";
            case  "film" -> queryString = "SELECT * FROM film";
            case  "staff" -> queryString = "SELECT * FROM staff";
            case  "store" -> queryString = "SELECT * FROM store";
        }

        Collection<Object> outputList = new ArrayList<>();

        try {
            session = factory.openSession();
            session.beginTransaction();
            outputList.addAll(session.createNativeQuery(queryString, tableClass).getResultList());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
        return outputList;
    }

    public Collection<Object> getDataList(Class<?> tableClass, String parameterText){

        String queryString = "";

        switch (tableClass.getSimpleName().toLowerCase(Locale.ROOT)){
            case "actor" -> queryString = "SELECT * FROM actor WHERE actor.last_name LIKE '" + parameterText + "%'";
            case "address" -> queryString = "SELECT * FROM address WHERE address.address LIKE '" + parameterText + "%'";
            case "customer" -> queryString = "SELECT * FROM customer WHERE last_name LIKE '" + parameterText + "%'";
            case "film" -> queryString = "SELECT * FROM film WHERE film.title LIKE '" + parameterText + "%'";
            case "staff" -> queryString = "SELECT * FROM staff WHERE staff.last_name LIKE '" + parameterText + "%'";
        }

        Collection<Object> outputList = new ArrayList<>();

        try {
            session = factory.openSession();
            session.beginTransaction();
            outputList.addAll(session.createNativeQuery(queryString, tableClass).getResultList());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
        return outputList;
    }
}
