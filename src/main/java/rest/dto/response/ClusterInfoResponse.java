package rest.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ClusterInfoResponse {
    public int id;
    public int customerId;
    public double memory;
    public int port;
    public String name;
    public String releaseName;
    public String mancenterInternalUrl;
    public int clientCount;
    public int memoryUsage;
    public long creationTime;
    public long startTime;
    public String hazelcastVersion;
    public String managementCenterVersion;
    public String managementCenterAlias;
    public String clusterPassword;
    public String clusterPasswordSalt;
    public String state;
    public String desiredState;
    public List<Object> mapConfigs;
    public List<Object> jcacheConfigs;
    public List<Object> listConfigs;
    public List<Object> setConfigs;
    public List<Object> multiMapConfigs;
    public List<Object> replicatedMapConfigs;
    public List<Object> queueConfigs;
    public List<Object> ringBufferConfigs;
    public List<Object> reliableTopicConfigs;
    public List<Object> topicConfigs;
    public ClusterType clusterType;
    public List<CloudProvider> cloudProviders;
    public DefaultCloudProvider defaultCloudProvider;
    public List<Region> regions;
    public List<AvailabilityZone> availabilityZones;
    public List<KubernetesCluster> kubernetesClusters;
    public boolean hotRestartEnabled;
    public boolean hotBackupEnabled;
    public boolean isFree;
    public boolean tlsEnabled;
    public String discoveryUrl;
    public List<Object> allowedIps;
    public boolean autoScalingEnabled;
    public boolean ipWhitelistEnabled;
    public boolean isDedicated;
    public ProgressInfo progressInfo;
    public List<Token> tokens;
    public boolean isPrivate;
    public int maxAvailableMemory;

    public class ClusterType {
        public int id;
        public String name;
        public String title;
        public String description;
        public String tooltip;
        public String tooltipDisabled;
        public int heap;
        public int nativeMemory;
        public String podMemoryRequest;
        public String podCpuRequest;
        public String podMemoryLimit;
        public String podCpuLimit;
        public int scaleFactor;
        public boolean isEnabled;
        public int memoryRangeMin;
        public int memoryRangeMax;
        public double maxMemberMemory;
        public double hourlyMemberPrice;
        public boolean freeClusterType;
    }

    public class CloudProvider {
        public int id;
        public String name;
        public String title;
    }

    public class DefaultCloudProvider {
        public int id;
        public String name;
        public String title;
    }

    public class Region {
        public int id;
        public int cloudProviderId;
        public String name;
        public String title;
    }

    public class AvailabilityZone {
        public int id;
        public int regionId;
        public String name;
        public String title;
    }

    public class KubernetesCluster {
        public int id;
        public String name;
    }

    public class ProgressInfo {
        public String status;
        public int totalItems;
        public int completedItems;
        public String currentStatusText;
    }

    public class Token {
        public String sourceIdentifier;
        public String token;
    }
}
