import java.io.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

    public class Main {
        private static final String FILE_NAME = "numbers.txt";
        private static final Random random = new Random();

        public static void main(String[] args) {
            ExecutorService executor = Executors.newFixedThreadPool(3);

            // Запись четных чисел
            Runnable evenNumberWriter = () -> {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                    while (true) {
                        int evenNumber = random.nextInt(100) * 2;
                        synchronized (writer) {
                            writer.write(evenNumber + "\n");
                            writer.flush();
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };

            // Запись нечетных чисел
            Runnable oddNumberWriter = () -> {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                    while (true) {
                        int oddNumber = random.nextInt(100) * 2 + 1;
                        synchronized (writer) {
                            writer.write(oddNumber + "\n");
                            writer.flush();
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };

            // Задача для чтения файла
            Runnable fileReader = () -> {
                try {
                    while (true) {
                        synchronized (Main.class) {
                            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                                String line;
                                String lastLine = null;
                                while ((line = reader.readLine()) != null) {
                                    lastLine = line;
                                }
                                if (lastLine != null) {
                                    System.out.println("Последнее число в файле: " + lastLine);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            executor.submit(evenNumberWriter);
            executor.submit(oddNumberWriter);
            executor.submit(fileReader);

            // Ожидание завершения работы потоков
            try {TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            executor.shutdownNow();
        }
    }

