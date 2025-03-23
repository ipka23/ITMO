import mainpac.Serv;

public class TestMain {
    public static void main(String[] args) {
        new Thread(new Serv()).start();
    }
}
