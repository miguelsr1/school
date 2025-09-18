package sv.com.jsof.school.db;

import lombok.Getter;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class DataBaseSupport {

    @Inject
    DataSourceApp dsApp;

    @Getter
    Jdbi jdbi;

    @PostConstruct
    public void init() {
        try {
            jdbi = Jdbi.create(dsApp.getEschoolDS());
            jdbi.installPlugin(new SqlObjectPlugin());
        } catch (Exception ex) {
            log.error(Thread.currentThread().getId() + ": OCURRIO UN ERROR EN DataBaseSupport.init()", ex);
        }
    }
}
