package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {

	public static void main(String[] args) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		System.out.println(list.size());
		Map<String, String> m1 = new HashMap<String, String>();
		m1.put(null, null);
		list.add(m1);
		Map<String, String> m2 = new HashMap<String, String>();
		m2.put("key2", "v2");
		list.add(m2);
		System.out.println(list.size());
		System.out.println(list.get(0));
		System.out.println(list);
		
		try{
			//throw new RuntimeException("gg");
			System.out.println("ok");
		}catch (Exception e){
			System.err.println("catch");
		}
	}
}
