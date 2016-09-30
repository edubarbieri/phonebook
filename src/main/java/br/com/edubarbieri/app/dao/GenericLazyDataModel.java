package br.com.edubarbieri.app.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * 
 * @author Eduardo dos Santos Barbieri
 */
public class GenericLazyDataModel<T> extends LazyDataModel<T> {
	private static final long serialVersionUID = -208142309438498976L;
	private static final Logger LOG = Logger
			.getLogger(GenericLazyDataModel.class);
	private final Class<T> entityClass;
	private final String className;

	public GenericLazyDataModel(Class<T> entityClass) {
		this.entityClass = entityClass;
		this.className = entityClass.getName();
		LOG.debug("GenericLazyDataModel instanciado para classe " + className);
	}

	@Override
	public Object getRowKey(T object) {
		// Utiliza o hashCode para retornar o id dos registo
		// hashCode deve ser subscrito nos beans, adiciona logica com o id
		return object.hashCode();
	}

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		LOG.debug("GenericLazyDataModel_" + className + ": load - Enter");
		List<T> dados = null;
		Session session = HibernateUtil.getSession();
		try {
			// criteria para massa de dados
			Criteria resultCriteria = session.createCriteria(entityClass);
			// criteria para total de registros
			Criteria countCriteria = session.createCriteria(entityClass);
			// adiciona os parametros nas criterias
			for (Iterator<String> it = filters.keySet().iterator(); it
					.hasNext();) {
				String filterProperty = it.next();
				String filterValue = (String)filters.get(filterProperty);
				resultCriteria.add(Restrictions.ilike(filterProperty,
						filterValue, MatchMode.ANYWHERE));
				countCriteria.add(Restrictions.ilike(filterProperty,
						filterValue, MatchMode.ANYWHERE));
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
			this.setRowCount(((Number) countCriteria.setProjection(
					Projections.rowCount()).uniqueResult()).intValue());
			// seta o primeiro registro e a quantidade de registro na criteria
			// dos dados
			resultCriteria.setFirstResult(first);
			resultCriteria.setMaxResults(pageSize);

			dados = resultCriteria.list();
		} catch (Exception e) {
			LOG.error("GenericLazyDataModel_" + className + ": load - Error", e);
		} finally {
			session.close();
			LOG.debug("GenericLazyDataModel_" + className + ": load - Exit");
		}
		return dados;
	}

	// @Override
	// public List<T> load(int first, int pageSize, String sortField, SortOrder
	// sortOrder, Map<String, String> filters) {
	// LOG.debug("GenericLazyDataModel_" + className + ": load - Enter");
	// List<T> dados = null;
	// Session session = HibernateUtil.getSession();
	// try {
	// //criteria para massa de dados
	// Criteria resultCriteria = session.createCriteria(entityClass);
	// //criteria para total de registros
	// Criteria countCriteria = session.createCriteria(entityClass);
	// //adiciona os parametros nas criterias
	// for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
	// String filterProperty = it.next();
	// String filterValue = filters.get(filterProperty);
	// resultCriteria.add(Restrictions.ilike(filterProperty, filterValue,
	// MatchMode.ANYWHERE));
	// countCriteria.add(Restrictions.ilike(filterProperty, filterValue,
	// MatchMode.ANYWHERE));
	// }
	// //adiciona ordernaçao na criteria dos dados
	// if (sortField != null) {
	// if (sortOrder == SortOrder.ASCENDING) {
	// resultCriteria.addOrder(Order.asc(sortField));
	// } else if (sortOrder == SortOrder.DESCENDING) {
	// resultCriteria.addOrder(Order.desc(sortField));
	// }
	// }
	// //seta o total de registro como resultado da criteria de total de
	// registros
	// this.setRowCount(((Number)countCriteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
	// //seta o primeiro registro e a quantidade de registro na criteria dos
	// dados
	// resultCriteria.setFirstResult(first);
	// resultCriteria.setMaxResults(pageSize);
	//
	// dados = resultCriteria.list();
	// }catch(Exception e){
	// LOG.error("GenericLazyDataModel_" + className + ": load - Error", e);
	// }finally {
	// session.close();
	// LOG.debug("GenericLazyDataModel_" + className + ": load - Exit");
	// }
	// return dados;
	// }

}
