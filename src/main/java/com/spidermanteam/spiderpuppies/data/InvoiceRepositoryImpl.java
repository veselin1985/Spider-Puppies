package com.spidermanteam.spiderpuppies.data;

import com.spidermanteam.spiderpuppies.data.base.GenericRepository;
import com.spidermanteam.spiderpuppies.data.base.InvoiceRepository;
import com.spidermanteam.spiderpuppies.models.Invoice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {


  private SessionFactory sessionFactory;

  @Autowired
  public InvoiceRepositoryImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }


  @Override
  public void create(Invoice invoice) {
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.save(invoice);
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  @Override
  public Invoice findById(int id) {
    Invoice invoice = new Invoice();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      invoice = session.get(Invoice.class, id);
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return invoice;
  }

  @Override
  public Invoice findByIdAndClientId(int id, int clientId) {
    Invoice invoice = new Invoice();
    List<Invoice> invoiceList = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      String query = "from Invoice as i where i.id=:id and i.subscriber.client.id=:clientId";
      invoiceList = session.createQuery(query)
          .setParameter("clientId", clientId)
          .setParameter("id", id).list();
      invoice = invoiceList.get(0);
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return invoice;
  }

  @Override
  public List<Invoice> listAll() {
    List<Invoice> invoices = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      invoices = session.createQuery("from Invoice ").list();
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return invoices;
  }

  @Override
  public void update(Invoice invoice) {
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(invoice);
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  @Override
  public void delete(int id) {
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      Invoice invoice = session.get(Invoice.class, id);
      session.delete(invoice);
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  @Override
  public List<Invoice> findInvoicesByPhone(String phone) {
    List<Invoice> invoiceList = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      String query = "from Invoice as i where i.subscriber.phone=:phoneNum";
      invoiceList = session.createQuery(query)
          .setParameter("phoneNum", phone).list();
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return invoiceList;
  }

  @Override
  public List<Invoice> findInvoicesByPhoneAndClientId(String phone, int clientId) {
    List<Invoice> invoiceList = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      String query = "from Invoice as i where i.subscriber.phone=:phoneNum and i.subscriber.client.id=:clientId";
      invoiceList = session.createQuery(query)
          .setParameter("phoneNum", phone)
          .setParameter("clientId", clientId).list();
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return invoiceList;
  }

  @Override
  public List<Invoice> findAllPendingInvoicesByClientId(int id) {
    List<Invoice> invoiceList = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      String query = "from Invoice as i where i.subscriber.client.id=:clientId and i.status=:status";
      invoiceList = session.createQuery(query)
          .setParameter("clientId", id)
          .setParameter("status", "0").list();
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return invoiceList;
  }

  @Override
  public List<Invoice> findAllInvoicesByClientId(int id) {
    List<Invoice> invoiceList = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      String query = "from Invoice as i where i.subscriber.client.id=:clientId";
      invoiceList = session.createQuery(query)
          .setParameter("clientId", id).list();
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return invoiceList;
  }

  @Override
  public List<Invoice> findLastTenPaymentsBySubscriberId(int id) {
    List invoiceList = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();

      invoiceList = session.createQuery("from Invoice as i " +
          "where i.status=:status " +
          "and i.subscriber.id=:id " +
          "order by i.paymentDate desc")
          .setParameter("status", "1")
          .setParameter("id", id)
          .setMaxResults(10)
          .list();
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return invoiceList;
  }

  @Override
  public List<Invoice> findLastTenPaymentsByClientId(int clientId) {
    List invoiceList = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();

      invoiceList = session.createQuery("from Invoice as i " +
          "where i.status=:status " +
          "and i.subscriber.client.id=:id " +
          "order by i.paymentDate desc")
          .setParameter("status", "1")
          .setParameter("id", clientId)
          .setMaxResults(10)
          .list();
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return invoiceList;
  }

  @Override
  public List<Invoice> findLastTenPayments() {
    List invoiceList = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();

      invoiceList = session.createQuery("from Invoice as i " +
          "where i.status=:status " +
          "order by i.paymentDate desc")
          .setParameter("status", "1")
          .setMaxResults(10)
          .list();
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return invoiceList;
  }

  @Override
  public List<Invoice> findDueInvoicesByPhone(String phone) {
    {
      List<Invoice> invoiceList = new ArrayList<>();
      try (Session session = sessionFactory.openSession()) {
        session.beginTransaction();
        String query = "from Invoice as i where i.subscriber.phone=:phoneNum and i.status=:status";
        invoiceList = session.createQuery(query)
            .setParameter("phoneNum", phone)
            .setParameter("status", "0").list();
        session.getTransaction().commit();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      return invoiceList;
    }
  }
}
