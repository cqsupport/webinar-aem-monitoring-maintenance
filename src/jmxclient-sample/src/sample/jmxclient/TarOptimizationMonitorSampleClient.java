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

import java.util.Date;
import java.util.Hashtable;

import javax.management.openmbean.TabularData;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;

import sample.jmxclient.WorkflowJMXSampleClient.WorkflowMBean;

public class TarOptimizationMonitorSampleClient {
	public static void main(String[] args) throws Exception{
		String userid = "controlRole";
		String password = "admin";
		String serverUrl = "service:jmx:rmi:///jndi/rmi://127.0.0.1:9000/jmxrmi";
		String OBJECT_NAME = "com.adobe.granite:type=Repository";
		String[] buffer = new String[] { userid, password };
		Hashtable<String, String[]> attributes = new Hashtable<String, String[]>();
		attributes.put("jmx.remote.credentials", (String[]) buffer);		
		MBeanServerConnection server = (MBeanServerConnection) JMXConnectorFactory.connect(new JMXServiceURL(serverUrl), attributes).getMBeanServerConnection();
		ObjectName name = new ObjectName(OBJECT_NAME);
		ManagedRepositoryMBean tarPm = (ManagedRepositoryMBean) MBeanServerInvocationHandler.newProxyInstance(server, name, ManagedRepositoryMBean.class,false);
		logResult(tarPm.getTarOptimizationRunningSince());
		logResult("The remaining optimization work in KiloBytes",tarPm.getTarOptimizationWork());
		logResult("TarPM optimization speed (kb per second)",tarPm.getTarOptimizationRate());
	}
	
	public static void logResult(String operation, long counter) {
		System.out.println("--------------"+operation+"--------------");
		try {
			System.out.println("Took is "+counter);
		} catch (Exception e) {
			System.out.println("-----Error during operation "+operation+"--------------"+e.getMessage());		
		}
		//System.out.println("-----Completed "+operation+"--------------");		
		System.out.println("       ");		
	}

	public static void logResult(Long tarPMOptimizationTimems) {
		Date tarPMOptimizationTime = new Date(tarPMOptimizationTimems);
		Date currDate = new Date();
		long diff = currDate.getTime() - tarPMOptimizationTime.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		System.out.println("Current date & time is "+ currDate.toString());
		System.out.println("Tar PM Started at "+ tarPMOptimizationTime.toString());

		System.out.print("Tar PM Optimization running since ");
		System.out.print(diffDays + " days, ");
		System.out.print(diffHours + " hours, ");
		System.out.print(diffMinutes + " minutes, ");
		System.out.print(diffSeconds + " seconds.");		
		System.out.println("       ");		
	}	
	
	public static interface ManagedRepositoryMBean extends DynamicMBean {
		public long getTarOptimizationRunningSince();
		public long getTarOptimizationWork();
		public long getTarOptimizationRate();
    }
}
