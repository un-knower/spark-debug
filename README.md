# spark-debug
### 1. spark 任务 远程debug
- step1. 提交任务
```
spark-submit --class cn.xukai.spark.SimpleApp --master local[4] \
--driver-java-options "-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=9091" \
/application/spark-2.2.0-bin-hadoop2.7/examples/jars/spark.util-1.0-SNAPSHOT.jar
```
当执行完step1 会停留在Listening for transport dt_socket at address: 9091 

- step2. idea 在remote 中添加 ip port 执行debug后， step1 开始继续向下执行到断点位置。


### spark sql Dmeo
### 1. 遇到的问题
在执行 rdd -> dataset 时，发现提交job 到集群可以执行成功，但是在idea运行时报如下错误
```
17/07/13 18:06:48 WARN TaskSetManager: Lost task 0.0 in stage 4.0 (TID 4, 192.168.107.128, executor 0): java.lang.ClassCastException: cannot assign instance of scala.collection.immutable.List$SerializationProxy to field org.apache.spark.rdd.RDD.org$apache$spark$rdd$RDD$$dependencies_ of type scala.collection.Seq in instance of org.apache.spark.rdd.MapPartitionsRDD
	at java.io.ObjectStreamClass$FieldReflector.setObjFieldValues(ObjectStreamClass.java:2133)
	at java.io.ObjectStreamClass.setObjFieldValues(ObjectStreamClass.java:1305)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2024)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at org.apache.spark.serializer.JavaDeserializationStream.readObject(JavaSerializer.scala:75)
	at org.apache.spark.serializer.JavaSerializerInstance.deserialize(JavaSerializer.scala:114)
	at org.apache.spark.scheduler.ResultTask.runTask(ResultTask.scala:80)
	at org.apache.spark.scheduler.Task.run(Task.scala:108)
	at org.apache.spark.executor.Executor$TaskRunner.run(Executor.scala:335)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)

17/07/13 18:06:48 INFO TaskSetManager: Starting task 0.1 in stage 4.0 (TID 5, 192.168.107.128, executor 0, partition 0, PROCESS_LOCAL, 4872 bytes)
17/07/13 18:06:48 INFO TaskSetManager: Lost task 0.1 in stage 4.0 (TID 5) on 192.168.107.128, executor 0: java.lang.ClassCastException (cannot assign instance of scala.collection.immutable.List$SerializationProxy to field org.apache.spark.rdd.RDD.org$apache$spark$rdd$RDD$$dependencies_ of type scala.collection.Seq in instance of org.apache.spark.rdd.MapPartitionsRDD) [duplicate 1]
17/07/13 18:06:48 INFO TaskSetManager: Starting task 0.2 in stage 4.0 (TID 6, 192.168.107.128, executor 0, partition 0, PROCESS_LOCAL, 4872 bytes)
17/07/13 18:06:48 INFO TaskSetManager: Lost task 0.2 in stage 4.0 (TID 6) on 192.168.107.128, executor 0: java.lang.ClassCastException (cannot assign instance of scala.collection.immutable.List$SerializationProxy to field org.apache.spark.rdd.RDD.org$apache$spark$rdd$RDD$$dependencies_ of type scala.collection.Seq in instance of org.apache.spark.rdd.MapPartitionsRDD) [duplicate 2]
17/07/13 18:06:48 INFO TaskSetManager: Starting task 0.3 in stage 4.0 (TID 7, 192.168.107.128, executor 0, partition 0, PROCESS_LOCAL, 4872 bytes)
17/07/13 18:06:48 INFO TaskSetManager: Lost task 0.3 in stage 4.0 (TID 7) on 192.168.107.128, executor 0: java.lang.ClassCastException (cannot assign instance of scala.collection.immutable.List$SerializationProxy to field org.apache.spark.rdd.RDD.org$apache$spark$rdd$RDD$$dependencies_ of type scala.collection.Seq in instance of org.apache.spark.rdd.MapPartitionsRDD) [duplicate 3]
17/07/13 18:06:48 ERROR TaskSetManager: Task 0 in stage 4.0 failed 4 times; aborting job
17/07/13 18:06:48 INFO TaskSchedulerImpl: Removed TaskSet 4.0, whose tasks have all completed, from pool 
17/07/13 18:06:48 INFO TaskSchedulerImpl: Cancelling stage 4
17/07/13 18:06:48 INFO DAGScheduler: ResultStage 4 (show at App.scala:53) failed in 0.564 s due to Job aborted due to stage failure: Task 0 in stage 4.0 failed 4 times, most recent failure: Lost task 0.3 in stage 4.0 (TID 7, 192.168.107.128, executor 0): java.lang.ClassCastException: cannot assign instance of scala.collection.immutable.List$SerializationProxy to field org.apache.spark.rdd.RDD.org$apache$spark$rdd$RDD$$dependencies_ of type scala.collection.Seq in instance of org.apache.spark.rdd.MapPartitionsRDD
	at java.io.ObjectStreamClass$FieldReflector.setObjFieldValues(ObjectStreamClass.java:2133)
	at java.io.ObjectStreamClass.setObjFieldValues(ObjectStreamClass.java:1305)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2024)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at org.apache.spark.serializer.JavaDeserializationStream.readObject(JavaSerializer.scala:75)
	at org.apache.spark.serializer.JavaSerializerInstance.deserialize(JavaSerializer.scala:114)
	at org.apache.spark.scheduler.ResultTask.runTask(ResultTask.scala:80)
	at org.apache.spark.scheduler.Task.run(Task.scala:108)
	at org.apache.spark.executor.Executor$TaskRunner.run(Executor.scala:335)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)

Driver stacktrace:
17/07/13 18:06:48 INFO DAGScheduler: Job 4 failed: show at App.scala:53, took 0.636632 s
Exception in thread "main" org.apache.spark.SparkException: Job aborted due to stage failure: Task 0 in stage 4.0 failed 4 times, most recent failure: Lost task 0.3 in stage 4.0 (TID 7, 192.168.107.128, executor 0): java.lang.ClassCastException: cannot assign instance of scala.collection.immutable.List$SerializationProxy to field org.apache.spark.rdd.RDD.org$apache$spark$rdd$RDD$$dependencies_ of type scala.collection.Seq in instance of org.apache.spark.rdd.MapPartitionsRDD
	at java.io.ObjectStreamClass$FieldReflector.setObjFieldValues(ObjectStreamClass.java:2133)
	at java.io.ObjectStreamClass.setObjFieldValues(ObjectStreamClass.java:1305)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2024)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at org.apache.spark.serializer.JavaDeserializationStream.readObject(JavaSerializer.scala:75)
	at org.apache.spark.serializer.JavaSerializerInstance.deserialize(JavaSerializer.scala:114)
	at org.apache.spark.scheduler.ResultTask.runTask(ResultTask.scala:80)
	at org.apache.spark.scheduler.Task.run(Task.scala:108)
	at org.apache.spark.executor.Executor$TaskRunner.run(Executor.scala:335)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)

Driver stacktrace:
	at org.apache.spark.scheduler.DAGScheduler.org$apache$spark$scheduler$DAGScheduler$$failJobAndIndependentStages(DAGScheduler.scala:1499)
	at org.apache.spark.scheduler.DAGScheduler$$anonfun$abortStage$1.apply(DAGScheduler.scala:1487)
	at org.apache.spark.scheduler.DAGScheduler$$anonfun$abortStage$1.apply(DAGScheduler.scala:1486)
	at scala.collection.mutable.ResizableArray$class.foreach(ResizableArray.scala:59)
	at scala.collection.mutable.ArrayBuffer.foreach(ArrayBuffer.scala:48)
	at org.apache.spark.scheduler.DAGScheduler.abortStage(DAGScheduler.scala:1486)
	at org.apache.spark.scheduler.DAGScheduler$$anonfun$handleTaskSetFailed$1.apply(DAGScheduler.scala:814)
	at org.apache.spark.scheduler.DAGScheduler$$anonfun$handleTaskSetFailed$1.apply(DAGScheduler.scala:814)
	at scala.Option.foreach(Option.scala:257)
	at org.apache.spark.scheduler.DAGScheduler.handleTaskSetFailed(DAGScheduler.scala:814)
	at org.apache.spark.scheduler.DAGSchedulerEventProcessLoop.doOnReceive(DAGScheduler.scala:1714)
	at org.apache.spark.scheduler.DAGSchedulerEventProcessLoop.onReceive(DAGScheduler.scala:1669)
	at org.apache.spark.scheduler.DAGSchedulerEventProcessLoop.onReceive(DAGScheduler.scala:1658)
	at org.apache.spark.util.EventLoop$$anon$1.run(EventLoop.scala:48)
	at org.apache.spark.scheduler.DAGScheduler.runJob(DAGScheduler.scala:630)
	at org.apache.spark.SparkContext.runJob(SparkContext.scala:2022)
	at org.apache.spark.SparkContext.runJob(SparkContext.scala:2043)
	at org.apache.spark.SparkContext.runJob(SparkContext.scala:2062)
	at org.apache.spark.sql.execution.SparkPlan.executeTake(SparkPlan.scala:336)
	at org.apache.spark.sql.execution.CollectLimitExec.executeCollect(limit.scala:38)
	at org.apache.spark.sql.Dataset.org$apache$spark$sql$Dataset$$collectFromPlan(Dataset.scala:2853)
	at org.apache.spark.sql.Dataset$$anonfun$head$1.apply(Dataset.scala:2153)
	at org.apache.spark.sql.Dataset$$anonfun$head$1.apply(Dataset.scala:2153)
	at org.apache.spark.sql.Dataset$$anonfun$55.apply(Dataset.scala:2837)
	at org.apache.spark.sql.execution.SQLExecution$.withNewExecutionId(SQLExecution.scala:65)
	at org.apache.spark.sql.Dataset.withAction(Dataset.scala:2836)
	at org.apache.spark.sql.Dataset.head(Dataset.scala:2153)
	at org.apache.spark.sql.Dataset.take(Dataset.scala:2366)
	at org.apache.spark.sql.Dataset.showString(Dataset.scala:245)
	at org.apache.spark.sql.Dataset.show(Dataset.scala:644)
	at org.apache.spark.sql.Dataset.show(Dataset.scala:603)
	at org.apache.spark.sql.Dataset.show(Dataset.scala:612)
	at cn.xukai.spark.App$.main(App.scala:53)
	at cn.xukai.spark.App.main(App.scala)
Caused by: java.lang.ClassCastException: cannot assign instance of scala.collection.immutable.List$SerializationProxy to field org.apache.spark.rdd.RDD.org$apache$spark$rdd$RDD$$dependencies_ of type scala.collection.Seq in instance of org.apache.spark.rdd.MapPartitionsRDD
	at java.io.ObjectStreamClass$FieldReflector.setObjFieldValues(ObjectStreamClass.java:2133)
	at java.io.ObjectStreamClass.setObjFieldValues(ObjectStreamClass.java:1305)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2024)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at scala.collection.immutable.List$SerializationProxy.readObject(List.scala:479)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1058)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1909)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2018)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
	at org.apache.spark.serializer.JavaDeserializationStream.readObject(JavaSerializer.scala:75)
	at org.apache.spark.serializer.JavaSerializerInstance.deserialize(JavaSerializer.scala:114)
	at org.apache.spark.scheduler.ResultTask.runTask(ResultTask.scala:80)
	at org.apache.spark.scheduler.Task.run(Task.scala:108)
	at org.apache.spark.executor.Executor$TaskRunner.run(Executor.scala:335)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
17/07/13 18:06:48 INFO SparkContext: Invoking stop() from shutdown hook
```
解决办法：
需要将jar包 添加到集群中去。
ss.sparkContext.addJar("D:\\workspace\\sparkutil\\target\\spark.util-1.0-SNAPSHOT.jar")


## spark 某商品日交易额统计