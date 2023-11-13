package io.dapr.examples.workflows.perftest.basic;

import com.microsoft.durabletask.Task;
import io.dapr.workflows.Workflow;
import io.dapr.workflows.WorkflowStub;

import java.time.Duration;

public class BasicWorkflow extends Workflow {
    @Override
    public WorkflowStub create() {
        return ctx -> {
            // wait for external event
            Task<Void> signal = ctx.waitForExternalEvent("signal");
            // create a timer
            Task<Void> timer = ctx.createTimer(Duration.ofSeconds(5));
            // wait for task that completes first
            Task<?> await = ctx.anyOf(signal, timer).await();
            // decide which activity to be executed
            if (await == signal) {
                // call a sub workflow
                Void signalActivity = ctx.callSubWorkflow("signalSubWorkflow").await();
            } else {
                // call a activity
                Void timerActivity = ctx.callActivity("timerActivity").await();
            }
            // finish success
            ctx.complete("finish");
        };
    }
}
