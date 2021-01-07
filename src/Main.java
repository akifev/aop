import app.Test;
import profiler.Profiler;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Profiler profiler = Profiler.aspectOf();

        long totalProgramExecutionTime = runTest();

        Map<String, Long> executions = profiler.getExecutions();
        Map<String, Long> executionTimes = profiler.getExecutionTimes();
        
        printBenchmarks(totalProgramExecutionTime, executions, executionTimes);
    }

    private static void printBenchmarks(long totalProgramExecutionTime, Map<String, Long> executions, Map<String, Long> executionTimes) {
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.printf("%30s %10s %20s %20s %20% %n", "METHOD", "COUNT", "TOTAL TIME(ns)", "AVG TIME(ns)", "%");
        System.out.println("---------------------------------------------------------------------------------------------------------------");

        for (String signature : executions.keySet()) {
            long count = executions.get(signature);
            long totalTime = executionTimes.get(signature);
            double averageTime = totalTime * 1.0 / count;
            double percent = totalTime * 100.0 / totalProgramExecutionTime;

            System.out.printf("%30s %10s %20s %20.2f %20.0f%% %n", signature, count, totalTime, averageTime, percent);
        }
    }

    private static long runTest() {
        long totalTime = System.nanoTime();
        try {
            Test test = new Test();
            test.run();
        } catch (Throwable ignored) {
        } finally {
            totalTime = System.nanoTime() - totalTime;
        }
        return totalTime;
    }
}