package view;

import Controller.GameController.FourChoiceController;
import Controller.WordController.NormalWordController;
import Controller.WordController.VietAnhWordController;
import Model.EnglishWord;
import Model.FourChoiceQuestion;
import Model.VietAnhWord;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.bson.Document;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HelloController {

    // Translate Page
    @FXML
    private TextArea paragraph;

    //Translate Page
    @FXML
    private Label translatedParagraph;

    // Translate Button
    @FXML
    private Button engToViet;

    // Translate Button
    @FXML
    private Button vietToEng;

    // Game Menu Button
    @FXML
    private Button hangmanGame;

    // Game Menu Button
    @FXML
    private Button wordleGame;

    // Game Menu Button
    @FXML
    private Button fourAnswerGame;

    // Sidebar button
    @FXML
    private Button mainButton;

    // Sidebar button
    @FXML
    private Button vietAnhButton;

    // Sidebar button
    @FXML
    private Button translateButton;

    // Sidebar button
    @FXML
    private Button gameButton;

    // Sidebar button
    @FXML
    private Button newWordButton;

    // Center page
    @FXML
    private StackPane mainPage;

    // Center page
    @FXML
    private StackPane gamePage;

    // Center page
    @FXML
    private StackPane translatePage;

    // Center page
    @FXML
    private StackPane vietAnhPage;

    // Center page
    @FXML
    private StackPane newWordPage;

    // Game Page
    @FXML
    private StackPane hangmanPage;

    // Game Page
    @FXML
    private StackPane wordlePage;

    // Game Page
    @FXML
    private StackPane fourAnswerPage;

    // Search Page Action
    @FXML
    private TextField searchField;

    // Search Page Action
    @FXML
    private ListView<String> searchResult;

    // Viet Anh Page
    @FXML
    private TextField lookupField;

    // Viet Anh Page
    @FXML
    private ListView wordTable;

    // Viet Anh Page
    @FXML
    private ListView lookupTable;

    // Viet Anh Page
    @FXML
    private AnchorPane veWordInfo;

    // Viet Anh Page
    @FXML
    private Label engWordInfo;

    // Viet Anh Page
    @FXML
    private Label vietWordInfo;

    // Search Page
    @FXML
    private AnchorPane wordInfo;

    // Search Page
    @FXML
    private AnchorPane mainInfo;

    // Search Page
    @FXML
    private Label wordLabel;

    // Search Page
    @FXML
    private Label pronounceLabel;

    // Search Page
    @FXML
    private Label typeLabel;

    // Search Page
    @FXML
    private Label definitionLabel;

    // Question Game
    @FXML
    private Label questionLabel;

    // Question Game
    @FXML
    private Button answer1;

    // Question Game
    @FXML
    private Button answer2;

    // Question Game
    @FXML
    private Button answer3;

    // Question Game
    @FXML
    private Button answer4;

    // Question Game
    @FXML
    private Label gameResult;

    // Question Game
    @FXML
    private AnchorPane mainGame;

    // Question Game
    @FXML
    private AnchorPane endGame;

    // Question Game
    @FXML
    private Button resetButton;

    // Question Game
    @FXML
    private Button quitButton;

    // Add Word
    @FXML
    private Button submitAdd;

    @FXML
    private TextField wordField;

    @FXML
    private TextField definitionField;

    @FXML
    private TextField typeField;

    @FXML
    private Button deleteButton;

    private Document currentDoc;

    private HashMap<String, Document> wordSearchResult;

    private HashMap<String, Document> vietAnhDefaultTable;

    private HashMap<String, Document> lookupResultTable;

    private final VietAnhWordController vietAnhWordController = new VietAnhWordController();

    private final NormalWordController normalWordController = new NormalWordController();

    private final FourChoiceController questionController = new FourChoiceController();

    public void setWordTable() {
        veWordInfo.setVisible(false);
        wordTable.setVisible(true);

        VietAnhWord filter = new VietAnhWord();
        filter.setVietWord("a");
        List<String> veTable = new ArrayList<>();
        vietAnhDefaultTable = new HashMap<>();

        for (Document document: vietAnhWordController.readOperation(filter, 100)) {
            veTable.add(document.getString("vietWord"));
            vietAnhDefaultTable.put(document.getString("vietWord"), document);
        }

        wordTable.getItems().addAll(veTable);
        wordTable.getSelectionModel().selectedItemProperty().addListener(this::vietAnhDefaultWordInfo);
    }

    public void handleVietAnhSearch() {
        String search = lookupField.getText();
        if (!search.isEmpty()) {
            List<String> lookupResult = new ArrayList<>();
            lookupTable.setVisible(true);
            lookupResultTable = new HashMap<>();
            VietAnhWord filter = new VietAnhWord();
            filter.setVietWord(search);
            for (Document document: vietAnhWordController.readOperation(filter, 10)) {
                lookupResult.add(document.getString("vietWord"));
                lookupResultTable.put(document.getString("vietWord"), document);
            }
            lookupTable.getItems().addAll(lookupResult);
            lookupTable.getSelectionModel().selectedItemProperty().addListener(this::vietAnhLookupWordInfo);
        } else {
            lookupTable.setVisible(false);
        }
    }

    private void vietAnhDefaultWordInfo(ObservableValue<? extends List<String>> observableValue, Object o, Object o1) {
        ObservableList<String> selectedItems = wordTable.getSelectionModel().getSelectedItems();
        String getSelectedItem = selectedItems.isEmpty() ? "no selected" : selectedItems.toString();
        if (!getSelectedItem.equals("no selected")) {
            String key = getSelectedItem.substring(1, getSelectedItem.length() - 1);
            Document document = vietAnhDefaultTable.get(key);
            engWordInfo.setText(document.getString("engWord").toString());
            vietWordInfo.setText(document.getString("vietWord").toString());
            wordTable.setVisible(false);
            veWordInfo.setVisible(true);
        }
    }

    private void vietAnhLookupWordInfo(ObservableValue<? extends List<String>> observableValue, Object o, Object o1) {
        ObservableList<String> selectedItems = lookupTable.getSelectionModel().getSelectedItems();
        String getSelectedItem = selectedItems.isEmpty() ? "no selected" : selectedItems.toString();
        if (!getSelectedItem.equals("no selected")) {
            String key = getSelectedItem.substring(1, getSelectedItem.length() - 1);
            Document document = lookupResultTable.get(key);
            engWordInfo.setText(document.getString("engWord").toString());
            vietWordInfo.setText(document.getString("vietWord").toString());
            wordTable.setVisible(false);
            lookupTable.setVisible(false);
            veWordInfo.setVisible(true);
        }
    }

    public void switchFrom(ActionEvent event) {
        mainPage.setVisible(false);
        gamePage.setVisible(false);
        translatePage.setVisible(false);
        vietAnhPage.setVisible(false);
        newWordPage.setVisible(false);

        hangmanPage.setVisible(false);
        wordlePage.setVisible(false);
        fourAnswerPage.setVisible(false);

        if (event.getSource() == mainButton) {
            mainPage.setVisible(true);
            mainInfo.setVisible(true);
            wordInfo.setVisible(false);
        } else if (event.getSource() == gameButton) {
            gamePage.setVisible(true);
        } else if (event.getSource() == translateButton) {
            translatePage.setVisible(true);
        } else if (event.getSource() == vietAnhButton) {
            vietAnhPage.setVisible(true);
            setWordTable();
        } else if (event.getSource() == newWordButton) {
            newWordPage.setVisible(true);
        }
    }

    public void switchGame(ActionEvent event) {
        hangmanPage.setVisible(false);
        wordlePage.setVisible(false);
        fourAnswerPage.setVisible(false);

        if (gamePage.isVisible()) {
            if (event.getSource() == hangmanGame) {
                gamePage.setVisible(false);
                hangmanPage.setVisible(true);
            } else if (event.getSource() == wordleGame) {
                gamePage.setVisible(false);
                wordlePage.setVisible(true);
            } else if (event.getSource() == fourAnswerGame) {
                gamePage.setVisible(false);
                fourAnswerPage.setVisible(true);
                questionController.setMaxQuest(10);
                questionController.setNumQuest(1);
                questionController.setRightAnswer(0);
                changeQuestion();
                questionController.setState("playing");
            }
        }
    }

    public void handleTranslate(ActionEvent event) throws IOException, InterruptedException {
        String text = paragraph.getText();
        String body = null;

        if (event.getSource() == engToViet) {
            body = String.format("q=%s&target=%s&source=%s",
                    URLEncoder.encode(text, StandardCharsets.UTF_8), "vi", "en");
        }
        if (event.getSource() == vietToEng) {
            body = String.format("q=%s&target=%s&source=%s",
                    URLEncoder.encode(text, StandardCharsets.UTF_8), "en", "vi");
        }
        assert body != null;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", "83f9090b96msh88d38347d55a09cp108392jsn8a7d85d31bc0")
                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            JSONObject json = new JSONObject(response.body());
            String translatedText = json
                    .getJSONObject("data")
                    .getJSONArray("translations")
                    .getJSONObject(0)
                    .getString("translatedText");
            translatedParagraph.setText(URLDecoder.decode(translatedText, StandardCharsets.UTF_8));
        }
    }

    public void handleSearch() {
        String search = searchField.getText();
        if (!search.isEmpty()) {
            searchResult.setVisible(true);
            List<String> wordResult = new ArrayList<>();
            wordSearchResult = new HashMap<>();
            EnglishWord filter = new EnglishWord();
            filter.setWord(search);
            for (Document document : normalWordController.readOperation(filter, 10)) {
                wordResult.add(document.getString("word"));
                wordSearchResult.put(document.getString("word"), document);
            }
            searchResult.getItems().addAll(wordResult);
            searchResult.getSelectionModel().selectedItemProperty().addListener(this::selectionChange);
        } else {
            searchResult.setVisible(false);
        }
    }

    private void selectionChange(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        ObservableList<String> selectedItems = searchResult.getSelectionModel().getSelectedItems();
        String getSelectedItem = selectedItems.isEmpty() ? "no selected" : selectedItems.toString();
        String key = getSelectedItem.substring(1, getSelectedItem.length() -1);
        if (!key.equals("no selected")) {
            wordInfo.setVisible(true);
            mainInfo.setVisible(false);
            Document word = wordSearchResult.get(key);
            currentDoc = word;
            wordLabel.setText(word.getString("word"));
            pronounceLabel.setText(word.getString("pronunciation"));
            List<String> definition = word.getList("definition", String.class);
            List<String> type = word.getList("type", String.class);
            StringBuilder definitionText = new StringBuilder();
            StringBuilder typeText = new StringBuilder();
            for (String def : definition) {
                definitionText.append(def).append("\n");
            }
            definitionLabel.setText(definitionText.toString());
            for (String t : type) {
                typeText.append(t);
                if (!Objects.equals(type.getLast(), t)) {
                    typeText.append(", ");
                }
            }
            typeLabel.setText(typeText.toString());
        }
    }

    public void handleQuestionGame(ActionEvent event) {
        if (questionController.getState().equals("playing")) {
            String choice = null;
            if (event.getSource() == answer1) {
                choice = answer1.getText();
            }
            if (event.getSource() == answer2) {
                choice = answer2.getText();
            }
            if (event.getSource() == answer3) {
                choice = answer3.getText();
            }
            if (event.getSource() == answer4) {
                choice = answer4.getText();
            }
            String result = questionController.processQuest(choice);
            if (Objects.equals(result, "Right")) {
                questionController.setRightAnswer(questionController.getRightAnswer() + 1);
            }
            changeQuestion();
            questionController.setNumQuest(questionController.getNumQuest() + 1);
            if (questionController.getNumQuest() == 10) {
                questionController.setState("end");
            }
        }
        if (questionController.getState().equals("end")) {
            mainGame.setVisible(false);
            endGame.setVisible(true);
            gameResult.setText(String.format("Kết quả của bạn là %o/10. Chơi lại?", questionController.getRightAnswer()));
        }

    }

    public void handleResetGame(ActionEvent event) {
        if (questionController.getState().equals("end")) {
            if (event.getSource() == resetButton) {
                fourAnswerPage.setVisible(true);
                questionController.setMaxQuest(10);
                questionController.setNumQuest(1);
                questionController.setRightAnswer(0);
                changeQuestion();
                questionController.setState("playing");
                mainGame.setVisible(true);
                endGame.setVisible(false);
            }

            if (event.getSource() == quitButton) {
                fourAnswerPage.setVisible(false);
                gamePage.setVisible(true);
            }
        }

    }

    private void changeQuestion() {
        questionController.setQuest();
        FourChoiceQuestion question = questionController.getQuest();
        answer1.setText(question.getAnswer1());
        answer2.setText(question.getAnswer2());
        answer3.setText(question.getAnswer3());
        answer4.setText(question.getAnswer4());
        questionLabel.setText(question.getQuestion());
    }

    public void handleAddWord(ActionEvent event) {
        if (event.getSource() == submitAdd) {
            if (wordField.getText() != null && typeField.getText() != null && definitionField.getText() != null) {
                EnglishWord word = new EnglishWord();
                word.setWord(wordField.getText());
                word.setDefinition(new ArrayList<>());
                word.setType(new ArrayList<>());
                word.getDefinition().add(typeField.getText());
                word.getDefinition().add(definitionField.getText());

                normalWordController.writeOperation(word);
            }
        }
    }

    public void handleDelete(ActionEvent event) {
        if (event.getSource() == deleteButton && currentDoc != null) {
            EnglishWord word = new EnglishWord();
            word.setWord(currentDoc.getString("word"));
            word.setDefinition(currentDoc.getList("definition", String.class));
            word.setType(currentDoc.getList("type", String.class));
            normalWordController.deleteOperation(word);
            wordInfo.setVisible(false);
            mainInfo.setVisible(true);
        }
    }
}