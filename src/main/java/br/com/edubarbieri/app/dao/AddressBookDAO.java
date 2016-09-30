package br.com.edubarbieri.app.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.edubarbieri.app.bean.Address;
import br.com.edubarbieri.app.bean.AddressPhone;
import br.com.edubarbieri.app.util.JSFUtil;

public class AddressBookDAO extends LazyDataModel<Address> implements Serializable {
	private static final Logger LOG = Logger.getLogger(AddressBookDAO.class);
	private static final long serialVersionUID = 1693274399061213225L;

	private String filter;

	public AddressBookDAO() {
	}

	public void saveOrUpdate(Address address) throws Exception {
		LOG.debug("Call saveOrUpdate - Enter");
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(address);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
		LOG.debug("Call saveOrUpdate - Exit");
	}

	public void delete(Integer id) {
		LOG.debug("Call delete - Enter");
		Session session = HibernateUtil.getSession();
		try {
			Transaction tx = session.getTransaction();
			tx.begin();
			session.delete(session.get(Address.class, id));
			tx.commit();
		} finally {
			session.close();
		}
		LOG.debug("Call delete - Exit");
	}

	@Override
	public Object getRowKey(Address object) {
		return object.getId();
	}

	@Override
	public List<Address> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		LOG.debug("Call load - Enter");
		List<Address> dados = null;
		Session session = HibernateUtil.getSession();
		try {
			// criteria para massa de dados
			Criteria resultCriteria = session.createCriteria(Address.class);
			// criteria para total de registros
			Criteria countCriteria = session.createCriteria(Address.class);
			// adiciona os parametros nas criterias
			for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
				String filterProperty = it.next();
				String filterValue = filters.get(filterProperty).toString();
				resultCriteria.add(Restrictions.ilike(filterProperty, filterValue, MatchMode.ANYWHERE));
				countCriteria.add(Restrictions.ilike(filterProperty, filterValue, MatchMode.ANYWHERE));
			}

			/* Filtro da tela */
			if (filter != null && !filter.equals("")) {
				List<Integer> idAddressPhone = session.createCriteria(AddressPhone.class)
						.setProjection(Projections.projectionList().add(Projections.property("address.id")))
						.add(Restrictions.ilike("name", filter, MatchMode.ANYWHERE)).list();
				
				if(idAddressPhone != null && idAddressPhone.size() > 0){
					resultCriteria.add(Restrictions.disjunction().add(Restrictions.ilike("name", filter, MatchMode.ANYWHERE))
							.add(Restrictions.in("id", idAddressPhone)));
					countCriteria.add(Restrictions.disjunction().add(Restrictions.ilike("name", filter, MatchMode.ANYWHERE))
							.add(Restrictions.in("id", idAddressPhone)));
				}else{
					resultCriteria.add(Restrictions.ilike("name", filter, MatchMode.ANYWHERE));
					countCriteria.add(Restrictions.ilike("name", filter, MatchMode.ANYWHERE));
				}
				

			}

			// adiciona ordernaçao na criteria dos dados
			if (sortField != null) {
				if (sortOrder == SortOrder.ASCENDING) {
					resultCriteria.addOrder(Order.asc(sortField));
				} else if (sortOrder == SortOrder.DESCENDING) {
					resultCriteria.addOrder(Order.desc(sortField));
				}
			}
			// seta o total de registro como resultado da criteria de total de
			// registros
			this.setRowCount(((Number) countCriteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
			// seta o primeiro registro e a quantidade de registro na criteria
			// dos dados
			resultCriteria.setFirstResult(first);
			resultCriteria.setMaxResults(pageSize);

			dados = resultCriteria.list();
		} catch (Exception e) {
			LOG.error("load - Error", e);
			JSFUtil.addErrorMessage("Erro", "Erro ao realizar pesquisa!");
		} finally {
			session.close();
		}
		return dados;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

}
