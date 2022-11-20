package eu.heliumiot.console.jpa.db;

import org.hibernate.tool.schema.spi.SchemaFilter;
import org.hibernate.tool.schema.spi.SchemaFilterProvider;

public class JpaFilterProvider implements SchemaFilterProvider {

    @Override
    public SchemaFilter getCreateFilter() {
        return JpaFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getDropFilter() {
        return JpaFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getMigrateFilter() {
        return JpaFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getValidateFilter() {
        return JpaFilter.INSTANCE;
    }
}