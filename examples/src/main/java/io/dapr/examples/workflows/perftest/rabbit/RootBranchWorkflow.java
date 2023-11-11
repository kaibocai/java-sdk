/*
 * Copyright 2023 The Dapr Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
limitations under the License.
*/

package io.dapr.examples.workflows.perftest.rabbit;

import io.dapr.workflows.WorkflowStub;

import static io.dapr.examples.workflows.perftest.rabbit.Constants.DEPTH;
import static io.dapr.examples.workflows.perftest.rabbit.Constants.NUM_OF_CHILDREN;

public class RootBranchWorkflow extends io.dapr.workflows.Workflow {
    @Override
    public WorkflowStub create() {
        return ctx -> {
            int sum = 0;
            for (int i = 0; i < NUM_OF_CHILDREN; i++) {
                sum += ctx.callSubWorkflow(MiddleBranchWorkflow.class.getName(), DEPTH, int.class).await();
            }
            ctx.complete(sum);
            ctx.getLogger().info("Root workflow returns: " + sum);
        };
    }
}
