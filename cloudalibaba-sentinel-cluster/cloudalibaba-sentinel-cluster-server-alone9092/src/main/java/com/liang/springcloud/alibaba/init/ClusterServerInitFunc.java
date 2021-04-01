package com.liang.springcloud.alibaba.init;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterParamFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerFlowConfig;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liang.springcloud.alibaba.Constants;

import java.util.List;
import java.util.Set;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-04-01 10:01
 */
public class ClusterServerInitFunc implements InitFunc {

    //nacos集群地址
    private final String remoteAddress = "localhost:8848";
    //配置的分组名称
    private final String groupId = "SENTINEL_ALONE_GROUP";

    //配置的dataId
    private final String namespaceSetDataId = "cluster-server-namespace-set";
    private final String serverTransportDataId = "cluster-server-transport-config";
    private final String serverFlowDataId = "cluster-server-flow-config";

    @Override
    public void init() {

        //监听特定namespace（集群的namespace或客户端项目名）下的集群限流规则
        initPropertySupplier();
        // 设置tokenServer管辖的作用域(即管理哪些应用)
        initTokenServerNameSpaces();

        // Server transport configuration data source.
        //Server端配置
        initServerTransportConfig();

        // 初始化最大qps
        initServerFlowConfig();

        //初始化服务器状态
        initStateProperty();

    }

    private  void initPropertySupplier(){

        // Register cluster flow rule property supplier which creates data source by namespace.
        // Flow rule dataId format: ${namespace}-flow-rules
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<FlowRule>> ds = new NacosDataSource<>(remoteAddress, groupId,
                    namespace + Constants.FLOW_POSTFIX,
                    source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
            return ds.getProperty();
        });
        // Register cluster parameter flow rule property supplier which creates data source by namespace.
        ClusterParamFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<ParamFlowRule>> ds = new NacosDataSource<>(remoteAddress, groupId,
                    namespace + Constants.PARAM_FLOW_POSTFIX, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {}));
            return ds.getProperty();
        });

    }


    private void initTokenServerNameSpaces(){
        // Server namespace set (scope) data source.
        ReadableDataSource<String, Set<String>> namespaceDs = new NacosDataSource<>(remoteAddress, groupId,
                namespaceSetDataId, source -> JSON.parseObject(source, new TypeReference<Set<String>>() {}));
        ClusterServerConfigManager.registerNamespaceSetProperty(namespaceDs.getProperty());
    }

    private void initServerTransportConfig(){
        // Server transport configuration data source.
        ReadableDataSource<String, ServerTransportConfig> transportConfigDs = new NacosDataSource<>(remoteAddress,
                groupId, serverTransportDataId,
                source -> JSON.parseObject(source, new TypeReference<ServerTransportConfig>() {}));
        ClusterServerConfigManager.registerServerTransportProperty(transportConfigDs.getProperty());
    }


    private void initServerFlowConfig(){

        // Server namespace set (scope) data source.
        ReadableDataSource<String, ServerFlowConfig> serverFlowConfig = new NacosDataSource<>(remoteAddress, groupId,
                serverFlowDataId, source -> JSON.parseObject(source, new TypeReference<ServerFlowConfig>() {}));

        ClusterServerConfigManager.registerGlobalServerFlowProperty(serverFlowConfig.getProperty());
    }

    private void initStateProperty() {
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_SERVER);

    }
}
