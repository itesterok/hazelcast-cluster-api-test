package rest.client;

import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.http.ResponseEntity;
import rest.dto.request.CredentialsRequest;
import rest.dto.request.NewClusterRequest;
import rest.dto.response.AllClustersInfoResponse;
import rest.dto.response.ClusterInfoResponse;
import rest.dto.response.TokenResponse;

import java.util.concurrent.TimeUnit;

public interface CloudHazelcastApi {
  String URL = "https://coordinator.hazelcast.cloud";

  @RequestLine("POST /customers/login")
  @Headers("Content-Type: application/json")
  ResponseEntity<TokenResponse> login(CredentialsRequest credentials);

  @RequestLine("POST /cluster")
  @Headers({"Content-Type: application/json", "Authorization: Bearer {token}"})
  ClusterInfoResponse createCluster(@Param("token") String token, NewClusterRequest request);

  @RequestLine("GET /cluster")
  @Headers({"Content-Type: application/json", "Authorization: Bearer {token}"})
  AllClustersInfoResponse listAllClusters(@Param("token") String token);

  @RequestLine("GET /cluster/{cluster}")
  @Headers({"Content-Type: application/json", "Authorization: Bearer {token}"})
  ClusterInfoResponse getCluster(@Param("token") String token, @Param("cluster") Integer clusterId);

  @RequestLine("DELETE /cluster/{cluster}")
  @Headers({"Content-Type: application/json", "Authorization: Bearer {token}"})
  ResponseEntity<String> deleteCluster(
      @Param("token") String token, @Param("cluster") Integer clusterId);

  static CloudHazelcastApi getDefault() {
    final Decoder decoder = new ResponseEntityDecoder(new GsonDecoder());
    final Encoder encoder = new GsonEncoder();
    return Feign.builder()
        .encoder(encoder)
        .decoder(decoder)
        .logger(new Logger.ErrorLogger())
        .logLevel(Logger.Level.FULL)
        .options(new Request.Options(20, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true))
        .target(CloudHazelcastApi.class, URL);
  }
}
