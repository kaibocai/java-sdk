package io.dapr.examples.workflows.perftest.basic;

import io.dapr.workflows.Workflow;
import io.dapr.workflows.WorkflowStub;

public class SignalSubWorkflow extends Workflow {
    @Override
    public WorkflowStub create() {
        return ctx -> {
            Void signalActivity = ctx.callActivity("signalActivity").await();
            ctx.complete("finish sub-workflow");
        };
    }
}
