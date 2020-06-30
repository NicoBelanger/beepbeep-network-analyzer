/*
  BeepBeep, an event stream processor
  Copyright (C) 2008-2016 Sylvain Hallé
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published
  by the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.
  You should have received a copy of the GNU Lesser General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package beepbeeptests;

import java.net.InetAddress;
import java.util.Set;

import org.jnetpcap.packet.JPacket;
import org.jnetpcap.protocol.network.Ip6;

import com.ptr.v6app.jnetpcap.packet.NeighborAdvertisement;
import ca.uqac.lif.cep.Context;
import ca.uqac.lif.cep.EventTracker;
import ca.uqac.lif.cep.functions.Function;

/**
 * Function to check is Neighbor Advertisement packet is used for DAD
 *
 */

public class isDadNA extends Function
{
	private static final NeighborAdvertisement na = new NeighborAdvertisement();
	
	@Override
	public Function duplicate(boolean arg0) {
		// TODO Auto-generated method stub
		return new isDadNA();
	}

	@Override
	public void evaluate(Object[] arg0, Object[] arg1, Context arg2, EventTracker arg3) {
		// TODO Auto-generated method stub
		try
		{
			JPacket packet = (JPacket) arg0[0];
			
			final Ip6 ip6 = new Ip6();
			
			if (packet.hasHeader(na) && packet.hasHeader(ip6))
		    {
		    	if((na.oFlag() == 1 || na.sFlag() == 1) && InetAddress.getByAddress(ip6.destination()).getHostAddress().toUpperCase().startsWith("FF02:"))
		    	{
		    		arg1[0] = true;
		    	}
		    	else
		    	{
		    		arg1[0] = false;
		    	}
		    }
			else
			{
				arg1[0] = false;
			}
			
		}
		catch (Exception e) 
		{
			arg1[0] = false;
		}
	}

	@Override
	public int getInputArity() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void getInputTypesFor(Set<Class<?>> arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg1 == 0)
			arg0.add(JPacket.class);
	}

	@Override
	public int getOutputArity() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Class<?> getOutputTypeFor(int arg0) {
		// TODO Auto-generated method stub
		if (arg0 == 0)
	        return boolean.class;
	    return null;
	}
}