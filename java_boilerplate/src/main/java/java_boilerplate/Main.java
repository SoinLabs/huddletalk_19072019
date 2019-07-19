package java_boilerplate;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class Main implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

    public ApiGatewayResponse handleRequest(ApiGatewayRequest request, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Starting...");

        //Function Logic
        String response;
        if (request.getHeaders().get("Content-Type") == "application/json") {
            // If you want to parse a JSON request to class (POJO)
            Gson g = new Gson();
            RequestClass requestObj = g.fromJson(request.getBody(), RequestClass.class);
            //response = "{\"message\": \"" + new StringBuilder(requestObj.getParameter()).reverse().toString() + "\"}";
            response = new StringBuilder(requestObj.getParameter()).reverse().toString();
        } else {
            //response = "{\"message\": \"" + new StringBuilder(request.getBody()).reverse().toString() + "\"}";
            response = new StringBuilder(request.getBody()).reverse().toString();
        }

        //logger.log(this.convertWithIteration(request.getHeaders()));

        logger.log("Done!");

        Map<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type","text/plain");
        //headers.put("Content-Type", "application/json");

        //logger.log(this.convertWithIteration(headers));

        return new ApiGatewayResponse(200, headers, response);
    }
    
    public String convertWithIteration(Map<String, ?> map) {
        StringBuilder mapAsString = new StringBuilder("{");
        for (String key : map.keySet()) {
            mapAsString.append(key + "=" + map.get(key) + ", ");
        }
        mapAsString.delete(mapAsString.length() - 2, mapAsString.length()).append("}");
        return mapAsString.toString();
    }
}