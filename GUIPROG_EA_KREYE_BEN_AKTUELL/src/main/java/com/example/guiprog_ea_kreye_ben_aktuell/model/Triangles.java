package com.example.guiprog_ea_kreye_ben_aktuell.model;


import java.util.ArrayList;

public class Triangles extends ArrayList<Triangle>
{
    public Triangles ()
    {
        super();
    }

    public Triangles (int initialCapacity)
    {
        super(initialCapacity);
    }

    public void addTriangle (Triangle triangle)
    {
        this.add(triangle);
    }


    public double calculateSurfaceArea ()
    {
        double surfaceArea = 0;
        for (Triangle triangle : this)
        {
            surfaceArea += triangle.calculateArea();
        }
        return surfaceArea;
    }

    @Override
    public String toString ()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (Triangle triangle : this)
        {
            stringBuilder.append(triangle.toString());
        }
        return stringBuilder.toString();
    }
}