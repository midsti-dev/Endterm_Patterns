import java.io.IOException;
/**
 *                  The ClearConsole class is responsible for clearing the console screen.
 *                  It detects the operating system and uses the appropriate command to clear the terminal.
 */
public class ClearConsole {
    private String os;
    ClearConsole(){
        this.os = System.getProperty("os.name").toLowerCase(); 
    }
/**
*              Clears the console screen based on the detected operating system.
*              It supports Windows, Linux, Unix, and macOS.
*/
    void clear(){
        try {
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } else {
                System.out.println("Unsupported operating system.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
