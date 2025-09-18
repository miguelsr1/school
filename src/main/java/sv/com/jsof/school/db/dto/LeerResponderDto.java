package sv.com.jsof.school.db.dto;

import lombok.Data;

/**
 *
 * @author msanchez
 */
@Data
public class LeerResponderDto {
    private Integer idLeerReponder;
    private String sonido;
    private String frase;
    private String palabra;
    private String opcion;
    private Boolean correcta;
    private Integer idPregunta;
}
