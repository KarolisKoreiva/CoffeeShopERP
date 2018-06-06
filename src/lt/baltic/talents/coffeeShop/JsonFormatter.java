package lt.baltic.talents.coffeeShop;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import com.google.gson.Gson;

/**
 * 
 * Creates new formatter that gives messages in json format
 * 
 * @author Karolis
 *
 */

public class JsonFormatter extends Formatter{

	private Gson gson;
	
	public JsonFormatter(){
		gson = new Gson();
	}
	
	@Override
	public String format(LogRecord record) {
		
		return gson.toJson(record) + "\n";
	}

}
