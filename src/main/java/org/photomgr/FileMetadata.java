package org.photomgr;

import org.photomgr.dto.FileMetaDto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileMetadata {

    private static final String PHOTO_LIST = "/home/evalle/Pictures/TMP/list.txt";
    private static SimpleDateFormat dFormat;
    private static StringBuilder sb;
    private static  Calendar calendar;

    protected static void displayFileDetails(String filepath) throws Exception {
        Path path = Paths.get(filepath);

        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

            System.out.println("File: " + path.getFileName());
            System.out.println("Size: " + attrs.size() + " bytes");
            System.out.println("Created: " + formatTime(attrs.creationTime()));
            System.out.println("Last Modified: " + formatTime(attrs.lastModifiedTime()));
            System.out.println("Last Accessed: " + formatTime(attrs.lastAccessTime()));
            System.out.println("Is Directory: " + attrs.isDirectory());
            System.out.println("Is Regular File: " + attrs.isRegularFile());
            System.out.println("Is Symbolic Link: " + attrs.isSymbolicLink());
        } catch (IOException e) {
            System.err.println("Error reading file attributes: " + e.getMessage());
        }
    }

    private static String formatTime(FileTime time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.toMillis());
    }

    /**
     * Despliega metadatos en pantalla de un archivo por ruta
     * @param filePath
     * @throws IOException
     */
    protected static void showFileDetails(String filePath) throws IOException {
        System.out.format("\n\n <showFileDetails> %s %n", filePath);
        dFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sb = new StringBuilder("Path:").append(filePath).append(":\n");
        calendar = Calendar.getInstance();
        //TimeZone tz = TimeZone.getTimeZone("EST");  // GMT | IST
        calendar.setTimeZone(TimeZone.getTimeZone("EST")); //calendar.setTimeZone(tz);

        //Nombre y extension
        String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
        String extension = fileName.substring(fileName.lastIndexOf(".")+1);
        sb.append("fileName: ").append(fileName).append("\n")
                .append("Extension: ").append(extension).append("\n");


        //OBtiene attributos de archivo
//        File file = new File(filePath);
//        Path p = Paths.get(file.getAbsolutePath());
//        BasicFileAttributes attr
//                = Files.getFileAttributeView(p, BasicFileAttributeView.class)
//                .readAttributes();
        BasicFileAttributes attr = Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class);

        sb
                .append("CreationTime: ").append( dFormat.format( attr.creationTime().toMillis() ) )
                .append("(").append(attr.creationTime().toMillis()).append(") [").append(attr.creationTime()).append("]\n")
                .append("lastAccessTime: ").append( dFormat.format( attr.lastAccessTime().toMillis() ) )
                .append("(").append(attr.lastAccessTime().toMillis()).append(") [").append( attr.lastAccessTime() ).append("]\n")
                .append("lastModifiedTime: ").append( dFormat.format( attr.lastModifiedTime().toMillis() ) )
                .append("(").append(attr.lastModifiedTime().toMillis()).append(") [").append( attr.lastModifiedTime() ).append("]\n")
                .append("isDirectory: ").append(attr.isDirectory()).append("\n")
                .append("isOther: ").append(attr.isOther()).append("\n")
                .append("isRegularFile: ").append(attr.isRegularFile()).append("\n")
                .append("isSymbolicLink: ").append(attr.isSymbolicLink()).append("\n")
                .append("size: ").append(attr.size()).append("\n");

        //Genera fecha a partir de atributo creationTime
        calendar.setTime( new Date( attr.lastModifiedTime().toMillis() ) ); //lastModifiedTime is Taken time
        int year = calendar.get(Calendar.YEAR),  month = calendar.get(Calendar.MONTH)+1, day= calendar.get(Calendar.DATE);
        int hours = calendar.get(Calendar.HOUR_OF_DAY)+1, mins=calendar.get(Calendar.MINUTE), secs =calendar.get(Calendar.SECOND); //Calendar.HOUR => 12 = 00
        //System.out.format("Year: %d, Month: %d, Day: %d, Hour: %d, Minute: %d, Second: %d %n", year, month, day, hours, mins, secs );
        sb.append("Date:: Year=").append(year).append(", Month=").append(month).append(", Day=").append(day)
                .append(", hours=").append(hours).append(", mins=").append(mins).append(", secs=").append(secs).append("\n");


        FileTime fileTime= attr.creationTime();
        System.out.println("Created on: "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format((fileTime.toMillis())));

        //Salida
        System.out.println(sb);
    }


    protected static void testCalendar() {
        // create a calendar
        Calendar cal = Calendar.getInstance();

        // print current time zone
        System.out.println("Current Time Zone: " + cal.getTimeZone().getDisplayName() );

        // set the time zone with the given time zone value
        TimeZone tz = TimeZone.getTimeZone("IST");  // GMT | IST
        cal.setTimeZone(tz);
        System.out.println("New Time Zone: " + cal.getTimeZone().getDisplayName());
    }

    /**
     * Obtiene Metadata de una ruta de archivo, en un archivo DTO
     * @param fullFilePath
     * @return
     * @throws Exception
     */
    public static FileMetaDto getFileMetaDto(String fullFilePath) throws Exception{

        FileMetaDto dto = new FileMetaDto(fullFilePath);

        String fileName = fullFilePath.substring(fullFilePath.lastIndexOf("/")+1);
        dto.setFileName(fileName);
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            dto.setFileExt( fileName.substring(dotIndex + 1) );
        }

        BasicFileAttributes attr = Files.readAttributes(Paths.get(fullFilePath), BasicFileAttributes.class);

        dto.setCreationTime( attr.creationTime() );
        dto.setLastAccessTime( attr.lastAccessTime() );
        dto.setLastModifiedTime( attr.lastModifiedTime() );
        dto.setSize( attr.size() );
//        private Date fileDate;

        //Genera fecha a partir de atributo lastModifiedTime
        dto.setFileDate( new Date( attr.lastModifiedTime().toMillis() ) );

        return dto;
    }

    public static void main(String[] args) {
        String filePath =
//                "/home/netto/TEMP/PhotoTest/DSC_1555.JPG";  // Oct16, 00:09:08
//                "/home/netto/TEMP/PhotoTest/DSC_1437.JPG";  // 12:13:ss +-   [ 20251007_1213SSx ]
                  "/home/netto/TEMP/PhotoTest/PXL_20251007_161320219.jpg";  // 12:13:ss [20251007_1213SSx]
//                  "/home/netto/TEMP/PhotoTest/PXL_20251007_151902240.jpg"; // ==> 11:19:ss

        try {
//            testCalendar();
//            displayFileDetails(filePath);
            showFileDetails(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
