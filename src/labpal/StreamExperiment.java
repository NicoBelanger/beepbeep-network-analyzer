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

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.Pushable;
import ca.uqac.lif.cep.tmf.BlackHole;
import ca.uqac.lif.cep.tmf.Source;
import ca.uqac.lif.json.JsonList;
import ca.uqac.lif.labpal.Experiment;
import ca.uqac.lif.labpal.ExperimentException;
import ca.uqac.lif.cep.supplychain.labpal.BoundedSource;
import ca.uqac.lif.cep.supplychain.labpal.SupplyChainLab;

/**
 * Experiment that connects a source to a processor and measures its
 * throughput
 */
public abstract class StreamExperiment extends Experiment
{
  /**
   * The average number of events processed per second
   */
  public static final transient String THROUGHPUT = "Throughput";

  /**
   * Cumulative running time (in ms)
   */
  public static final transient String TIME = "Running time";

  /**
   * Number of events processed
   */
  public static final transient String LENGTH = "Stream length";

  /**
   * Whether the experiment uses multiple threads or a single one
   */
  public static final transient String MULTITHREAD = "Multi-threaded";
  
  /**
   * The name of the property being monitored
   */
  public static final transient String PROPERTY = "Property";

  /**
   * The processor that is being monitored in this experiment
   */
  protected transient Processor m_processor;

  /**
   * The source from which the input events will originate
   */
  protected transient Source m_source;

  /**
   * The interval at which the experiment updates its data on
   * runtime and throughput
   */
  protected int m_eventStep = 1000;
  
  /**
   * The predicted throughput for this experiment. This is only used to give an
   * estimate of the time remaining before the experiment is done.
   */
  protected float m_predictedThroughput = 0;
  
  /**
   * An internal URL to the picture that represents the property
   */
  protected transient String m_imageUrl = null;
  
  /**
   * The description of the property being monitored
   */
  protected transient String m_propertyDescription = null;

  /**
   * Creates a new empty stream experiment
   */
  public StreamExperiment()
  {
    describe(THROUGHPUT, "The average number of events processed per second");
    describe(TIME, "Cumulative running time (in ms)");
    describe(LENGTH, "Number of events processed");
    describe(MULTITHREAD, "Whether the experiment uses multiple threads or a single one");
    describe(PROPERTY, "The name of the property being monitored");
    JsonList x = new JsonList();
    x.add(0);
    write(LENGTH, x);
    JsonList y = new JsonList();
    y.add(0);
    write(TIME, y);
  }

  @Override
  public void execute() throws ExperimentException, InterruptedException 
  {
    JsonList length = (JsonList) read(LENGTH);
    JsonList time = (JsonList) read(TIME);
    // Setup processor chain
    Pullable s_p = m_source.getPullableOutput();
    Pushable t_p = m_processor.getPushableInput();
    BlackHole hole = new BlackHole();
    Connector.connect(m_processor, hole);
    long start = System.currentTimeMillis();
    int event_count = 0;
    int source_length = m_source.getInputCount();
    while (s_p.hasNext())
    {
      if (event_count % m_eventStep == 0 && event_count > 0)
      {
        long lap = System.currentTimeMillis();
        length.add(event_count);
        time.add(lap - start);
        float prog = ((float) event_count) / ((float) source_length);
        setProgression(prog);
      }
      Object o = s_p.pull();
      t_p.push(o);
      event_count++;
    }
    long end = System.currentTimeMillis();
    write(THROUGHPUT, (1000f * (float) SupplyChainLab.MAX_TRACE_LENGTH) / ((float) (end - start)));
  }

  /**
   * Sets the processor that is being monitored in this experiment
   * @param p The processor
   */
  public void setProcessor(Processor p)
  {
    m_processor = p;
  }

  /**
   * Sets the source from which the input events will originate
   * @param s The source
   */
  public void setSource(Source s)
  {
    m_source = s;
  }

  /**
   * Sets the interval at which the experiment updates its data on
   * runtime and throughput
   * @param step The interval
   */
  public void setEventStep(int step)
  {
    m_eventStep = step;
  }
  
  /**
   * Sets a picture for the processor chain evaluated by this experiment
   * @param url The URL for the picture
   */
  public void setImageUrl(String url)
  {
    m_imageUrl = url;
  }
  
  /**
   * Sets the textual description of the property being evaluated
   * @param description The description
   */
  public void setPropertyDescription(String description)
  {
    m_propertyDescription = description;
  }
  
  /**
   * Sets the predicted throughput for this experiment. This is only used to 
   * give an estimate of the time remaining before the experiment is done. 
   * @param throughput The throughput, in number of events per second
   */
  public void setPredictedThroughput(float throughput)
  {
    m_predictedThroughput = throughput;
  }
  
  @Override
  public float getDurationEstimate(float factor)
  {
    if (m_predictedThroughput <= 0)
    {
      return 0f;
    }
    return factor * ((float) SupplyChainLab.MAX_TRACE_LENGTH) / m_predictedThroughput;
  }
}