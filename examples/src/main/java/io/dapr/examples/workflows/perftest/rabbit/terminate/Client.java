package io.dapr.examples.workflows.perftest.rabbit.terminate;

import io.dapr.examples.workflows.perftest.rabbit.RootBranchWorkflow;
import io.dapr.workflows.client.DaprWorkflowClient;
import io.dapr.workflows.client.WorkflowInstanceStatus;

import java.util.concurrent.TimeoutException;

public class Client {
    public static void main(String[] args) {
        try (DaprWorkflowClient client = new DaprWorkflowClient()) {
            String instanceId = client.scheduleNewWorkflow(RootBranchWorkflow.class);
            System.out.printf("Started a new rabbit model workflow with instance ID: %s%n", instanceId);

            client.terminateWorkflow(instanceId, "terminate-test");
            WorkflowInstanceStatus instanceStatus = client.waitForInstanceCompletion(instanceId, null, true);
            String res = instanceStatus.readOutputAs(String.class);

            // res == "terminate-test"
//            instanceStatus.getRuntimeStatus().equals(TERMINATED);

        } catch (TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
