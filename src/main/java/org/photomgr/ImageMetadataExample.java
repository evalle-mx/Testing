//package org.photomgr;
//
//import com.drew.imaging.ImageMetadataReader;
//import com.drew.metadata.*;
//import com.drew.metadata.exif.*;
//import java.io.File;
//
//public class ImageMetadataExample {
//    public static void main(String[] args) {
//        File imageFile = new File("path/to/your/photo.jpg");
//
//        try {
//            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
//
//            for (Directory directory : metadata.getDirectories()) {
//                for (Tag tag : directory.getTags()) {
//                    System.out.println(tag);
//                }
//
//                if (directory.hasErrors()) {
//                    for (String error : directory.getErrors()) {
//                        System.err.println("ERROR: " + error);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Error reading image metadata: " + e.getMessage());
//        }
//    }
//}
