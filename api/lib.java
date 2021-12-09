package api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * this class contains function that we use in all the project.
 */
public class lib {

    /**
     * the function will get Json file name and will return JSON object.
     * @param filename
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String jsonString = new String(Files.readAllBytes(Paths.get(filename)));
        JSONObject json = new JSONObject(jsonString);
        return json;
    }

}

