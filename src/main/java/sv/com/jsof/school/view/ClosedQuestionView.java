package sv.com.jsof.school.view;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import sv.com.jsof.school.db.dto.ClosedQuestionDto;
import sv.com.jsof.school.repository.CursoRepository;
import sv.com.jsof.school.view.practice.PracticeView;
import sv.com.jsof.school.view.practice.Pregunta;

/**
 *
 * @author msanchez
 */
@Named
@ViewScoped
public class ClosedQuestionView implements Serializable, Pregunta {

    @Getter
    private List<ClosedQuestionDto> opciones;
    @Getter
    private boolean respuestaSeleccionada = false;
    @Getter
    private boolean respuestaCorrecta = false;
    @Getter
    private String mensajeRespuesta = "";
    @Getter
    private String question;

    @Inject
    PracticeView practiceView;

    @Inject
    CursoRepository cursoRepository;

    @PostConstruct
    public void init() {
        opciones = cursoRepository.getLstClosedQuestion(practiceView.getCurrent().getIdPregunta());
        question = opciones.get(0).getPregunta();

        Collections.shuffle(opciones);
    }

    public void seleccionarRespuesta(ClosedQuestionDto opcion) {
        this.respuestaSeleccionada = true;
        this.respuestaCorrecta = opcion.getCorrecta();

        if (respuestaCorrecta) {
            practiceView.showMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", null);
        } else {
            practiceView.showMessage(FacesMessage.SEVERITY_ERROR, "INCORRECTO", null);
        }
    }

    @Override
    public void siguiente() {
        if(!respuestaCorrecta){
             practiceView.showMessage(FacesMessage.SEVERITY_WARN, "INFORMACIÓN", "DEBE DE SELECCIONAR UNA OPCION ANTES DE CONTINUAR");
             return;
        }
        
        practiceView.nextStep();
    }
}
