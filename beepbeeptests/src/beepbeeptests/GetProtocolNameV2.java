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

import org.jnetpcap.packet.JPacket;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;

import ca.uqac.lif.cep.functions.UnaryFunction;

/**
 * PacketFunction to get the protocol name of a network packet for IPv4 and IPv6
 *
 */
public class GetProtocolNameV2 extends UnaryFunction<JPacket, String>
{

  private static final Ip4 ip4 = new Ip4();
  private static final Ip6 ip6 = new Ip6();

  public GetProtocolNameV2()
  {
    super(JPacket.class, String.class);
  }

  @Override
  public String getValue(JPacket packet)
  {
    int id = -1;
    String result = "Undefined";
    if (packet.hasHeader(ip4))
    {
      id = ip4.type();
    }
    else if (packet.hasHeader(ip6))
    {
    	id = ip6.next();
    }
    
    switch (id)
    {
    case 0:
    	result = "IPv6 Hop-by-Hop Option";
    	break;
    case 1:
      result = "ICMP";
      break;
    case 4:
      result = "IPv4";
      break;
    case 6:
      result = "TCP";
      break;
    case 17:
      result = "UDP";
      break;
    case 58:
    	result = "ICMPv6";
    	break;
    default:
      result = "Undefined";
      break;
    }
    return result;
  }

  @Override
  public GetProtocolNameV2 duplicate(boolean with_state)
  {
    return this;
  }
}