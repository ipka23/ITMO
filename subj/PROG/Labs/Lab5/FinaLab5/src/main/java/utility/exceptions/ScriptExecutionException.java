package utility.exceptions;

public class ScriptExecutionException extends Exception{
    public ScriptExecutionException(){
        super("Непредвиденная ошибка во время выполнения скрипта!");
    }
}
