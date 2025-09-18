package sv.com.jsof.school.db.dto;

import lombok.Data;

/**
 *
 * @author msanchez
 */
@Data
public class PreguntaDto {

    private Integer idPregunta;
    private Integer idEvaluacion;
    private Integer idTipoPregunta;
    private Integer order;
    private String descripcion;
}
