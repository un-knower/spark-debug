package cn.xukai.spark.java.parquet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.GroupFactory;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * parquet 读写操作
 * Created by kaixu on 2018/2/6.
 */
public class ParquetDemo {

    /**
     * 写入Parquet文件
     * @param outPath 输出Parquet格式
     * @param inPath  输入普通文本文件
     * parquet schema 解释：
     *                每一个字段有三个属性：重复数、数据类型、字段名
     *                重复数有三种：
     *                              1. required（出现1次）
     *                              2. repeated (出现0次或多次)
     *                              3. optional (出现0次或1次)
     *                每一个字段的数据类型分为两种：
     *                              1. group 复杂类型
     *                              2. primitive 基本类型
     *                基本类型：
     *                           INT64、INT32、BOOLEAN, BINARY, FLOAT, DOUBLE, INT96, FIXED_LEN_BYTE_ARRAY
     *                repeated和required不光在次数上的区别，序列化后生成的数据类型也不同
     *                比如：        repeqted修饰 ttl2 打印出来为 WrappedArray([7,7_a])
                                    required修饰 ttl2 打印出来为 [7,7_a]　　
     * @throws IOException
     */
    static void parquetWriter(String outPath,String inPath) throws IOException {
        MessageType schema = MessageTypeParser.parseMessageType(
                 " message Pair {\n" +
                        " required binary city (UTF8);\n" +
                        " required binary ip (UTF8);\n" +
                        " repeated group time {\n"+
                        " required int32 ttl;\n"+
                        " required binary ttl2;\n"+
                        "}\n"+
                        "}");
        GroupFactory factory = new SimpleGroupFactory(schema);
        Path path = new Path(outPath);
        Configuration configuration = new Configuration();
        GroupWriteSupport writeSupport = new GroupWriteSupport();
        writeSupport.setSchema(schema,configuration);
        ParquetWriter<Group> writer = new ParquetWriter<Group>(path,configuration,writeSupport);
        //把本地文件读取进去，用来生成parquet格式文件
        BufferedReader br =new BufferedReader(new FileReader(new File(inPath)));
        String line="";
        Random r=new Random();
        while((line=br.readLine())!=null){
            String[] strs=line.split("\\s+");
            if(strs.length==2) {
                Group group = factory.newGroup()
                        .append("city",strs[0])
                        .append("ip",strs[1]);
                Group tmpG =group.addGroup("time");
                tmpG.append("ttl", r.nextInt(9)+1);
                tmpG.append("ttl2", r.nextInt(9)+"_a");
                writer.write(group);
            }
        }
        System.out.println("write end");
        writer.close();
    }

    /**
     * 读取parquet 格式文件
     * @param inPath        parquet文件路径
     * @throws Exception
     */
    static void parquetReaderV2(String inPath) throws Exception{
        GroupReadSupport readSupport =  new GroupReadSupport();
        ParquetReader.Builder<Group> reader = ParquetReader.builder(readSupport,new Path(inPath));
        ParquetReader<Group> build=reader.build();
        Group line=null;
        while((line=build.read())!=null){
            Group time= line.getGroup("time", 0);
            //通过下标和字段名称都可以获取
            /*System.out.println(line.getString(0, 0)+"\t"+
            line.getString(1, 0)+"\t"+
            time.getInteger(0, 0)+"\t"+
            time.getString(1, 0)+"\t");*/

            System.out.println(line.getString("city", 0)+"\t"+
                    line.getString("ip", 0)+"\t"+
                    time.getInteger("ttl", 0)+"\t"+
                    time.getString("ttl2", 0)+"\t");

            //System.out.println(line.toString());

        }
        System.out.println("读取结束");
    }
    public static void main(String[] args) throws Exception {
//        parquetWriter("parquet-1","parquet.txt");
        parquetReaderV2("parquet-1");
    }
}
