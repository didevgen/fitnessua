package ua.malibu.ostpc.controllers.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.malibu.ostpc.controllers.BaseController;
import ua.malibu.ostpc.exceptions.rest.RestException;

@RestController
public class TestController extends BaseController {

    @RequestMapping(value = "/test")
    @ResponseBody
    public ResponseEntity<?> test() {
        throw new RestException(HttpStatus.BAD_REQUEST, 400001, "You sucked");
    }

}
