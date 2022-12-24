package eu.heliumiot.console.jpa.db;


import org.hibernate.boot.model.relational.Namespace;
import org.hibernate.boot.model.relational.Sequence;
import org.hibernate.mapping.Table;
import org.hibernate.tool.schema.spi.SchemaFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaFilter implements SchemaFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final JpaFilter INSTANCE = new JpaFilter();

    @Override
    public boolean includeNamespace(Namespace namespace) {
        return true;
    }

    @Override
    public boolean includeTable(Table table) {
        //log.error("#### look for table "+table.getName());
        // make sure we process only the helium_* tables
        if ( table.getName().startsWith("helium_") ) {
            return true;
        }

        return false;
    }

    @Override
    public boolean includeSequence(Sequence sequence) {
        return true;
    }
}