package study.algo;

import java.util.Arrays;

/**
 * From a given array of integer, use Binary search to find the position where a given number is (-1 if is not present)
 */
public class BinarySearch {

    static int[] numbers = {2,5,8,11,25,39,55,68};
    static int numFind = 39;  // 39->5, 38 -> -1, 5 -> 1

    public static void main(String[] args) {
//        System.out.println(searchWhile(numbers, numFind));
        System.out.println(searchRecursive(numbers, numFind));
//        System.out.println(searchWithLibrary(numbers, numFind));
    }


    /**
     * Uses a while to implement binary search
     * @param arrNums
     * @param numberToFind
     * @return
     */
    protected static int searchWhile(int[] arrNums, int numberToFind){

        int iInicio = 0, iFin = arrNums.length-1;

        while(iInicio <= iFin){
            //System.out.printf("inicio %d fin %d\n", iInicio, iFin );
            int middlePosition = (iInicio+iFin)/2;  //gets the element in the middle (size =11, mid = 5.x)

            int numPos = arrNums[middlePosition]; // holds the number in the middle position

            if(numberToFind == numPos){ // Number to find == number in Position?
                //exito
                return middlePosition; //Breaks the while and return position
            }
            else if(numberToFind < numPos){  // number to find lower than Number in position?
                iFin = middlePosition-1;  // DISCARD the higher/right side of the array
            }else { // numberToFind greater than number in position
                iInicio = middlePosition+1; // DISCARD the lower/left side of the array
            }
        }

        return -1; //returns negative, indicating number is not in array
    }

    /**
     * Uses a recursive method to implement binary
     * @param arrNums
     * @param numberToFind
     * @return
     */
    protected static int searchRecursive(int[] arrNums, int numberToFind){
        int iInicio = 0, iFin = arrNums.length-1;
        return recursiveBinary(arrNums, numberToFind, iInicio, iFin);
    }

    /**
     * Recursive function, for not found returns -1
     * @param arrNums
     * @param numberToFind
     * @param iInicio
     * @param iFin
     * @return
     */
    private static int recursiveBinary(int[] arrNums, int numberToFind, int iInicio, int iFin){
        if(iInicio <= iFin && iInicio <= arrNums.length-1 ){
            int middlePosition = iInicio + (iFin-iInicio) /2;
            int numPos = arrNums[middlePosition];
            if(numberToFind == numPos){ // Number to find == number in Position?
                //exito
                return middlePosition; //Breaks the while and return position
            }
            else if(numberToFind < numPos){  // number to find lower than Number in position?
               // iFin = middlePosition-1;  // DISCARD the higher/right side of the array
                return recursiveBinary( arrNums, numberToFind, iInicio,  middlePosition-1);
            }else { // numberToFind greater than number in position
//                iInicio = middlePosition+1; // DISCARD the lower/left side of the array
                return recursiveBinary( arrNums, numberToFind, middlePosition+1,  iFin);
            }
        }
        return -1; //Not found
    }

    /**
     * Implementation using a library of Java
     * @param arrNums
     * @param numberToFind
     * @return
     */
    protected static int searchWithLibrary(int[] arrNums, int numberToFind){
        return Arrays.binarySearch(arrNums, numberToFind);
    }
}
