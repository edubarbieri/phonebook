package br.com.edubarbieri.app.dao;

import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/**
 *
 * @author Eduardo dos Santos Barbieri
 */
public class GenericDAO<T> implements Serializable {
	private static final long serialVersionUID = -3714054978170495654L;
	private static Logger LOG = Logger.getLogger(GenericDAO.class);
    private final Class<T> entityClass;
    private String className;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.className = entityClass.getName();
        LOG.debug("GenericDAO Instanciado para classe " + className);
    }

    public void saveOrUpdate(T object) throws Exception {
        LOG.debug("GenericDAO_" + className + ":saveOrUpdate - Enter");
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.saveOrUpdate(object);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        LOG.debug("GenericDAO_" + className + ":saveOrUpdate - Exit");
    }

    public void delete(Integer id) {
        LOG.debug("GenericDAO_" + className + ": delete - Enter");
        Session session = HibernateUtil.getSession();
        try {
            Transaction tx = session.getTransaction();
            tx.begin();
            session.delete(session.get(entityClass, id));
            tx.commit();
        } finally {
            session.close();
        }
        LOG.debug("GenericDAO_" + className + ": delete - Exit");
    }

    public T get(Integer id) {
        LOG.debug("GenericDAO_" + className + ": get - Enter");
        Session session = HibernateUtil.getSession();
        try {
            return (T) session.get(entityClass, id);
        } finally {
            session.close();
            LOG.debug("GenericDAO_" + className + ": get - Exit");
        }
    }

    public List<T> listOrderAsc(String fieldOrder) throws Exception {
        LOG.debug("GenericDAO_" + className + ": listOrderAsc(String fieldOrder) - Enter");
        Session session = HibernateUtil.getSession();
        try {
            return session.createCriteria(entityClass).addOrder(Order.asc(fieldOrder)).list();
        } finally {
            session.close();
            LOG.debug("GenericDAO_" + className + ": listOrderAsc(String fieldOrder) - Exit");
        }
    }

    public List<T> listOrderDesc(String fieldOrder) throws Exception {
        LOG.debug("GenericDAO_" + className + ": listOrderDesc(String fieldOrder) - Enter");
        Session session = HibernateUtil.getSession();
        try {
            return session.createCriteria(entityClass)
                    .addOrder(Order.desc(fieldOrder)).list();
        } finally {
            session.close();
            LOG.debug("GenericDAO_" + className + ": listOrderDesc(String fieldOrder) - Exit");
        }
    }
}
