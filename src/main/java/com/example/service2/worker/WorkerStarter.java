package com.example.service2.worker;

import com.example.service2.activities.TestActivityImpl;
import com.example.service2.workflows.TestWorkflowImpl;
import io.temporal.activity.ActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkflowImplementationOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class WorkerStarter implements ApplicationRunner {
    @Autowired
    private TestActivityImpl testActivityImpl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        WorkerFactory factory = createWorkerFactory();
        Worker worker = factory.newWorker("TASK_QUEUE");
        WorkflowImplementationOptions workflowOptions = createWorkflowImplementationOptions();
        worker.registerWorkflowImplementationTypes(workflowOptions, TestWorkflowImpl.class);
        worker.registerActivitiesImplementations(testActivityImpl);
        factory.start();
    }

    private WorkerFactory createWorkerFactory() {
        WorkflowClient client = createWorkflowClient();
        return WorkerFactory.newInstance(client);
    }

    private WorkflowClient createWorkflowClient() {
        WorkflowServiceStubs serviceStubs = createWorkflowServiceStubs();
        return WorkflowClient.newInstance(serviceStubs);
    }

    private WorkflowServiceStubs createWorkflowServiceStubs() {
        return WorkflowServiceStubs.newLocalServiceStubs();
    }

    private WorkflowImplementationOptions createWorkflowImplementationOptions() {
        return WorkflowImplementationOptions.newBuilder()
                .setActivityOptions(createActivityOptionsMap())
                .build();
    }

    private Map<String, ActivityOptions> createActivityOptionsMap() {
        Map<String, ActivityOptions> activityOptionsMap = new HashMap<>();
        activityOptionsMap.put("PrintHello", createActivityOptions());
        return activityOptionsMap;
    }

    private ActivityOptions createActivityOptions() {
        return ActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(60))
                .build();
    }
}
