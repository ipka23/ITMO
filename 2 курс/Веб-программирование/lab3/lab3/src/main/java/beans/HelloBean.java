package beans;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Named("helloBean")
@SessionScoped
@Getter
@Setter
public class HelloBean implements Serializable {
    private String name;
    private String greeting;

    public String sayHello(){
        if (name == null || name.trim().isEmpty()) {
            this.greeting = "Hello!";
        } else {
            this.greeting = "Hello, " + name;
        }
        return null;
    }
}