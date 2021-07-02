package org.zhouhy.jwt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
    
    public static final ObjectMapper mapper = new ObjectMapper();    
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    
    public static String toString(Object obj){
        if(obj == null){
            return null;
        }
        if(obj.getClass() == String.class){
            return (String)obj;
        }

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错：" + obj, e);
            return null;
        }
    }
    
}
