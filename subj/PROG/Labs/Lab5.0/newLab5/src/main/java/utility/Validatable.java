package utility;

import exceptions.ValidateException;

public interface Validatable {
    boolean isValid() throws ValidateException;
}
