import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestASClient {

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

// запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента      
        try (Socket socket = new Socket("localhost", 3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream());) {

            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");

// проверяем живой ли канал и работаем если живой           
            while (true) {
                System.out.print("$ ");
                String clientCommand = br.readLine();
// ждём консоли клиента на предмет появления в ней данных
                if (br.ready()) {

// данные появились - работаем                      
                    System.out.println("Client start writing in channel...");
                    Thread.sleep(1000);

// пишем данные с консоли в канал сокета для сервера            
                    oos.writeUTF(clientCommand);
                    oos.flush();
                    System.out.println("Clien sent message " + clientCommand + " to server.");
                    Thread.sleep(1000);
// ждём чтобы сервер успел прочесть сообщение из сокета и ответить

// проверяем условие выхода из соединения           
                    if (clientCommand.equalsIgnoreCase("exit")) {

// если условие выхода достигнуто разъединяемся             
                        System.out.println("Client kill connections");
                        Thread.sleep(2000);
                        break;
// смотрим что нам ответил сервер на последок перед закрытием ресурсов          
//                        if (ois.read() > -1) {
//                            System.out.println("reading...");
//                            String in = ois.readUTF();
//                            System.out.println(in);
//                        }

// после предварительных приготовлений выходим из цикла записи чтения               
//                        break;
                    }

// если условие разъединения не достигнуто продолжаем работу            
                    System.out.println("Client sent message & start waiting for data from server...");
                    Thread.sleep(2000);

// проверяем, что нам ответит сервер на сообщение(за предоставленное ему время в паузе он должен был успеть ответить)           
                    if (ois.read() > -1) {

// если успел забираем ответ из канала сервера в сокете и сохраняем её в ois переменную,  печатаем на свою клиентскую консоль                       
                        System.out.println("reading...");
                        String in = ois.readUTF();
                        System.out.println(in);
                    }
                }
            }
// на выходе из цикла общения закрываем свои ресурсы
            System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}