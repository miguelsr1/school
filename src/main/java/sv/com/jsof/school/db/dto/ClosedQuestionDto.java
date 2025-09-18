package sv.com.jsof.school.db.dto;

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
public class ClosedQuestionDto {

    private String pregunta;
    private String palabra;
    private Boolean correcta;
}
