public class Runner {
    StandartConsole console;
    public Runner(StandartConsole console) {
        this.console = console;
    }

    public void run() {
        String userCommand = "";
        while (true) {
            console.prompt();
            try {
                userCommand = console.read();
                if (userCommand.isEmpty()){
                    continue;
                }
                else if (userCommand.equals("hi")){
                    System.out.println("Hello console!");
                    break;
                }
                else if (userCommand.equals("exit")){
                    break;
                }
                else {
                    System.out.println(userCommand);
                    break;
                }
            }  catch (Exception e) {}
        }
    }
}
