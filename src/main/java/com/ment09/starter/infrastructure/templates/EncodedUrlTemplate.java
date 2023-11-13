package com.ment09.starter.infrastructure.templates;

import org.springframework.util.MultiValueMap;

public interface EncodedUrlTemplate<T> {
    MultiValueMap<String, String> encodedUrlBody(T t);
}
