package sv.com.jsof.school.repository;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.jdbi.v3.core.Jdbi;
import sv.com.jsof.school.db.DataBaseSupport;
import sv.com.jsof.school.db.dto.ClosedQuestionDto;
import sv.com.jsof.school.db.dto.LeerResponderDto;
import sv.com.jsof.school.db.dto.ParDto;
import sv.com.jsof.school.db.dto.PreguntaDto;
import sv.com.jsof.school.dto.AvanceCurso;
import sv.com.jsof.school.repository.dao.CursoDao;

/**
 *
 * @author msanchez
 */
@Stateless
public class CursoRepository {

    Jdbi jdbi;

    @Inject
    DataBaseSupport dataBaseSupport;

    CursoDao cursoDao;

    @PostConstruct
    public void init() {
        jdbi = dataBaseSupport.getJdbi();
        cursoDao = jdbi.onDemand(CursoDao.class);
    }

    public List<AvanceCurso> getLstCursos() {
        return cursoDao.listCursos();
    }

    public List<PreguntaDto> getLstPreguntas(Integer idEvaluacion) {
        return cursoDao.listPreguntas(idEvaluacion);
    }

    public List<LeerResponderDto> getLstLeerResponder(Integer idPregunta){
        return cursoDao.listLeerResponder(idPregunta);
    }
    
    public List<ParDto> getLstPar(Integer idPregunta){
        return cursoDao.listPares(idPregunta);
    }
    
    public List<ClosedQuestionDto> getLstClosedQuestion(Integer idPregunta){
        return cursoDao.listClosedQuestion(idPregunta);
    }
}
