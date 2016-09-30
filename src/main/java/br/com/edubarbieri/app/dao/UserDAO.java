package br.com.edubarbieri.app.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.edubarbieri.app.bean.User;

/**
 *
 * @author Eduardo dos Santos Barbieri
 */
public class UserDAO extends GenericDAO<User>{
	private static final long serialVersionUID = 3652235393807419536L;


	public UserDAO() {
        super(User.class);
    }

    public User get(String userName){
        Session session = HibernateUtil.getSession();
        try {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq(User.USERNAME, userName));            
            return (User) criteria.uniqueResult();
        } finally {
            session.close();
        }
    }

}
