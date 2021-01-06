### Redis主从复制

以三台redis实例构成一主二从为例，以下为搭建一个redis主从复制集群的操作步骤

##### 1. 下载redis

下载地址： https://github.com/microsoftarchive/redis/releases

或使用本文件夹中提供的压缩包：Redis-x64-3.0.504.zip

将下载完毕的压缩包解压缩，可以根据redis监听的端口号分别建立redis-6379、redis-6380、redis-6381三个文件夹

##### 2. 配置不同实例的redis

打开每个实例文件集中的``redis.windows.conf``文件，修改端口配置

![image-20210101180606160](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101180606160.png)

这里将三个redis实例分别配置端口号6379、6380、6381

> 补充：redis主库存在密码
>
> 如下图所示，可以在``redis.windows.conf``文件中配置操作redis的密码
>
> ![redis密码](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101180759965.png)

> 若主库存在密码设置，还需要在从库的``redis.windows.conf``文件中额外配置主库密码（主库可以不用配置）
>
> ![image-20210101181131091](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101181131091.png)

##### 3. 将每个redis实例配置成系统服务

使用如下命令，为每个redis实例建立系统服务

**redis-server --service-install <u>redis.windows.conf</u> --loglevel verbose  --service-name <u>redis-master</u>**

注意以上命令中，第一个下划线部分为每个redis实例对应的配置文件，第二个下划线部分为每个redis实例的服务名称

为三个实例建立完服务之后，可以在window的服务里查找到如下服务项目

![image-20210101181808508](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101181808508.png)

将所有的服务启动，即可通过redis-cli访问每个redis实例

##### 4. 配置主从复制

通过redis-cli命令连接6380、6381两个实例，为它们配置主库（注意之前redis主库若有密码，从库的额外配置）

命令：**slaveof 主库IP 主库端口**

![image-20210101182654917](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101182654917.png)

可以通过命令：**info replication** 查看每个redis实例的主从状态

**主库：**

![image-20210101195459621](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101195459621.png)

**从库：**

![image-20210101195601446](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101195601446.png)

如此就可以在主库进行写操作，从库同步主库数据（**注：从库不具有写的功能**）

![image-20210101200216705](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101200216705.png)

### Sentinel集群

Sentinel集群用来当主库宕机之后，重新选举新的主库

##### 1. 在各个redis实例文件夹下创建sentinel.conf

文件内容如下（已在sentinel-xxxxx中提供）：注意主库有密码要配置密码信息

> #当前Sentinel服务运行的端口，注意不同的sentinel端口要不同
> port 26380
>
> #master
> #Sentinel去监视一个名为mymaster的主redis实例，这个主实例的IP地址为本机地址127.0.0.1，端口号为6379，
> #而将这个主实例判断为失效至少需要2个 Sentinel进程的同意，只要同意Sentinel的数量不达标，自动failover就不会执行，密码lansun
> sentinel monitor mymaster 127.0.0.1 6379 1
>
> sentinel auth-pass mymaster lansun
>
> #指定了Sentinel认为Redis实例已经失效所需的毫秒数。当 实例超过该时间没有返回PING，或者直接返回错误，那么Sentinel将这个实例标记为主观下线。
> #只有一个 Sentinel进程将实例标记为主观下线并不一定会引起实例的自动故障迁移：只有在足够数量的Sentinel都将一个实例标记为主观下线之后，实例才会被标记为客观下线，这时自动故障迁移才会执行
> sentinel down-after-milliseconds mymaster 5000
>
> #指定了在执行故障转移时，最多可以有多少个从Redis实例在同步新的主实例，在从Redis实例较多的情况下这个数字越小，同步的时间越长，完成故障转移所需的时间就越长
> sentinel config-epoch mymaster 12
>
> #如果在该时间（ms）内未能完成failover操作，则认为该failover失败
> sentinel leader-epoch mymaster 49

##### 2. 根据sentinel.conf启动各sentinel实例

命令：**redis-server.exe sentinel.conf --sentinel**

##### 3. 模拟主库宕机，令sentinel重新选举

在服务中关闭主库服务

![image-20210101211803327](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101211803327.png)

可以看到sentinel集群重新选举主节点，主节点被切换为6381节点

![image-20210101211921720](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101211921720.png)

在6381节点通过命令``info replication``查看主从信息，可以看到已经变为master节点

![image-20210101212032798](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101212032798.png)

此时，再次启动原先6371主节点服务，可以看到sentinel将该节点变为6381节点的从节点

![image-20210101212226828](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101212226828.png)

再通过命令``info replication``查看原6371节点信息，变为slave，master为6381节点

![image-20210101212328089](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210101212328089.png)

