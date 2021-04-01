package com.liang.springcloud.alibaba.init;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liang.springcloud.alibaba.Constants;

import java.util.List;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-04-01 17:47
 */
public class ClusterClientInitFunc implements InitFunc {

    //项目名称
    private static final String APP_NAME = AppNameUtil.getAppName();

    //nacos集群地址
    private final String remoteAddress = "localhost:8848";

    //nacos配置的分组名称
    private final String groupId = "SENTINEL_ALONE_GROUP";

    //项目名称 + Constants的配置名称，组成配置的dataID
    private final String flowDataId = APP_NAME + Constants.FLOW_POSTFIX;
    private final String paramDataId = APP_NAME + Constants.PARAM_FLOW_POSTFIX;
    private final String configDataId = "cluster-client-config";
    private final String serverDataId =  "cluster-server-config";


    @Override
    public void init() throws Exception {

        // Register client dynamic rule data source.
        //客户端，动态数据源的方式配置sentinel的流量控制和热点参数限流的规则。
        initDynamicRuleProperty();

        // Register token client related data source.
        // Token client common config
        // 集群限流客户端的配置属性
        initClientConfigProperty();
        // Token client assign config (e.g. target token server) retrieved from assign map:
        //初始化Token客户端
        initClientServerAssignProperty();

        //初始化客户端状态
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

    private void initClientServerAssignProperty() {
        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new NacosDataSource<>(remoteAddress, groupId,
                serverDataId, source -> JSON.parseObject(source, new TypeReference<ClusterClientAssignConfig>() {}));
        ClusterClientConfigManager.registerServerAssignProperty(clientAssignDs.getProperty());
    }

    private void initStateProperty() {
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);

    }
}
