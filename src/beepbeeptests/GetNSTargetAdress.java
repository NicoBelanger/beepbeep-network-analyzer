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
import java.net.UnknownHostException;

import org.jnetpcap.packet.JPacket;
import com.ptr.v6app.jnetpcap.packet.NeighborSolicitation;

import ca.uqac.lif.cep.functions.UnaryFunction;

/**
 * PacketFunction to get the target IP address of a Neighbor Solicitation packet
 *
 */
public class GetNSTargetAdress extends UnaryFunction<JPacket, String>
{
  private static final NeighborSolicitation ns = new NeighborSolicitation();

  public GetNSTargetAdress()
  {
    super(JPacket.class, String.class);
  }

  @Override
  public String getValue(JPacket packet)
  {
    String result = "Undefined";
    
    if (packet.hasHeader(ns))
    {
    	try {
			result = InetAddress.getByAddress(ns.targetAddress()).getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    return result;
  }

  @Override
  public GetNSTargetAdress duplicate(boolean with_state)
  {
    return this;
  }
}