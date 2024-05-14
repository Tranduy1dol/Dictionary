package Model;

import java.util.ArrayList;
import java.util.List;

public class EnglishWord extends Word {
    private String word;
    private String pronunciation;
    private List<String> type;
    private List<String> definition;

    public EnglishWord() {
        this.definition = new ArrayList<>();
        this.type = new ArrayList<>();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getDefinition() {
        return definition;
    }

    public void setDefinition(List<String> definition) {
        this.definition = definition;
    }

}
