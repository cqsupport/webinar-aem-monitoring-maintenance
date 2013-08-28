package sample.jmxclient;

import java.util.Date;
import java.util.Hashtable;

import javax.management.DynamicMBean;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class DataStoreGCJMXSampleClient {
	public static void main(String[] args) throws Exception {
		String userid = "controlRole";
		String password = "admin";
		String serverUrl = "service:jmx:rmi:///jndi/rmi://localhost:9100/jmxrmi";
		String OBJECT_NAME = "com.adobe.granite:type=Repository";
		String[] buffer = new String[] { userid, password };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("jmx.remote.credentials", (String[]) buffer);
		MBeanServerConnection server = (MBeanServerConnection) JMXConnectorFactory
				.connect(new JMXServiceURL(serverUrl), attributes)
				.getMBeanServerConnection();
		ObjectName name = new ObjectName(OBJECT_NAME);
		RepositoryMBean repo = (RepositoryMBean) MBeanServerInvocationHandler
				.newProxyInstance(server, name, RepositoryMBean.class, false);
		repo.runDataStoreGarbageCollection(true);

	}

	public static void logValue(String name, String value) {
		System.out.println(name + ": " + value);
	}

	public static interface RepositoryMBean extends DynamicMBean {
		public void runDataStoreGarbageCollection(java.lang.Boolean delete);

		public void runDataStoreClassicGarbageCollection(
				java.lang.Boolean delete);
	}
}
