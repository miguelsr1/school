package sv.com.jsof.school.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import sv.com.jsof.school.db.dto.ParDto;
import sv.com.jsof.school.repository.CursoRepository;
import sv.com.jsof.school.view.practice.PracticeView;
import sv.com.jsof.school.view.practice.Pregunta;

@Named
@ViewScoped
public class MatchView implements Serializable, Pregunta {

    private static final long serialVersionUID = 1L;

    @Inject
    PracticeView practiceView;
    @Inject
    CursoRepository cursoRepository;

    @Getter
    private boolean finalizado = false;

    private Map<String, String> parejasOriginales; // Mapa para almacenar las parejas correctas
    private List<String> palabrasIzquierda; // Palabras a mostrar en la columna izquierda
    private List<String> palabrasDerecha; // Palabras a mostrar en la columna derecha (desordenadas)

    private String seleccionIzquierda; // Almacena la palabra seleccionada de la izquierda
    private String seleccionDerecha; // Almacena la palabra seleccionada de la derecha

    private List<String> parejasEncontradasIzquierda; // Para deshabilitar botones de parejas encontradas
    private List<String> parejasEncontradasDerecha; // Para deshabilitar botones de parejas encontradas

    private String mensaje; // Mensajes para el usuario
    private int numeroIntentos = 0;

    private List<ParDto> lstPar;

    @PostConstruct
    public void duolingoBean() {
        inicializarJuego();
    }

    public void inicializarJuego() {
        lstPar = cursoRepository.getLstPar(practiceView.getCurrent().getIdPregunta());

        parejasOriginales = new HashMap<>();

        parejasOriginales = lstPar.stream()
                .collect(Collectors.toMap(ParDto::getElementoIzquierdo, ParDto::getElementoDerecho));

        /*parejasOriginales.put("mesa", "table");
        parejasOriginales.put("libro", "book");
        parejasOriginales.put("agua", "water");
        parejasOriginales.put("perro", "dog");
        parejasOriginales.put("gato", "cat");
        parejasOriginales.put("sol", "sun");*/
        palabrasIzquierda = new ArrayList<>(parejasOriginales.keySet());
        palabrasDerecha = new ArrayList<>(parejasOriginales.values());
        Collections.shuffle(palabrasDerecha); // Desordenar la columna derecha

        seleccionIzquierda = null;
        seleccionDerecha = null;
        mensaje = "";

        parejasEncontradasIzquierda = new ArrayList<>();
        parejasEncontradasDerecha = new ArrayList<>();
    }

    public void seleccionarIzquierda(String palabra) {
        if (!parejasEncontradasIzquierda.contains(palabra)) {
            seleccionIzquierda = palabra;
            verificarPareja();
        }
    }

    public void seleccionarDerecha(String palabra) {
        if (!parejasEncontradasDerecha.contains(palabra)) {
            seleccionDerecha = palabra;
            verificarPareja();
        }
    }

    public void verificarPareja() {
        if (seleccionIzquierda != null && seleccionDerecha != null) {
            String palabraEsperada = parejasOriginales.get(seleccionIzquierda);
            if (palabraEsperada != null && palabraEsperada.equals(seleccionDerecha)) {
                parejasEncontradasIzquierda.add(seleccionIzquierda);
                parejasEncontradasDerecha.add(seleccionDerecha);
            } else {
                numeroIntentos++;
                mensaje = "<span style='color:red'>Intentos fallidos: " + numeroIntentos + "</span>";
            }
            // Reiniciar selecciones para la siguiente ronda
            seleccionIzquierda = null;
            seleccionDerecha = null;

            if (parejasEncontradasIzquierda.size() == palabrasIzquierda.size()) {
                mensaje = " ¡Felicidades, has encontrado todas las parejas! </br><span style='color:red'>Intentos fallidos: " + numeroIntentos + "</span>";
                finalizado = true;
            }
        }
    }

    // Getters y Setters
    public List<String> getPalabrasIzquierda() {
        return palabrasIzquierda;
    }

    public List<String> getPalabrasDerecha() {
        return palabrasDerecha;
    }

    public String getSeleccionIzquierda() {
        return seleccionIzquierda;
    }

    public void setSeleccionIzquierda(String seleccionIzquierda) {
        this.seleccionIzquierda = seleccionIzquierda;
    }

    public String getSeleccionDerecha() {
        return seleccionDerecha;
    }

    public void setSeleccionDerecha(String seleccionDerecha) {
        this.seleccionDerecha = seleccionDerecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<String> getParejasEncontradasIzquierda() {
        return parejasEncontradasIzquierda;
    }

    public List<String> getParejasEncontradasDerecha() {
        return parejasEncontradasDerecha;
    }

    public boolean isBotonIzquierdaDeshabilitado(String palabra) {
        return parejasEncontradasIzquierda.contains(palabra);
    }

    public boolean isBotonDerechaDeshabilitado(String palabra) {
        return parejasEncontradasDerecha.contains(palabra);
    }

    @Override
    public void siguiente() {
        if (parejasEncontradasIzquierda.size() != palabrasIzquierda.size()) {
            practiceView.showMessage(FacesMessage.SEVERITY_WARN, "INFORMACIÓN", "DEBE DE ENCONTRAR TODAS LAS PAREJAS PARA CONTINUAR");
            return;
        }
        practiceView.nextStep();
    }
}
