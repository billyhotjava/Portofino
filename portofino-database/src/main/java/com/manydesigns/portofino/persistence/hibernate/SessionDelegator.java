package com.manydesigns.portofino.persistence.hibernate;

import org.hibernate.*;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.persister.entity.EntityPersister;

import java.io.Serializable;

public class SessionDelegator extends SessionDelegatorBaseImpl {
    private HibernateDatabaseSetup setup;

    public SessionDelegator(HibernateDatabaseSetup setup, Session session) {
        super((SessionImplementor) session);
        this.setup = setup;
    }

    @Override
    public EntityPersister getEntityPersister(String entityName, Object object) throws HibernateException {
        return super.getEntityPersister(setup.translateEntityNameFromJpaToHibernate(entityName), object);
    }

    @Override
    public Object get(String entityName, Serializable id) {
        return super.get(setup.translateEntityNameFromJpaToHibernate(entityName), id);
    }

    @Override
    public Object get(String entityName, Serializable id, LockMode lockMode) {
        return super.get(setup.translateEntityNameFromJpaToHibernate(entityName), id, lockMode);
    }

    @Override
    public Object get(String entityName, Serializable id, LockOptions lockOptions) {
        return super.get(setup.translateEntityNameFromJpaToHibernate(entityName), id, lockOptions);
    }

    @Override
    public Object load(String entityName, Serializable id, LockOptions lockOptions) {
        return super.load(setup.translateEntityNameFromJpaToHibernate(entityName), id, lockOptions);
    }

    @Override
    public Object load(String entityName, Serializable id) {
        return super.load(setup.translateEntityNameFromJpaToHibernate(entityName), id);
    }

    @Override
    public void replicate(String entityName, Object object, ReplicationMode replicationMode) {
        super.replicate(setup.translateEntityNameFromJpaToHibernate(entityName), object, replicationMode);
    }

    @Override
    public Serializable save(String entityName, Object object) {
        return super.save(setup.translateEntityNameFromJpaToHibernate(entityName), object);
    }

    @Override
    public void saveOrUpdate(String entityName, Object object) {
        super.saveOrUpdate(setup.translateEntityNameFromJpaToHibernate(entityName), object);
    }

    @Override
    public void update(String entityName, Object object) {
        super.update(setup.translateEntityNameFromJpaToHibernate(entityName), object);
    }

    @Override
    public Object merge(String entityName, Object object) {
        return super.merge(setup.translateEntityNameFromJpaToHibernate(entityName), object);
    }

    @Override
    public void persist(String entityName, Object object) {
        super.persist(setup.translateEntityNameFromJpaToHibernate(entityName), object);
    }

    @Override
    public void delete(String entityName, Object object) {
        super.delete(setup.translateEntityNameFromJpaToHibernate(entityName), object);
    }

    @Override
    public void lock(String entityName, Object object, LockMode lockMode) {
        super.lock(setup.translateEntityNameFromJpaToHibernate(entityName), object, lockMode);
    }

    @Override
    public void refresh(String entityName, Object object) {
        super.refresh(setup.translateEntityNameFromJpaToHibernate(entityName), object);
    }

    @Override
    public void refresh(String entityName, Object object, LockOptions lockOptions) {
        super.refresh(setup.translateEntityNameFromJpaToHibernate(entityName), object, lockOptions);
    }

    @Override
    public Criteria createCriteria(String entityName) {
        return super.createCriteria(setup.translateEntityNameFromJpaToHibernate(entityName));
    }

    @Override
    public Criteria createCriteria(String entityName, String alias) {
        return super.createCriteria(setup.translateEntityNameFromJpaToHibernate(entityName), alias);
    }
}
