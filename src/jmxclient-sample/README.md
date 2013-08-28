### jmxclient-sample
This is a collection of simple java command line applications that perform various monitoring and maintenance tasks against Adobe AEM (CQ) instances.

### Instructions
To set up JMX remoting on your AEM instances, you must modify the crx-quickstart/bin/start script and add the following to the CQ_JVM_OPTS variable:
``-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9000 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false``
