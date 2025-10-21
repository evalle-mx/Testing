package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void parseFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            System.err.println("Invalid filename");
            return;
        }

        // --- 1. Get the file extension ---
        String extension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1);
        }

        // --- 2. Extract last three digits before extension ---
        String nameWithoutExtension = fileName.substring(0, dotIndex);
        String lastThreeDigits = "";

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
        System.out.println("File name: " + fileName);
        System.out.println("Extension: " + extension);
        System.out.println("Last three digits: " + lastThreeDigits);
    }

    public static void main(String[] args) {
        parseFileName("PXL_20251007_201938370.PANO.jpg");
    }
}