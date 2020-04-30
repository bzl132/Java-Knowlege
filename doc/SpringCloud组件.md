# Spring Cloud 组件

## 注册中心

- CAP理论
  - 一致性(Consistency) (所有节点在同一时间具有相同的数据)
  - 可用性(Availability) (保证每个请求不管成功或者失败都有响应)
  - 分隔容忍(Partition tolerance) (系统中任意信息的丢失或失败不会影响系统的继续运作)

### Zookeeper

- CP原则
- 选举期间或半数服务挂掉，服务就不可用
- 通信方式：
  - Client和Follower： NIO
  - Follower和Leader：CS模式
    - 选举中 AuthFastLeaderElection和LeaderElection采用UDP模式进行通信，而FastLeaderElection仍然采用tcp/ip模式

### Eureka

- AP原则
- 90秒 没接到服务心跳注销服务，心跳30秒一次
- 节点在15分钟没85%以上都没有心跳，开启自我保护
  - 不移除其他服务
  - 接受注册
  - 网络稳定会同步

### Consul

- 

### Nacos

-  Nacos默认提供权重设置功能，调整承载流量压力
- 动态配置
- Nacos可用根据业务和环境进行分组管理
- 想在线对服务进行上下线和流量管理
- 不想采用MQ实现配置中心动态刷新
- 不想新增配置中心生产集群