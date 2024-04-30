package com.tibame.group1.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Json處理共用
 *
 * @author Jimmy Kang
 */
public class JsonUtils {

    private static final ObjectMapper objectMapper =
            new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private static final ObjectMapper objectMapperWithUpper =
            new ObjectMapper()
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);

    /**
     * To json.
     *
     * @param obj the obj
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    public static String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * To pretty json.
     *
     * @param obj the obj
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    public static String toPrettyJson(Object obj) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    /**
     * To object.
     *
     * @param <T> the generic type
     * @param clazz the clazz
     * @param jsonString the json string
     * @return the t
     */
    public static <T> T toObject(Class<T> clazz, String jsonString) throws JsonProcessingException {
        return toObject(clazz, jsonString, false);
    }

    /**
     * To object.
     *
     * @param <T> the generic type
     * @param clazz the clazz
     * @param jsonString the json string
     * @param isEnableUnknownKey 是否允許有未知的key
     * @return the t
     */
    public static <T> T toObject(Class<T> clazz, String jsonString, boolean isEnableUnknownKey)
            throws JsonProcessingException {
        if (StringUtils.isEmpty(jsonString)) return null;
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, !isEnableUnknownKey);
        return objectMapper.readValue(jsonString, clazz);
    }

    /**
     * To objects.
     *
     * @param <T> the generic type
     * @param clazz the clazz
     * @param jsonString the json string
     * @return the list
     * @throws JsonProcessingException the json processing exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static <T> List<T> toObjects(Class<T> clazz, String jsonString) throws IOException {
        return toObjects(clazz, jsonString, false);
    }

    /**
     * To objects.
     *
     * @param <T> the generic type
     * @param clazz the clazz
     * @param jsonString the json string
     * @param isEnableUnknownKey 是否允許有未知的key
     * @return the list
     * @throws JsonProcessingException the json processing exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static <T> List<T> toObjects(
            Class<T> clazz, String jsonString, boolean isEnableUnknownKey) throws IOException {
        jsonString = jsonString.replaceAll("&quot;", "\"");
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, !isEnableUnknownKey);
        JsonNode rootNode = objectMapper.readTree(new StringReader(jsonString));
        List<T> ts = new ArrayList<>();
        if (rootNode.isArray()) {
            for (final JsonNode objNode : rootNode) {
                ts.add(objectMapper.treeToValue(objNode, clazz));
            }
            return ts;
        } else {
            T t = toObject(clazz, jsonString);
            if (t != null) {
                ts.add(t);
            }
        }
        return ts;
    }
}
