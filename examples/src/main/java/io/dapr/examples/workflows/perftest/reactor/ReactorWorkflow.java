package io.dapr.examples.workflows.perftest.reactor;

import io.dapr.workflows.Workflow;
import io.dapr.workflows.WorkflowStub;

public class ReactorWorkflow extends Workflow {
    @Override
    public WorkflowStub create() {
        return ctx -> {
            Person person = ctx.waitForExternalEvent("signal", Person.class).await();
            ctx.complete(person.name);
        };
    }

    private static class Person{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
