package sv.com.jsof.school.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import sv.com.jsof.school.dto.CursoDetalleDto;
import sv.com.jsof.school.dto.LeccionDto;

/**
 *
 * @author migue
 */
@Named
@ViewScoped
public class CursoDetalleView implements Serializable {

    private Long cursoId = 1l;
    private Long leccionActualId = 1l;
    private CursoDetalleDto curso; // DTO más completo para el detalle del curso
    private LeccionDto leccionActual;

    // --- Getters y Setters para cursoId y leccionActualId (desde viewParam) ---
    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public Long getLeccionActualId() {
        return leccionActualId;
    }

    public void setLeccionActualId(Long leccionActualId) {
        this.leccionActualId = leccionActualId;
    }

    // --- Getters para curso y leccionActual ---
    public CursoDetalleDto getCurso() {
        return curso;
    }

    public LeccionDto getLeccionActual() {
        return leccionActual;
    }

    public void loadCourseDetails() {
        if (cursoId == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "ID de curso no especificado."));
            return;
        }
        // Simular carga de datos reales de BD
        // En una aplicación real, aquí llamarías a un servicio para obtener el curso y sus lecciones
        if (cursoId == 1L) {
            curso = new CursoDetalleDto(1L, "Introducción a Java", 60,
                    Arrays.asList(new LeccionDto(1L, "Variables y Tipos de Datos", "https://www.youtube.com/embed/dQw4w9WgXcQ?autoplay=1", "Aprende qué son las variables y los tipos de datos en Java.", true, true),
                            new LeccionDto(2L, "Condicionales (if/else)", "https://www.youtube.com/embed/Fq85F0R542g?autoplay=1", "Explora cómo tomar decisiones en tu código con if/else.", true, true),
                            new LeccionDto(3L, "Bucles (for/while)", "https://www.youtube.com/embed/B_mS2jL8h6c?autoplay=1", "Domina los bucles para repetir acciones.", false, false), // Lección no completada aún
                            new LeccionDto(4L, "Evaluación - Módulo 1", "https://www.youtube.com/embed/fake-url-for-evaluation", "Realiza la evaluación del primer módulo.", false, false), // Lección de tipo evaluación, bloqueada hasta el momento
                            new LeccionDto(5L, "Clases y Objetos", "https://www.youtube.com/embed/fake-url-for-oop", "Introducción a la Programación Orientada a Objetos.", false, false) // Bloqueada
                    )
            );
            // Marcar lecciones anteriores como completadas (solo para demo)
            curso.getLecciones().get(0).setCompletada(true);
            curso.getLecciones().get(1).setCompletada(true);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Curso no encontrado."));
            curso = new CursoDetalleDto(cursoId, "Curso Desconocido", 0, new ArrayList<>());
        }

        // Encontrar la lección actual
        if (leccionActualId != null) {
            Optional<LeccionDto> found = curso.getLecciones().stream()
                    .filter(l -> l.getId().equals(leccionActualId))
                    .findFirst();
            if (found.isPresent()) {
                leccionActual = found.get();
                // Aquí podrías marcar la lección como "vista" o "en progreso"
                if (!leccionActual.getCompletada()) {
                    // Lógica para marcar como completada al cargar o al ver el video
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Lección Iniciada", "Has comenzado la lección: " + leccionActual.getTitulo()));
                    // Simular que se marca como completada al iniciarla
                    leccionActual.setCompletada(true);
                    //updateCourseProgress();
                }
            } else {
                // Si la lección no existe o está bloqueada, redirige a la primera disponible o muestra error
                leccionActual = curso.getLecciones().stream().filter(l -> !l.getBloqueada()).findFirst().orElse(null);
                if (leccionActual == null) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No hay lecciones disponibles para este curso."));
                } else {
                    leccionActualId = leccionActual.getId();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Lección no encontrada o bloqueada, cargando la primera disponible."));
                }
            }
        } else if (!curso.getLecciones().isEmpty()) {
            // Si no se especifica leccionId, cargar la primera lección disponible
            leccionActual = curso.getLecciones().get(0);
            leccionActualId = leccionActual.getId();
        }
    }
}
