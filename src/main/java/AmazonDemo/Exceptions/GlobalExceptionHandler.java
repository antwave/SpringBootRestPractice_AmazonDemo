package AmazonDemo.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomBaseException.class)
    public ResponseEntity<SimpleResponse> handleProductNotFoundException(CustomBaseException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getSimpleResponse());
    }

}
