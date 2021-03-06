# JAVA基础

问题：

- 基本类型的存储方式？
- Float和Double为什么会有精度问题？
- 为什么要设计包装类？自动拆箱会出现哪些问题？
- Java是值传递还是引用传递？

Java中的数据类型有两类：

- 值类型（内置数据类型/基本数据类型）
- 引用类型（除值类型外都是）

## 基本数据类型

Java 语言提供了 **8** 种基本类型

- 整数型
  - `byte` - 8 位。-128 ~ 127
  - `short` - 16 位。-32768~32767
  - `int` - 32 位。
  - `long` - 64 位，赋值时一般在数字后加上 `l` 或 `L`。
- 浮点型
  - `float` - 32 位，直接赋值时必须在数字后加上 `f` 或 `F`。
  - `double` - 64 位，赋值时一般在数字后加 `d` 或 `D` 。
- 字符型
  - `char` - 16 位，存储 Unicode 码，用单引号赋值。
- 布尔型
  - `boolean` -8位，只有 true 和 false 两个取值。
- Void
  - 没有返回值

### 问题：

#### 1.基本类型的存储方式？为什么byte范围只能是-128-127？

计算机中数据都是以二进制进行存储的。byte类型长度是8位，最大能存256个数字，最高位是符号位，0为正/1为负，二进制值范围是10000000-01111111。

#### 2.Float和Double为什么会有精度问题？

float的长度是32位，其中最高位是符号位，其次8位是阶码(e)，后面是尾数比如0.09f转成二进制就是 0 01111011 01110000101000111101100，从二进制转回后是0.0900000035762786865234375

第一段代表了符号(s) : 0 正数， 1 负数 ， 其实更准确的表达是 (-1) ^0

第二段是阶码(e)：01111011 　，对应的10进制是　123

第三段是尾数(M)

小数点右边能表达的值是 1/2, 1/4, 1/8, 1/16, 1/32, 1/64, 1/128 … 1/(2^n）

整数位会除2，小数位会乘以2，最后算出的值是接近原值的数值，除了上述小数点能表达值能精确存储，其他值是不精确的

double与float存储方式一样，只是下面不一样

符号位 ：1位
阶码 ： 11位
尾数： 52位

具体转换看demo：study.data_type.BasicData

#### 3.为什么要设计包装类？自动拆箱会出现哪些问题？

- 由于范型需要，基本类型不能用做范型，虽然可以用Object但是不精准，范型就没意义了
- 有些字段所有数值都有意义，需要有null做异常判断
- 包装类中可以实现特定的方法

#### 4.Java是值传递还是引用传递？

值传递：调用函数时将参数复制一份传入，当函数内的变量修改时，不会影响原值。

引用传递：会影响

基本类型是值传递，引用类型是引用传递。

#### 5.整型缓存

Byte/Short/Integer/Long都有缓存，缓存范围是-128-127.

## 引用类型

### String

- 字符串由final修饰的一个byte[]承载
- 计算后hash值会缓存，默认是0
- StringBuilder和StringBuffer区别
  - Buffer方法用synchronized修饰是线程安全的，效率较低
  - Builder是非线程安全的，效率较高

# 集合

问题：

- ArrayList和LinkedList哪个效率更高？
- HashMap的扩容机制？put()运行机制？
- Set的实现原理？

### List

- ArrayList
  - 初始化未传入初始容量时，不会初始化数组，传入容量时会初始化数组，但size未初始化还是0
    - 此时调用set方法会报下标越界错误，size是0
  - 如果未传入容量时，第一次add元素会初始化长度为10的数组
  - 容器满后，再进行add时会触发扩容，扩容时，会增加当前容量的一半
  - 当容量超过Integer.MAX_VALUE时会报OutOfMemoryError
- LinkedList
  - 双端链表

### Set

- HashSet
  - 使用HashMap实现，value是用一个Object对象
- TreeSet
  - 使用TreeMap实现，value是用一个Object对象
  - 元素按照红黑树排列
  - 必须传入comparator，或元素实现Comparator接口，用于比较
- LinkedHashSet
  - 继承于HashSet，LinkedHashMap实现
- ConcurrentSkipListSet
  - 基于ConcurrentSkipListMap实现。

### Map

- HashMap
  - 初始化时，不传入负载，和初始值，默认loadFactor=0.75，initialCapacity=16
  - 传入初始值或负载，initialCapacity为最接近的2的n次方
  - 扩容的阀值是initialCapacity * loadFactor，超过这个值就扩容一倍
  - put()执行过程
    - 容器未初始化，会先初始化
    - 通过Key的hash与容量-1 进行与运算去除高位后得出就是元素在hash表的位置
    - 如果当前位置没有元素，则直接替换
    - 有元素时
      - 遍历链表如果Key的hash/equals 相等则替换value
      - 当链表长度>=8时链表转红黑树
- LinkedHashMap
  - 继承HashMap，不同处是按照插入顺序维持了一个双端列表
- ConcurrentHashMap
  - 初始化与HashMap类似，不同：会初始化用于控制并发的sizeCtl ，初始化为容器的容量，-1 时 处于并发
  - put()执行过程
    - 容器未初始化，会先初始化
    - 对应位置为null，会用CAS替换
    - 有元素时，synchronized锁住头元素，遍历元素查找对应位置替换
    - 当元素数量大于8
      - 容器长度小于64时，调整表大小
      - 容器长度大于64时，将链表转换成红黑树
- ConcurrentSkipListMap
  - 基于跳跃表实现的Map
  - Key要实现Comparator，或传入Comparator
  - put()过程
    - head为空，直接赋值head
    - 找位置
    - 插入节点
    - 构建索引(跳跃表)。。。

# 多线程



#### 高并发下的内存管理？

- 原因：高并发时，大量的类似对象创建，导致垃圾回收器触发，内存占用过满，垃圾回收时间过长，程序停止时间长
- 对象池，重复利用对象，避免频繁垃圾回收



# 锁

## synchronized 

| 锁状态   | 存储内容                                                | 存储内容 |
| :------- | :------------------------------------------------------ | :------- |
| 无锁     | 对象的hashCode、对象分代年龄、是否是偏向锁（0）         | 01       |
| 偏向锁   | 偏向线程ID、偏向时间戳、对象分代年龄、是否是偏向锁（1） | 01       |
| 轻量级锁 | 指向栈中锁记录的指针                                    | 00       |
| 重量级锁 | 指向互斥量（重量级锁）的指针                            | 10       |

- 无锁
- 偏向锁
  - 偏向锁是指一段同步代码一直被一个线程所访问
- 轻量级锁
  - 是指当锁是偏向锁的时候，被另外的线程所访问，偏向锁就会升级为轻量级锁，其他线程会通过自旋的形式尝试获取锁，不会阻塞，从而提高性能。
- 重量级锁
  - 但是当自旋超过一定的次数，或者一个线程在持有锁，一个在自旋，又有第三个来访时，轻量级锁升级为重量级锁

### ReentrantLock

- 公平锁
  - 
- 非公平锁

## 并发包锁实现

### AQS

- 信号量
- 

### CAS ABA问题解决

- 什么是aba
  - 线程一/二先取值A
  - 线程二替换前，线程一先替换成B再替换成A
- 解决：AtomicStampedReference

## 分布式锁实现

### 1.数据库实现



### 2. Redis实现

- **获取锁：**
  - 调用 setnx 尝试获取锁，如果设置成功，表示获取到了锁
  - 设置失败，此时需要判断锁是否过期
    - 未过期，则表示获取失败；循环等待，并再次尝试获取锁
    - 已过期，getset再次设置锁，判断是否获取了锁（根据返回的值进行判断，后面给出具体的方案）
    - 若失败，则重新进入获取锁的逻辑
- 释放锁：
  - 一个原则就是确保每个业务方释放的是自己的锁

### 3. Zookeeper实现

- 创建锁的根节点
- 在锁的根节点创建临时节点
  - 节点创建的时候判断自己是不是最小的节点，是就获取到锁
  - 不是时为前一个节点创建一个监听器，监听前一个节点的变化
- 当前获取锁的线程释放锁时，要删除自己的节点
- 下一个节点获取到前一个节点的变化，获取到锁



# JVM

#### JVM 调优

##### 什么情况下需要调优？

- heap 内存（老年代）持续上涨达到设置的最大内存值；
- Full GC 次数频繁；
  - 对象创建过多
  - jmap内存dump文件
  - Visual VM 打开dump文件
- GC 停顿时间过长（超过1秒）；
  - 
- 应用出现OutOfMemory 等内存异常；
  - 堆 分析堆
  - 栈 分析栈
- 应用中有使用本地缓存且占用大量内存空间；
- 系统吞吐量与响应性能不高或下降。

