package com.jtechy.hibernate.main;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.jtechy.hibernate.model.Employee;
import com.jtechy.hibernate.util.HibernateUtil;

public class HibernateMain {

	public static void main(String[] args) {
		Employee emp = new Employee();
		emp.setName("Ganesh");
		emp.setRole("Developer");
		emp.setInsertTime(new Date());

		// Get Session
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		// start transaction
		session.beginTransaction();
		// Save the Model object
		session.save(emp);
		
		emp.setName("Ramesh");

		// Criteria example
		Query query = session.createQuery("from Employee");
		List<Employee> empList = query.list();
		for (Employee emp1 : empList) {
			System.out.println("List of Employees::" + emp1.getId() + "," + emp1.getName());
		}

		// HQL example - Get Employee with id
		query = session.createQuery("from Employees where id= :id");
		query.setLong("id", 2);
		Employee emp2 = (Employee) query.uniqueResult();
		System.out.println("Employee Name=" + emp2.getName());

		// Get with ID, creating new Criteria to remove all the settings
		Criteria  criteria = session.createCriteria(Employee.class).add(Restrictions.eq("name", "test")).setFirstResult(21).setMaxResults(30);
		Employee emp3 = (Employee) criteria.uniqueResult();
		System.out.println("Name=" + emp3.getName());

		// Commit transaction
		session.getTransaction().commit();

		// terminate session factory, otherwise program won't end
		HibernateUtil.getSessionFactory().close();
	}

}
