package com.mikiruki.vendingsystemapi.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mikiruki.vendingsystemapi.models.Product;

import java.io.IOException;
import java.io.StringWriter;

public class ProductSerializer extends JsonSerializer<Product> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, product.getName());
        jsonGenerator.writeFieldName(writer.toString());
    }
}
