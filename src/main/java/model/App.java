package model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Maven + Hibernate + HSQL");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        App app = new App();
        app.saveContact("One");
        app.saveContact("Two");
        app.saveContact("Three");
        app.listContact();
    }

    public Integer saveContact(String contactName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Integer contactId = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Contact contact = new Contact();
            contact.setName(contactName);
            contactId = (Integer) session.save(contact);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return contactId;
    }

    public void listContact() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            @SuppressWarnings("unchecked")
            List<Contact> contactList = session.createQuery("from Contact").list();
            for (Iterator<Contact> iterator = contactList.iterator(); iterator.hasNext(); ) {
                Contact contact = (Contact) iterator.next();
                System.out.println(contact.getName());
            }

            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}