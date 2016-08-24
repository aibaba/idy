package redisson;

import java.util.concurrent.TimeUnit;

import org.redisson.Config;
import org.redisson.Redisson;

public class BusinessLock {
	
	private static final String LOCK_NAME_PREFIXED = "BUSINESS_LOCK_";
	private static final String DATA_NAME_PREFIXED = "BUSINESS_LOCK_DATA_";
	private static final String BUSINESS_KEY = "BUSINESS_KEY";
	
    static Redisson redissonClient;
    
    static {
    	Config config = new Config();
		config.useSingleServer().setAddress("10.141.4.77:6379").setPassword("yrdtest");
		redissonClient = Redisson.create(config);
    }
    
	public static boolean lock(Long applyId, Long waitTime, Long leaseTime, BusinessEnum businessEnum, Long loanId) {
		if(applyId == null || waitTime == null || leaseTime == null || businessEnum == null) {
			throw new RuntimeException("业务锁失败，applyId或waitTime或leaseTime或businessEnum为空");
		}
		
		if(applyId.longValue() <= 0 || waitTime.longValue() <= 0 || leaseTime.longValue() <= 0) {
			throw new RuntimeException("业务锁失败，applyId或waitTime或leaseTime值不正确");
		}
		
		if(BusinessEnum.TRANSFER_LOAN.getCode().equals(businessEnum.getCode()) && (loanId == null || loanId.longValue() <= 0)) {
			throw new RuntimeException("业务锁失败，businessEnum[" + businessEnum + "]，loanId[" + loanId + "]， 转让债权时loanId为空");
		}
		
		boolean result = false;
		
		DistributeLockFactory factory = DistributeLockFactoryManager.getDistributeLockFactory(redissonClient);
		DistributeLock dLock = factory.getDistributeLock(LOCK_NAME_PREFIXED + applyId);
	
		boolean isDLock = false;
	    try{
	    	isDLock = dLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
	    } catch(Exception e) {
	    	isDLock = false;
	    	e.printStackTrace();
	    }
	    
	    if(!isDLock) {
	    	System.out.println("业务锁失败，applyId[" + applyId + "]被锁定，获取锁失败");
	    } else {
	    	try {
	    		org.redisson.core.RMap<String, String> rmap = redissonClient.getMap(DATA_NAME_PREFIXED + applyId);
	    		
		    	if(rmap.isEmpty()) {
		    		rmap.put(BUSINESS_KEY, businessEnum.getCode());
		    		if(BusinessEnum.TRANSFER_LOAN.getCode().equals(businessEnum.getCode())) {
		    			rmap.put(loanId.toString(), null);
		    		}
		    		rmap.expire(leaseTime, TimeUnit.SECONDS);
		    		result = true;
		    	} else {
		    		String code = rmap.get(BUSINESS_KEY);
		    		BusinessEnum business = BusinessEnum.getBusinessEnumByCode(code);
		    		if(business != null) {
		    			System.out.println("业务锁，applyId[" + applyId + "]当前正在进行[" + business.getcName() + "]操作");
			    		
			    		if(business.getCode().equals(businessEnum.getCode()) && BusinessEnum.TRANSFER_LOAN.getCode().equals(business.getCode())) {
			    			if(rmap.containsKey(loanId.toString())) {
			    				System.out.println("业务锁，applyId[" + applyId + "]当前正在进行[" + business.getcName() + "]操作，转让的loanId[" + loanId + "]正在进行，不能并发");
			    			} else {
			    				System.out.println("业务锁，applyId[" + applyId + "]当前正在进行[" + business.getcName() + "]操作，转让的loanId[" + loanId + "]没有进行");
			    				rmap.put(loanId.toString(), null);
			    				rmap.expire(leaseTime, TimeUnit.SECONDS);
			    				result = true;
			    			}
			    		} else {
			    			System.out.println("业务锁，applyId[" + applyId + "]当前正在进行[" + business.getcName() + "]操作, 不能并发进行[" + businessEnum.getcName() + "]操作");
			    		}
		    		} else {
		    			System.out.println("业务锁，applyId[" + applyId + "]正在进行的业务代码是[" + code + "], 业务类型不存在");
		    		}
		    	}
		    } finally {
	            dLock.unlock();
			}
	    }
	    
	    return result;
	}
	
	public static boolean unlock(Long applyId, Long waitTime, Long leaseTime, BusinessEnum businessEnum, Long loanId) {
		System.out.println("业务解锁开始，applyId[" + applyId + "]，waitTime[" + waitTime + "]，leaseTime[" + leaseTime + "]，businessEnum[" + businessEnum + "]，loanId[" + loanId + "]");
		if(applyId == null || waitTime == null || leaseTime == null || businessEnum == null) {
			throw new RuntimeException("业务解锁失败，applyId或waitTime或leaseTime或businessEnum为空");
		}
		
		if(applyId.longValue() <= 0 || waitTime.longValue() <= 0 || leaseTime.longValue() <= 0) {
			throw new RuntimeException("业务解锁失败，applyId或waitTime或leaseTime值不正确");
		}
		
		if(BusinessEnum.TRANSFER_LOAN.getCode().equals(businessEnum.getCode()) && (loanId == null || loanId.longValue() <= 0)) {
			throw new RuntimeException("业务解锁失败，businessEnum[" + businessEnum + "]，loanId[" + loanId + "]， 转让债权时loanId为空");
		}
		
		boolean result = false;
		
		DistributeLockFactory factory = DistributeLockFactoryManager.getDistributeLockFactory(redissonClient);
		DistributeLock dLock = factory.getDistributeLock(LOCK_NAME_PREFIXED + applyId);
	
		boolean isDLock = false;
	    try{
	    	isDLock = dLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
	    } catch(Exception e) {
	    	System.out.println("业务解锁，获取分布式锁异常");
	    	isDLock = false;
	    	e.printStackTrace();
	    }
	    
	    if(!isDLock) {
	    	System.out.println("业务解锁失败，applyId[" + applyId + "]被锁定，获取锁失败");
	    } else {
	    	try {
	    		org.redisson.core.RMap<String, String> rmap = redissonClient.getMap(DATA_NAME_PREFIXED + applyId);
			    if(rmap.isEmpty()) {
			    	System.out.println("业务解锁，applyId[" + applyId + "]当前没有进行[" + businessEnum.getcName() + "]操作，无需解锁");
			    	result = true;
			    } else {
			    	String code = rmap.get(BUSINESS_KEY);
		    		BusinessEnum business = BusinessEnum.getBusinessEnumByCode(code);
		    		if(business != null) {
		    			System.out.println("业务解锁，applyId[" + applyId + "]当前正在进行[" + business.getcName() + "]操作");
		    			if(business.getCode().equals(businessEnum.getCode())) {
		    				if(BusinessEnum.TRANSFER_LOAN.getCode().equals(business.getCode())) {
		    					if(rmap.containsKey(loanId.toString())) {
		    						rmap.remove(loanId.toString());
		    						System.out.println("业务解锁，applyId[" + applyId + "]当前正在进行[" + business.getcName() + "]操作，转让的loanId[" + loanId + "]存在，解锁成功");
		    					}
		    					
		    					if(rmap.size() <= 1) {
		    						rmap.delete();
		    						System.out.println("业务解锁，applyId[" + applyId + "]当前正在进行[" + business.getcName() + "]操作，解锁转让的loanId[" + loanId + "]后，map中没有锁定的loanId，删除map，解锁成功");
		    						result = true;
		    					}
		    				} else {
		    					rmap.delete();
		    					System.out.println("业务解锁，applyId[" + applyId + "]当前正在进行[" + business.getcName() + "]操作, 要求进行解锁的操作是[" + businessEnum.getcName() + "]，解锁成功");
		    					result = true;
		    				}
		    			} else {
		    				System.out.println("业务解锁，applyId[" + applyId + "]当前正在进行[" + business.getcName() + "]操作, 要求进行解锁的操作是[" + businessEnum.getcName() + "]，不能解锁");
		    			}
		    		} else {
		    			System.out.println("业务解锁，applyId[" + applyId + "]正在进行的业务代码是[" + code + "], 业务类型不存在");
		    		}
			    }
		    } finally {
	            dLock.unlock();
			}
	    }
	    
	    return result;
	}

}
