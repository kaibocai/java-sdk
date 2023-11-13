package io.dapr.examples.workflows.perftest.basic;

import io.dapr.workflows.Workflow;
import io.dapr.workflows.WorkflowStub;

public class SubLongRunWorkflow extends Workflow {
    @Override
    public WorkflowStub create() {
        return ctx -> {
            Integer counter = ctx.getInput(int.class);
            if (counter == null) counter = 0;
            while (true) {
                ctx.waitForExternalEvent("long-run-signal").await();
                counter++;
                // When the counter is larget, call continueAsNew to clean up replaying events.
                if (counter == 3000) ctx.continueAsNew(counter);
                // If the workflow counter can add up to a large number, it is considered success.
                if (counter == 50000) ctx.complete(counter);
            }
        };
    }
}

