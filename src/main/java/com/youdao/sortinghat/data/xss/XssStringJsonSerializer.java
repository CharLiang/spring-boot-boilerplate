/**
 * @(#)XssStringJsonSerializer.java, 2018-09-06.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.xss;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;

/**
 * XssStringJsonSerializer
 *
 * @author 
 *
 */
public class XssStringJsonSerializer extends JsonSerializer<String> {

    @Override
    public Class<String> handledType() {
        return String.class;
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            String encodedValue = HtmlUtils.htmlEscape(value);
            jsonGenerator.writeString(encodedValue);
        }
    }

}