#### Windows搭建redis cluster

参考：https://www.cnblogs.com/yangda/p/13227003.html

##### 1.下载windows版本的Redis

下载地址：https://github.com/microsoftarchive/redis/releases

![image-20210102090150959](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210102090150959.png)

选择zip压缩包下载，下载完毕根据自己的需求解压缩

##### 2.下载和安装RubyInstaller

下载地址：http://rubyinstaller.org/downloads/

![image-20210102090314385](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210102090314385.png)

下载并安装操作系统相应位数的exe安装文件（已在文件夹中提供），记住全选安装

![img](https://img2020.cnblogs.com/blog/449713/202007/449713-20200702203728140-84407305.png)

##### 3. 安装Redis的Ruby驱动redis-xxxx.gem

下载地址 https://rubygems.org/pages/download

![image-20210102090620327](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210102090620327.png)

下载完毕之后（压缩包在文件夹中已提供），解压缩到根目录执行如下命令：**ruby setup.rb**

![image-20210102090740872](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210102090740872.png)

之后，再通过命令安装redis，命令：**gem install redis**

##### 4. 安装集群脚本redis-trib

将文件夹中的**redis-trib.rb**文件放到redis根目录

##### 5. 配置6个redis实例，3主3从

将原先redis压缩包内的``redis.windows.conf``额外复制出如下6份

![image-20210102091403088](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210102091403088.png)

每份配置文件针对性的修改端口信息

>port 9000              <----
>cluster-enabled yes
>cluster-config-file nodes-9000.conf      <----
>cluster-node-timeout 15000
>appendonly yes

通过命令**redis-server.exe redis.windows - 9xxx.conf**启动6个redis实例

##### 6. 启动redis cluster集群

将文件**redis-trib.rb**复制到redis目录下，执行如下命令：

**ruby redis-trib.rb create --replicas 1 127.0.0.1:9000 127.0.0.1:9001 127.0.0.1:9002 127.0.0.1:9003 127.0.0.1:9004 127.0.0.1:9005**

--replicas 1 表示每个主数据库拥有从数据库个数为1。master节点不能少于3个，所以我们用了6个redis

![image-20210102091812525](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210102091812525.png)

可以看到帮我们配置好了3主3从的配置，并询问我们是否可以采用此配置，输入yes启动集群

##### 7. 连通redis cluster

进入redis安装目录，打开cmd，输入命令：**redis-cli.exe -h 127.0.0.1 -c -p 9000**

注意：-c 参数表示连接redis cluster，缺少此参数将会导致redis cluster重定向节点异常

![image-20210102092318864](C:\Users\Sholybell\AppData\Roaming\Typora\typora-user-images\image-20210102092318864.png)

如上图所示，通过9000端口的redis主库操作的时候，自动根据key重定向到另一个9002主库进行写入