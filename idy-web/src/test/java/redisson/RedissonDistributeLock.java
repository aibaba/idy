package redisson;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

import org.redisson.core.RLock;

public class RedissonDistributeLock implements DistributeLock {
	private RLock rlock;
	
	public RedissonDistributeLock(RLock rlock) {
		this.rlock = rlock;
	}
	@Override
	public void lockInterruptibly(long leaseTime, TimeUnit unit)
			throws InterruptedException {
		rlock.lockInterruptibly(leaseTime, unit);

	}

	@Override
	public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit)
			throws InterruptedException {
		return rlock.tryLock(waitTime, leaseTime, unit);
	}

	@Override
	public void lock(long leaseTime, TimeUnit unit) {
		rlock.lock(leaseTime, unit);

	}

	@Override
	public void forceUnlock() {
		rlock.forceUnlock();

	}

	@Override
	public boolean isLocked() {
		return rlock.isLocked();
	}

	@Override
	public boolean isHeldByCurrentThread() {
		return rlock.isHeldByCurrentThread();
	}

	@Override
	public int getHoldCount() {
		return rlock.getHoldCount();
	}
	
	@Override
	public void lock() {
		rlock.lock();
	}
	@Override
	public void lockInterruptibly() throws InterruptedException {
		rlock.lockInterruptibly();
	}
	@Override
	public boolean tryLock() {
		return rlock.tryLock();
	}
	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		return rlock.tryLock(time, unit);
	}
	@Override
	public void unlock() {
		rlock.unlock();
	}
	@Override
	public Condition newCondition() {
		return rlock.newCondition();
	}

}
