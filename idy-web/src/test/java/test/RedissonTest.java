package test;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RMap;

public class RedissonTest {

	public static void main(String[] args) {
		Config config = new Config();
		config.useSingleServer().setAddress("10.141.4.77:6379").setPassword("yrdtest");
		
		Redisson redisson = Redisson.create(config);
		RMap<String, String> m = redisson.getMap("a");
		System.out.println(m.isEmpty());
		m.put("k1", null);
		m.put("k1", null);
		m.put("k3", null);
		System.out.println(m.isEmpty());
		System.out.println(m.size());
		m.remove("k1");
		System.out.println(m.isEmpty());
		System.out.println(m.size());
		m.put("k1", null);
		m.put("k2", null);
		m.delete();
		System.out.println(m.isEmpty());
		System.out.println(m.size());
		
		redisson.shutdown();
	}
	

    //RLock lock = redisson.getLock("haogrgr");
}
