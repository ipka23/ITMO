package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Named
@Setter
@Getter
@ViewScoped
public class ClockBean implements Serializable {
    private String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
//    public String getCurrentTime() {
//        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
//    }
//    public void setCurrentTime(SimpleDateFormat dateFormat) {
//        this.currentTime = dateFormat;
//    }
    public void updateCurrentTime() {
        this.currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
    }
}