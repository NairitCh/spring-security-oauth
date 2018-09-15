package com.spring.security.oauth2.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import com.spring.security.oauth2.model.Company;
import com.spring.security.oauth2.model.Department;
import com.spring.security.oauth2.model.Employee;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
   /* @Autowired
    Company company_;
    
    @Autowired
    Employee employee_;
    
    @Autowired
    Department department_;*/

    Company company = new Company();
    
    @Override
    public Company find(Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = builder.createQuery(Company.class);

        Root<Company> root = query.from(Company.class);
        /*root.fetch(Company.cars, JoinType.LEFT);
        Fetch<Company, Department> departmentFetch = root.fetch(company_.departments, JoinType.LEFT);
        Fetch<Department, Employee> employeeFetch = departmentFetch.fetch(department_.employees, JoinType.LEFT);
        employeeFetch.fetch(employee_.address, JoinType.LEFT);
        departmentFetch.fetch(department_.offices, JoinType.LEFT);*/

        query.select(root).distinct(true);
        /*Predicate idPredicate = builder.equal(root.get(company_.id), id);
        query.where(builder.and(idPredicate));*/

        return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
    }

    @Override
    public Company find(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = builder.createQuery(Company.class);

        Root<Company> root = query.from(Company.class);
        /*root.fetch(company_.cars, JoinType.LEFT);
        Fetch<Company, Department> departmentFetch = root.fetch(company_.departments, JoinType.LEFT);
        Fetch<Department, Employee> employeeFetch = departmentFetch.fetch(department_.employees, JoinType.LEFT);
        employeeFetch.fetch(employee_.address, JoinType.LEFT);
        departmentFetch.fetch(department_.offices, JoinType.LEFT);*/

        query.select(root).distinct(true);
        Predicate idPredicate = builder.equal(root.get(company.name), name);
        query.where(builder.and(idPredicate));

        return DataAccessUtils.singleResult(entityManager.createQuery(query).getResultList());
    }

    @Override
    public List<Company> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = builder.createQuery(Company.class);
        Root<Company> root = query.from(Company.class);
        query.select(root).distinct(true);
        TypedQuery<Company> allQuery = entityManager.createQuery(query);

        return allQuery.getResultList();
    }

    @Override
    public void create(Company company) {
        entityManager.persist(company);
    }

    @Override
    public Company update(Company company) {
        return entityManager.merge(company);
    }

    @Override
    public void delete(Long id) {
        Company company = entityManager.find(Company.class, id);
        delete(company);
    }

    @Override
    public void delete(Company company) {
        entityManager.remove(company);
    }
}
