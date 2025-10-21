package org.photomgr;
/**
 * Class used for rename files based on criteria and (metadata) Dates
 */

import org.photomgr.dto.FileMetaDto;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PhotoRenamer {

    private static final String PHOTO_LIST = "/home/netto/TEMP/PhotoTest/lista.txt";
    private static final String outPutDir = "/home/netto/TEMP/PhotoTest/";
    private static final String mvFile = "movePhotos.sh", mvReverseFile ="mvRevPhotos.sh";

    private static SimpleDateFormat dFormat;
    private static Calendar cal;
    private static Path path;
    private static BasicFileAttributes attrs;
    private static int dscHrs, dscMins, pxlHrs, pxlMins;

    private static final TimeZone tz = TimeZone.getTimeZone("EST");


    /*  #######   COMO USAR ESTA CLASE ######
       0) Respaldar carpeta de imagenes
       1) Copiar (2) fotografias para sincronizar fechas ( a /home/netto/TEMP/PhotoTest)
       2) ejecutar showTimedName en archivo/foto base para confirmar fecha Base (fPath1)
       3) ejecutar syncFiles para calcular tiempo de modificacion (fPath1, fPath2)
       4) Modificar dscHrs y dscMins para renombrado (DSC es Nikon)
       5) Copiar Carpeta en Folder temporal
       6) Generar archivo PHOTO_LIST con las fotos a renombrar (Probar 1o con 2 fotos)
       7) ejecutar makeMV_script (Genera dos archivos, uno mv y otro reverse mv)
       8) Modificar permisos [chmod +x movePhotos.sh] en terminal
       9) ejecutar archivo sh en terminal
     */

    public static void main(String[] args) {
        dscHrs = 0;
        dscMins = 0;
        pxlHrs = 0;
        pxlMins = 0;

        String fPath1 ="/home/netto/TEMP/PhotoTest/DSC_1150.JPG"; //Foto con camara (Nikon)
        String fPath2 ="/home/netto/Pictures/2025/08-Aug/20250823_BayFront/PXL_20250823_000406410.jpg"; //Foto con camara (Pixel)
        String fechaBase = "2025-09-13 18:26:23"; /* Fecha especifica de la foto */

        try {
//            showTimedName(fPath2); //USO pixel como referencia porque la hora es automatica
//            syncFiles(fPath1, fPath2 , fechaBase);

            makeMV_script( PHOTO_LIST );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Regresa fecha con formato
     * @param time
     * @return
     */
    private static String formatTime(FileTime time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.toMillis()); //2025-09-20 15:19:57
    }

    /**
     * Agrega horas y minutos a una fecha en milisegundos (Convierte horas en minutos)
     * @param timeInMillis
     * @param hours
     * @param mins
     * @return
     */
    private static long addHoursToMillis(long timeInMillis, long hours, long mins) {
        mins+= (hours*60);
        return addMinsToMillis(timeInMillis, mins); //timeInMillis+(hours * 60 * 60 *1000);
    }

    /**
     * Agrega minutos a una fecha en milisegundos
     * @param timeInMillis
     * @param mins
     * @return
     */
    private static long addMinsToMillis(long timeInMillis, long mins) {

        return timeInMillis + (mins * 60 *1000);
    }

    /**
     * Analiza metadata de dos archivos y los compara con una fecha base
     * @param filePath1
     * @param filePath2
     * @param fechaBase
     * @throws Exception
     */
    protected static void syncFiles(String filePath1, String filePath2, String fechaBase) throws Exception {
        System.out.println("Archivo 1: " + filePath1 );
        System.out.println("Archivo 2: " + filePath2 );
        System.out.println("Fecha Base(String): " + fechaBase);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2025-09-20 15:19:57
        Date baseDate = formatter.parse(fechaBase);

//        System.out.println("Fecha real: ");
        FileMetaDto fileDto1 = FileMetadata.getFileMetaDto(filePath1);
        FileMetaDto fileDto2 = FileMetadata.getFileMetaDto(filePath2);
//        System.out.println(fileDto.toString() );

        System.out.println("Fecha Base: \t" + baseDate );   //Tue Oct 07 12:20:05 EDT 2025
        System.out.println("Fecha {F1}: \t" + fileDto1.getFileDate() + ", diferencia en minutos:" + Math.abs(fileDto1.diffWithDate(baseDate)) + ", __Hrs = " + (fileDto1.diffWithDate(baseDate)/60*-1) + ", __Mins = "+ (fileDto1.diffWithDate(baseDate)%60*-1) + " ("+ fileDto1.getFileName()+")" );  //
        System.out.println("Fecha {F2}: \t" + fileDto2.getFileDate() + ", diferencia en minutos:" + Math.abs(fileDto2.diffWithDate(baseDate)) + ", __Hrs = " + (fileDto2.diffWithDate(baseDate)/60*-1) + ", __Mins = "+ (fileDto2.diffWithDate(baseDate)%60*-1) + " ("+ fileDto2.getFileName()+")" );  // + "diferencia en minutos:" + minsDif ); //
        System.out.println("Nota: cambiar solo si la diferencia es de mas de 5 minutos");
    }



    /**
     *  Genera dos archivos SH con instruccion para renombrar archivos (mv)
     * @param pathListFile
     * @throws Exception
     */
    public static void makeMV_script(String pathListFile) throws Exception {
        System.out.println("lsFile = "+ pathListFile);
        String mvLine;
        Path listPath = Paths.get(pathListFile);
        int lineNumber = 0;

        try (BufferedReader reader = Files.newBufferedReader(listPath)) {
            String filepath, folder = "";

            StringBuilder sbMv = new StringBuilder();
            StringBuilder sbRev = new StringBuilder();

            while ((filepath = reader.readLine()) != null) {
//                showTimedName(line);
//                System.out.println("\n ____________  \n ");
                cal = Calendar.getInstance();
                cal.setTimeZone(tz);
                path = Paths.get(filepath);
                attrs = Files.readAttributes(path, BasicFileAttributes.class);
                long fileMillisTime = attrs.lastModifiedTime().toMillis();

                folder = filepath.substring(0, filepath.lastIndexOf("/")+1);
                String fileName = filepath.substring(filepath.lastIndexOf("/")+1);
                String newFileName = getNameFromTime(fileName, fileMillisTime);
                sbMv.append("mv ").append(folder).append(fileName).append(" ").append(folder).append(newFileName).append("\n");
                sbRev.append("mv ").append(folder).append(newFileName).append(" ").append(folder).append(fileName).append("\n");
                lineNumber++;
                System.out.println("Line " + lineNumber );
            }
            sbMv.append("echo '").append(lineNumber).append(" files in ").append(folder).append(" (should) be renamed!!'");
            System.out.println(sbMv.toString());
            FileMgr.exportToTxt(sbMv, outPutDir + mvFile);
            FileMgr.exportToTxt(sbRev, outPutDir+ mvReverseFile );
            System.out.println("run: \n chmod +x movePhotos.sh \n ./movePhotos.sh");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    
    /**
     * Metodo DEMO que obtiene metadata de archivo(foto) y genera nuevo nombre
     * @param filepath
     * @throws Exception
     */
    protected static void showTimedName(String filepath) throws Exception {
        cal = Calendar.getInstance();
        cal.setTimeZone(tz);
        path = Paths.get(filepath);
        attrs = Files.readAttributes(path, BasicFileAttributes.class);

        String fileName = filepath.substring(filepath.lastIndexOf("/")+1);

        long fileMillisTime = attrs.lastModifiedTime().toMillis();
        String newFileName = getNameFromTime(fileName, fileMillisTime);
//        System.out.println("new File Name: " + newFileName );
    }

    /**
     * A partir de un archivo y una fecha, genera un nombre con formato -YYYYMMDD_hhmmss_NAME.ext-
     * @param fileName
     * @param fileMillisTime
     * @return
     */
    private static String getNameFromTime(String fileName, long fileMillisTime) {
        System.out.println("time in millis: " + fileMillisTime);
        String noDupCons = "00";
        long newMilisTime = fileMillisTime;

        String extension = "";
        String lastThreeDigits = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1);
        }

        // --- 2. Extract last three digits before extension ---
        String nameWithoutExtension = fileName.substring(0, dotIndex);

        // Use regex to find digits at the end of the name
        String digitsOnly = nameWithoutExtension.replaceAll("\\D+", " "); // remove non-digits
        String[] parts = digitsOnly.trim().split("\\s+");
        if (parts.length > 0) {
            String lastNumber = parts[parts.length - 1];
            if (lastNumber.length() >= 3) {
                lastThreeDigits = lastNumber.substring(lastNumber.length() - 3);
            } else {
                lastThreeDigits = lastNumber; // if shorter than 3 digits
            }
        }

        // --- 3. Print results ---
//        System.out.println("Original File name: " + fileName);
//        System.out.println("Extension: " + extension);
//        System.out.println("Last three digits: " + lastThreeDigits);

        if(fileName.startsWith("PXL")){
            noDupCons = "PXL"+lastThreeDigits;
            // No hours added
            newMilisTime = addHoursToMillis(fileMillisTime, pxlHrs,pxlMins);
        }
        if(fileName.startsWith("DSC_")){
            noDupCons = "DSC"+lastThreeDigits;
            // Add 4 hours for DSC (Nikon)
            newMilisTime = addHoursToMillis(fileMillisTime, dscHrs, dscMins); //4, 58);
        }
        /* *** Fotos con nombre de edicion o differente a default  ** */
        if(fileName.toUpperCase().contains("ANIMATION")){
            System.out.println("ES ANIMATION (Google Photos)");
            noDupCons += "-anim";
        } else if(fileName.toUpperCase().contains("PANO")){
            System.out.println("ES panoramica");
            noDupCons += "-pano";
        } else if(fileName.toUpperCase().contains("TS")){
            System.out.println("ES TS-video");
            noDupCons += "-tsv";
        }else if(fileName.toUpperCase().contains("NIGHT")){
            System.out.println("ES Nocturna");
            noDupCons += "-night";
        }


        cal.setTime( new Date( newMilisTime) ); //lastModifiedTime is Taken time
        Date currentDatePlusOne = cal.getTime();

        String datedNameSegm =
                addZero(cal.get(Calendar.YEAR)) + addZero(cal.get(Calendar.MONTH)+1) + addZero( cal.get(Calendar.DATE)) +
                "_"+
                addZero(cal.get(Calendar.HOUR_OF_DAY) +1 ) +addZero(cal.get(Calendar.MINUTE)) +addZero(cal.get(Calendar.SECOND));
        String mss = ""+cal.get(Calendar.MILLISECOND);


        String newFileName = datedNameSegm + noDupCons + "."+extension.toLowerCase();

        System.out.println("FileName: " + fileName);
        System.out.println("Last Modified: " + attrs.lastAccessTime() + " ["+ formatTime(attrs.lastModifiedTime()) + "]");
        System.out.println("noDupCons: " + noDupCons);
        System.out.println("datedNameSegm: " + datedNameSegm);
        System.out.println("miliseconds: " + mss);
        System.out.println("newFileName: " + newFileName); /* Displayed on parent */
        System.out.println("fechaBase = " + ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fileMillisTime)  ) + " ["+ new Date( fileMillisTime) +"]");
        return newFileName;
    }


    /**
     * Adds Zeros ( 0 => 00, 9 => 09, 59 => 59)
     * @param number
     * @return
     */
    private static String addZero(int number){
        return number<1?"00":number<10?"0"+number:
                ""+number;
    }

}
