package redisson;

import org.redisson.Redisson;

public class DistributeLockFactoryManager  {
	private static RedissonDistributeLockFactory redissonDistributeLockFactory = null;

	public static RedissonDistributeLockFactory getDistributeLockFactory(Redisson redisson){
		if(redissonDistributeLockFactory == null) {
			synchronized (redisson) {
				if(redissonDistributeLockFactory == null) {
					redissonDistributeLockFactory = new RedissonDistributeLockFactory(redisson);
				}
			}
		}
		return redissonDistributeLockFactory;
	}

}
