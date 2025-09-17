package sv.com.jsof.school.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class VocabularioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // Clase interna para representar una pregunta
    public static class PreguntaVocabulario {

        private String oracion;
        private String palabraResaltada;
        private String significadoCorrecto;
        private List<String> opciones; // Incluye el correcto y 3 distractores

        public PreguntaVocabulario(String oracion, String palabraResaltada, String significadoCorrecto, List<String> distractores) {
            this.oracion = oracion;
            this.palabraResaltada = palabraResaltada;
            this.significadoCorrecto = significadoCorrecto;
            this.opciones = new ArrayList<>(distractores);
            this.opciones.add(significadoCorrecto); // Añadir el correcto a las opciones
            Collections.shuffle(this.opciones); // Mezclar las opciones
        }

        // Getters
        public String getOracion() {
            return oracion;
        }

        public String getPalabraResaltada() {
            return palabraResaltada;
        }

        public String getSignificadoCorrecto() {
            return significadoCorrecto;
        }

        public List<String> getOpciones() {
            return opciones;
        }

        // Método para construir la oración con la palabra resaltada
        public String getOracionConResaltado() {
            return oracion.replace(palabraResaltada, "<span class='highlight'> " + palabraResaltada + " </span>");
        }
    }

    private List<PreguntaVocabulario> todasLasPreguntas;
    private PreguntaVocabulario preguntaActual;
    private int indicePreguntaActual;

    private String seleccionUsuario;
    private String mensaje;
    private boolean respuestaCorrecta; // Para cambiar el estilo del mensaje
    private boolean preguntaRespondida; // Para deshabilitar botones después de responder

    public VocabularioBean() {
        inicializarPreguntas();
        cargarSiguientePregunta();
    }

    private void inicializarPreguntas() {
        todasLasPreguntas = new ArrayList<>();
        // Pregunta 1
        todasLasPreguntas.add(new PreguntaVocabulario(
                "El río se desbordó e inundó el pueblo.",
                "desbordó",
                "se salió de sus límites",
                List.of("se secó", "se congeló", "se oscureció")
        ));
        // Pregunta 2
        todasLasPreguntas.add(new PreguntaVocabulario(
                "Su rostro mostraba una expresión de júbilo tras la victoria.",
                "júbilo",
                "gran alegría",
                List.of("tristeza", "cansancio", "confusión")
        ));
        // Pregunta 3
        todasLasPreguntas.add(new PreguntaVocabulario(
                "La antigua casa tenía un aspecto lúgubre.",
                "lúgubre",
                "sombrío y triste",
                List.of("alegre y brillante", "moderno y funcional", "cómodo y acogedor")
        ));
        // Pregunta 4
        todasLasPreguntas.add(new PreguntaVocabulario(
                "Es un hombre muy perspicaz para los negocios.",
                "perspicaz",
                "agudo y astuto",
                List.of("despistado", "lento", "ingenuo")
        ));
        // Pregunta 5
        todasLasPreguntas.add(new PreguntaVocabulario(
                "Tenía una postura erguida y elegante.",
                "erguida",
                "recta y derecha",
                List.of("curvada", "relajada", "inclinada")
        ));

        Collections.shuffle(todasLasPreguntas); // Mezclar el orden de las preguntas
        indicePreguntaActual = -1; // Para que la primera llamada a cargarSiguientePregunta empiece en 0
    }

    public void cargarSiguientePregunta() {
        seleccionUsuario = null;
        mensaje = "";
        respuestaCorrecta = false;
        preguntaRespondida = false;

        indicePreguntaActual++;
        if (indicePreguntaActual < todasLasPreguntas.size()) {
            preguntaActual = todasLasPreguntas.get(indicePreguntaActual);
        } else {
            // Se terminaron las preguntas
            preguntaActual = null;
            mensaje = "¡Has completado todas las preguntas!";
        }
    }

    public void seleccionarOpcion(String opcion) {
        if (preguntaActual == null || preguntaRespondida) {
            return; // No hacer nada si no hay pregunta o ya se respondió
        }

        seleccionUsuario = opcion;
        if (seleccionUsuario.equals(preguntaActual.getSignificadoCorrecto())) {
            mensaje = "¡Correcto!";
            respuestaCorrecta = true;
        } else {
            mensaje = "Incorrecto. El significado correcto era: " + preguntaActual.getSignificadoCorrecto();
            respuestaCorrecta = false;
        }
        preguntaRespondida = true; // Deshabilitar botones
    }

    // Getters
    public PreguntaVocabulario getPreguntaActual() {
        return preguntaActual;
    }

    public String getMensaje() {
        return mensaje;
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
}
