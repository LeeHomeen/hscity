package kr.co.socsoft.util;
 
 
import java.util.List;
import java.util.Map;
 
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.SimpleType;
import org.codehaus.jackson.type.JavaType;
 
 
public class JsonHelper {
 
    private static ObjectMapper mJsonMapper;
 
 
    public static final synchronized ObjectMapper getInstance() {
        if (mJsonMapper == null) {
            mJsonMapper = new ObjectMapper();
        }
        return mJsonMapper;
    }
 
 
    public static final synchronized void recycleInstance() {
        if (mJsonMapper != null) {
            mJsonMapper = null;
        }
    }
 
 
    public static final <T> T parseJsonObject(String source, Class<T> resultClass) {
        T instance = null;
        try {
            instance = getInstance().readValue(source, resultClass);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }
 
 
    public static final <T> List<T> parseJsonArray(String source, Class<T> itemClass) {
        List<T> resultList = null;
        try {
            JavaType javaType = CollectionType.construct(List.class, SimpleType.construct(itemClass));
            resultList = getInstance().readValue(source, javaType);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
 
 
    public static final Map<String, String> parseJsonMap(String source) {
        Map<String, String> resultMap = null;
 
        try {
            JavaType type = SimpleType.construct(String.class);
            MapType typeRef = MapType.construct(Map.class, type, type);
 
            resultMap = getInstance().readValue(source, typeRef);
        }
        catch (Exception e) {
        }
        return resultMap;
    }
 
 
    public static final <T> Map<String, List<T>> parseJsonListMap(String source, Class<T> itemClass) {
        Map<String, List<T>> resultMap = null;
 
        try {
            JavaType keyType = SimpleType.construct(String.class);
            JavaType valueType = CollectionType.construct(List.class, SimpleType.construct(itemClass));
            MapType typeRef = MapType.construct(Map.class, keyType, valueType);
 
            resultMap = getInstance().readValue(source, typeRef);
        }
        catch (Exception e) {
        }
        return resultMap;
    }
 
 
    public static final <T> T parseJsonObject(JsonNode node, Class<T> resultClass) {
        T instance = null;
        try {
            instance = getInstance().readValue(node, resultClass);
        }
        catch (Exception e) {
        }
        return instance;
    }
 
 
    public static final <T> List<T> parseJsonArray(JsonNode node, Class<T> itemClass) {
        List<T> resultList = null;
        try {
            JavaType javaType = CollectionType.construct(List.class, SimpleType.construct(itemClass));
            resultList = getInstance().readValue(node, javaType);
        }
        catch (Exception e) {
        }
        return resultList;
    }
}