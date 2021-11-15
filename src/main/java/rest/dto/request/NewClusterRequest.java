package rest.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewClusterRequest {
    public List<String> zones;
    public List<Integer> cloudProviders;
    public List<Integer> regions;
    public String name;
    public String hazelcastVersion;
    public int memory;
    public boolean tlsEnabled;
    public boolean autoScalingEnabled;
    public boolean hotRestartEnabled;
    public boolean hotBackupEnabled;
    public boolean ipWhitelistEnabled;
    public List<Object> clusterIpWhiteList;
    public int clusterTypeId;
    public List<Object> mapConfigs;
    public List<Object> jcacheConfigs;
    public List<Object> replicatedMapConfigs;
    public List<Object> queueConfigs;
    public List<Object> setConfigs;
    public List<Object> listConfigs;
    public List<Object> topicConfigs;
    public List<Object> multiMapConfigs;
    public List<Object> ringBufferConfigs;
    public List<Object> reliableTopicConfigs;
}
