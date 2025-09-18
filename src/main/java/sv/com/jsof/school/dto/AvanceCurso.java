package sv.com.jsof.school.dto;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author msanchez
 */
@Data
public class AvanceCurso implements Serializable {
    private Integer idAvanceCurso;
    private String nombreCurso;
    private String descripcionCurso;
    private Integer avance;
    private Boolean bloqueado;
    private Boolean finalizado;
}
