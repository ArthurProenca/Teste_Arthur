package bitway.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class CEPDeserializer implements JsonDeserializer<CEP> {
    @Override
    public CEP deserialize(JsonElement jsonElement, Type member, JsonDeserializationContext context) throws JsonParseException {
        JsonArray array = jsonElement.getAsJsonArray();
        if (array.size() != 10) {
            // outsized array
            throw new JsonParseException("CEP not correctly instanced");
        }
        // cria o aluno, usando as informações do array
        return new CEP(1,
                array.get(1).getAsString(),
                array.get(2).getAsString(),
                array.get(3).getAsString(),
                array.get(4).getAsString(),
                array.get(5).getAsString(),
                array.get(6).getAsString(),
                array.get(7).getAsString(),
                array.get(8).getAsInt(),
                array.get(9).getAsInt(),
                array.get(10).getAsInt());
    }
}
