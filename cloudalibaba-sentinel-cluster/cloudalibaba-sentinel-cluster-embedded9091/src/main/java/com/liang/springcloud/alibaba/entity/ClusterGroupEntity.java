package com.liang.springcloud.alibaba.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-03-31 11:05
 */
public class ClusterGroupEntity implements Serializable {

    private String serverId;
    private String ip;
    private Integer port;

    private Set<String> clientSet;


    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Set<String> getClientSet() {
        return clientSet;
    }

    public void setClientSet(Set<String> clientSet) {
        this.clientSet = clientSet;
    }

    @Override
    public String toString() {
        return "ClusterGroupEntity{" +
                "serverId='" + serverId + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", clientSet=" + clientSet +
                '}';
    }
}
