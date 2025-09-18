package sv.com.jsof.school.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author msanchez
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaVocabulario {

    private String sonido;
    private String frase;
    private String palabraResaltada;
    private String significadoCorrecto;
    private List<String> opciones;

    public String getOracionConResaltado() {
        return frase.replace(palabraResaltada, "<span class='highlight'> " + palabraResaltada + " </span>");
    }
}
