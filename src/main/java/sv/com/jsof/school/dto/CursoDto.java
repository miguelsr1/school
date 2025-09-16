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
    private String nombre;
    private String descripcionCorta;
    private int progreso; // Porcentaje de avance
    private String imagenUrl;

}
