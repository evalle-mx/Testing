package org.photomgr;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileMgr {

    private static final String outPutDir = "/home/netto/TEMP/PhotoTest/";


    public static void exportToTxt(StringBuilder content, String filePath) {
        if (content == null) {
            System.err.println("Content is null — nothing to write.");
            return;
        }

        Path path = Paths.get(filePath);

        try {
            // Write the StringBuilder content to the file (UTF-8)
            Files.write(path, content.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println("✅ File successfully written to: " + path.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("❌ Error writing file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Example usage
        StringBuilder sb = new StringBuilder();
        sb.append("This is a test file.\n");
        sb.append("Exported using Java!\n");
        sb.append("Timestamp: ").append(System.currentTimeMillis());

        exportToTxt(sb, outPutDir +"output.txt");
    }



    /**
     * Mueve/Renombra el archivo a nuevo directorio
     * @param dirToMove
     * @param file
     * @return
     */

    public static boolean moveFile(String dirToMove, String newName, File file){
        boolean moved = false;
        try{
            // Destination directory
            File dir = new File(dirToMove);

            // Move file to new directory
            moved = file.renameTo(new File(dir, newName ));

        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(" Creado " + moved );
        return moved;
    }
}
