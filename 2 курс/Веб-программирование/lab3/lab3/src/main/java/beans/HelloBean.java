package beans;

import lombok.Data;

import javax.faces.bean.ManagedBean;

@ManagedBean
@Data
public class HelloBean {
    private String name;
    private String greeting;

    public String sayHello(){
        this.greeting = "Hello, " + name;
        return null;
    }
}
