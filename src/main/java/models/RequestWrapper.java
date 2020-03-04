package models;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;

@RequiredArgsConstructor
public class RequestWrapper {
    private Request Request;

    public models.Request getRequest() {
        return Request;
    }

    public void setRequest(models.Request request) {
        Request = request;
    }
}
