package com.products.database;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ProductDAO extends GenericHibernateDAO<Product, Integer> {
	
	public List<Product> getProductListByCriteria(String searchDescription,
			Double searchMinPrice, Double searchMaxPrice){
		
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = null;
		List<Product> productList = null;

	    try {
			transaction = session.beginTransaction();
			Criteria crit = session.createCriteria(Product.class);
			
			// Define Search HQL:
			if (searchDescription != null && searchDescription.length() > 0)
			{	// searchValue is set:
				searchDescription = "%"+searchDescription+"%";
				crit.add(Restrictions.ilike("details", searchDescription ));
			}

			if (( searchMinPrice != null) && ( searchMaxPrice != null)) {		
					crit.add(Restrictions.between("price", searchMinPrice, searchMaxPrice));			
				}
			else 	if( searchMinPrice != null) {
					crit.add(Restrictions.ge("price", searchMinPrice));			
					}
			else if ( searchMaxPrice != null) {		
					crit.add(Restrictions.le("price", searchMaxPrice));			
			}

			productList = crit.list();
		
			transaction.commit(); 
			
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
	    return productList;
	}

	public void deleteByCategoryId(int categoryId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try
		{
			session.beginTransaction();
			Query query = session.createQuery("delete Product where categoryId = :categoryId");
			query.setParameter("categoryId", categoryId);
			query.executeUpdate();
			session.getTransaction().commit();
		}
		catch (HibernateException e)
		{
			//log.error("Hibernate Exception" + e.getMessage());
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}
}
