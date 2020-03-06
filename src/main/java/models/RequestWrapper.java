package models;
import lombok.RequiredArgsConstructor;
import javax.validation.Valid;

@RequiredArgsConstructor
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
