/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javafx.scene.layout.Pane;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import mv.MapViewerApp;
import mv.data.DataManager;
import mv.gui.Workspace;
import saf.AppTemplate;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;
import saf.components.AppWorkspaceComponent;
import saf.ui.AppGUI;

/**
 *
 * @author McKillaGorilla
 */
public class FileManager implements AppFileComponent {

    static final String JSON_SUBREGIONS = "SUBREGIONS";
    static final String JSON_SUBREGION_NUMBER = "NUMBER_OF_SUBREGIONS";
    static final String JSON_POLYGON_NUMBER = "NUMBER_OF_SUBREGION_POLYGONS";
    static final String JSON_POLYGONS = "SUBREGION_POLYGONS";
    static final String JSON_X = "X";
    static final String JSON_Y = "Y";  

    MapViewerApp app;
    
    
       
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        DataManager dataManager = (DataManager) data;
        dataManager.reset();

        JsonObject json = loadJSONFile(filePath);

        int numberOfSubregions = getDataAsInt(json, JSON_SUBREGION_NUMBER);
        
        JsonArray subregions = json.getJsonArray(JSON_SUBREGIONS);
        

        for (int i = 0; i < numberOfSubregions; i++) {
            JsonObject currentSubregion = (JsonObject) subregions.get(i);
            int numberOfPolygons = getDataAsInt(currentSubregion, JSON_POLYGON_NUMBER);
            JsonArray currentPolygon = currentSubregion.getJsonArray(JSON_POLYGONS);
//            System.out.println(currentPolygon);  
            
            buildPolygonCoordinates(currentPolygon, dataManager);
            
        }
       
    }
    
    public double getDataAsDouble(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber) value;
        return number.bigDecimalValue().doubleValue();
    }

    public int getDataAsInt(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber) value;
        return number.bigIntegerValue().intValue();
    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void buildPolygonCoordinates(JsonArray jsonArray, DataManager dataManager) {
      
        for (int j = 0; j < jsonArray.size(); j++) {
            JsonArray currentArray = jsonArray.getJsonArray(j);
//            System.out.println(currentArray);
            
            double[] xValues = new double[currentArray.size()];
            double[] yValues = new double[currentArray.size()];
            for(int k = 0; k < currentArray.size(); k++){
                JsonObject currentPoint = (JsonObject) currentArray.get(k);
                
               
                xValues[k] = getDataAsDouble(currentPoint,JSON_X);
                yValues[k] = getDataAsDouble(currentPoint,JSON_Y);
                
                xValues[k] = ((xValues[k] + 180)/360);
                yValues[k] = ((90 - yValues[k])/190);
                
                //xValues[k] = xValues[k] ;
                //yValues[k] = yValues[k];
            }
            
//           System.out.println(xValues[0]);
            
            dataManager.addPolygon(xValues, yValues);
        }

    }

}
