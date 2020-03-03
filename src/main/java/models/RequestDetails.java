package models;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class RequestDetails {
    @Id
    private int id;
    LocalDate date;
    LocalTime time;
}
