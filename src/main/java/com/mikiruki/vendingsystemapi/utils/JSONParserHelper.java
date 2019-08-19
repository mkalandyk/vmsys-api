package com.mikiruki.vendingsystemapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.lang.reflect.ParameterizedType;

public abstract class JSONParserHelper<T> {

    final static Logger logger = Logger.getLogger(JSONParserHelper.class);

    public T parseJSONToObject(String json){
        ObjectMapper mapper = new ObjectMapper();
        if(!json.equals("{}")) {
            try {
                ParameterizedType superClassType = (ParameterizedType)getClass().getGenericSuperclass();
                Class type = (Class)superClassType.getActualTypeArguments()[0];
                return (T) mapper.readValue(json, type);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
        return null;
    }

}
