package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IP {

	public static void main(String[] args) throws UnknownHostException {
		InetAddress ar = InetAddress.getLocalHost();
		System.err.println(ar.getHostAddress().toString());
		//10.106.0.189
	}
}
