package com.example.guiprog_ea_kreye_ben_aktuell.model;

public class Polyhedron {


    private Triangles triangles;

    private double volume;

    private double surfaceArea;


    public Polyhedron (Triangles triangles)
    {
        this.triangles = triangles;
    }

    public double calculateSurfaceArea () {
        return this.surfaceArea = triangles.calculateSurfaceArea();
    }

    public double getSurfaceArea ()
    {
        return surfaceArea;
    }


    public Triangles getTriangles ()
    {
        return triangles;
    }

    public void setTriangles (Triangles triangles)
    {
        this.triangles = triangles;
    }

    @Override
    public String toString() {
        return "Polyhedron:" +
                "\n\tVolume: " + String.format("%.2f", volume) +
                "\n\tSurface area: " + String.format("%.2f", surfaceArea) +
                "\n\tQuantity of Triangles: " + triangles.size();
    }
}
