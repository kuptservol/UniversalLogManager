package ru.skuptsov.logviewer.persistance.dao.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ru.skuptsov.logviewer.model.LogMessage;
import ru.skuptsov.logviewer.persistance.dao.LogMessageDAO;

public class HibernateTemplateDao extends HibernateDaoSupport implements
		LogMessageDAO {

	@Override
	public boolean save(Object object) throws Exception {
		try {
			getHibernateTemplate().save(object);
			return true;
		} catch (Exception e) {
			//TODO: Exception
			logger.error(e);

		}
		return false;
	}

	@Override
	public boolean delete(Object object) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
