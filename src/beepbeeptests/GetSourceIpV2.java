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
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;

import ca.uqac.lif.cep.functions.UnaryFunction;

/**
 * PacketFunction to get source IP from a network packet for IPv4 and IPv6
 *
 */
public class GetSourceIpV2 extends UnaryFunction<JPacket, String>
{

  private static final Ip4 ip4 = new Ip4();
  private static final Ip6 ip6 = new Ip6();

  public GetSourceIpV2()
  {
    super(JPacket.class, String.class);
  }

  @Override
  public String getValue(JPacket packet)
  {
    if (packet.hasHeader(ip4))
    {
      return FormatUtils.ip(ip4.source());
    }
    else if (packet.hasHeader(ip6))
    {
    	return FormatUtils.ip(ip6.source());
    }
    
    return "";
  }

  @Override
  public GetSourceIpV2 duplicate(boolean with_state)
  {
    return this;
  }
}
