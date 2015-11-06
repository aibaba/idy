package com.test.redis;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.test.exception.resolver.StackTrace;

/**
 * 
 * @author gaopengbd
 * @param <T>
 *
 */
public class JedisPool implements FactoryBean<DgShardedJedis>,DisposableBean{

	@Getter
	@Setter
	private String masters ;//ip:port,ip:port..
	
	@Getter
	@Setter
	private String slaves;
	
	private List<JedisShardInfo> masterList = new ArrayList<JedisShardInfo>();
	
	private List<JedisShardInfo> slaveList = new ArrayList<JedisShardInfo>();
	
	@Getter
	@Setter
	private JedisPoolConfig config;
	
	private volatile static DgShardedJedis instance; 
	
	@Getter
	private static ShardedJedisPool writePool;
	
	@Getter
	private static ShardedJedisPool readPool;
	
	private static Logger logger = LoggerFactory.getLogger(JedisPool.class);
	
	public void init(){
		try {
			this.initMasters();
			this.initSlaves();
		} catch (Exception e) {
			throw new RuntimeException("redis pool init fail!");
		}
	}
	
	private void initMasters(){
		if(masters == null || masters.equals("")){
			logger.warn("masters config is null!");
		}else {
			String[] hostArr = masters.trim().split(",");
			for(String host : hostArr){
				masterList.add(create(host.trim()));
			}
			writePool = new ShardedJedisPool(config, masterList);
		}
	}
	
	private JedisShardInfo create(String host){
		String ip = host.split(":")[0];
		int port = Integer.parseInt(host.split(":")[1]);
		return new JedisShardInfo(ip,port);
	}
	
	private void initSlaves(){
		if(slaves == null || slaves.equals("")){
			logger.info("slaves config is null!");
		}else {
			String[] hostArr = slaves.trim().split(",");
			for(String host : hostArr){
				slaveList.add(create(host.trim()));
			}
			readPool = new ShardedJedisPool(config, slaveList);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static ShardedJedis getJedis(){
		if(writePool == null) return null;
		return writePool.getResource();
	}
	
	public static ShardedJedis getReadJedis(){
		if(readPool == null) return null;
		return readPool.getResource();
	}
	
	public static void returnResource(ShardedJedis jedis){
		if(writePool == null || jedis == null) return ;
		try {
			writePool.returnResource(jedis);
		}catch (Exception e){
			logger.error(StackTrace.getExceptionTrace(e));
		}
	}
	
	public static void returnResource(ShardedJedis jedis, boolean broken){
		if(broken) {
			returnBrokenResource(jedis);
		}else {
			returnResource(jedis);
		}
	}
	
	public static void returnReadResource(ShardedJedis jedis){
		if(readPool == null || jedis == null) return ;
		try {
			readPool.returnResource(jedis);
		}catch (Exception e){
			logger.error(StackTrace.getExceptionTrace(e));
		}
	}
	
	public static void returnBrokenResource(ShardedJedis jedis){
		if(writePool == null || jedis == null) return ;
		try {
			writePool.returnBrokenResource(jedis);
		}catch (Exception e){
			logger.error(StackTrace.getExceptionTrace(e));
		}
	}
	
	public static void returnReadBrokenResource(ShardedJedis jedis){
		if(readPool == null || jedis == null) return ;
		try {
			readPool.returnBrokenResource(jedis);
		}catch (Exception e){
			logger.error(StackTrace.getExceptionTrace(e));
		}
	}
	
	protected static void checkPool(ShardedJedisPool pool){
		if(writePool == null){
			throw new RuntimeException("writePool is null!");
		}
	}
	
	public void destroy(){
		if(writePool != null){
			writePool.destroy();
		}
		if(readPool != null){
			readPool.destroy();
		}
	}

	@Override
	public DgShardedJedis getObject() throws Exception {
		if(instance == null) {
			instance = new DgShardedJedis();
		}
		return instance;
	}

	@Override
	public Class<?> getObjectType() {
		return DgShardedJedis.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
