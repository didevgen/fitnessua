package ua.malibu.ostpc.controllers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

@Transactional
public class BaseController {

    protected static final transient Logger logger = Logger.getLogger(BaseController.class.getName());

    @PersistenceContext
    protected EntityManager entityManager;

    public BaseController() {
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
