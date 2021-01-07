package profiler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public aspect Profiler {
    private final Map<String, Long> executions = new ConcurrentHashMap<>();
    private final Map<String, Long> executionTimes = new ConcurrentHashMap<>();

    public Map<String, Long> getExecutions() {
        return executions;
    }

    public Map<String, Long> getExecutionTimes() {
        return executionTimes;
    }

    pointcut methodExecution(): execution(* app.*.*(..));

    Object around(): methodExecution() {
        final long startTime = System.nanoTime();
        try {
            return proceed();
        } finally {
            final long executionTime = System.nanoTime() - startTime;
            final String signature = thisJoinPoint.getSignature().toLongString();
            executions.compute(signature, (k, v) -> v == null ? 1 : v + 1);
            executionTimes.compute(signature, (k, v) -> v == null ? executionTime : v + executionTime);
        }
    }
}
