package io.dapr.examples.workflows.perftest.basic;

import io.dapr.workflows.Workflow;
import io.dapr.workflows.WorkflowStub;

public class LongRunWorkflow extends Workflow {

    @Override
    public WorkflowStub create() {
        return ctx -> {
            int result = 0;
            for (int i = 0; i < 10; i++) {
                // spawns 10 long-running sub-workflow
                result += ctx.callSubWorkflow("subLongRunWorkflow", null, int.class).await();
            }
            ctx.complete(result);
        };
    }
}
