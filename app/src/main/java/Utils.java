import java.io.IOException;

public class Utils {
    public static void argHandler(String[] args, ProgramInitParameters programInitParameters) throws IOException {
        int index = 0;
        if (isKey(args[0])) {
            keysHandler(args[0], programInitParameters);

            index++;
            if (isKey(args[1])) {
                keysHandler(args[1], programInitParameters);
                index++;
            }
        }

        if (programInitParameters.getSortType() == null) {
            throw new IOException("Не введён ключ определения типа данных для начала работы программы.");
        }

        if (programInitParameters.getSortOrder() == null) {
            programInitParameters.setSortOrder(Order.Ascend);
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

        if (programInitParameters.getOutputFileName() == null) {
            throw new IOException("Ошибка: отсутствует имя конечного файла в аргументах вызова программы.");
        }

        if (programInitParameters.getInputFileNames().size() == 0) {
            throw new IOException("Ошибка: отсутствуют имена файлов в аргументах вызова программы.");
        }
    }

    public static Boolean isKey(String arg) {
        return arg.startsWith("-") && arg.length() == 2;
    }

    public static void keysHandler(String arg, ProgramInitParameters programInitParameters) {
        if (arg.equals("-i") || arg.equals("-s"))
        {
            if (programInitParameters.getSortType() == null)
            {
                checkSortType(arg, programInitParameters);
            } else {
                System.out.println("Предупреждение: введён ключ определения типа входных данных, " +
                        "при уже установленном типе данных. Ключ '" + arg + "игнорируется.");
            }
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
