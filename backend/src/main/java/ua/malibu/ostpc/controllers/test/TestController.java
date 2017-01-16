package ua.malibu.ostpc.controllers.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.controllers.BaseController;
import ua.malibu.ostpc.exceptions.rest.RestException;

import java.io.IOException;

@RestController
public class TestController extends BaseController {

    @RequestMapping(value = "/test")
    @ResponseBody
    public ResponseEntity<?> test() {
        throw new RestException(HttpStatus.BAD_REQUEST, 400001, "You sucked");
    }

    @RequestMapping(value = "/test/{testId}", method = RequestMethod.GET)
    public void tes1(@PathVariable (name = "testId") Integer testId) throws IOException {
    }


}
