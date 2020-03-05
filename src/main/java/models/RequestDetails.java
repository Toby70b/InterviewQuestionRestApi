package models;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import misc.Device;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class RequestDetails {
    @NonNull
    @NotNull
    private LocalDate date;
    @NonNull
    @NotNull
    private LocalTime time;
    @NonNull
    @NotNull
    private Device device;
    @NonNull
    @NotNull
    private String ipAddress;
    private LocationDetails locationDetails;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocationDetails getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(LocationDetails locationDetails) {
        this.locationDetails = locationDetails;
    }
}
