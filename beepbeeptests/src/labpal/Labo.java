package labpal;

//import static labpal.StreamExperiment.NUMPACK;
import static labpal.StreamExperiment.TIME;
import static labpal.StreamExperiment.LENGTH;
/*import static ca.uqac.lif.cep.supplychain.labpal.ParcelExperiment.NUM_HOPS;
import static ca.uqac.lif.cep.supplychain.labpal.ParcelExperiment.NUM_PARCELS;
import static ca.uqac.lif.cep.supplychain.labpal.StreamExperiment.LENGTH;
import static ca.uqac.lif.cep.supplychain.labpal.StreamExperiment.PROPERTY;
import static ca.uqac.lif.cep.supplychain.labpal.StreamExperiment.THROUGHPUT;
import static ca.uqac.lif.cep.supplychain.labpal.StreamExperiment.TIME;*/

import org.jnetpcap.packet.JRegistry;
import org.jnetpcap.packet.RegistryHeaderErrors;

import com.ptr.v6app.jnetpcap.packet.Icmp6;
import com.ptr.v6app.jnetpcap.packet.NeighborAdvertisement;
import com.ptr.v6app.jnetpcap.packet.NeighborSolicitation;

//import ca.uqac.lif.cep.supplychain.labpal.DataStats;
//import ca.uqac.lif.cep.supplychain.labpal.LabStats;
//import ca.uqac.lif.cep.supplychain.labpal.ParcelExperiment;
import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.LatexNamer;
import ca.uqac.lif.labpal.TitleNamer;

public class Labo extends Laboratory {
  
  /**
   * The step (in number of events) at which measurements are made in each experiment
   */
  public static int s_eventStep = 10000;

  /**
   * The maximum trace length to generate
   */
  public static int MAX_TRACE_LENGTH = 1000001;

  /**
   * A nicknamer
   */
  public static transient LatexNamer s_nicknamer = new LatexNamer();

  /**
   * A title namer
   */
  public static transient TitleNamer s_titleNamer = new TitleNamer();

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
		Region reg = new Region();

		StreamExperiment exp = m_factory.get(reg);
		ExperimentTable et = new ExperimentTable(LENGTH, TIME);
		s_titleNamer.setTitle(et, reg, "Processing time according to number of packets (", ")");
		s_nicknamer.setNickname(et, reg, "tTimePacketsNum", "");
		et.add(exp);
		add(et);
		Scatterplot plot = new Scatterplot(et);
		s_titleNamer.setTitle(et, reg, "Processing time according to number of packets (", ")");
		s_nicknamer.setNickname(et, reg, "pTimePacketsNum", "");
		plot.setCaption(Axis.X, "Number of packets processed").setCaption(Axis.Y, "Time (ms)");
		add(plot);
	}
	
	public static void main(String[] args) {
		  try {
				JRegistry.register(Icmp6.class);
				JRegistry.register(NeighborSolicitation.class);
				JRegistry.register(NeighborAdvertisement.class);
			} catch (RegistryHeaderErrors e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  
	    initialize(args, Labo.class);
	  }

}
