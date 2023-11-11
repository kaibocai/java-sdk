package io.dapr.examples.workflows.perftest.reactor;

import io.dapr.workflows.client.DaprWorkflowClient;
import io.dapr.workflows.client.WorkflowInstanceStatus;

import java.util.concurrent.TimeoutException;

public class ReactorClient {
    /**
     * The main method of this app.
     *
     * @param args The port the app will listen on.
     * @throws Exception An Exception.
     */
    public static void main(String[] args) {
        try (DaprWorkflowClient client = new DaprWorkflowClient()) {
            String instanceId = "test";
            client.scheduleNewWorkflow(ReactorWorkflow.class, null, instanceId);
            System.out.printf("Started a new signal model workflow with instance ID: %s%n", instanceId);
            WorkflowInstanceStatus workflowInstanceStatus =
                    client.waitForInstanceCompletion(instanceId, null, true);

            String result = workflowInstanceStatus.readOutputAs(String.class);
            System.out.printf("workflow instance with ID: %s completed with result: %s%n", instanceId, result);

        } catch (TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
