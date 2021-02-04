## 毕业作业-总结

#### JVM

字节码：JVM通过将class文件编译成字节码文件，从而实现一次编译在各种安装了JVM的操作系统都能运行

类加载器：启动类加载器，扩展类加载器，应用类加载器，这三个类加载器在逻辑上属于父子关系（非继承关系）。所有自定义的类加载器都默认继承应用类加载器，采用双亲委托机制进行类加载，加载过程：装载、链接（校验、准备、解析）、初始化

内存结构：JVM内部划分为堆（共享数据区）、栈（每个线程单独分配一个栈）、元数据区

常见命令：jps/jinfo、jstat、jmap、jstack、jcmd、jrunscript/jjs

图形化管理工具：jconsole、jvisualvm、VisualGC、JMC

GC：

​	垃圾回收算法：标记清除算法、标记复制算法、标记清除整理算法

​	GC种类：串行GC、并行GC、CMS、G1、ZGC、Shenandoah GC

#### NIO

IO交互方式：同步阻塞、异步非阻塞

IO种类：阻塞式IO、非阻塞IO、IO复用、信号驱动IO、异步IO

epoll取代select、poll的原因：①不需要将socket文件句柄频繁在用户态和内核态切换，②不用每次都遍历所有的socket句柄才能确认触发事件的socket，③超越了文件描述符1024上限

reactor模型：使用一个线程组轮询监听socket连接事件，然后再使用一组线程组轮询监听所有已经建立连接的socket的可读可写事件，再将socket传递给业务线程池处理，Netty已经实现了这个模型

#### 并发编程

进程：操作系统的基本运行单位，一个进程包含多个线程，这些线程共享进程的资源

线程状态：ready、running、timed_wait、wait、block

线程安全三要素：可见性、有序性、原子性

线程安全JMM：happens-before原则

线程池：

​	内建线程池：newSingleThreadExecutor、newFixedThreadPool、newCachedThreadPool、newScheduledThreadPool

​	线程池任务拒绝策略：AbortPolicy、DiscardPolicy、DiscardOldestPolicy、CallerRunsPolicy

​	线程池线程扩展默认规则：根据任务数量创建核心线程，之后的任务入队到等待队列，等待队列满了再次创建到最大线程，之后开始执行任务拒绝策略

​	线程安全类：ConcurrentHashMap、CopyOnWriteArrayList、HashTable、ThreadLocal、AtomicXxxx....

#### Spring 和 ORM 框架

###### Spring

DI:控制反转，通过注解配置或者XML配置方式，配置要在Spring项目中使用的对象，由Spring启动的时候加载并创建注入到目标对象中

Bean生命周期：实例化--->属性赋值--->检查Aware系列接口--->BeanPostProcessor前置处理--->InitializingBean接口--->自定义init-method方法--->BeanPostProcessor后置处理--->注册Destruction系列接口--->使用--->销毁执行DisposableBean接口--->执行destroy-method方法

AOP：通过JDK动态代理或者GCLIB增强的方式，为目标类的方法执行工程嵌入自定义逻辑，执行顺序为：环绕通知、前置通知、后置通知或者异常通知、执行返回通知

自动配置：在指定目录META-INF/spring.factories配置相关自动配置类，SpringBoot启动过程会加在该类判断是否应该初始化，相关的条件化注解有：@ConditionalOnBean、@ConditionalOnClass、@ConditionalOnMissingBean、@ConditionalOnProperty、@ConditionalOnResource、@ConditionalOnSingleCandidate、@ConditionalOnWebApplication...

###### ORM

常见的ORM框架：Mybatis、Hibernate、JPA

Mybatis:

​	优点：原生SQL（XML语法），直观，对DBA友好

​	缺点：繁琐，可以用Mybatis-generator（过时，可考虑使用通用Mapper）、Mybatis-Plus之类的插件

Hibernate:

​	优点：简单场景不用写SQL（HQL，Cretiria、SQL）

​	缺点：HQL生成的SQL难以阅读，且要要运行之后才能看到，对DBA不友好

#### MySQL

范式：第一范式、第二范式、第三范式、BC范式、第四范式、第五范式

表引擎：MyISAM、InnoDB、Archive、Monery

索引结构：B+树		索引类型：聚簇索引、唯一索引、非聚簇索引

可靠模型ACID：原子性、一致性、隔离性、持久性

事务隔离级别：读未提交、读已提交、可重复读、串行化

MySQL高可用方案：主从复制、LVS+KeepAlived、MHA、MySQL Cluster、Orchestrator

#### 分库分表

系统扩展方向：①clone整个系统复制，采用集群提升处理性能；②解耦不同的功能复制，业务拆分，每个业务系统采用集群提供服务；③针对不同的业务拆分数据库，进行数据分片，通过分库分表提

垂直拆分：

​	优点：单表（单库）变小，便于管理和维护；对性能和容量有提升作用；改造后，系统和数据复杂度降低；可以作为微服务改造的基础。

​	缺点：库变多，管理变复杂；对业务系统有较强的侵入性；改造过程复杂，容易出故障；拆分到一定程度就无法继续拆分，业务无法再拆分更详细的子业务；

水平拆分：

​	优点：解决容量问题；比垂直拆分对系统影响小；部分提升性能和稳定性；

​	缺点：集群规模大，管理复杂；复杂SQL支持问题（业务侵入性、性能）；数据迁移问题；一致性问题

数据迁移方式：全量、全量+增量、通过binlog的全量+增量

分布式事务：TCC、AT

#### RPC和微服务

###### RPC

远程过程调用（Remote Procedure Call），提供一个服务注册和发现中心，然后服务调用方本地暴露一个接口，通过**动态代理**实现该接口的代理类，之后由代理类负责将请求转换成只对应的PRC协议，执行SOCKET通信，并获得响应

序列化协议：①语言原生的序列化，RMI，Remoting；②二进制平台无关，Hessian，avro，kyro，fst等；③文本，JSON、XML等

传输方式：TCP/SSL、HTTP/HTTPS

常见的PRC技术：Java RMI、.NET Remoting、Hessian、Thrift、gRPC

###### 微服务

微服务框架核心能力：①面向接口代理的高性能RPC调用；②智能负载均衡；③服务自动注册与发现；④高度可扩展能力；⑤运行期流量调度；⑥可视化的服务治理与运维

微服务解决方案：Dubbo、Spring Cloud、Spring Cloud Alibaba

#### 分布式缓存

常见的分布式缓存中间件：Redis、Memcached、Hazelcast/Ignite

Redis:

​	数据类型：string、hash、set、list、zset、bitmaps、hyperloglogs、geo

​	备份方式：AOF、RDB

​	集群方式：主从复制（slaveof）、Redis Sentinel、Redis Cluster

​	使用场景：缓存热数据、会话、token等；业务数据去重；业务数据排名；全局流量控制；分布式锁；秒杀库存计算

缓存穿透：大量并发查询不存在的KEY，导致都直接将压力透传到数据库

​	解决方案：①缓存空值的KEY；②完全以缓存为准，使用延迟异步加载；③Bloom过滤或RoaringBitmap判断KEY是否存在

缓存击穿：某个KEY失效的时候，正好有大量并发请求访问这个KEY

​	解决方案：①KEY的更新操作添加全局互斥锁；②完全以缓存为准，使用延迟异步加载 的策略2，这样就不会触发更新

缓存雪崩：当某一时刻发生大规模的缓存失效的情况，会有大量的请求进来直接打到数据库，
导致数 据库压力过大升值宕机

​	解决方案：①更新策略在时间上做到比较均匀；②使用的热数据尽量分散到不同的机器上；③实现熔断限流机制，对系统进行负载能力控制；④多台机器做主从复制或者多副本，实现高可用

#### 分布式消息队列

消息队列解决了什么：

​	异步通信：异步通信，减少线程等待，特别是处理批量等大事务、耗时操作

​	系统解耦：系统不直接调用，降低依赖，特别是不在线也能保持通信最终完成

​	削峰平谷：压力大的时候，缓冲部分请求消息，类似于背压处理

​	可靠通信：提供多种消息模式、服务质量、顺序保障等

MQ消息处理模式：PTP（点对点队列）、发布订阅模式（主题）

消息处理保障机制：

​	At most once，至多一次，消息可能丢失但是不会重复发送

​	At least once，至少一次，消息不会丢失，但是可能会重复

​	Exactly once，精确一次，每条消息肯定会被传输一次且仅一次

消息队列中间件：ActiveMQ、RabbitMQ、Kafka、RocketMQ、Pulsar

