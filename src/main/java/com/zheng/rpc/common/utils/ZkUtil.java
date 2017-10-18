package com.zheng.rpc.common.utils;

import com.zheng.rpc.common.constants.ZkConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.spi.ServiceRegistry;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * zk通用方法
 * Created by zhenglian on 2017/10/17.
 */
public class ZkUtil {
    private static Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);
    /**
     * zk节点路径分隔符
     */
    private static final String ZK_NODE_SEPERATOR = "/";

    /**
     * 判断zookeeper集群中指定节点是否存在
     *
     * @param nodePath
     * @return
     * @throws Exception
     */
    public static boolean existNode(ZooKeeper zk, String nodePath) throws Exception {
        Stat stat = zk.exists(nodePath, false);
        return Optional.ofNullable(stat).isPresent();
    }

    /**
     * 创建服务器注册的父级节点
     * 父级节点是持久化类型的
     * 如果没有创建父级节点而直接创建子节点，zookeeper将报错
     */
    public static void createNode(ZooKeeper zk, String data) {
        // 创建节点前先成功创建父节点
        createParentNode(zk);
        // 创建子节点
        String childNode = new StringBuilder(ZkConstants.PARENT_NODE)
                .append(ZkConstants.DATA_NODE).toString();
        try {
            if (!existNode(zk, childNode)) {
                zk.create(childNode, data.getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL_SEQUENTIAL);
            }
        } catch (Exception e) {
            logger.error("创建节点{}发生异常，异常信息: {}", childNode, e.getLocalizedMessage());
        }
    }

    /**
     * 创建父节点
     */
    public static void createParentNode(ZooKeeper zk) {
        String node = ZkConstants.PARENT_NODE;
        try {
            if (!existNode(zk, node)) {
                zk.create(node, "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            logger.error("创建节点{}发生异常，异常信息: {}", node, e.getLocalizedMessage());
        }
    }

    /**
     * 获取指定节点下的子节点对应的值
     *
     * @param zk
     * @param parentNode
     */
    public static Map<String, Object> getChildren(ZooKeeper zk, String parentNode, Watcher watcher) {
        Map<String, Object> children = new HashMap<>();
        List<String> nodes;
        try {
            if (!Optional.ofNullable(watcher).isPresent()) {
                nodes = zk.getChildren(parentNode, false);
            } else {
                nodes = zk.getChildren(parentNode, watcher);
            }
        } catch (Exception e) {
            throw new RuntimeException("获取指定节点：" + parentNode + "下的子节点异常，错误原因：" + e.getLocalizedMessage());
        }
        if (CollectionUtils.isEmpty(nodes)) {
            return new HashMap<>();
        }
        nodes.stream()
                .filter(node -> Optional.ofNullable(node).isPresent())
                .forEach(node -> {
                    String path = new StringBuilder(ZkConstants.PARENT_NODE)
                            .append(ZK_NODE_SEPERATOR)
                            .append(node).toString();
                    try {
                        byte[] bytes = zk.getData(path, false, null);
                        String data = new String(bytes, StandardCharsets.UTF_8);
                        children.put(node, data);
                    } catch (Exception e) {
                        throw new RuntimeException("获取指定节点: " + path + "值发生异常，异常原因: " + e.getLocalizedMessage());
                    }
                });

        return children;
    }
}
