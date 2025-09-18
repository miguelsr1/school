package sv.com.jsof.school.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import sv.com.jsof.school.dto.AvanceCurso;
import sv.com.jsof.school.dto.CursoDto;
import sv.com.jsof.school.repository.CursoRepository;

/**
 *
 * @author migue
 */
@Named
@ViewScoped
public class DashboardEstudianteView implements Serializable {

    private List<CursoDto> cursosEnProgreso;
    private List<CursoDto> cursosFinalizados;
    private int activeIndex = 0; // Para el p:tabView
    
    
    @Getter
    private List<AvanceCurso> lstAvanceCurso;

    @Inject
    CursoRepository cursoRepository;


    @PostConstruct
    public void init() {
        lstAvanceCurso = cursoRepository.getLstCursos();
        
        // Simulación de datos para demostración
        /*cursosEnProgreso = new ArrayList<>();
        cursosEnProgreso.add(new CursoDto(1L, "Introducción a Java", "Aprende los fundamentos de Java.", 60, "https://via.placeholder.com/300x150/FF5733/FFFFFF?text=Java"));
        cursosEnProgreso.add(new CursoDto(2L, "Desarrollo Web con JSF", "Aprende Jakarta Faces y PrimeFaces.", 25, "https://via.placeholder.com/300x150/3366FF/FFFFFF?text=JSF"));
        cursosEnProgreso.add(new CursoDto(3L, "Bases de Datos SQL", "Modelo y Consulta de Datos.", 90, "https://via.placeholder.com/300x150/33CC33/FFFFFF?text=SQL"));

        cursosFinalizados = new ArrayList<>();
        cursosFinalizados.add(new CursoDto(4L, "Fundamentos de HTML/CSS", "Crea páginas web estáticas.", 100, "https://via.placeholder.com/300x150/FFCC00/000000?text=HTML/CSS"));*/
    }

    // --- Métodos de Acción ---
    public String continuarCurso(Long cursoId) {
        // Lógica para redirigir a la primera lección o la última lección vista
        // Por ahora, redirigimos a una página genérica de detalle de curso
        return "/cursoDetalle.xhtml?faces-redirect=true&cursoId=" + cursoId + "&leccionId=1";
    }

    public void verCertificado(Long cursoId) {
        // Lógica para generar y mostrar el certificado
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Certificado", "Generando certificado para curso " + cursoId));
    }

    // --- Getters y Setters ---
    public List<CursoDto> getCursosEnProgreso() {
        return cursosEnProgreso;
    }

    public List<CursoDto> getCursosFinalizados() {
        return cursosFinalizados;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }
}
