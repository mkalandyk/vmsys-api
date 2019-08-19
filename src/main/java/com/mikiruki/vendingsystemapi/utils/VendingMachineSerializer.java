package com.mikiruki.vendingsystemapi.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mikiruki.vendingsystemapi.models.VendingMachine;

import java.io.IOException;
import java.io.StringWriter;

public class VendingMachineSerializer extends JsonSerializer<VendingMachine> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(VendingMachine vendingMachine, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        StringWriter writer = new StringWriter();
        String value = String.format("#%s : %s", vendingMachine.getMachineId(), vendingMachine.getAddress());
        mapper.writeValue(writer, value);
        jsonGenerator.writeFieldName(writer.toString());
    }
}