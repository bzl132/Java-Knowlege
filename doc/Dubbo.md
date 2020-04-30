# Dubbo

## 服务的注册与发现

总结一下服务注册与发现机制： 基于注册 中心的事件通知（订阅与发布），一切支持事件订阅与发布的框架都可以作为Dubbo注册中心的选型。

1. 服务提供者在暴露服务时，会向注册中心注册自己，具体就是在${service interface}/providers目录下添加 一个节点（临时），服务提供者需要与注册中心保持长连接，一旦连接断掉（重试连接）会话信息失效后，注册中心会认为该服务提供者不可用（提供者节点会被删除）。
2. 消费者在启动时，首先也会向注册中心注册自己，具体在${interface interface}/consumers目录下创建一个节点。
3. 消费者订阅${service interface}/ [  providers、configurators、routers ]三个目录，这些目录下的节点删除、新增事件都胡通知消费者，根据通知，重构服务调用器(Invoker)。

https://juejin.im/post/5e0857d5e51d4557ec03179e



