package com.tanj.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class zkTest {

    private static String connectString = "192.168.188.133:2181";
    private static int sessionTimeout = 2000;
    private ZooKeeper zooKeeper = null;

    /**
     * 创建连接
     *
     * @throws IOException
     */
    @Before
    public void init() throws IOException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                // 收到事件通知后的回调函数（用户的业务逻辑）
                try {
                    List<String> childrens = zooKeeper.getChildren("/", true);

                    for (String children : childrens) {
                        System.out.println("输出结果"+children);

                    }
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建节点
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void createNode() throws KeeperException, InterruptedException {
       //参数 1：要创建的节点的路径； 参数 2：节点数据 ； 参数 3：节点权限 ；参数 4：节点的类型
        String path = zooKeeper.create("/atjack", "hahaa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("输出的是" + path);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 判断节点是否存在
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void exist() throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists("/atjack", false);
        System.out.println(exists == null ? "noNode" : "exists");
    }

    @Test
    public void getDateAndWatch() throws KeeperException, InterruptedException {
        List<String> childrens = zooKeeper.getChildren("/", true);

        for (String children : childrens) {
            System.out.println("输出结果"+children);

        }

        // 延时阻塞
        Thread.sleep(Long.MAX_VALUE);
    }

}
