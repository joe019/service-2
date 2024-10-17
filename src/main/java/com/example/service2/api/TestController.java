package com.example.service2.api;

import com.example.service2.workflows.TestWorkflow;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/test")
public class TestController {

    @GetMapping("tracing")
    public void testTracing() {
        log.info("Received request from Service 1");
        TestWorkflow workflow = createWorkflow();
        WorkflowExecution workflowExecution = WorkflowClient.start(workflow::callTestActivity);
        log.info("Workflow started. Workflow ID: {}", workflowExecution.getWorkflowId());
    }

    private TestWorkflow createWorkflow() {
        WorkflowClient client = createWorkflowClient();
        WorkflowOptions options = createWorkflowOptions();
        return client.newWorkflowStub(TestWorkflow.class, options);
    }

    private WorkflowClient createWorkflowClient() {
        WorkflowServiceStubs serviceStubs = createWorkflowServiceStubs();
        return WorkflowClient.newInstance(serviceStubs);
    }

    private WorkflowServiceStubs createWorkflowServiceStubs() {
        return WorkflowServiceStubs.newLocalServiceStubs();
    }

    private WorkflowOptions createWorkflowOptions() {
        return WorkflowOptions.newBuilder()
                .setTaskQueue("TASK_QUEUE")
                .build();
    }
}