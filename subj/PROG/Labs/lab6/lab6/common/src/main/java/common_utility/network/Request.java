package common_utility.network;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {
    private String message;
    private String fileName;
    private File scriptFile;
    public Request(String message) {
        this.message = message;
    }



}
