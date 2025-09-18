package sv.com.jsof.school.repository.dao;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import sv.com.jsof.school.db.dto.ClosedQuestionDto;
import sv.com.jsof.school.db.dto.LeerResponderDto;
import sv.com.jsof.school.db.dto.ParDto;
import sv.com.jsof.school.db.dto.PreguntaDto;
import static sv.com.jsof.school.db.query.QueryNativa.QUERY_CLOSED_QUESTION;
import static sv.com.jsof.school.db.query.QueryNativa.QUERY_CURSOS_AVANCE;
import static sv.com.jsof.school.db.query.QueryNativa.QUERY_LEER_RESPONDER;
import static sv.com.jsof.school.db.query.QueryNativa.QUERY_PARES;
import sv.com.jsof.school.dto.AvanceCurso;

/**
 *
 * @author msanchez
 */
public interface CursoDao {

    @SqlQuery(QUERY_CURSOS_AVANCE)
    @RegisterBeanMapper(AvanceCurso.class)
    List<AvanceCurso> listCursos();

    @SqlQuery("select * from pregunta where id_evaluacion = :idEvaluacion order by orden")
    @RegisterBeanMapper(PreguntaDto.class)
    List<PreguntaDto> listPreguntas(@Bind("idEvaluacion") Integer idEvaluacion);

    @SqlQuery(QUERY_LEER_RESPONDER)
    @RegisterBeanMapper(LeerResponderDto.class)
    List<LeerResponderDto> listLeerResponder(@Bind("idPregunta") Integer idPregunta);

    @SqlQuery(QUERY_PARES)
    @RegisterBeanMapper(ParDto.class)
    List<ParDto> listPares(@Bind("idPregunta") Integer idPregunta);

    @SqlQuery(QUERY_CLOSED_QUESTION)
    @RegisterBeanMapper(ClosedQuestionDto.class)
    List<ClosedQuestionDto> listClosedQuestion(@Bind("idPregunta") Integer idPregunta);
}
