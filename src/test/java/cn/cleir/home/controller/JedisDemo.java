package cn.cleir.home.controller;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisDemo {

    @Test
    public void demo1(){
        //设置地址端口号
        Jedis jedis = new Jedis("192.168.31.79", 6379);
        //保存数据
        jedis.set("name", "cleir_leong");
        //获取数据
        String value = jedis.get("name");
        System.out.println(value);
        jedis.close();
    }

    @Test
    /**
     * 连接池
     */
    public void demo2(){
        //获取来连接池的配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大链接数
        config.setMaxTotal(30);
        //设置最大空闲连接数
        config.setMaxIdle(10);

        //获得连接池
        JedisPool jedisPool = new JedisPool(config, "192.168.31.79", 6379);

        //获得核心对象
        Jedis jedis = null;
        try{
            //通过连接池获得连接
            jedis = jedisPool.getResource();
            //设置数据
            jedis.set("name", "leong");
            //获取数据
            String value = jedis.get("name");
            System.out.println(value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (jedis != null){
                jedis.close();
            }
            if (jedisPool != null){
                jedisPool.close();
            }
        }
    }

    @Test
    public void inco(){
        double cicun = 6.21;
        double bili = (double) 9 /(double) 18.7;
        int xiangsu = 2248 * 1080;
        double y = cicun / Math.sqrt(bili + 1);
        double x = bili * y;
        double xs = xiangsu / (x * y);
        System.out.println("面积:" + x * y);
        System.out.println("像素密度:" + xs);
    }



}
