/*
    Processor chains for hyperconnected logistics
    Copyright (C) 2018-2019 Laboratoire d'informatique formelle

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package labpal;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.tmf.Source;
import ca.uqac.lif.labpal.ExperimentFactory;
import ca.uqac.lif.labpal.Region;
import netp.PacketSource;

import beepbeeptests.DadProcessor;

/**
 * An {@link ExperimentFactory} that produces {@link ParcelExperiment}s.
 */
public class StreamExperimentFactory extends ExperimentFactory<Labo,StreamExperiment>
{
  public StreamExperimentFactory(Labo lab)
  {
    super(lab, StreamExperiment.class);
  }
  
  @Override
  protected StreamExperiment createExperiment(Region r)
  {
    Processor p = new DadProcessor();
    StreamExperiment exp = new DadExp();
    exp.setProcessor(p);
    exp.setSource(getSource(r));
    exp.setNumEvents(getNumEvents(r));
    return exp;
  }

	private int getNumEvents(Region r) {
		// TODO Auto-generated method stub
		return 9;
	}
	
	private Source getSource(Region r) {
		// TODO Auto-generated method stub
		//return new PacketSource("ipv6.DAD.issue.pcap");
		return new PacketSource("bigcap.pcap");
	}

}
