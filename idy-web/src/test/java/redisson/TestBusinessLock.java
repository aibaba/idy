package redisson;

public class TestBusinessLock {
	
	//模拟转让债权内部并发
	static	Long waitTime = 10L;
	static	Long leaseTime = 30L;

	public static void main(String[] args) throws Exception {
		try {
			testTransfer();
			testTransferAndRepayment();
			testTransferAndRepayment2();
			testTransferAndRepayment3();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BusinessLock.redissonClient.shutdown();
		}
	}
	
	static void testTransferAndRepayment() throws Exception{
		testTransferLock("转让申请1，加锁", 100000L, 100001L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferLock("还款申请1，加锁", 100000L, 100001L, 0, BusinessEnum.REPAYMENT);
		testTransferUnlock("转让申请1，解锁", 100000L, 100001L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferLock("还款申请1，加锁", 100000L, 100001L, 0, BusinessEnum.REPAYMENT);
		testTransferUnlock("还款申请1，解锁", 100002L, 100001L, 0, BusinessEnum.REPAYMENT);
	}
	
	static void testTransferAndRepayment2() throws Exception{
		testTransferLock("还款申请1，加锁", 100000L, 100001L, 0, BusinessEnum.REPAYMENT);
		testTransferLock("转让申请1，加锁", 100000L, 100001L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferUnlock("还款申请1，解锁", 100000L, 100001L, 0, BusinessEnum.REPAYMENT);
		testTransferLock("转让申请1，加锁", 100000L, 100001L, 0, BusinessEnum.TRANSFER_LOAN);
	}
	
	static void testTransferAndRepayment3() throws Exception{
		testTransferLock("还款申请1，加锁", 100000L, 100000L, 0, BusinessEnum.REPAYMENT);
		testTransferLock("转让申请1，加锁", 100000L, 100000L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferLock("转让申请2，加锁", 100000L, 100001L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferUnlock("还款申请1，解锁", 100000L, 100001L, 0, BusinessEnum.REPAYMENT);
		testTransferLock("转让申请1，加锁", 100000L, 100001L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferUnlock("转让申请1，解锁", 100000L, 100001L, 0, BusinessEnum.TRANSFER_LOAN);
	}
	
	
	static void testTransfer() throws InterruptedException{
		testTransferLock("转让申请1-1，加锁", 1000L, 1001L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferLock("转让申请2-1，加锁", 1000L, 1002L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferLock("转让申请3，加锁", 1000L, 1003L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferLock("转让申请4，加锁", 1000L, 1004L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferLock("转让申请2-2，加锁", 1000L, 1002L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferLock("转让申请2-1，加锁", 1000L, 1001L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferUnlock("转让申请1-2,释放", 1000L, 1001L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferUnlock("转让申请2-2,释放", 1000L, 1002L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferLock("转让申请1-1，加锁", 1000L, 1001L, 0, BusinessEnum.TRANSFER_LOAN);
		testTransferLock("转让申请2-1，加锁", 1000L, 1002L, 0, BusinessEnum.TRANSFER_LOAN);
		//testTransferLock("转让申请5，加锁", 1001L, 1004L, 0, BusinessEnum.TRANSFER_LOAN);
		//testTransferLock("转让申请6，加锁", 1002L, 1005L, 0, BusinessEnum.TRANSFER_LOAN);
	}
	
	static void testTransferLock(String code, Long applyId, Long loanId, int sleep, BusinessEnum enu) throws InterruptedException{
		System.out.println(code + "===========开始，模拟转让债权内部并发...,applyId=" + applyId + ",待转让的债权LoanId=" + loanId);
		boolean lock = BusinessLock.lock(applyId, waitTime, leaseTime, enu, loanId);
		if(lock) {
			System.out.println(code + "-----------获取锁成功applyId=" + applyId + ",待转让的债权LoanId=" + loanId);
			if(sleep > 0) {
				Thread.sleep(sleep);
			}
		}else {
			System.err.println(code + "***********获取锁失败applyId=" + applyId + ",待转让的债权LoanId=" + loanId);
		}
		
	}
	
	static void testTransferUnlock(String code, Long applyId, Long loanId, int sleep, BusinessEnum enu) throws InterruptedException{
		System.out.println(code + "===========释放锁，模拟转让债权内部并发...,applyId=" + applyId + ",待转让的债权LoanId=" + loanId);
		boolean lock = BusinessLock.unlock(applyId, waitTime, leaseTime, enu, loanId);
		if(lock) {
			System.out.println(code + "-----------释放锁成功applyId=" + applyId + ",待转让的债权LoanId=" + loanId);
			if(sleep > 0) {
				Thread.sleep(sleep);
			}
		}else {
			System.err.println(code + "***********释放锁失败applyId=" + applyId + ",待转让的债权LoanId=" + loanId);
		}
		
	}
}
