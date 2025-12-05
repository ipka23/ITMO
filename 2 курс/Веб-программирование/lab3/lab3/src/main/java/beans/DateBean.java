package beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Named
@Setter
@Getter
@ApplicationScoped


public class DateBean implements Serializable {
    private String timezone = "UTC";

    private Date currentDateTime;

    public void setTimezone(String zone) {
        this.timezone = zone;
        updateCurrentDateTime();
    }
    public void updateCurrentDateTime() {
        this.currentDateTime =  Date.from(ZonedDateTime.now(ZoneId.of(timezone)).toInstant());
    }
}