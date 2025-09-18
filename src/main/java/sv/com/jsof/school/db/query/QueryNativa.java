package sv.com.jsof.school.db.query;

/**
 *
 * @author msanchez
 */
public class QueryNativa {

    public static final String QUERY_CURSOS_AVANCE = "SELECT \n"
            + "	ac.id_avance_curso,\n"
            + "    c.nombre_curso,\n"
            + "    c.descripcion_curso,\n"
            + "    ac.avance,\n"
            + "    ac.bloqueado,\n"
            + "    ac.finalizado\n"
            + "FROM school.avance_curso ac\n"
            + "	inner join school.curso c on ac.id_curso = c.id_curso";

    public static final String QUERY_LEER_RESPONDER = "SELECT \n"
            + "	lr.id_leer_reponder,\n"
            + "	lr.sonido,\n"
            + "	lr.frase,\n"
            + "    lr.palabra,\n"
            + "    d.frase opcion,\n"
            + "    d.correcta\n"
            + "FROM school.leer_reponder lr\n"
            + "	inner join school.definicion d on lr.id_leer_reponder = d.id_leer_reponder\n"
            + "WHERE id_pregunta = :idPregunta ";

    public static final String QUERY_PARES = "select \n"
            + "	par.elemento_izquierdo,\n"
            + "    par.elemento_derecho\n"
            + "from pregunta p inner join par on p.id_pregunta = par.id_pregunta\n"
            + "where p.id_pregunta = :idPregunta";

    public static final String QUERY_CLOSED_QUESTION = "select \n"
            + "	om.pregunta,\n"
            + "	opc.palabra,\n"
            + "    opc.correcta\n"
            + "from opcion_multiple om\n"
            + "	inner join opcion opc on om.id_opcion_multiple = opc.id_opcion_multiple\n"
            + "where om.id_pregunta = :idPregunta";
}
