package ua.malibu.ostpc.dtos.auth;

/**
 * Created by Eugne on 27.12.2016.
 */
public class Wrapper {

    private Object responseResult;

    public Wrapper(Object responseResult) {
        this.responseResult = responseResult;
    }

    public Object getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(Object responseResult) {
        this.responseResult = responseResult;
    }
}
