package models;

import lombok.AllArgsConstructor;

import javax.validation.Valid;

@AllArgsConstructor
public class RequestWrapper {
    @Valid
    private Request Request;

    public models.Request getRequest() {
        return Request;
    }

    public void setRequest(models.Request request) {
        Request = request;
    }
}
