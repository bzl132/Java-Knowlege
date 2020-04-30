# Redis

https://www.runoob.com/redis/redis-scripting.html



## 分布式锁的问题

1. 未释放锁
   - 业务代码问题
2. B的锁被A给释放了
3. 数据库事物超时
4. 锁过期了，业务还没执行完
5. 主从复制的坑

## 作用

1. 排行榜
2. 计算器/限速器
3. 好友关系：并集/差集
4. 简单消息队列：不需要高可靠
5. Session共享
6. 



## 数据类型

1. String
2. List
3. Set
4. Sorted Set
5. Hash
6. Bitmap 位图
7. HyperLogLog



### 数据命令

#### String

- set
- get
- getrange 字符串范围
- getset
- getbit 字符窜偏移
- setbit
- setex
- setnx
- strlen 字符串长度
- mget 获取多个Key的值
- mset
- msetnx
- incr
- incrby
- decr

#### List

- lpush 头部添加一个元素
- lpop 移除头部并获取这个元素
- blpop
- blpop
- llen
- lrange

#### Set

- sadd
- sdiff
- sinter

#### Sorted Set

- zadd
- zcard
- zcount key min max
- zrange key min max

#### Hash

- hdel
- hexists
- hget
- hkeys
- hmget
- hlen

#### Bitmap

#### HyperLogLog

- pfadd
- pfcount
- pfmerge

## 事物

- 多个命令保持原子性执行

## Lua脚本

- Eval 执行脚本
- eval script numkeys key。。。 arg。。。

## 管道

- Subscribe 订阅管道
- publish 发布信息

## 持久化

- RDB
  - 按照规则定时将内存数据存储到磁盘上
  - save 900 1 save 300 10 save 60 10000 三条有一条满足就会自动执行快照，规则可配置
  - save bgsave FLUSHALL 可手动触发快照
    - save 停止Redis线程，主线程备份快照，影响使用，不建议生产使用
    - bgsave 子线程执行快照备份
    - FLUSHALL 清空内存数据，配置快照规则时会执行快照备份
- AOF
  - 每执行一次更改会，会讲命令记录下来
  - appendonly yes 开启 appendfilename 改文件名 默认 appendonly.aof
  - 配置重写策略
    - 百分比 auto-aof-rewritepercentage
    - 大小 auto-aof-rewrite-min-size 

## 内存回收策略

- 默认
  - noeviction 达到阀值，所有引起申请内存的命令会报错
- 所有数据
  - allkeys-lru 所有数据中淘汰最近最少数据
  - allkeys-random 所有数据 随机移除
- 可过期数据
  - volation-random 从设置过期时间的数据集中淘汰任意数据
  - volation-lru 从设置过期时间的数据集中淘汰最近最少使用数据
  - volation-ttl 从设置过期时间的数据集中淘汰快要过期的数据

## redis设置过期key如何去清除？

- 自动删除
  - 消极方法：发现失效才去删除
  - 积极方法：周期性的测试一些Key，过期就删除

## Redis是单进程单线程？性能为什么这么快

- 瓶颈不在CPU，而是内存和网络带宽
- 利用队列使并发访问变串行访问，不会有并发问题
- IO多路复用
  - epoll + 自己实现的简单事件框架
- 基于内存，读写速度快
- 单线程省去上下文切换的开销
- 不需要各种锁

## 高可用

### 主从模式

- 工作方式
  - 完整复制 2.8前
    - SYNC，在主完成后台存储后，会同步全量文件
    - 每次都同步全量数据，性能会影响
  - 部分复制
    - 从机记录Master的runid和offset
    - 当从断开再连接时，会比较runid是否一致，一致会部分同步，不一致全量
  - 无磁盘复制 2.8.18
    - 子进程直接将**RDB**通过网络发送给从服务器，不使用磁盘作为中间存储

### 哨兵

#### 作用

- 监控
- 提醒
- 自动故障转移

#### 工作方式

- 每个哨兵 1s/次向 Master/Slave/Sentinel发送PING
- 除Master外PING时间超过配置则进程被标记下线
- Master如果被标记下线时，所有其他哨兵已1次/s确认Master状态
- 如果确认的哨兵大于制定配置，则Master被下线
- Master下线，哨兵会1次/s通知Slave选举Master
- 如果Master在选举前恢复，会清除下线标记



## select poll epoll 区别

1. select
   - select 控制的fd_set有上限 32位 1024 64位2048
     - fd_set是一种数据结构，是一个long数组与已打开的文件或socket相关连，记录其状态
   - 获取到完成通知后，要轮询所有fd_set，控制的流越多越耗性能
2. poll
   - 使用链表fd_set没有上限
   - 获取通知后，遍历文件描述符
3. epoll
   - 没有上限
   - 当文件描述符准备好后，会主动调用callback函数

### Redis 常见性能问题和解决方案？

1、Master 最好不要做任何持久化工作，如 RDB 内存快照和 AOF 日志文件

2、 如果数据比较重要，某个 Slave 开启 AOF 备份数据，策略设置为每秒同步一次

3、为了主从复制的速度和连接的稳定性， Master 和 Slave 最好在同一个局域网内

4、尽量避免在压力很大的主库上增加从库

5、主从复制不要用图状结构，用单向链表结构更为稳定，即： Master <- Slave1 <- Slave2 <-Slave3



## 缓存数据一致性

- 初级：先删除缓存数据，再更新数据，下一次查询时家长数据
  - 再更新时如果出现查询情况，会把已经旧数据再次加载到缓存中，此时会出现数据不一致
    - 1.性能要求不高，可以锁住读
    - 2.通过定时任务，更新缓存数据，能保证一定的数据有效性
- 队列模式：
  - 更新数据时，根据唯一标识路由到JVM队列中，缓存为空，讲读请求放入队列等待更新
  - 读请求长时间阻塞，超时
- 热点数据
  - 分布式锁，避免大量请求同时重新构建缓存
  - 数据永不过期

- 热点数据备份

  - 在代理中对key末尾加随机数值

  