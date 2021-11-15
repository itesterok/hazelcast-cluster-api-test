import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rest.client.CloudHazelcastApi;
import rest.dto.request.CredentialsRequest;
import rest.dto.request.NewClusterRequest;
import rest.dto.response.ClusterInfoResponse;
import rest.service.CloudHazelcastService;

import java.time.Duration;
import java.util.Arrays;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public final class ClusterManagementTest {
  private final CloudHazelcastApi client = CloudHazelcastApi.getDefault();
  private final CloudHazelcastService service = new CloudHazelcastService(client);
  private static String JWT;

  @BeforeAll
  public static void beforeAll() {
    JWT =
        step(
            "Log in with provided credentials and obtain JWT",
            () ->
                CloudHazelcastApi.getDefault()
                    .login(
                        CredentialsRequest.builder()
                            .email(System.getProperty("email"))
                            .password(System.getProperty("password"))
                            .build())
                    .getBody()
                    .getToken());
  }

  @BeforeEach
  public void beforeEach() {
    step(
        "As a prerequisite, delete the cluster which might be not yet deleted",
        () -> {
          client.listAllClusters(JWT).getContent().stream()
              .map(info -> info.getId())
              .findFirst()
              .ifPresent(id -> client.deleteCluster(JWT, id));
        });
    step(
        "Wait until all cluster are deleted",
        () -> {
          await()
              .atMost(Duration.ofSeconds(10))
              .until(() -> client.listAllClusters(JWT).numberOfElements == 0);
        });
  }

  @Test
  @DisplayName("Creation / deletion cluster functionality test")
  @Description(
      "Given the User is logged in\n"
          + "When the User creates the Cluster it gets created\n"
          + "And When the User deletes the Cluster it gets deleted\n")
  public void createCluster() {
    // given
    NewClusterRequest newClusterRequest =
        NewClusterRequest.builder()
            .name("My test name")
            .cloudProviders(Arrays.asList(1))
            .clusterTypeId(1)
            .hazelcastVersion("4.2")
            .regions(Arrays.asList(1))
            .build();

    // when
    int clusterId = service.withToken(JWT).createClusterAndWaitUntilRunning(newClusterRequest);
    ClusterInfoResponse clusterInfo = client.getCluster(JWT, clusterId);

    ResponseEntity<String> deletionResponse =
        step("Delete the cluster", () -> client.deleteCluster(JWT, clusterId));

    // then
    step(
        "Assert all the details are correct",
        () -> {
          assertThat(clusterInfo.getName()).isEqualTo(newClusterRequest.getName());
          assertThat(clusterInfo.getCloudProviders().get(0).id)
              .isEqualTo(newClusterRequest.getCloudProviders().get(0));
          assertThat(clusterInfo.getClusterType().id).isEqualTo(newClusterRequest.clusterTypeId);
          assertThat(clusterInfo.getHazelcastVersion())
              .isEqualTo(newClusterRequest.hazelcastVersion);
          assertThat(clusterInfo.regions.get(0).id).isEqualTo(newClusterRequest.regions.get(0));
          assertThat(deletionResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        });
  }
}
