/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.gui;


import java.util.Vector;
import javafx.scene.Node;
import javafx.scene.canvas.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javax.json.JsonArray;
import javax.json.JsonObject;
import saf.components.AppWorkspaceComponent;
import mv.MapViewerApp;
import mv.PropertyType;
import mv.data.DataManager;
import properties_manager.PropertiesManager;
import saf.ui.AppGUI;

/**
 *
 * @author McKillaGorilla
 */
public class Workspace extends AppWorkspaceComponent {

    static final String MAP_BACKGROUND = "-fx-background-color: #0033cc";
    MapViewerApp app;
    Vector<Polygon> polygons;
    Vector<Double> xVector;
    Vector<Double> yVector;
    Double xScale, yScale;
    Pane pane = new Pane();
    PropertiesManager props = PropertiesManager.getPropertiesManager();

    public Workspace(MapViewerApp initApp) {
        app = initApp;
        DataManager dataManager = (DataManager) app.getDataComponent();
        polygons = dataManager.getPolygons();
        xVector = dataManager.getXVector();
        yVector = dataManager.getYVector();

        // REMOVE THE NEW AND SAVE BUTTONS FROM THE TOOLBAR
        AppGUI mapViewerGUI = app.getGUI();
        BorderPane appPane = mapViewerGUI.getAppPane();
        FlowPane fileToolbarPane = (FlowPane) appPane.getTop();
        fileToolbarPane.getChildren().remove(0);
        fileToolbarPane.getChildren().remove(2);
        

        workspace = new Pane();            
        app.getGUI().getAppPane().setCenter(workspace); 
        
        reloadWorkspace();
        
        
        
        

    }

    @Override
    public void reloadWorkspace() {
        workspace.getChildren().clear();
        workspace.toBack();
        workspace.setStyle(MAP_BACKGROUND);
        setupHandlers();
        
        Polygon thisPolygon = new Polygon();
        
        for (int i = 0; i < polygons.size(); i++) {
            thisPolygon = polygons.elementAt(i);
            thisPolygon.setFill(Color.GREEN);
            thisPolygon.setStroke(Color.BLACK);
            workspace.getChildren().add(thisPolygon);
        }
       // System.out.println("HELLO");       
    }

    @Override
    public void initStyle() {

    }

    public double getWidth() {
        return workspace.getWidth();
    }
    
    public double getHeight() {
        return workspace.getHeight();
    }

    private void setupHandlers() {
        workspace.setOnMouseClicked(e -> {
            System.out.println("Click");
            
            zoomOnMouse(e);
        });
    }

    private void zoomOnMouse(MouseEvent event) {
        
        if (event.getButton() == MouseButton.SECONDARY) {
            if (workspace.getScaleX() > 0){
                workspace.setScaleX(workspace.getScaleX() - 1);
                workspace.setScaleY(workspace.getScaleY() - 1);
            }
        }
        
        else if (event.getButton() == MouseButton.PRIMARY) {
            workspace.setScaleX(workspace.getScaleX() + 1);
            workspace.setScaleY(workspace.getScaleY() + 1);
        }
        
    }
}
