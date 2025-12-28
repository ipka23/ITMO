package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String id;
    private boolean isValid;

    public AuthResponse(String message, String id) {
        this.isValid = true;
        this.message = message;
        this.id = id;
    }

    public AuthResponse(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

}


