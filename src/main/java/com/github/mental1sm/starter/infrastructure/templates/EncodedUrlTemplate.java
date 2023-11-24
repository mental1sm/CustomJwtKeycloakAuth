package com.github.mental1sm.starter.infrastructure.templates;

import org.springframework.util.MultiValueMap;

/**
 * Шаблон encodedurl для запроса
*/
public interface EncodedUrlTemplate<T> {
    /**
     * Метод для получения encodedurl тела
     * @param t входные данные, необходимые для размещения их в теле запроса
     * @return urlencodedbody в формате MultiValueMap
    */
    MultiValueMap<String, String> encodedUrlBody(T t);
}
