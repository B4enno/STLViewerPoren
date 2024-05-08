package com.example.guiprog_ea_kreye_ben_aktuell.model;

import javax.vecmath.Vector3d;

public class Triangle implements Comparable<Triangle>
{
    private Vertex[] vertices = new Vertex[3];
    private Vector3d[] edges = new Vector3d[3];
    private Vector3d normal = new Vector3d(0, 0, 0);
    private double area;


    /**
     * Create a triangle from three vertices.
     * @param v1
     * @param v2
     * @param v3
     */
    public Triangle (Vertex v1, Vertex v2, Vertex v3, Vector3d normal)
    {
        this.vertices[0] = v1;
        this.vertices[1] = v2;
        this.vertices[2] = v3;
        this.edges[0] = new Vector3d(v2.getPosX() - v1.getPosX(), v2.getPosY() - v1.getPosY(), v2.getPosZ() - v1.getPosZ());
        this.edges[1] = new Vector3d(v3.getPosX() - v2.getPosX(), v3.getPosY() - v2.getPosY(), v3.getPosZ() - v2.getPosZ());
        this.edges[2] = new Vector3d(v1.getPosX() - v3.getPosX(), v1.getPosY() - v3.getPosY(), v1.getPosZ() - v3.getPosZ());
        //calculates the normal
        this.normal.cross(edges[0], edges[1]);
        // Normalize the normal, bringing it to unit length (length = 1)
        this.normal.normalize();
    }

    /**
     * Calculate the area of the triangle.
     * @return The area of the triangle.
     */
    public double calculateArea ()
    {
        // Calculate the area of the triangle using Heron's formula
        double a = edges[0].length();
        double b = edges[1].length();
        double c = edges[2].length();
        double s = (a + b + c) / 2;
        return this.area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    public double getArea ()
    {
        return area;
    }

    public Vertex[] getVertices ()
    {
        return vertices;
    }

    public Vector3d[] getEdges ()
    {
        return edges;
    }

    public Vector3d getNormal ()
    {
        return normal;
    }

    /**
     * Get the vertex at the specified index. The index should be between 0 and 2.
     * @param index
     * @return
     */
    public Vertex getVertex (int index)
    {
        return vertices[index];
    }

    /**
     * Get the edge at the specified index. The index should be between 0 and 2.
     * @param index
     * @return
     */
    public Vector3d getEdge (int index)
    {
        return edges[index];
    }

    @Override
    public String toString ()
    {
        return "\n\t Triangle: " +
                "\n\t Vertex1 : " + vertices[0] +
                "\n\t Vertex2 : " + vertices[1] +
                "\n\t Vertex3 : " + vertices[2] +
                "\n\t normal : " + normal +
                "\n\t area : " + String.format("%.2f", area);

    }

    @Override
    public int compareTo (Triangle other)
    {
        return Double.compare(this.area, other.area);
    }

}
