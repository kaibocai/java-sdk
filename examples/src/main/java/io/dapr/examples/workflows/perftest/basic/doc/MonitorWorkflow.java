package io.dapr.examples.workflows.perftest.basic.doc;

import io.dapr.workflows.Workflow;
import io.dapr.workflows.WorkflowStub;

public class MonitorWorkflow extends Workflow {
    @Override
    public WorkflowStub create() {
        return ctx -> {
            String status = ctx.callActivity("GetStatus", String.class).await();
            if ("healthy".equals(status)) {

            } else {

            }
        };
    }
}
