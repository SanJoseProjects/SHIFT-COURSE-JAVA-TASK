import java.io.IOException;

public class Utils {
    public static void argHandler(String[] args, ProgramInitParameters programInitParameters) throws IOException {
        int index = 0;
        if (args[0].startsWith("-") && args[0].length() == 2) {
            keysHandler(args[0], programInitParameters);

            index++;
            if (args[1].startsWith("-") && args[1].length() == 2) {
                keysHandler(args[1], programInitParameters);
                index++;
            }
        } else {
            throw new IOException("Не введён ключ определения типа данных для начала работы программы.");
        }

        if (programInitParameters.getSortType() == null) {
            programInitParameters.setSortType(Type.Integer);
        }

        while (index < args.length) {
            if (isKey(args[index])) {
                System.out.println("Ошибка: введено невалидное количество ключей. " +
                        "Ключ '"+ args[index] +"' игнорируется");
                index++;
                continue;
            }
            if (programInitParameters.getOutputFileName().equals("")) {
                programInitParameters.setOutputFileName(args[index]);
            } else {
                programInitParameters.setInputFileNames(args[index]);
            }

            index++;
        }
    }

    public static Boolean isKey(String arg) {
        return arg.startsWith("-") && arg.length() == 2;
    }

    public static void keysHandler(String arg, ProgramInitParameters programInitParameters) {
        if (arg.equals("-i") || arg.equals("-s"))
        {
            checkSortType(arg, programInitParameters);
        } else if (arg.equals("-a") || arg.equals("-d")) {
            checkSortOrder(arg, programInitParameters);
        }
    }

    public static void checkSortType(String arg, ProgramInitParameters programInitParameters) {
        switch (arg) {
            case "-i":
                programInitParameters.setSortType(Type.Integer);
                break;

            case "-s":
                programInitParameters.setSortType(Type.String);
                break;
        }

    }

    public static void checkSortOrder(String arg, ProgramInitParameters programInitParameters) {
        switch (arg) {
            case "-a":
                programInitParameters.setSortOrder(Order.Ascend);
                break;

            case "-d":
                programInitParameters.setSortOrder(Order.Decrease);
                break;
        }

    }
}
