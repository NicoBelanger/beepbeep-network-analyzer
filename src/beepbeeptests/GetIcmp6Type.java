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
import com.ptr.v6app.jnetpcap.packet.Icmp6;
import ca.uqac.lif.cep.functions.UnaryFunction;

/**
 * PacketFunction to get the type of ICMPv6 packet
 *
 */
public class GetIcmp6Type extends UnaryFunction<JPacket, String>
{
  private static final Icmp6 icmp6 = new Icmp6();

  public GetIcmp6Type()
  {
    super(JPacket.class, String.class);
  }

  @Override
  public String getValue(JPacket packet)
  {
    String result = "Not ICMPv6";
    
    if (packet.hasHeader(icmp6))
    {
    	int type = icmp6.type();
    	switch (type)
        {
        case 135:
          result = "Neighbor Solicitation";
          break;
        case 136:
          result = "Neighbor Advertisement";
          break;
        default:
          result = ""+type;
          break;
        }
    }
    return result;
  }

  @Override
  public GetIcmp6Type duplicate(boolean with_state)
  {
    return this;
  }
}