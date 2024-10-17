package com.example.service2.workflows;

import com.example.service2.activities.TestActivity;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestWorkflowImpl implements TestWorkflow {
    private final TestActivity testActivity = Workflow.newActivityStub(TestActivity.class);

    @Override
    public void callTestActivity() {
        log.info("Calling test activity");
        testActivity.printHello();
    }
}
