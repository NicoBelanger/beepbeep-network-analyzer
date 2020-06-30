package beepbeeptests;

import org.jnetpcap.packet.JFlow;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Connector.ConnectorException;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.cep.io.Print;
import ca.uqac.lif.cep.tmf.Filter;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Pump;
import ca.uqac.lif.cep.tmf.QueueSink;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Strings;
import netp.*;
import netp.functions.GetSourceIp;

public class jcapTest {
	
	void test0() throws Exception
	{
		class HasTwoPacketsOrMore extends UnaryFunction<JFlow, Boolean>
	    {

	      public HasTwoPacketsOrMore()
	      {
	        super(JFlow.class, Boolean.class);
	      }

	      /**
	       * @param flow
	       *          The flow we want to compare the size
	       */
	      @Override
	      public Boolean getValue(JFlow flow)
	      {
	        Integer size = flow.size();
	        if (size >= 2)
	        {
	          return true;
	        }
	        return false;
	      }
	    }

	    PacketSource source = new PacketSource("test.pcap");

	    FlowTransmitter flow = new FlowTransmitter();
	    try
	    {
	      Connector.connect(source, flow);
	    }
	    catch (ConnectorException e)
	    {
	      e.printStackTrace();
	    }

	    ApplyFunction hasFiveOrMore = new ApplyFunction(new HasTwoPacketsOrMore());
	    try
	    {
	      Connector.connect(flow, hasFiveOrMore);
	    }
	    catch (ConnectorException e)
	    {
	      e.printStackTrace();
	    }

	    QueueSink sink = new QueueSink(1);
	    try
	    {
	      Connector.connect(hasFiveOrMore, sink);
	    }
	    catch (ConnectorException e)
	    {
	      e.printStackTrace();
	    }

	    // compute the first 15 packets
	    for (int i = 0; i < 15; i++)
	    {
	      source.push();
	      Boolean output = (Boolean) sink.remove()[0];
	      System.out.println(i + ": " + output);
	    }
	}
	
	void test1() throws Exception
	{
		// outputs packets from a network interface
	    // "any" takes packets from all devices
	    NetworkInterfaceSource source = new NetworkInterfaceSource("\\Device\\NPF_{48383C67-232D-4D96-8874-054AC66810B8}");
	
	    // extract source IP address of packet
	    ApplyFunction sourceIp = new ApplyFunction(new GetSourceIp());
	    Connector.connect(source, sourceIp);
	
	    // retrieve results
	    QueueSink sink = new QueueSink(1);
	    Connector.connect(sourceIp, sink);
	    
	    while(true)
	    {
			source.push();
			String output = (String) sink.remove()[0];
			System.out.println(output);
	    }
	    // compute the first 100 packets
	    /*for (int i = 0; i < 100; i++)
	    {
	      source.push();
	      String output = (String) sink.remove()[0];
	      System.out.println(i + ": " + output);
		}*/
	}
	
	void test4()
	{
	    PacketSource source = new PacketSource("bigcap.pcap");

	    ApplyFunction srcIp = new ApplyFunction(new GetSourceIp());
	    try
	    {
	      Connector.connect(source, srcIp);
	    }
	    catch (ConnectorException e)
	    {
	      e.printStackTrace();
	    }

	    QueueSink sink = new QueueSink(1);
	    try
	    {
	      Connector.connect(srcIp, sink);
	    }
	    catch (ConnectorException e)
	    {
	      e.printStackTrace();
	    }
	    
	    // compute the first 15 packets
	    for (int i = 0; i < 177946; i++)
	    {
	      source.push();
	      String output = (String) sink.remove()[0];
	      System.out.println(i + ": " + output);
	    }
	}
	
	void test5()
	{
	    PacketSource source = new PacketSource("ipv6.DAD.issue.pcap");
	    QueueSource icmpv6 = new QueueSource().setEvents("ICMPv6");
	    
	    Fork fork = new Fork(2);
	    Filter filter = new Filter();
	    ApplyFunction protName = new ApplyFunction(new GetProtocolNameV2());
	    ApplyFunction icmp6type = new ApplyFunction(new GetNSTargetAdress());
	    ApplyFunction eq = new ApplyFunction(Strings.matches);
	    Print print = new Print().setSeparator("\n");
	    Pump pump = new Pump(10);
	    
	    Connector.connect(source, fork);
	    Connector.connect(fork, 0, protName, 0);
	    Connector.connect(fork, 1, filter, 0);
	    Connector.connect(protName, 0, eq, 0);
	    Connector.connect(icmpv6, 0, eq, 1);
	    Connector.connect(eq, 0, filter, 1);
	    Connector.connect(filter, icmp6type);
	    Connector.connect(icmp6type, pump, print);
	    
	    Thread th = new Thread(pump);
	    th.start();
	}

}
