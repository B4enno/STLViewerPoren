package com.example.guiprog_ea_kreye_ben_aktuell.control;

import com.example.guiprog_ea_kreye_ben_aktuell.model.Polyhedron;
import com.example.guiprog_ea_kreye_ben_aktuell.view.ConsoleApplication;




public class PolyhedronController {

    private Polyhedron polyhedron;

    private ConsoleApplication consoleApplication = new ConsoleApplication();




    public PolyhedronController (Polyhedron polyhedron)
    {
        this.polyhedron = polyhedron;
    }

    public void calculateVolume ()
    {
    }

    public double calculateSurfaceArea ()
    {
        return polyhedron.calculateSurfaceArea();
    }

    public void calculateVolumeAndSurfaceArea ()
    {
    }

    public void calculateVolumeAndSurfaceArea (Polyhedron polyhedron)
    {
    }

    public Polyhedron getPolyhedron ()
    {
        return polyhedron;
    }



}
