package com.meiyou.utils;

import java.util.List;


import com.meiyou.model.Coordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.GeoRadiusParam;

public class RedisUtil {
    
	 private static JedisPool jedisPool = null;  
     // Redis服务器IP  
     private static String ADDR = "127.0.0.1";  
     // Redis的端口号  
     private static int PORT = 6379;  
     // 访问密码  
     private static String AUTH = "123456";  

     /** 
      * 初始化Redis连接池 
      */  
     static {  
         try {  
             JedisPoolConfig config = new JedisPoolConfig();  
             // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true  
             config.setBlockWhenExhausted(true);  
             // 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)  
             config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");  
             // 是否启用pool的jmx管理功能, 默认true  
             config.setJmxEnabled(true);  
             // 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
             config.setMaxIdle(8);  
             // 最大连接数, 默认8个  
             config.setMaxTotal(200);  
             // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
             config.setMaxWaitMillis(1000 * 100);  
             // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
             config.setTestOnBorrow(true);  
             jedisPool = new JedisPool(config, ADDR, PORT, 3000, AUTH);  
         } catch (Exception e) {  
             e.printStackTrace();  
         }  
     }  
     /** 
      * 获取Jedis实例 
      *  
      * @return 
      */  
     public synchronized static Jedis getJedis() {  
         try {  
             if (jedisPool != null) {  
                 Jedis resource = jedisPool.getResource();  
                 return resource;  
             } else {  
                 return null;  
             }  
         } catch (Exception e) {  
             e.printStackTrace();  
             return null;  
         }  
     }  

     /** 
      * 释放jedis资源 
      *  
      * @param jedis 
      */  
     public static void close(final Jedis jedis) {  
         if (jedis != null) {  
             jedis.close();  
         }  
     }  


     public static Long addReo(Coordinate coordinate, String table) {
         Jedis jedis = null;  
         try {  
             jedis = jedisPool.getResource();
             //第一个参数可以理解为表名  
             return jedis.geoadd(table,coordinate.getLongitude(),coordinate.getLatitude(),coordinate.getKey());  
         } catch (Exception e) {  
             System.out.println(e.getMessage());  
         } finally {  
             if (null != jedis)  
                 jedis.close();  
         }  
         return null;  
     }



    /**
     * 查询附近人
     * key 经度  维度  距离
     * return GeoRadiusResponse*/
    public static List<GeoRadiusResponse> geoQuery(Coordinate coordinate,double radius) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //200F GeoUnit.KM表示km
            return jedis.georadius(Constants.GEO_USER_KEY,coordinate.getLongitude(),coordinate.getLatitude(),radius,GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            if (null != jedis)
                jedis.close();
        }
    }



    /**
     * 查询附近动态
     * key 经度  维度  范围
     * return GeoRadiusResponse*/
    public static List<GeoRadiusResponse> geoQueryActivity(Coordinate coordinate, double radius) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //200F GeoUnit.KM表示km
            return jedis.georadius(Constants.GEO_ACTIVITY,coordinate.getLongitude(),coordinate.getLatitude()
                    ,radius,GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            if (null != jedis)
                jedis.close();
        }
    }

    /**
     * 设置token
     * @param uid
     * @param token
     * @return
     */
      public static boolean setToken(String uid,String token){
          Jedis jedis = null;
          try {
              jedis = jedisPool.getResource();
              jedis.set(uid,token);
              return true;
          } catch (Exception e) {
              System.out.println(e.getMessage());
          } finally {
              if (null != jedis)
                  jedis.close();

          }

          return false;
      }

    /**
     * 验证token
     * @param uid
     * @param token
     * @return
     */
     public static boolean authToken(String uid,String token){
         Jedis jedis = null;
         try {
             jedis = jedisPool.getResource();
             String tokenRedis = jedis.get(uid);
             if(tokenRedis.equals(token)){
                 return true;
             }
         } catch (Exception e) {
             System.out.println(e.getMessage());
         } finally {
             if (null != jedis)
                 jedis.close();
         }
         return false;
     }
}
