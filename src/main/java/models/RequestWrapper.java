package models;

import javax.persistence.Entity;

@Entity
public class RequestWrapper {
    private Request Request;

    public RequestWrapper() {
    }

    public RequestWrapper(Request request) {
        Request = request;
    }

    public models.Request getRequest() {
        return Request;
    }

    public void setRequest(models.Request request) {
        Request = request;
    }
}
