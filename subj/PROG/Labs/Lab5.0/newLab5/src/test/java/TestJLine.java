//import org.jline.reader.*;
//import org.jline.terminal.Terminal;
//import org.jline.terminal.TerminalBuilder;
//import org.jline.utils.AttributedString;
//
//public class TestJLine {
//    public static void main(String[] args) throws Exception {
//        // Создаем терминал
//        Terminal terminal = TerminalBuilder.builder()
//                .jna(true) // Используем JNA для Windows, если нужно
//                .build();
//
//        // Создаем LineReader с поддержкой истории
//        LineReader reader = LineReaderBuilder.builder()
//                .terminal(terminal)
////                .history(new MemoryHistory()) // История в памяти
//                .build();
//
//        // Настраиваем приглашение
//        String prompt = "> ";
//
//        // Основной цикл ввода
//        while (true) {
//            String line;
//            try {
//                line = reader.readLine(prompt); // Читаем строку с приглашением
//                if (line == null || "exit".equalsIgnoreCase(line.trim())) {
//                    break; // Выход при вводе "exit"
//                }
//
//                // Выводим введённую команду
//                System.out.println("Вы ввели: " + line);
//
//                // Добавляем команду в историю вручную (если нужно)
//                reader.getHistory().add(line);
//            } catch (UserInterruptException e) {
//                System.out.println("Прервано (Ctrl+C)");
//                break;
//            } catch (EndOfFileException e) {
//                System.out.println("Выход (Ctrl+D)");
//                break;
//            }
//        }
//    }
//}