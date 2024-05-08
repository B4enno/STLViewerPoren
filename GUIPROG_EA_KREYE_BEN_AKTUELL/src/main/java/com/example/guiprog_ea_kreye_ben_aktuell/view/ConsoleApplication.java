package com.example.guiprog_ea_kreye_ben_aktuell.view;

import com.example.guiprog_ea_kreye_ben_aktuell.model.Polyhedron;

import java.io.File;
import java.util.Scanner;
//TODO: ExceptionHandling implementieren
public class ConsoleApplication {

    private Scanner scanner;

    /**
     * Constructor for objects of class ConsoleApplication
     */
    public ConsoleApplication()
    {
        this.scanner = new Scanner(System.in);
    }

    public String askForFilePath() {
        while (true) {
            System.out.println("Please enter the path to the STL file:");
            String path = scanner.nextLine();

            File file = new File(path);
            if (file.exists() && file.isFile() && path.endsWith(".stl")) {
                return path;
            } else {
                System.out.println("The entered path is incorrect or the file does not exist."
                                    + " Please enter a valid STL file path.");
            }
        }
    }

    public void printPolyhedron(Polyhedron polyhedron)
    {
        System.out.println(polyhedron.toString());
    }

    public void printTriangles(Polyhedron polyhedron)
    {
        System.out.println(polyhedron.getTriangles().toString());
    }

    public void printTriangle(Polyhedron polyhedron, int index)
    {
        System.out.println(polyhedron.getTriangles().get(index).toString());
    }

    public void printLastTriangle(Polyhedron polyhedron)
    {
        System.out.println(polyhedron.getTriangles().getLast().toString());
    }

    public void printFirstTriangle(Polyhedron polyhedron)
    {
        System.out.println(polyhedron.getTriangles().getFirst().toString());
    }
}
