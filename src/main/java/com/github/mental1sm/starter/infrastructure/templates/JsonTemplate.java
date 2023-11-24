package com.github.mental1sm.starter.infrastructure.templates;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Шаблон json тела для запроса
 */
public interface JsonTemplate<T> {

    /**
     * Метод для получения json тела
     * @param t входные данные, необходимые для размещения их в теле запроса
     * @return json тело, помещенное в одну строку
    */
    String getJsonifiedString(T t) throws JsonProcessingException;
}
