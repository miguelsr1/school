package sv.com.jsof.school.view.practice;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;

/**
 *
 * @author msanchez
 */
@Named
@ViewScoped
public class PracticeView implements Serializable {

    @Getter
    private String step;
    private int idStep = 0;

    public void nextStep() {
        idStep ++;
        switch (idStep) {
            case 0:
                step = "fragment/start.xhtml";
                break;
            case 1:
                step = "fragment/read-answer.xhtml";
                break;
            case 2:
                step = "fragment/match.xhtml";
                break;
            case 3:
                step = "fragment/closed-question.xhtml";
                break;
            case 4:
                step = "fragment/translate.xhtml";
                break;
            case 5:
                step = "fragment/closed-question.xhtml";
                break;
            case 6:
                step = "fragment/complete-sentence.xhtml";
                break;
            case 7:
                step = "fragment/complete-sentence.xhtml";
                break;
            case 8:
                step = "fragment/match.xhtml";
                break;
            case 9:
                step = "fragment/closed-question.xhtml";
                break;
            case 10:
                step = "fragment/read-answer.xhtml";
                break;
            case 11:
                step = "fragment/closed-question.xhtml";
                break;
            default:
                break;
        }
    }
}
