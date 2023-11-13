package io.dapr.examples.workflows.perftest.basic.doc;

import com.microsoft.durabletask.TaskCanceledException;
import io.dapr.workflows.Workflow;
import io.dapr.workflows.WorkflowStub;

import java.time.Duration;

import static io.dapr.examples.workflows.perftest.basic.doc.Constants.ORDER_APPROVAL_THRESHOLD;

public class ExternalSystemInteractionWorkflow extends Workflow {
    @Override
    public WorkflowStub create() {
        return ctx -> {
            // ...other steps...
            Integer orderCost = ctx.getInput(int.class);
            // Require orders over a certain threshold to be approved
            if (orderCost > ORDER_APPROVAL_THRESHOLD) {
                try {
                    // Request human approval for this order
                    ctx.callActivity("RequestApprovalActivity", orderCost, Void.class).await();
                    // Pause and wait for a human to approve the order
                    boolean approved = ctx.waitForExternalEvent("ManagerApproval", Duration.ofDays(3), boolean.class).await();
                    if (!approved) {
                        // The order was rejected, end the workflow here
                        ctx.complete("Process reject");
                    }
                } catch (TaskCanceledException e) {
                    // An approval timeout results in automatic order cancellation
                    ctx.complete("Process cancel");
                }
            }
            // ...other steps...

            // End the workflow with a success result
            ctx.complete("Process approved");
        };
    }
}
