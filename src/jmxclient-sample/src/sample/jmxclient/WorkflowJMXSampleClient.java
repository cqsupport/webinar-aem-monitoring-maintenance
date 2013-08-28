/*
* Copyright 2013 Adobe Systems Incorporated
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package sample.jmxclient;

import javax.management.DynamicMBean;
import java.util.Hashtable;

import javax.management.openmbean.TabularData;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;

/***
 * 
 * @author Sham Hassan Chikkegowda
 * 
 */
public class WorkflowJMXSampleClient {
	public static void main(String[] args) throws Exception {
		String userid = "controlRole";
		String password = "admin";
		String serverUrl = "service:jmx:rmi:///jndi/rmi://localhost:9100/jmxrmi";
		String OBJECT_NAME = "com.adobe.granite.workflow:type=Maintenance";
		String[] buffer = new String[] { userid, password };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("jmx.remote.credentials", (String[]) buffer);
		MBeanServerConnection server = (MBeanServerConnection) JMXConnectorFactory
				.connect(new JMXServiceURL(serverUrl), attributes)
				.getMBeanServerConnection();
		ObjectName name = new ObjectName(OBJECT_NAME);
		WorkflowMBean workflow = (WorkflowMBean) MBeanServerInvocationHandler
				.newProxyInstance(server, name, WorkflowMBean.class, false);

		/* Execute all the methods */
		String model = "";
		Integer numberOfDays = 1;
		Boolean dryRun = true;
		boolean restartInstance = false;

		logTabularData("purgeCompleted",
				workflow.purgeCompleted(model, numberOfDays, dryRun));
		logTabularData("purgeActive",
				workflow.purgeActive(model, numberOfDays, dryRun));
		// logCount("countStaleWorkflows",workflow.countStaleWorkflows( model));
		// logTabularData("restartStaleWorkflows",workflow.restartStaleWorkflows(
		// model, dryRun) );
		logTabularData("fetchModelList", workflow.fetchModelList());
		logCount("countRunningWorkflows", workflow.countRunningWorkflows(model));
		logCount("countCompletedWorkflows",
				workflow.countCompletedWorkflows(model));
		logTabularData("listRunningWorkflowsPerModel",
				workflow.listRunningWorkflowsPerModel());
		logTabularData("listCompletedWorkflowsPerModel",
				workflow.listCompletedWorkflowsPerModel());
		logTabularData("returnWorkflowQueueInfo",
				workflow.returnWorkflowQueueInfo());
		logTabularData("returnWorkflowJobTopicInfo",
				workflow.returnWorkflowJobTopicInfo());
		logCount("returnFailedWorkflowCount",
				workflow.returnFailedWorkflowCount(model));
		logTabularData("returnFailedWorkflowCountPerModel",
				workflow.returnFailedWorkflowCountPerModel());
		// logTabularData("terminateFailedInstances",workflow.terminateFailedInstances(
		// restartInstance, dryRun, model) );
		// logTabularData("retryFailedWorkItems",workflow.retryFailedWorkItems(
		// dryRun, model));
	}

	public static void logTabularData(String operation, TabularData data) {
		System.out.println("-----Operation " + operation + "--------------");
		try {
			System.out.println("Number of records " + data.size());
			int counter = 0;
			for (Object o : data.values()) {
				System.out.println("Record Number " + (++counter));
				CompositeData row = (CompositeData) o;
				CompositeType type = row.getCompositeType();

				for (Object k : type.keySet()) {

					String key = k.toString();
					System.out.println(key + " -- " + row.get(key));
				}
			}
		} catch (Exception e) {
			System.out.println("-----Error during operation " + operation
					+ "--------------" + e.getMessage());
		}
		System.out.println("-----Completed " + operation + "--------------");
		System.out.println("       ");

	}

	public static void logCount(String operation, int counter) {
		System.out.println("--------------" + operation + "--------------");
		try {
			System.out.println("count is " + counter);
		} catch (Exception e) {
			System.out.println("-----Error during operation " + operation
					+ "--------------" + e.getMessage());
		}
		System.out.println("-----Completed " + operation + "--------------");
		System.out.println("       ");
	}

	public static interface WorkflowMBean extends DynamicMBean {
		public TabularData purgeCompleted(String model, Integer numberOfDays,
				Boolean dryRun);

		public TabularData purgeActive(String model, Integer numberOfDays,
				Boolean dryRun);

		public int countStaleWorkflows(String model);

		public TabularData restartStaleWorkflows(String model, Boolean dryRun);

		public TabularData fetchModelList();

		public int countRunningWorkflows(String model);

		public int countCompletedWorkflows(String model);

		public TabularData listRunningWorkflowsPerModel();

		public TabularData listCompletedWorkflowsPerModel();

		public TabularData returnWorkflowQueueInfo();

		public TabularData returnWorkflowJobTopicInfo();

		public int returnFailedWorkflowCount(String model);

		public TabularData returnFailedWorkflowCountPerModel();

		public TabularData terminateFailedInstances(boolean restartInstance,
				boolean dryRun, String model);

		public TabularData retryFailedWorkItems(boolean dryRun, String model);
	}
}
