package sv.com.jsof.school.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class DuolingoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parejasOriginales; // Mapa para almacenar las parejas correctas
    private List<String> palabrasIzquierda; // Palabras a mostrar en la columna izquierda
    private List<String> palabrasDerecha; // Palabras a mostrar en la columna derecha (desordenadas)

    private String seleccionIzquierda; // Almacena la palabra seleccionada de la izquierda
    private String seleccionDerecha; // Almacena la palabra seleccionada de la derecha

    private List<String> parejasEncontradasIzquierda; // Para deshabilitar botones de parejas encontradas
    private List<String> parejasEncontradasDerecha; // Para deshabilitar botones de parejas encontradas

    private String mensaje; // Mensajes para el usuario

    public DuolingoBean() {
        inicializarJuego();
    }

    public void inicializarJuego() {
        parejasOriginales = new HashMap<>();
        parejasOriginales.put("mesa", "table");
        parejasOriginales.put("libro", "book");
        parejasOriginales.put("agua", "water");
        parejasOriginales.put("perro", "dog");
        parejasOriginales.put("gato", "cat");
        parejasOriginales.put("sol", "sun");

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
                mensaje = "¡Correcto! Has encontrado una pareja.";
                parejasEncontradasIzquierda.add(seleccionIzquierda);
                parejasEncontradasDerecha.add(seleccionDerecha);
            } else {
                mensaje = "Incorrecto. Intenta de nuevo.";
            }
            // Reiniciar selecciones para la siguiente ronda
            seleccionIzquierda = null;
            seleccionDerecha = null;

            if (parejasEncontradasIzquierda.size() == palabrasIzquierda.size()) {
                mensaje += " ¡Felicidades, has encontrado todas las parejas!";
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
}
