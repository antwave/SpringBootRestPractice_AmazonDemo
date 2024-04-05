package AmazonDemo.Exceptions;

import org.springframework.http.HttpStatus;

public class CatFactDownException extends CustomBaseException {

    public CatFactDownException() {
        super(HttpStatus.SERVICE_UNAVAILABLE, new SimpleResponse("The External CatFact Service is Unavailable"));
    }
}
