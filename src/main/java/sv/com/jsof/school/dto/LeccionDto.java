package sv.com.jsof.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author migue
 */
@Data
@AllArgsConstructor
public class LeccionDto {

    private Long id;
    private String titulo;
    private String videoUrl;
    private String descripcion;
    private Boolean bloqueada;
    private Boolean completada;
}
