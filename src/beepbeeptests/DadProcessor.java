package beepbeeptests;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.tmf.Filter;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Multiplex;
import netp.PacketSource;

public class DadProcessor extends GroupProcessor {

	public DadProcessor() {
		super(1, 1);
		Multiplex plex0 = new Multiplex(2);

		GroupProcessor filterDadNSNA = new GroupProcessor(1, 2);
		{
		  Fork fork = new Fork(4);
		  ApplyFunction dadns = new ApplyFunction(new isDadNS());
		  ApplyFunction dadna = new ApplyFunction(new isDadNA());
		  Filter filterNs = new Filter();
		  Filter filterNa = new Filter();
		  
		  Connector.connect(fork, 0, dadns, 0);
		  Connector.connect(fork, 1, filterNs, 0);
		  Connector.connect(fork, 2, dadna, 0);
		  Connector.connect(fork, 3, filterNa, 0);
		  
		  Connector.connect(dadns, 0, filterNs, 1);
		  Connector.connect(dadna, 0, filterNa, 1);

		  filterDadNSNA.associateInput(0, fork, 0);
		  filterDadNSNA.associateOutput(0, filterNs, 0);
		  filterDadNSNA.associateOutput(1, filterNa, 0);
		}
		
		GroupProcessor processNS = new GroupProcessor(1, 1);
		{
			Fork fork0 = new Fork(2);
			Fork fork1 = new Fork(3);
			ApplyFunction mac = new ApplyFunction(new GetSourceMAC());
			ApplyFunction targetip = new ApplyFunction(new GetNSTargetAdress());
			ApplyFunction time = new ApplyFunction(new GetTimeStamp());
			ApplyFunction isGlob = new ApplyFunction(new isTargetGlobal());
			Filter globalFilt = new Filter();
			ApplyFunction arpPutNS = new ApplyFunction(new insertInARP());
			
			Connector.connect(fork0, 0, globalFilt, 0);
			Connector.connect(fork0, 1, isGlob, 0);
			Connector.connect(isGlob, 0, globalFilt, 1);
			
			Connector.connect(globalFilt, fork1);
			Connector.connect(fork1, 0, mac, 0);
			Connector.connect(fork1, 1, targetip, 0);
			Connector.connect(fork1, 2, time, 0);
			
			Connector.connect(mac, 0, arpPutNS, 0);
			Connector.connect(targetip, 0, arpPutNS, 1);
			Connector.connect(time, 0, arpPutNS, 2);
			
			processNS.associateInput(0, fork0, 0);
			processNS.associateOutput(0, arpPutNS, 0);
		}

		GroupProcessor processNA = new GroupProcessor(1, 1);
		{
			Fork fork0 = new Fork(2);
			Fork fork1 = new Fork(3);
			ApplyFunction mac = new ApplyFunction(new GetSourceMAC());
			ApplyFunction targetip = new ApplyFunction(new GetNATargetAdress());
			ApplyFunction time = new ApplyFunction(new GetTimeStamp());
			ApplyFunction isGlob = new ApplyFunction(new isTargetGlobal());
			Filter globalFilt = new Filter();
			ApplyFunction arpPutNA = new ApplyFunction(new insertInARPforNA());
			
			Connector.connect(fork0, 0, globalFilt, 0);
			Connector.connect(fork0, 1, isGlob, 0);
			Connector.connect(isGlob, 0, globalFilt, 1);
			
			Connector.connect(globalFilt, fork1);
			Connector.connect(fork1, 0, mac, 0);
			Connector.connect(fork1, 1, targetip, 0);
			Connector.connect(fork1, 2, time, 0);
			
			Connector.connect(mac, 0, arpPutNA, 0);
			Connector.connect(targetip, 0, arpPutNA, 1);
			Connector.connect(time, 0, arpPutNA, 2);

			processNA.associateInput(0, fork0, 0);
			processNA.associateOutput(0, arpPutNA, 0);
		}
		
		//Connector.connect(source, filterDadNSNA);
		Connector.connect(filterDadNSNA, 0, processNS, 0);
		Connector.connect(filterDadNSNA, 1, processNA, 0);
		
		Connector.connect(processNS, 0, plex0, 0);
		Connector.connect(processNA, 0, plex0, 1);

		associateInput(0, filterDadNSNA, 0);
        associateOutput(0, plex0, 0);

	}

}
