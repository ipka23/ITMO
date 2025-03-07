package utility.interfaces;

import utility.exceptions.ValidateException;

public interface Validatable {
    boolean isValid() throws ValidateException;
}
