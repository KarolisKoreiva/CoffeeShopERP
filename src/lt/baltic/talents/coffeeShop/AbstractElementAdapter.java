package lt.baltic.talents.coffeeShop;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AbstractElementAdapter implements JsonSerializer<Dishes>, JsonDeserializer<Dishes> {
    
	private static final Logger logger = Logger.getLogger(Utils.class.getName());
	
	@Override
    public JsonElement serialize(Dishes src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }

    @Override
    public Dishes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return context.deserialize(element, Class.forName("lt.baltic.talents.coffeeShop." + type));
        } catch (ClassNotFoundException cnfe) {
            JsonParseException e = new JsonParseException("Unknown element type: " + type, cnfe);
            logger.warning(e.getMessage());
            throw e;
        }
    }
}