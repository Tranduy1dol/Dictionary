package Controller.GameController;

import Config.Connect;
import Model.FourChoiceQuestion;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class FourChoiceController extends GameController<FourChoiceQuestion> {

    private final List<FourChoiceQuestion> questDatabase;

    private FourChoiceQuestion question;

    private int numQuest;

    private int rightAnswer;

    private int maxQuest;

    public FourChoiceController() {
        questDatabase = new ArrayList<>();
        FindIterable<Document> result = Connect.client.getDatabase("Dictionary")
                .getCollection("QuestionGame")
                .find()
                .limit(10);
        for (Document document : result) {
            FourChoiceQuestion question = new FourChoiceQuestion();
            question.setQuestion(document.getString("question"));
            question.setAnswer1(document.getString("answer1"));
            question.setAnswer2(document.getString("answer2"));
            question.setAnswer3(document.getString("answer3"));
            question.setAnswer4(document.getString("answer4"));
            question.setRightAnswer(document.getString("rightAnswer"));
            questDatabase.add(question);
        }
    }


    @Override
    public void setQuest() {
        int questionIndex = generateRandomNumber(0, 10);
        question = questDatabase.get(questionIndex);
        System.out.println(question.getQuestion());
    }

    @Override
    public FourChoiceQuestion getQuest() {
        return this.question;
    }

    @Override
    public String processQuest(String input) {
        if (Objects.equals(input, question.getRightAnswer())) {
            return "Right";
        }
        return "Wrong";
    }

    public int getNumQuest() {
        return numQuest;
    }

    public void setNumQuest(int numQuest) {
        this.numQuest = numQuest;
    }

    public int getMaxQuest() {
        return maxQuest;
    }

    public void setMaxQuest(int maxQuest) {
        this.maxQuest = maxQuest;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
