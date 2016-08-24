package redisson;

import org.redisson.Redisson;
import org.redisson.core.RLock;

public class RedissonDistributeLockFactory implements DistributeLockFactory {
	private Redisson redisson;
    private static final String PREFIX = "redisson.lock.";
	public RedissonDistributeLockFactory(Redisson redisson) {
		this.redisson = redisson;
	}

	public DistributeLock getDistributeLock(String name) {
		if (redisson == null) {
			throw new RuntimeException("redissson is not set!");
		}
        String lockName = PREFIX + name;
        RLock rlock = redisson.getLock(lockName);
		return new RedissonDistributeLock(rlock);
	}

}
