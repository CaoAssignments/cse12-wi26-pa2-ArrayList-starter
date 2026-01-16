/**
 * DO NOT MODIFY THIS FILE
 * This file is for CSE 12 PA2 in Winter 2026,
 * and contains a versatile runtime tester for use with Java's ArrayList Class.
*/

// IMPORTANT: Do not import java.util.ArrayList for Task 1
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * This class provides functionality to test the runtime of ArrayList methods
 * regardless of return type.
 * 
 * Instance variables:
 * numIters - How many times a method will be run
 * nanoToMilli - # of nanoseconds in a millisecond
 * listWithInt - ArrayList that is used to execute the methods
 */
public class RuntimeTester {

    // Sentinel; must be set by command-line input
    private static int numIters = -1;

    private static double nanoToMilli = 1000000.0;

    // Clean, aligned, cross-platform-safe formatting
    private static String formatOutput =
        "Operation: %s%n" +
        "  Per operation:  %.2f ns%n" +
        "  Total time:     %.3f ms%n" +
        "  Iterations:     %d%n%n";

    private static ArrayList listWithInt;

    public static <T> T measureTime(String s, Callable<T> method){
        listWithInt = new ArrayList<Integer>();
        long startTime = System.nanoTime();

        try {
            for (int i = 0; i < numIters - 1; i++){
                method.call();
            }
            return method.call();
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            long endTime = System.nanoTime();

            long totalNs = endTime - startTime;
            double perOpNs = (double) totalNs / numIters;
            double totalMs = totalNs / nanoToMilli;

            System.out.printf(formatOutput, s, perOpNs, totalMs, numIters);
        }
    }

    public static void measureTime(String s, Runnable r){
        listWithInt = new ArrayList<Integer>();
        long startTime = System.nanoTime();

        for (int i = 0; i < numIters; i++){
            r.run();
        }

        long endTime = System.nanoTime();

        long totalNs = endTime - startTime;
        double perOpNs = (double) totalNs / numIters;
        double totalMs = totalNs / nanoToMilli;

        System.out.printf(formatOutput, s, perOpNs, totalMs, numIters);
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args){

        if (args.length == 0) {
            System.err.println("ERROR: You must provide the number of iterations.");
            System.err.println("Usage: java RuntimeTester <numIterations>");
            System.exit(1);
        }

        try {
            int parsed = Integer.parseInt(args[0]);
            if (parsed <= 0) {
                System.err.println("ERROR: Iteration count must be positive.");
                System.exit(1);
            }
            numIters = parsed;
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Invalid number format for iteration count.");
            System.exit(1);
        }

        RuntimeTester.measureTime("add(0, 0)", () -> listWithInt.add(0, 0));
        RuntimeTester.measureTime("append(0)", () -> listWithInt.add(0));
    }
}
