package sv.com.jsof.school.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sv.com.jsof.school.db.dto.LeerResponderDto;
import sv.com.jsof.school.dto.PreguntaVocabulario;
import sv.com.jsof.school.repository.CursoRepository;
import sv.com.jsof.school.view.practice.PracticeView;
import sv.com.jsof.school.view.practice.Pregunta;

@Named
@ViewScoped
@Slf4j
public class ReadAnswerView implements Serializable, Pregunta {

    private static final long serialVersionUID = 1L;

    @Getter
    private List<LeerResponderDto> lstLeerResponderDtos;

    @Inject
    CursoRepository cursoRepository;

    @Inject
    PracticeView practiceView;

    @PostConstruct
    public void init() {
        lstLeerResponderDtos = cursoRepository.getLstLeerResponder(practiceView.getCurrent().getIdPregunta());

        initPreguntas();
    }

    private List<PreguntaVocabulario> todasLasPreguntas;
    private PreguntaVocabulario preguntaActual;
    private int indicePreguntaActual;

    private String seleccionUsuario;
    private boolean respuestaCorrecta; // Para cambiar el estilo del mensaje
    private boolean preguntaRespondida; // Para deshabilitar botones después de responder

    private void initPreguntas() {
        todasLasPreguntas = new ArrayList<>();
        Map<Integer, List<LeerResponderDto>> agrupadas = lstLeerResponderDtos.stream()
                .collect(Collectors.groupingBy(LeerResponderDto::getIdLeerReponder, LinkedHashMap::new, Collectors.toList()));

        for (List<LeerResponderDto> grupo : agrupadas.values()) {
            LeerResponderDto primera = grupo.get(0);

            PreguntaVocabulario dto = new PreguntaVocabulario();
            dto.setSonido(primera.getSonido());
            dto.setFrase(primera.getFrase());
            dto.setPalabraResaltada(primera.getPalabra());
            // Encontrar la opción correcta
            grupo.stream()
                    .filter(LeerResponderDto::getCorrecta)
                    .findFirst()
                    .ifPresent(correcta -> {
                        dto.setSignificadoCorrecto(correcta.getOpcion());
                    });

            List<String> lstOpciones = grupo.stream().map(LeerResponderDto::getOpcion).collect(Collectors.toList());
            Collections.shuffle(lstOpciones);
            dto.setOpciones(lstOpciones);

            todasLasPreguntas.add(dto);
        }

        Collections.shuffle(todasLasPreguntas);
        indicePreguntaActual = -1; // Para que la primera llamada a cargarSiguientePregunta empiece en 0

        cargarSiguientePregunta();
    }

    public void cargarSiguientePregunta() {
        seleccionUsuario = null;
        respuestaCorrecta = false;
        preguntaRespondida = false;

        indicePreguntaActual++;
        if (indicePreguntaActual < todasLasPreguntas.size()) {
            preguntaActual = todasLasPreguntas.get(indicePreguntaActual);
        } else {
            // Se terminaron las preguntas
            preguntaActual = null;
        }
    }

    public void seleccionarOpcion(String opcion) {
        if (preguntaActual == null || preguntaRespondida) {
            return; // No hacer nada si no hay pregunta o ya se respondió
        }

        seleccionUsuario = opcion;
        if (seleccionUsuario.equals(preguntaActual.getSignificadoCorrecto())) {
            practiceView.showMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", preguntaActual.getPalabraResaltada() + " SIGNIFICA " + seleccionUsuario);
            respuestaCorrecta = true;

        } else {
            practiceView.showMessage(FacesMessage.SEVERITY_ERROR, "INCORRECTO", "EL SIGNIFICADO CORRECTO ES: " + preguntaActual.getSignificadoCorrecto());
            respuestaCorrecta = false;
        }
        preguntaRespondida = true; // Deshabilitar botones
    }

    // Getters
    public PreguntaVocabulario getPreguntaActual() {
        return preguntaActual;
    }

    public boolean isRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public boolean isPreguntaRespondida() {
        return preguntaRespondida;
    }

    public boolean isJuegoTerminado() {
        return preguntaActual == null;
    }

    @Override
    public void siguiente() {
        if (seleccionUsuario == null) {
            practiceView.showMessage(FacesMessage.SEVERITY_WARN, "INFORMACIÓN", "DEBE DE SELECCIONAR UNA OPCION ANTES DE CONTINUAR");
            return;
        }
        practiceView.nextStep();
    }
}
