import java.io.IOException;

public class ClearConsole {
    private String os;
    ClearConsole(){
        this.os = System.getProperty("os.name").toLowerCase(); 
    }

    void clear(){
        try {
            if (os.contains("win")) {  // If the OS is Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {  // If the OS is Linux, Unix, or macOS
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } else {
                System.out.println("Unsupported operating system.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
