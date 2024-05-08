package com.example.guiprog_ea_kreye_ben_aktuell.control;

//TODO: berechnete Normale zum validieren der normale nutzen
//TODO PolyhedronController implementieren
//TODO berechnung des Volumens
//TODO THREADS FÜR VOLUMEN

import com.example.guiprog_ea_kreye_ben_aktuell.model.Polyhedron;
import com.example.guiprog_ea_kreye_ben_aktuell.view.ConsoleApplication;

import java.io.IOException;

//kkk

public class ApplicationController {

    private PolyhedronController polyhedronController;

    private STLReader stlReader;

    private ConsoleApplication consoleApplication;

    public ApplicationController() {
        this.stlReader = new STLReader();
        this.consoleApplication = new ConsoleApplication();
    }

    public void readSTLFile(String filepath) throws IOException {
        this.polyhedronController = new PolyhedronController(stlReader.readSTL(filepath));
    }

    public void readSTLFileInConsole() throws IOException {
        this.polyhedronController = new PolyhedronController(stlReader.readSTL(consoleApplication.askForFilePath()));
    }

    public void sortTriangles() {
        polyhedronController.getPolyhedron().getTriangles().sort(null);
    }

    public void calculateSurfaceArea() {
        polyhedronController.getPolyhedron().calculateSurfaceArea();
    }

    public void printPolyhedron() {
        consoleApplication.printPolyhedron(polyhedronController.getPolyhedron());
    }

    public void printTriangles() {
        consoleApplication.printTriangles(polyhedronController.getPolyhedron());
    }

    public void printTriangle(int index) {
        consoleApplication.printTriangle(polyhedronController.getPolyhedron(), index);
    }

    public void printLastTriangle() {
        consoleApplication.printLastTriangle(polyhedronController.getPolyhedron());
    }

    public void printFirstTriangle() {
        consoleApplication.printFirstTriangle(polyhedronController.getPolyhedron());
    }

    public void calculateVolume() {
        polyhedronController.calculateVolume();
    }

    public void calculateVolumeAndSurfaceArea() {
        polyhedronController.calculateVolumeAndSurfaceArea();
    }



    public PolyhedronController getPolyhedronController() {
        return polyhedronController;
    }

    public STLReader getStlReader() {
        return stlReader;
    }

    public ConsoleApplication getConsoleApplication() {
        return consoleApplication;
    }
}
