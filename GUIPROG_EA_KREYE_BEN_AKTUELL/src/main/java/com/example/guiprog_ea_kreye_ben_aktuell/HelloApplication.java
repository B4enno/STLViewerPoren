package com.example.guiprog_ea_kreye_ben_aktuell;

import com.example.guiprog_ea_kreye_ben_aktuell.control.ApplicationController;
import com.example.guiprog_ea_kreye_ben_aktuell.control.PolyhedronController;
import com.example.guiprog_ea_kreye_ben_aktuell.control.STLReader;
import com.example.guiprog_ea_kreye_ben_aktuell.model.Polyhedron;
import com.example.guiprog_ea_kreye_ben_aktuell.view.ConsoleApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        ApplicationController applicationController = new ApplicationController();

        applicationController.readSTLFileInConsole();

        applicationController.getConsoleApplication().printPolyhedron(applicationController.getPolyhedronController().getPolyhedron());

        for(int i = 0; i < 50; i++)
        {
            System.out.println();
        }
        applicationController.getPolyhedronController().getPolyhedron().calculateSurfaceArea();
        applicationController.getPolyhedronController().getPolyhedron().getTriangles().sort(null);
        applicationController.getConsoleApplication().printTriangles(applicationController.getPolyhedronController().getPolyhedron());
        applicationController.getConsoleApplication().printPolyhedron(applicationController.getPolyhedronController().getPolyhedron());

    }
}