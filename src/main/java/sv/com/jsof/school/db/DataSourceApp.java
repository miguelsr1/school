package sv.com.jsof.school.db;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class DataSourceApp {

    @Getter
    @Resource(lookup = "java:/eschoolDS")
    private DataSource eschoolDS;

}
