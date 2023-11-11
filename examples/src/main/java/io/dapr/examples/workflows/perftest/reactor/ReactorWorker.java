package io.dapr.examples.workflows.perftest.reactor;

import io.dapr.workflows.runtime.WorkflowRuntime;
import io.dapr.workflows.runtime.WorkflowRuntimeBuilder;

public class ReactorWorker {
    /**
     * The main method of this app.
     *
     * @param args The port the app will listen on.
     * @throws Exception An Exception.
     */
    public static void main(String[] args) {
        WorkflowRuntimeBuilder builder = new WorkflowRuntimeBuilder()
                .registerWorkflow(ReactorWorkflow.class);

        // Build and then start the workflow runtime pulling and executing tasks
        try (WorkflowRuntime runtime = builder.build()) {
            System.out.println("Start workflow runtime");
            runtime.start();
        }
    }
}
