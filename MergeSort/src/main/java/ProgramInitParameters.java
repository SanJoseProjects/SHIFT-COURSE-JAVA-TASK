import java.util.ArrayList;

public class ProgramInitParameters {
    private Order sortOrder;
    private Type sortType;
    private String outputFileName;
    private final ArrayList<String> inputFileNames;

    public ProgramInitParameters() {
        sortOrder = null;
        sortType = null;
        outputFileName = "";
        this.inputFileNames = new ArrayList<>();
    }

    public void setSortOrder(Order sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setSortType(Type sortType) {
        this.sortType = sortType;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void setInputFileNames(String inputFileNames) { this.inputFileNames.add(inputFileNames);}

    public Order getSortOrder() {
        return sortOrder;
    }

    public Type getSortType() {
        return sortType;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public ArrayList<String> getInputFileNames() {
        return inputFileNames;
    }
}
