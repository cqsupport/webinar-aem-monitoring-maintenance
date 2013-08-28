package sample.jmxclient;

import java.util.Date;
import java.util.Hashtable;

import javax.management.AttributeValueExp;
import javax.management.DynamicMBean;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.Query;
import javax.management.QueryExp;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/***
 * 
 * @author Andrew Khoury
 * 
 */
public class ReplicationJMXSampleClient {
	public static void main(String[] args) throws Exception {
		String userid = "controlRole";
		String password = "admin";
		String serverUrl = "service:jmx:rmi:///jndi/rmi://127.0.0.1:9100/jmxrmi";
		if (!(args.length > 0 && args[0] != null && args[0].length() > 0)) {
			System.out
					.println("Error: You must specify an agent id as a parameter.");
			return;
		}
		String OBJECT_NAME = "com.adobe.granite.replication:id=\"" + args[0]
				+ "\",type=agent";
		String[] buffer = new String[] { userid, password };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("jmx.remote.credentials", (String[]) buffer);
		MBeanServerConnection server = (MBeanServerConnection) JMXConnectorFactory
				.connect(new JMXServiceURL(serverUrl), attributes)
				.getMBeanServerConnection();
		ObjectName name = new ObjectName(OBJECT_NAME);
		ReplicationMBean replication = (ReplicationMBean) MBeanServerInvocationHandler
				.newProxyInstance(server, name, ReplicationMBean.class, false);

		logValue("Id", replication.getId());
		logValue("Valid", String.valueOf(replication.getValid()));
		logValue("Enabled", String.valueOf(replication.getEnabled()));
		logValue("QueueBlocked", String.valueOf(replication.getQueueBlocked()));
		logValue("QueuePaused", String.valueOf(replication.getQueuePaused()));
		logValue("QueueNumEntries",
				String.valueOf(replication.getQueueNumEntries()));
		logValue("QueueStatusTime",
				String.valueOf(replication.getQueueStatusTime()));
		logValue("QueueNextRetryTime",
				String.valueOf(replication.getQueueNextRetryTime()));
		logValue("QueueProcessingSince",
				String.valueOf(replication.getQueueProcessingSince()));
		logValue("QueueLastProcessTime",
				String.valueOf(replication.getQueueLastProcessTime()));
	}

	public static void logValue(String name, String value) {
		System.out.println(name + ": " + value);
	}

	public static interface ReplicationMBean extends DynamicMBean {
		public String getId();

		public boolean getValid();

		public boolean getEnabled();

		public boolean getQueueBlocked();

		public boolean getQueuePaused();

		public long getQueueNumEntries();

		public Date getQueueStatusTime();

		public Date getQueueNextRetryTime();

		public Date getQueueProcessingSince();

		public Date getQueueLastProcessTime();

		public void queueForceRetry();

		public void queueClear();
	}
}
