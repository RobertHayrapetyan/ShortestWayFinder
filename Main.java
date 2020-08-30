import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static float[] A = {0, 1};
    private static float[] B = {1, 0.5f};
    private static float[] C = {1.5f, 1};
    private static float[] D = {3, 0};
    private static float[] E = {2, 3};
    private static float[] F = {1.5f, 4.5f};
    private static float[] G = {5, 2};
    private static float[] H = {4, 1};
    private static float[] K = {2, 2};

    private static int[] visited = new int[9];
    private static double[] minDistance = new double[9];

    private static double[][] connectedCities = new double[9][9];

    public static void main(String[] args){

        //Getting Start and End points
        System.out.println("Enter Start and End points from A, B, C, D, E, F, G, H, K");
        System.out.print("Start point -> ");
        int start = pointInput();
        System.out.print("End point -> ");
        int end = pointInput();

        //Setting connections between points
        connectedCities[0][1] = connectedCities[1][0] = calcPointsDistance(A, B);
        connectedCities[0][2] = connectedCities[2][0] = calcPointsDistance(A, C);
        connectedCities[0][8] = connectedCities[8][0] = calcPointsDistance(A, K);
        connectedCities[0][5] = connectedCities[5][0] = calcPointsDistance(A, F);
        connectedCities[1][3] = connectedCities[3][1] = calcPointsDistance(B, D);
        connectedCities[2][3] = connectedCities[3][2] = calcPointsDistance(C, D);
        connectedCities[2][7] = connectedCities[7][2] = calcPointsDistance(C, H);
        connectedCities[2][6] = connectedCities[6][2] = calcPointsDistance(C, G);
        connectedCities[2][8] = connectedCities[8][2] = calcPointsDistance(C, K);
        connectedCities[4][8] = connectedCities[8][4] = calcPointsDistance(E, K);
        connectedCities[4][6] = connectedCities[6][4] = calcPointsDistance(E, G);
        connectedCities[4][5] = connectedCities[5][4] = calcPointsDistance(E, F);
        connectedCities[7][6] = connectedCities[6][7] = calcPointsDistance(H, G);




//        System.out.println("     A     B     C     D     E     F     G     H     K");
//        for (int i = 0; i < 9; i++){
//            if (i == 8){
//                System.out.print('K');
//            } else {
//                System.out.print(Character.toChars('A' + i));
//            }
//
//            for (int j = 0; j < 9; j++){
//                System.out.print("   " + connectedCities[i][j]);
//            }
//            System.out.println();
//        }


        //Mark all points as Not Visited("1")
        //Set all min distances between start point and other points "1000000"
        for (int i = 0; i < 9; i++){
            visited[i] = 1;
            minDistance[i] = 1000000;
        }

        //Set min distance from Start point to itself "0"
        minDistance[start] = 0;

        //Start our algorithm("Dextra's algorithm")=====================================================================
        int minIndex;
        do {
            minIndex = 1000000;
            double min = 1000000;
            for (int i = 0; i <9; i++){
                //If point is not visited and distance between points is less then min
                if ((visited[i] == 1) && (minDistance[i] < min)){
                    //Change values
                    min = minDistance[i];
                    minIndex = i;
                }
            }

            //Adding founded distance to point current distance and compare with point current distance
            if (minIndex != 1000000){
                for (int i = 0; i < 9; i++){
                    if (connectedCities[minIndex][i] > 0){
                        double temp = min + connectedCities[minIndex][i];
                        if (temp < minDistance[i]){
                            minDistance[i] = temp;
                        }
                    }
                }
                visited[minIndex] = 0;
            }
        } while (minIndex < 1000000);

//        for (int i = 0; i < 9; i++ ){
//            System.out.print(minDistance[i] + "  ");
//        }

        //Finding the shortest path=====================================================================================
        int[] visitedPoints = new int[9]; // Visited points array
        int endPoint = end; // End point index 9 - 1 = 8
        visitedPoints[0] = endPoint;
        int forwardPointIndex = 1;
        double lastPointWeight = minDistance[endPoint];

        while (endPoint != start){
            for (int i = 0; i < 9; i++){
                if (connectedCities[i][endPoint] != 0){
                    double temp = lastPointWeight - connectedCities[i][endPoint];
                    if (temp == minDistance[i]){
                        lastPointWeight = temp;
                        endPoint = i;
                        visitedPoints[forwardPointIndex] = i;
                        forwardPointIndex++;
                    }
                }
            }
        }

        System.out.print("\n");
        for (int i = forwardPointIndex -1; i >= 0; i--){
            if (visitedPoints[i] == 8){
                System.out.print("K");
            } else {
                System.out.print(Character.toChars(visitedPoints[i] + 65));
            }
            System.out.print(" -> ");
        }
        System.out.println(minDistance[end] + " km");
    }

    private static float calcPointsDistance(float[] a, float[] b) {
        float distance;
        distance = (float) Math.sqrt((a[0]-b[0]) * (a[0]-b[0]) + (a[1]-b[1]) * (a[1]-b[1]));
        return distance;
    }

    private static int pointInput() {
        int point;
        String st1 = scanner.nextLine();
        st1 = st1.toUpperCase();
        if (st1.charAt(0) != 'K'){
            point = st1.charAt(0) - 65;
        } else {
            point = st1.charAt(0) - 67;
        }
        return point;
    }
}
