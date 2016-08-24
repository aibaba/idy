package redisson;

public interface DistributeLockFactory {
	
	DistributeLock getDistributeLock(String name);
	
}
