import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ProgramInitParameters programInitParameters = new ProgramInitParameters();

        try {
            Utils.argHandler(args, programInitParameters);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            System.exit(0);
        }

        try {
            MergeSort.mergeSort(programInitParameters);
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
