package ua.malibu.ostpc.controllers;

import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Transactional
public class BaseController {

    protected static final transient Logger logger = Logger.getLogger(BaseController.class.getName());

    public BaseController() {
    }

}
