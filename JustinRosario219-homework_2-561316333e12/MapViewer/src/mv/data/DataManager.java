package mv.data;



import java.util.Vector;
import javafx.scene.shape.Polygon;
import javax.json.JsonArray;
import saf.components.AppDataComponent;
import mv.MapViewerApp;
import mv.gui.Workspace;
import saf.ui.AppGUI;

/**
 *
 * @author McKillaGorilla
 */
public class DataManager implements AppDataComponent {
    MapViewerApp app;
    Vector<Polygon> polygons = new Vector();
    Vector<Double> xVector = new Vector();
    Vector<Double> yVector = new Vector();
    AppGUI appGUI;
    Double xScale, yScale;
    Workspace workspace;
    
    public DataManager(MapViewerApp initApp) {
        app = initApp;
        workspace = (Workspace)app.getWorkspaceComponent();
    }
    
    public void addPolygon(double[] xValues, double[] yValues) {
        Polygon newPolygon = new Polygon();   
        for (int i = 0; i < xValues.length; i++) {
                        
            newPolygon.getPoints().add(xValues[i]* app.getGUI().getPrimaryScene().getWidth());
            newPolygon.getPoints().add(yValues[i]* app.getGUI().getPrimaryScene().getHeight());
        }
//        System.out.println(newPolygon);
        polygons.add(newPolygon);
    }
    
    public Vector<Polygon> getPolygons() {
        return polygons;
    }
    
    public Vector<Double> getXVector() {
        return xVector;
    }
    
        public Vector<Double> getYVector() {
        return yVector;
    }
    
    @Override
    public void reset() {
        
    }



 
    
  
}
