# Spring

### IOC



### AOP

- 动态代理
  - JDK实现
  - CGLIB
- 多个AOP同时切到一个方法时，会根据order顺序执行





## Spring Boot 

### 初始化流程

- SpringApplication 初始化
  - 推断应用类型：Reactive/servlet/none
  - SpringFactoriesLoader 加载classPath下 META-INF/spring.factories
    - `文件中所有可用的 `ApplicationContextInitializer
    - ApplicationListener
  - 推断并设置main方法定义类
- 调用run()方法
  - 通知所有Listener开始启动
  - 根据参数/配置创建环境实体，并通知环境实体已准备好
  - 创建ApplicationContext并初始化 环境实体加载相关配置，通知上下文已准备好
  - refresh ApplicationContext
  - 通知已完成启动

### Starter 创建

- maven 添加spring-boot-autoconfigure 自动化配置依赖

- 创建配置类，设置好条件及需要初始化的Bean

- META-INF 目录下创建 spring.factories

  - ```
    org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.objcoding.starters.helloworld.HelloworldAutoConfiguration
    ```

