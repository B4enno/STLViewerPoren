package com.example.guiprog_ea_kreye_ben_aktuell.model;

public class Vertex {

    private double posX;
    private double posY;
    private double posZ;

    public Vertex (double posX, double posY, double posZ)
    {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public double getPosX ()
    {
        return posX;
    }

    public double getPosY ()
    {
        return posY;
    }

    public double getPosZ ()
    {
        return posZ;
    }

    public void setPosX (double posX)
    {
        this.posX = posX;
    }

    public void setPosY (double posY)
    {
        this.posY = posY;
    }

    public void setPosZ (double posZ)
    {
        this.posZ = posZ;
    }

    @Override
    public String toString ()
    {
        return  "\n\tx = " + String.format("%.4f", posX) +
                "\n\ty = " + String.format("%.4f", posY) +
                "\n\tz = " + String.format("%.4f", posZ);
    }
}