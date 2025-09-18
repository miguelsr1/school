package sv.com.jsof.school.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author migue
 */
@Data
@AllArgsConstructor
public class CursoDto implements Serializable {

    private Long id;
    private String nombreCurso;
    private String descripcionCurso;
    private int idNivel;
    private String imagen;

}
