package rest.service;

import io.qameta.allure.Step;
import rest.client.CloudHazelcastApi;
import rest.dto.request.NewClusterRequest;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

public class CloudHazelcastService {
  CloudHazelcastApi client;
  private String jwt;

  public CloudHazelcastService(CloudHazelcastApi client) {
    this.client = client;
  }

  public CloudHazelcastService withToken(String jwt) {
    this.jwt = jwt;
    return this;
  }

  @Step
  public Integer createClusterAndWaitUntilRunning(NewClusterRequest request) {
    int clusterId = client.createCluster(jwt, request).getId();
    await()
        .atMost(Duration.ofSeconds(120))
        .pollInterval(Duration.ofSeconds(20))
        .until(() -> client.getCluster(jwt, clusterId).getState().equals("RUNNING"));
    return clusterId;
  }
}
