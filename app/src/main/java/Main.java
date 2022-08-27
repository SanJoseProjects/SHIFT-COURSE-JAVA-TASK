import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ProgramInitParameters programInitParameters = new ProgramInitParameters();

        try {
            Utils.argHandler(args, programInitParameters);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }


    }
}
