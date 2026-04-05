package com.farmSphere.estate.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ClusterResponse {
    private Long clusterId;
    private String clusterName;
    private String createdAt;
}
