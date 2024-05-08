package com.example.guiprog_ea_kreye_ben_aktuell.control;

import com.example.guiprog_ea_kreye_ben_aktuell.model.Polyhedron;
import com.example.guiprog_ea_kreye_ben_aktuell.model.Triangle;
import com.example.guiprog_ea_kreye_ben_aktuell.model.Triangles;
import com.example.guiprog_ea_kreye_ben_aktuell.model.Vertex;

import javax.vecmath.Vector3d;
import java.io.*;

/**
 * A class that reads STL files in both ASCII and binary formats and constructs a Polyhedron object from the data.
 * The class provides methods to read both types of STL files and parse the data into a Polyhedron object.
 * The class can determine the file format and call the appropriate method to read the file.
 */
public class STLReader {

    /**
     * Reads an STL file from the specified file path and constructs a Polyhedron object containing the triangles defined within the file.
     * The method determines whether the file is in ASCII or binary format and calls the appropriate method to read the file.
     * Precondition: The file must exist at the specified path.
     * Postcondition: Returns a Polyhedron object containing the triangles parsed from the STL file.
     * If the file is not found, or there is an error reading the file, an IOException is thrown.
     *
     * @param filePath - The path to the STL file to be read.
     * @return - A Polyhedron object containing the triangles parsed from the file.
     * @throws IOException - Thrown if there is an error reading from the file.
     */
    public Polyhedron readSTL(String filePath) throws IOException {
        if (isSTLAscii(filePath)) {
            return readSTLAscii(filePath);
        } else {
            return readSTLBinary(filePath);
        }
    }

    /**
     * Checks if a file specified by the file path is an ASCII STL file.
     * The method reads the first line of the file to determine if it starts with the keyword "solid",
     * which is indicative of ASCII format.
     * Precondition: The file should exist at the specified path.
     * Postcondition: Returns true if the first line starts with "solid", false otherwise.
     * If the file does not exist or an error occurs while reading, an exception is thrown.
     *
     * @param filePath - The path to the file to be checked.
     * @return - boolean indicating whether the file is an ASCII STL file.
     * @throws FileNotFoundException - Thrown if the file does not exist at the specified path.
     * @throws IOException           - Thrown if there is an error reading from the file.
     */
    public static boolean isSTLAscii(String filePath) throws IOException {
        File datei = new File(filePath);
        if (!datei.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(datei))) {
            String ersteZeile = reader.readLine();
            if (ersteZeile == null) {
                throw new IOException("File is empty: " + filePath);
            }
            return ersteZeile.trim().startsWith("solid");
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File was not found: " + filePath);
        } catch (IOException e) {
            throw new IOException("Error while reading file: " + filePath);
        }
    }

    /**
     * Reads an ASCII STL file from the specified file path and constructs a Polyhedron object.
     * The method processes each line of the file, identifying and parsing facets defined by a normal and three vertices.
     * Precondition: The file must exist at the specified path and must be in valid ASCII STL format starting with lines
     * that include "facet normal" followed by three "vertex" lines within each facet definition.
     * Postcondition: Returns a Polyhedron object containing all the triangles read from the file.
     * If the file is not found, or there is an error reading the file, an IOException is thrown.
     *
     * @param filePath - The path to the ASCII STL file to be read.
     * @return - Polyhedron object containing the triangles formed from the facets described in the file.
     * @throws FileNotFoundException - Thrown if the file does not exist at the specified path.
     * @throws IOException           - Thrown if there is an error reading from the file.
     */
    public Polyhedron readSTLAscii(String filePath) throws IOException {
        Triangles triangles = new Triangles();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("facet normal")) {
                    Vector3d normal = readNormal(line);
                    Triangle triangle = readTriangle(reader, normal);
                    triangles.add(triangle);
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File was not found: " + filePath);
        } catch (IOException e) {
            throw new IOException("Error while reading file: " + filePath);
        }
        return new Polyhedron(triangles);
    }

    /**
     * Parses a single line representing the normal vector of a facet from an ASCII STL file.
     * The line should be in the format "facet normal nx ny nz", where nx, ny, and nz are float values representing the
     * components of the normal vector.
     * Precondition: The line must correctly start with "facet normal" and contain exactly five parts separated by spaces.
     * Postcondition: Returns a Vector3d object representing the normal vector parsed from the line.
     * If the format is incorrect or parsing fails, an exception is thrown.
     *
     * @param line - The line from the STL file to be parsed.
     * @return - A Vector3d object containing the parsed normal vector.
     * @throws NumberFormatException    - Thrown if the float conversion of the normal coordinates fails.
     * @throws IllegalArgumentException - Thrown if the line does not meet the format requirements.
     */
    private Vector3d readNormal(String line) throws NumberFormatException {
        String[] parts = line.trim().split("\\s+");
        //check if the line was correctly split and has the expected length
        if (parts.length < 5 || !parts[0].equals("facet") || !parts[1].equals("normal")) {
            throw new IllegalArgumentException("Wrong normale format: " + line);
        }
        try {
            float x = Float.parseFloat(parts[2]);
            float y = Float.parseFloat(parts[3]);
            float z = Float.parseFloat(parts[4]);
            return new Vector3d(x, y, z);
        } catch (NumberFormatException e) {

            throw new NumberFormatException("Error parsing normale: " + line);
        }
    }

    /**
     * Parses the data for a single triangle from the ASCII STL file, using a buffered reader passed as an argument.
     * This method assumes that it is called immediately after reading a line that starts with "facet normal".
     * It reads the next lines corresponding to the vertices of the triangle and skips control lines like "outer loop" and "endfacet".
     * Precondition: The reader must be positioned right after the "facet normal" line, and the next lines should correctly represent vertices.
     * Postcondition: Returns a Triangle object composed of the parsed vertices and the normal vector provided.
     *
     * @param reader - The BufferedReader used to read the file. It should be set up to read the lines following the normal vector line.
     * @param normal - A Vector3d object representing the normal vector of the triangle to be constructed.
     * @return - A Triangle object that represents the triangle described in the STL file.
     * @throws IOException - Thrown if there is an error during reading from the file.
     */
    private Triangle readTriangle(BufferedReader reader, Vector3d normal) throws IOException {
        Vertex[] vertices = new Vertex[3];
        String line;
        // skip the line "outer loop"
        reader.readLine();

        for (int i = 0; i < 3; i++) {
            line = reader.readLine();
            vertices[i] = readVertex(line);
        }

        // skips the lines "endloop" and "endfacet"
        reader.readLine();
        reader.readLine();

        return new Triangle(vertices[0], vertices[1], vertices[2], normal);
    }

    /**
     * Parses a single line representing a vertex from an ASCII STL file.
     * The line should be in the format "vertex x y z", where x, y, and z are float values representing the coordinates.
     * Precondition: The line must contain at least three parts separated by spaces, representing the x, y, and z coordinates.
     * Postcondition: Returns a Vertex object containing the parsed coordinates.
     * If the format is incorrect or parsing fails, an exception is thrown.
     *
     * @param line - The line from the STL file to be parsed.
     * @return - A Vertex object containing the parsed coordinates.
     * @throws IllegalArgumentException - Thrown if the line does not meet the format requirements.
     */
    private Vertex readVertex(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid vertex format: \"" + line + "\". Expected: \"vertex x y z\".");
        }

        float x = Float.parseFloat(parts[1]);
        float y = Float.parseFloat(parts[2]);
        float z = Float.parseFloat(parts[3]);
        return new Vertex(x, y, z);
    }


    /**
     * Reads a binary STL file specified by the file path and constructs a Polyhedron object containing the triangles defined within the file.
     * Precondition: The file must exist and be accessible at the specified path. The file should be in valid binary STL format.
     * Postcondition: Returns a Polyhedron object consisting of the triangles parsed from the binary STL file.
     * If the file cannot be read, is not found, or does not conform to the expected binary STL format, appropriate exceptions are thrown.
     *
     * @param filePath - The path to the binary STL file to be read.
     * @return - A Polyhedron object containing the triangles parsed from the file.
     * @throws FileNotFoundException - Thrown if the file at the specified path is not found.
     * @throws IOException - Thrown if an I/O error occurs while reading from the file.
     */
    public Polyhedron readSTLBinary(String filePath) throws IOException {
        try (DataInputStream stream = new DataInputStream(new FileInputStream(filePath))) {
            skipHeader(stream); 
            int numberOfTriangles = readIntLE(stream);
            Triangles triangles = new Triangles(numberOfTriangles);

            for (int i = 0; i < numberOfTriangles; i++) {
                triangles.add(readTriangleFromBinary(stream));
            }
            return new Polyhedron(triangles);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File was not found: " + filePath);
        } catch (IOException e) {
            throw new IOException("Error while reading file: " + filePath);
        }
    }

    /**
     * Skips the header portion of a binary STL file using the provided DataInputStream.
     * Precondition: The stream should be opened and positioned at the beginning of the file.
     * Postcondition: The stream's position will be advanced by 80 bytes, bypassing the header of the binary STL file.
     *
     * @param stream - The DataInputStream connected to the binary STL file where the header is to be skipped.
     * @throws IOException - Thrown if there is an error skipping the 80 bytes, possibly due to an I/O issue like reaching the end of the file.
     */
    private void skipHeader(DataInputStream stream) throws IOException {
        stream.skipBytes(80);
    }


    /**
     * Reads a single triangle from a binary STL file stream. This includes reading the normal vector and the vertices for the triangle,
     * followed by skipping the attribute byte count.
     * The data for each element (normal and vertices) is read using little-endian format.
     * Precondition: The stream should be positioned at the start of a triangle's data in the binary STL file.
     * Postcondition: The stream's position will be advanced by 50 bytes after reading the triangle data.
     *
     * @param stream - The DataInputStream from which the triangle data is read. It must be positioned at the start of a triangle's data.
     * @return - A Triangle object composed of one normal vector and three vertices.
     * @throws IOException - Thrown if an I/O error occurs during reading, including if the end of the stream is unexpectedly reached.
     */
    private Triangle readTriangleFromBinary(DataInputStream stream) throws IOException {
        Vector3d normal = new Vector3d(readFloatLE(stream), readFloatLE(stream), readFloatLE(stream));
        Vertex v1 = new Vertex(readFloatLE(stream), readFloatLE(stream), readFloatLE(stream));
        Vertex v2 = new Vertex(readFloatLE(stream), readFloatLE(stream), readFloatLE(stream));
        Vertex v3 = new Vertex(readFloatLE(stream), readFloatLE(stream), readFloatLE(stream));
        stream.skipBytes(2); // skip the attribute byte count
        return new Triangle(v1, v2, v3, normal);
    }


    /**
     * Reads an integer from the provided DataInputStream in little-endian format.
     * Precondition: The stream should be positioned at the start of the integer data with right format.
     * Postcondition: The stream's position will be advanced by 4 bytes after reading the integer.
     *
     * @param stream - The DataInputStream from which the integer is read.
     * @return - The integer read from the stream, after converting from little-endian to Java's big-endian format.
     * @throws IOException - Thrown if an I/O error occurs while reading from the stream.
     */
    private int readIntLE(DataInputStream stream) throws IOException {
        return Integer.reverseBytes(stream.readInt());
    }

    /**
     * Reads a float from the provided DataInputStream in little-endian format.
     * Precondition: The stream should be positioned at the start of the float data with right format.
     * Postcondition: The stream's position will be advanced by 4 bytes after reading the float.
     *
     * @param stream - The DataInputStream from which the float is read.
     * @return - The float read from the stream, after converting from little-endian to Java's big-endian format.
     * @throws IOException - Thrown if an I/O error occurs while reading from the stream.
     */
    private float readFloatLE(DataInputStream stream) throws IOException {
        return Float.intBitsToFloat(readIntLE(stream));
    }
}
