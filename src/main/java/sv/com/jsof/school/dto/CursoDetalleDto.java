package sv.com.jsof.school.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author migue
 */
@Data
@AllArgsConstructor
public class CursoDetalleDto {

    private Long id;
    private String nombre;
    private int progreso;
    private List<LeccionDto> lecciones;
}
