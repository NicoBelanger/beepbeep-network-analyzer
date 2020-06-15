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
import org.jnetpcap.protocol.lan.Ethernet;
import ca.uqac.lif.cep.functions.UnaryFunction;

/**
 * PacketFunction to get source MAC from a network packet
 *
 */
public class GetSourceMAC extends UnaryFunction<JPacket, String>
{

  private static final Ethernet eth = new Ethernet();

  public GetSourceMAC()
  {
    super(JPacket.class, String.class);
  }

  @Override
  public String getValue(JPacket packet)
  {
    if (packet.hasHeader(eth))
    {
      return FormatUtils.mac(eth.source());
    }
    
    return "";
  }

  @Override
  public GetSourceMAC duplicate(boolean with_state)
  {
    return this;
  }
}