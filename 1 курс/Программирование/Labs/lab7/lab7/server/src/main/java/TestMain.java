public class TestMain {
    public static void main(String[] args) {
        Thread thread = new MyThread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
            }
        });
        thread.start();
        for (int i = 6; i < 10; i++) {
            System.out.println(i);
        }
    }
}
