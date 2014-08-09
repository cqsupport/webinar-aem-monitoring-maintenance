### jmxclient-sample
This is a collection of simple java command line applications that perform various monitoring and maintenance tasks against Adobe AEM (CQ) instances.

The majority of code provided in this section was implemented by Sham Sundar Hassan Chikkegowda (https://www.linkedin.com/pub/sham-sundar-hassan-chikkegowda)

### Instructions
To set up JMX remoting on your AEM instances, you must modify the ``crx-quickstart/bin/start`` script and add the following to the ``CQ_JVM_OPTS`` variable:
``-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9000 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false``

For an example ``crx-quickstart/bin/start`` script and a ``jmx.passwords`` file, see the ``scripts`` directory.

*Basic instructions to get the examples working:*

1. Copy the ``start`` and ``jmx.passwords`` files from the ``scripts`` directory to the ``crx-quickstart/bin`` directory of your local AEM (CQ) instance
2. Restart the instance.
3. Import the jmxclient-sample project in Eclipse IDE as an existing project.
3. Run the samples. They are hard-coded to point to 127.0.0.1:9000 ("localhost" port 9000).

### Description
JMX Client apps:
* ReplicationJMXSampleClient - Replication agent status info.
* DataStoreGCJMXSampleClient - Run DataStore GC.
* TarOptimizationMonitorSampleClient - Run TAR PM Optimization.
* WorkflowJMXSampleClient - Run all Workflow functions.

### Reference
* JMX Documentation - http://docs.oracle.com/javase/1.5.0/docs/guide/management/agent.html
