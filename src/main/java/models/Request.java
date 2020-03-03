package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Request {
    @Id
    private int id;
    private RequestDetails requestDetails;
    private User user;

    public Request(RequestDetails requestDetails, User user) {
        this.requestDetails = requestDetails;
        this.user = user;
    }

    public RequestDetails getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(RequestDetails requestDetails) {
        this.requestDetails = requestDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
