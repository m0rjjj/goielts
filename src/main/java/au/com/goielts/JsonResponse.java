package au.com.goielts;

import java.util.HashMap;
import java.util.Map;

public class JsonResponse {
	public boolean success = false;
	public Map<String, Object> data = new HashMap<>();


	public JsonResponse(boolean success) {
		super();
		this.success = success;
	}
	
	public JsonResponse(boolean success, Map<String, Object> data) {
		super();
		this.success = success;
		this.data = data;
	}
	
	public static JsonResponse factory(boolean status){
		return new JsonResponse(status);
	}

	public static JsonResponse factory(boolean status, Map<String, Object> data){
		return new JsonResponse(status, data);
	}
}
