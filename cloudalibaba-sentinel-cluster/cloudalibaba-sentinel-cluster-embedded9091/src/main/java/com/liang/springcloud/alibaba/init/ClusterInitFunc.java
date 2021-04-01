/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liang.springcloud.alibaba.init;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterParamFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.csp.sentinel.util.HostNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liang.springcloud.alibaba.Constants;
import com.liang.springcloud.alibaba.entity.ClusterGroupEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Eric Zhao
 */
public class ClusterInitFunc implements InitFunc {

    //应用名称
    private static final String APP_NAME = AppNameUtil.getAppName();

    //nacos集群地址
    private final String remoteAddress = "localhost:8848";

    //nacos配置的分组名称
    private final String groupId = "SENTINEL_GROUP";

    //配置的dataId
    private final String flowDataId = APP_NAME + Constants.FLOW_POSTFIX;
    private final String paramDataId = APP_NAME + Constants.PARAM_FLOW_POSTFIX;
    private final String configDataId = APP_NAME + Constants.CLIENT_CONFIG_POSTFIX;
    private final String clusterMapDataId = APP_NAME + Constants.CLUSTER_MAP_POSTFIX;

    private static final String SEPARATOR = "@";


    @Override
    public void init() {
        // Register client dynamic rule data source.
        //动态数据源的方式配置sentinel的流量控制和热点参数限流的规则。
        initDynamicRuleProperty();

        // Register token client related data source.
        // Token client common config
        // 集群限流客户端的配置属性
        initClientConfigProperty();
        // Token client assign config (e.g. target token server) retrieved from assign map:
        //初始化Token客户端
        initClientServerAssignProperty();

        // Register token server related data source.
        // Register dynamic rule data source supplier for token server:
        //集群的流控规则，比如限制整个集群的流控阀值,启动的时候需要添加-Dproject.name=项目名
        registerClusterRuleSupplier();
        // Token server transport config extracted from assign map:
        //初始化server的端口配置
        initServerTransportConfigProperty();

        // Init cluster state property for extracting mode from cluster map data source.
        //初始化集群中服务是客户端还是服务端
        initStateProperty();
    }

    private void initDynamicRuleProperty() {

        //流量控制的DataId分别是APP_NAME + Constants.FLOW_POSTFIX;热点参数限流规则的DataId是APP_NAME + Constants.PARAM_FLOW_POSTFIX;

        ReadableDataSource<String, List<FlowRule>> ruleSource = new NacosDataSource<>(remoteAddress, groupId,
            flowDataId, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(ruleSource.getProperty());

        ReadableDataSource<String, List<ParamFlowRule>> paramRuleSource = new NacosDataSource<>(remoteAddress, groupId,
            paramDataId, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {}));
        ParamFlowRuleManager.register2Property(paramRuleSource.getProperty());
    }

    private void initClientConfigProperty() {
        ReadableDataSource<String, ClusterClientConfig> clientConfigDs = new NacosDataSource<>(remoteAddress, groupId,
            configDataId, source -> JSON.parseObject(source, new TypeReference<ClusterClientConfig>() {}));
        ClusterClientConfigManager.registerClientConfigProperty(clientConfigDs.getProperty());
    }

    private void initServerTransportConfigProperty() {
        ReadableDataSource<String, ServerTransportConfig> serverTransportDs = new NacosDataSource<>(remoteAddress, groupId,
            clusterMapDataId, source -> {
            List<ClusterGroupEntity> groupList = new Gson().fromJson(source, new TypeToken<List<ClusterGroupEntity>>(){}.getType());
            return Optional.ofNullable(groupList)
                .flatMap(this::extractServerTransportConfig)
                .orElse(null);
        });
        ClusterServerConfigManager.registerServerTransportProperty(serverTransportDs.getProperty());
    }

    private void registerClusterRuleSupplier() {
        // Register cluster flow rule property supplier which creates data source by namespace.
        // Flow rule dataId format: ${namespace}-flow-rules
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<FlowRule>> ds = new NacosDataSource<>(remoteAddress, groupId,
                namespace + Constants.FLOW_POSTFIX, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
            return ds.getProperty();
        });
        // Register cluster parameter flow rule property supplier which creates data source by namespace.
        ClusterParamFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<ParamFlowRule>> ds = new NacosDataSource<>(remoteAddress, groupId,
                namespace + Constants.PARAM_FLOW_POSTFIX, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {}));
            return ds.getProperty();
        });
    }

    private void initClientServerAssignProperty() {
        // Cluster map format:
        // [{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","serverId":"112.12.88.68@8728","port":11111}]
        // serverId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)
        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new NacosDataSource<>(remoteAddress, groupId,
            clusterMapDataId, source -> {
            List<ClusterGroupEntity> groupList = new Gson().fromJson(source, new TypeToken<List<ClusterGroupEntity>>(){}.getType());
            return Optional.ofNullable(groupList)
                .flatMap(this::extractClientAssignment)
                .orElse(null);
        });
        ClusterClientConfigManager.registerServerAssignProperty(clientAssignDs.getProperty());
    }

    private void initStateProperty() {
        // Cluster map format:
        // [{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","serverId":"112.12.88.68@8728","port":11111}]
        // serverId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)
        ReadableDataSource<String, Integer> clusterModeDs = new NacosDataSource<>(remoteAddress, groupId,
            clusterMapDataId, source -> {
            List<ClusterGroupEntity> groupList = new Gson().fromJson(source, new TypeToken<List<ClusterGroupEntity>>(){}.getType());
            return Optional.ofNullable(groupList)
                .map(this::extractMode)
                .orElse(ClusterStateManager.CLUSTER_NOT_STARTED);
        });
        ClusterStateManager.registerProperty(clusterModeDs.getProperty());
    }

    private int extractMode(List<ClusterGroupEntity> groupList) {
        // If any server group serverId matches current, then it's token server.
        if (groupList.stream().anyMatch(this::machineEqual)) {
            return ClusterStateManager.CLUSTER_SERVER;
        }
        // If current machine belongs to any of the token server group, then it's token client.
        // Otherwise it's unassigned, should be set to NOT_STARTED.
        boolean canBeClient = groupList.stream()
            .flatMap(e -> e.getClientSet().stream())
            .filter(Objects::nonNull)
            .anyMatch(e -> e.equals(getCurrentMachineId()));
        return canBeClient ? ClusterStateManager.CLUSTER_CLIENT : ClusterStateManager.CLUSTER_NOT_STARTED;
    }

    private Optional<ServerTransportConfig> extractServerTransportConfig(List<ClusterGroupEntity> groupList) {
        return groupList.stream()
            .filter(this::machineEqual)
            .findAny()
            .map(e -> new ServerTransportConfig().setPort(e.getPort()).setIdleSeconds(600));
    }

    private Optional<ClusterClientAssignConfig> extractClientAssignment(List<ClusterGroupEntity> groupList) {
        if (groupList.stream().anyMatch(this::machineEqual)) {
            return Optional.empty();
        }
        // Build client assign config from the client set of target server group.
        for (ClusterGroupEntity group : groupList) {
            if (group.getClientSet().contains(getCurrentMachineId())) {
                String ip = group.getIp();
                Integer port = group.getPort();
                return Optional.of(new ClusterClientAssignConfig(ip, port));
            }
        }
        return Optional.empty();
    }

    private boolean machineEqual(/*@Valid*/ ClusterGroupEntity group) {
        return getCurrentMachineId().equals(group.getServerId());
    }

    private String getCurrentMachineId() {
        // Note: this may not work well for container-based env.
        return HostNameUtil.getIp() + SEPARATOR + TransportConfig.getRuntimePort();
    }
}