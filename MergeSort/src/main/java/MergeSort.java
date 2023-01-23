import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MergeSort {
    public static void mergeSort(ProgramInitParameters programInitParameters) throws FileNotFoundException {
        ArrayList<Path> inputFilePaths = new ArrayList<>();

        Path temp;
        for (String name : programInitParameters.getInputFileNames()) {
            temp = Path.of(name);
            if (Files.exists(temp))
                inputFilePaths.add(temp);
            else
                throw new FileNotFoundException("Ошибка: файл '" + name +"' не найден.");
        }

        if (programInitParameters.getSortType() == Type.Integer) {
            try {
                integerMergeSort(inputFilePaths, programInitParameters);
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                System.exit(0);
            }
        } else {
            stringMergeSort(inputFilePaths, programInitParameters);
        }
    }

    public static void integerMergeSort(ArrayList<Path> files, ProgramInitParameters programInitParameters)
            throws IOException {

        for (Path fileName : files) {
            try (BufferedReader reader = Files.newBufferedReader(fileName)) {
                String line = reader.readLine();
                if (!isInteger(line)) {
                    files.remove(fileName);
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }

        if (files.size() == 0)
            throw new IOException("Ошибка: ни один из файлов не содержит данные нужного типа.");


        //long chunkSize = Runtime.getRuntime().freeMemory() / files.size() / 4;
        long chunkSize = 3;
        ArrayList<Boolean> fileIsNotEnded = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            fileIsNotEnded.add(true);
        }

        ArrayList<ArrayList<Integer>> data = new ArrayList<>();
        ArrayList<Integer> loadsCount = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            data.add(readDataFromFile(files.get(i), 0, chunkSize, fileIsNotEnded, i));
            loadsCount.add(1);
        }


        int numberIndex;
        int chunkIndex = 0;
        do {
            String currentPath = new java.io.File(".").getCanonicalPath();
            Files.createDirectories(Paths.get(currentPath + "/chunks"));

            File file = new File(currentPath + "/chunks/chunk-"+chunkIndex+".txt");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter chunk = new PrintWriter(fileWriter);
            ArrayList<Integer> result = new ArrayList<>();
            for (int currentChunkSize = 0; currentChunkSize < chunkSize; currentChunkSize++) {
                if (data.isEmpty())
                    break;
                ArrayList<Integer> numbers = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    numbers.add(data.get(i).get(0));
                }

                numberIndex = findMinNumber(numbers);
                result.add(data.get(numberIndex).get(0));
                data.get(numberIndex).remove(0);

                if (data.get(numberIndex).size() == 0 && fileIsNotEnded.get(numberIndex)) {
                    data.get(numberIndex).addAll(readDataFromFile(files.get(numberIndex),
                            chunkSize * loadsCount.get(numberIndex), chunkSize, fileIsNotEnded, numberIndex));
                    loadsCount.set(numberIndex, loadsCount.get(numberIndex) + 1);
                } else if (!fileIsNotEnded.get(numberIndex) && data.get(numberIndex).isEmpty()) {
                    data.remove(numberIndex);
                    loadsCount.remove(numberIndex);
                    files.remove(numberIndex);
                    if(!fileIsNotEnded.get(numberIndex))
                        fileIsNotEnded.remove(numberIndex);
                }
            }
            for(int num : result) {
                chunk.println(num);
                chunk.flush();
            }
            chunk.close();

            chunkIndex++;
        } while (!endOfAllFiles(fileIsNotEnded));
    }

    public static boolean endOfAllFiles(ArrayList<Boolean> statuses) {
        int numberOfEndedFiles = 0;
        for (Boolean status : statuses) {
            if (!status)
                numberOfEndedFiles++;
        }

        return statuses.size() == numberOfEndedFiles;
    }


    public static int findMinNumber(ArrayList<Integer> numbers) {
        int resultIndex = 0;
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(resultIndex) > numbers.get(i))
                resultIndex = i;
        }

        return resultIndex;
    }

    public static void stringMergeSort(ArrayList<Path> files, ProgramInitParameters programInitParameters) {
        long maxChunkSize = Runtime.getRuntime().freeMemory() / files.size();
    }

    public static ArrayList<Integer> readDataFromFile(Path fileName, long startPose, long size, ArrayList<Boolean> fileStatus, int index)
            throws IOException {
        ArrayList<Integer> data = new ArrayList<>();

        String line = "a";
        FileInputStream fs= new FileInputStream(fileName.toString());
        BufferedReader buffer = new BufferedReader(new InputStreamReader(fs));
        for(int i = 0; i < startPose - 1; ++i)
            buffer.readLine();

        line = buffer.readLine();
        for (long i = 0; i < size && line != null; i++, line = buffer.readLine()) {
            if (isInteger(line))
                data.add(Integer.parseInt(line));
        }

        if (line == null)
            fileStatus.set(index, false);

        return data;
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
