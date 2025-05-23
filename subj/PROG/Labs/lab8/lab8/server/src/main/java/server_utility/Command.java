package server_utility;

import common_utility.network.Request;
import common_utility.network.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server_utility.interfaces.Executable;

import java.io.IOException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Command implements Executable {
    private String name;
    private String description;
//    private boolean hasArguments;  boolean hasArguments

    public abstract Response execute(String[] command) throws IOException, ClassNotFoundException;
    public abstract Response execute(String[] command, Request request) throws IOException, ClassNotFoundException;
}
