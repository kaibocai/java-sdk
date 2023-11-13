package io.dapr.examples.workflows.perftest.basic.doc;

import com.microsoft.durabletask.Task;
import io.dapr.workflows.Workflow;
import io.dapr.workflows.WorkflowStub;
import io.dapr.workflows.runtime.WorkflowActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FaninoutWorkflow extends Workflow {
    @Override
    public WorkflowStub create() {
        return ctx -> {
            // Get a list of N work items to process in parallel.
            Object[] workBatch = ctx.callActivity("GetWorkBatch", Object[].class).await();
            // Schedule the parallel tasks, but don't wait for them to complete yet.
            List<Task<Integer>> tasks = Arrays.stream(workBatch)
                    .map(workItem -> ctx.callActivity("ProcessWorkItem", workItem, int.class))
                    .collect(Collectors.toList());
            // Everything is scheduled. Wait here until all parallel tasks have completed.
            List<Integer> results = ctx.allOf(tasks).await();
            // Aggregate all N outputs and publish the result.
            int sum = results.stream().mapToInt(Integer::intValue).sum();
            ctx.complete(sum);
        };
    }
}
