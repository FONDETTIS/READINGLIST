/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package readinglist.idgenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.collections.MapUtils;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.type.Type;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Jeff Fischer
 */
public class IdOverrideTableGenerator extends TableGenerator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IdOverrideTableGenerator.class);

    public static final String ENTITY_NAME_PARAM = "entity_name";
    private static final Map<String, Field> FIELD_CACHE = MapUtils.synchronizedMap(new HashMap<String, Field>());

    private String entityName;

    private Field getIdField(Class<?> clazz) {
    	
    	LOGGER.warn("IDOVERRIDETABLEGENERATOR-getIdField1");
    	
        Field response = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                response = field;
                break;
            }
        }
        if (response == null && clazz.getSuperclass() != null) {
            response = getIdField(clazz.getSuperclass());
        }
        
        LOGGER.warn("IDOVERRIDETABLEGENERATOR-getIdField2-NAME = " + response.getName());

        return response;
    }

    @Override
    public Serializable generate(SessionImplementor session, Object obj) {
        /*
        This works around an issue in Hibernate where if the entityPersister is retrieved
        from the session and used to get the Id, the entity configuration can be recycled,
        which is messing with the load persister and current persister on some collections.
        This may be a jrebel thing, but this workaround covers all environments
         */
    	
    	
    	LOGGER.warn("IDOVERRIDETABLEGENERATOR-generate");
    	
        String objName = obj.getClass().getName();
        if (!FIELD_CACHE.containsKey(objName)) {
            Field field = getIdField(obj.getClass());
            if (field == null) {
                throw new IllegalArgumentException("Cannot specify IdOverrideTableGenerator for an entity (" + objName + ") that does not have an Id field declared using the @Id annotation.");
            }
            field.setAccessible(true);
            FIELD_CACHE.put(objName, field);
        }
        Field field = FIELD_CACHE.get(objName);
        final Serializable id;
        try {
            id = (Serializable) field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if ( id != null ) {
            return id;
        }
        return super.generate(session, obj);
    }

    @Override
    public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
    	
    	    	
    	if (params.get("entity_name").equals("macompagnie.dataPG.Administrator"))
    	{
    	LOGGER.warn("IDOVERRIDETABLEGENERATOR-configure");
    	LOGGER.warn("IDOVERRIDETABLEGENERATOR-table_name = " + params.get("table_name"));
    	LOGGER.warn("IDOVERRIDETABLEGENERATOR-entity_name = " + params.get(ENTITY_NAME_PARAM));
    	LOGGER.warn("IDOVERRIDETABLEGENERATOR-segment_column_name = " + params.get("segment_column_name"));
    	LOGGER.warn("IDOVERRIDETABLEGENERATOR-value_column_name = " + params.get("value_column_name"));
    	}
    	
        if (params.get("table_name") == null) {
            params.put("table_name", "SEQUENCE_GENERATOR");
        }

        if (params.get("segment_column_name") == null) {
            params.put("segment_column_name", "ID_NAME");
        }

        if (params.get("value_column_name") == null) {
            params.put("value_column_name", "ID_VAL");
        }

        if (params.get("increment_size") == null) {
            //params.put("increment_size", 50);
            params.put("increment_size", 1);
        }
        super.configure(type, params, dialect);
        entityName = (String) params.get(ENTITY_NAME_PARAM);
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
