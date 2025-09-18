package sv.com.jsof.school.view.practice;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import org.primefaces.PrimeFaces;
import sv.com.jsof.school.db.dto.PreguntaDto;
import sv.com.jsof.school.repository.CursoRepository;

/**
 *
 * @author msanchez
 */
@Named
@SessionScoped
public class PracticeView implements Serializable {

    @Inject
    CursoRepository cursoRepository;

    @Getter
    private String step;
    @Getter
    private int idStep = 0;
    @Getter
    private int totalStep = 0;
    @Getter
    private int avance = 0;
    @Getter
    private PreguntaDto current;

    private List<PreguntaDto> lstPreguntas;

    @PostConstruct
    public void init() {
        lstPreguntas = cursoRepository.getLstPreguntas(1);

        totalStep = lstPreguntas.size();
    }

    public void nextStep() {
        current = lstPreguntas.get(idStep);
        idStep++;
        if (idStep > 1) {
            avance = (int) (idStep * 100) / totalStep;
            PrimeFaces.current().ajax().update("progressBarClient");
        }

        switch (current.getIdTipoPregunta()) {
            case 0:
                step = "fragment/start.xhtml";
                break;
            case 1:
                step = "fragment/closed-question.xhtml";
                break;
            case 2:
                step = "fragment/translate.xhtml";
                break;
            case 3:
                step = "fragment/match.xhtml";
                break;
            case 4:
                step = "fragment/read-answer.xhtml";
                break;
            case 5:
                step = "fragment/closed-question.xhtml";
                break;
            case 6:
                step = "fragment/complete-sentence.xhtml";
                break;
            default:
                break;
        }
    }

    public void showMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
