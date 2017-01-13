package ua.malibu.ostpc.controllers;

import javax.transaction.Transactional;
import java.util.logging.Logger;

@Transactional
public class BaseController {

    protected static final transient Logger logger = Logger.getLogger(BaseController.class.getName());

    public BaseController() {
    }

}
