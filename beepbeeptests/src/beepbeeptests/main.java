package beepbeeptests;

import org.jnetpcap.packet.JRegistry;
import org.jnetpcap.packet.RegistryHeaderErrors;

import com.ptr.v6app.jnetpcap.packet.Icmp6;
import com.ptr.v6app.jnetpcap.packet.NeighborAdvertisement;
import com.ptr.v6app.jnetpcap.packet.NeighborSolicitation;

import ca.uqac.lif.cep.*;
import ca.uqac.lif.cep.functions.*;
import ca.uqac.lif.cep.io.*;
import ca.uqac.lif.cep.tmf.*;
import ca.uqac.lif.cep.util.*;
import util.UtilityMethods;
import java.io.*;

public class main {
  public static void main(String[] args) {
	  
	  try {
		JRegistry.register(Icmp6.class);
		JRegistry.register(NeighborSolicitation.class);
		JRegistry.register(NeighborAdvertisement.class);
	} catch (RegistryHeaderErrors e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  
	  DadDos dd = new DadDos();
	  
	  dd.start();
  }
  
  static void exo1_1()
  {
	  QueueSource source = new QueueSource();
	  source.setEvents(1, 3, 5, 7, 9);
	  Doubler doubler = new Doubler();
	  Connector.connect(source, doubler);
	  Doubler doubler2 = new Doubler();
	  Connector.connect(doubler, doubler2);
	  Pullable p = doubler2.getPullableOutput();
	  
	  for (int i = 0; i < 5; i++)
	  {
	      int x = (Integer) p.pull();
	      System.out.println("The event is: " + x);
	  }
  }
  
  static void exo1_2()
  {
	  QueueSource source1 = new QueueSource();
	  source1.setEvents(2, 3, 5, 7, 11);
	  QueueSource source2 = new QueueSource();
	  source2.setEvents(3, 5, 7, 11, 13);
	  Adder add = new Adder();
	  Connector.connect(source1, 0, add, 0);
	  Connector.connect(source2, 0, add, 1);
	  Pullable p = add.getPullableOutput();
	  for (int i = 0; i < 5; i++)
	  {
	      int x = (int) p.pull();
	      System.out.println("The event is: " + x);
	  }
  }
  
  static void exo2_1()
  {
	  QueueSource source = new QueueSource().setEvents(1, 2, 3, 4, 5);
	  
	  ApplyFunction add = new ApplyFunction(Numbers.addition);
	  Fork fork = new Fork(2);
	  Trim trim = new Trim(2);
	  
	  Connector.connect(source, fork);
	  Connector.connect(fork, 0, add, 0);
	  Connector.connect(fork, 1, trim, 0);
	  Connector.connect(trim, 0, add, 1);
	  
	  Pullable p = add.getPullableOutput();
	  
	  for (int i = 0; i < 3; i++)
	  {
	      float x = (float) p.pull();
	      System.out.println("The event is: " + x);
	  }
  }
  
  static void exo2_2()
  {
	  QueueSource source = new QueueSource().setEvents(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	  
	  Trim trim = new Trim(1);
	  CountDecimate dec = new CountDecimate(3);
	  
	  Connector.connect(source, trim);
	  Connector.connect(trim, dec);
	  Pullable p = dec.getPullableOutput();
	  
	  
	  for (int i = 0; i < 4; i++)
	  {
	      int x = (int) p.pull();
	      System.out.println("The event is: " + x);
	  }
  }
  
  static void exo2_3()
  {
	  QueueSource source = new QueueSource().setEvents(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	  
	  Fork fork = new Fork(3);
	  Trim trim = new Trim(1);
	  Trim trim2 = new Trim(2);
	  ApplyFunction add = new ApplyFunction(Numbers.addition);
	  ApplyFunction add2 = new ApplyFunction(Numbers.addition);
	  
	  Connector.connect(source, fork);
	  Connector.connect(fork, 0, add, 0);
	  Connector.connect(fork, 1, trim, 0);
	  Connector.connect(fork, 2, trim2, 0);
	  Connector.connect(trim, 0, add2, 0);
	  Connector.connect(trim2, 0, add2, 1);
	  Connector.connect(add2, 0, add, 1);
	  
	  Pullable p = add.getPullableOutput();
	  
	  for (int i = 0; i < 8; i++)
	  {
	      float x = (float) p.pull();
	      System.out.println("The event is: " + x);
	  }
  }
  
  static void exo2_4()
  {
	  QueueSource source = new QueueSource().setEvents(1, 2, 3, 4);
	  
	  Fork fork = new Fork(2);
	  ApplyFunction mult = new ApplyFunction(Numbers.multiplication);
	  
	  ApplyFunction add = new ApplyFunction(Numbers.addition);
	  
	  Connector.connect(source, fork);
	  Connector.connect(fork, 0, mult, 0);
	  Connector.connect(fork, 1, mult, 1);
	  
	  Pullable p = mult.getPullableOutput();
	  
	  for (int i = 0; i < 4; i++)
	  {
	      float x = (float) p.pull();
	      System.out.println("The event is: " + x);
	  }
  }
  
  //Est-ce possible? Fibonacci ???
  static void exo2_5()
  {	   
	  Fork fork = new Fork(2);
	  Trim trim = new Trim(1);
	  ApplyFunction add = new ApplyFunction(Numbers.addition);
	  Print print = new Print();
	  
	  Connector.connect(fork, 0, add, 0);
	  Connector.connect(fork, 1, trim, 0);
	  Connector.connect(trim, 0, add, 1);
	  Connector.connect(add, print);
	  
	  Pushable p = fork.getPushableInput();
	  
	  p.push(1);
	  p.push(1);
	  p.push(2);
	  p.push(3);
  }
  
  static void exo2_6()
  {	  
	  QueueSource source = new QueueSource().setEvents(10,3,41,2,1,2,40);
	  
	  ApplyFunction ite = new ApplyFunction(IfThenElse.instance);
	  
	  ApplyFunction gte = new ApplyFunction(Numbers.isGreaterOrEqual);
	  
	  TurnInto ten = new TurnInto(10);
	  
	  TurnInto zero = new TurnInto(0);
	  
	  Fork fork = new Fork(4);
	  
	  Connector.connect(source, fork);
	  Connector.connect(fork, 0, gte, 0);
	  Connector.connect(fork, 1, ten, 0);
	  Connector.connect(fork, 2, ite, 1);
	  Connector.connect(fork, 3, zero, 0);
	  Connector.connect(ten, 0, gte, 1);
	  Connector.connect(gte, 0, ite, 0);
	  Connector.connect(zero, 0, ite, 2);
	  
	  Pullable p = ite.getPullableOutput();
	  
	  for (int i = 0; i < 7; i++)
	  {
	      int x = (int) p.pull();
	      System.out.println("The event is: " + x);
	  }
  }
  
  static void exo2_7()
  {
	  QueueSource source = new QueueSource().setEvents(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
	  
	  GroupProcessor group = new GroupProcessor(1, 1);
	  {
		  Fork fork = new Fork(4);
		  ApplyFunction ite = new ApplyFunction(IfThenElse.instance);
		  ApplyFunction even = new ApplyFunction(Numbers.isEven);
		  ApplyFunction mult = new ApplyFunction(Numbers.multiplication);
		  TurnInto one = new TurnInto(1);
		  TurnInto minus = new TurnInto(-1);
		  Cumulate cnt = new Cumulate(
		            new CumulativeFunction<Number>(Numbers.addition));
		  
		  Connector.connect(fork, 0, one, 0);
		  Connector.connect(fork, 1, mult, 0);
		  Connector.connect(fork, 2, ite, 2);
		  Connector.connect(fork, 3, minus, 0);
		  Connector.connect(minus, 0, mult, 1);
		  Connector.connect(mult, 0, ite, 1);
		  Connector.connect(one, cnt);
		  Connector.connect(cnt, even);
		  Connector.connect(even, 0, ite, 0);
		  group.associateInput(0, fork, 0);
		  group.associateOutput(0, ite, 0);
	  }
	  
	  Connector.connect(source, group);
	  Pullable p = group.getPullableOutput();
	  
	  for (int i = 0; i < 6; i++)
	  {
	      System.out.println("The event is: " + p.pull());
	  }
  }
  
  static void exo2_8()
  {
	  QueueSource source = new QueueSource().setEvents(1);
	  ApplyFunction div = new ApplyFunction(Numbers.division);
	  Cumulate cnt2 = new Cumulate(
	            new CumulativeFunction<Number>(Numbers.addition));
	  CountDecimate dec = new CountDecimate(2);
	  TurnInto one2 = new TurnInto(1);
	  Fork fork3 = new Fork(2);
	  
	  TurnInto four = new TurnInto(4);
	  Fork fork2 = new Fork(2);
	  ApplyFunction mult2 = new ApplyFunction(Numbers.multiplication);
	  Cumulate sum = new Cumulate(
	            new CumulativeFunction<Number>(Numbers.addition));
	  
	  Connector.connect(source, cnt2);
	  Connector.connect(cnt2, dec);
	  Connector.connect(dec, fork3);
	  Connector.connect(fork3, 0, one2, 0);
	  Connector.connect(fork3, 1, div, 1);
	  Connector.connect(one2, 0, div, 0);
	  
	  GroupProcessor group = new GroupProcessor(1, 1);
	  {
		  Fork fork = new Fork(4);
		  ApplyFunction ite = new ApplyFunction(IfThenElse.instance);
		  ApplyFunction even = new ApplyFunction(Numbers.isEven);
		  ApplyFunction mult = new ApplyFunction(Numbers.multiplication);
		  TurnInto one = new TurnInto(1);
		  TurnInto minus = new TurnInto(-1);
		  Cumulate cnt = new Cumulate(
		            new CumulativeFunction<Number>(Numbers.addition));
		  
		  Connector.connect(fork, 0, one, 0);
		  Connector.connect(fork, 1, mult, 0);
		  Connector.connect(fork, 2, ite, 2);
		  Connector.connect(fork, 3, minus, 0);
		  Connector.connect(minus, 0, mult, 1);
		  Connector.connect(mult, 0, ite, 1);
		  Connector.connect(one, cnt);
		  Connector.connect(cnt, even);
		  Connector.connect(even, 0, ite, 0);
		  group.associateInput(0, fork, 0);
		  group.associateOutput(0, ite, 0);
	  }
	  
	  Connector.connect(div, group);
	  Connector.connect(group, sum);
	  Connector.connect(sum, fork2);
	  Connector.connect(fork2, 0, four, 0);
	  Connector.connect(fork2, 1, mult2, 0);
	  Connector.connect(four, 0, mult2, 1);
	  
	  Pullable p = mult2.getPullableOutput();
	  
	  for (int i = 0; i < 1000; i++)
	  {
	      System.out.println("The event is: " + p.pull());
	  }
  }
  
  static void exo2_9()
  {
	  QueueSource source = new QueueSource().setEvents(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
	  QueueSource source2 = new QueueSource().setEvents(2);
	  Fork fork2 = new Fork(2);
	  Fork fork3 = new Fork(2);
	  
	  ApplyFunction pow = new ApplyFunction(Numbers.power);
	  ApplyFunction pow2 = new ApplyFunction(Numbers.power);
	  ApplyFunction minus = new ApplyFunction(Numbers.subtraction);
	  
	  GroupProcessor group = new GroupProcessor(1, 1);
	  {
		  Fork fork = new Fork(2);
		  TurnInto one = new TurnInto(1);
		  Cumulate sum = new Cumulate(
		            new CumulativeFunction<Number>(Numbers.addition));
		  Cumulate cnt = new Cumulate(
		            new CumulativeFunction<Number>(Numbers.addition));
		  ApplyFunction div = new ApplyFunction(Numbers.division);
		  
		  Connector.connect(fork, 0, sum, 0);
		  Connector.connect(fork, 1, one, 0);
		  Connector.connect(one, cnt);
		  Connector.connect(sum, 0, div, 0);
		  Connector.connect(cnt, 0, div, 1);
		  group.associateInput(0, fork, 0);
		  group.associateOutput(0, div, 0);
	  }
	  
	  GroupProcessor group2 = new GroupProcessor(1, 1);
	  {
		  Fork fork = new Fork(2);
		  TurnInto one = new TurnInto(1);
		  Cumulate sum = new Cumulate(
		            new CumulativeFunction<Number>(Numbers.addition));
		  Cumulate cnt = new Cumulate(
		            new CumulativeFunction<Number>(Numbers.addition));
		  ApplyFunction div = new ApplyFunction(Numbers.division);
		  
		  Connector.connect(fork, 0, sum, 0);
		  Connector.connect(fork, 1, one, 0);
		  Connector.connect(one, cnt);
		  Connector.connect(sum, 0, div, 0);
		  Connector.connect(cnt, 0, div, 1);
		  group2.associateInput(0, fork, 0);
		  group2.associateOutput(0, div, 0);
	  }
	  
	  Connector.connect(source, fork3);
	  Connector.connect(fork3, 0, group, 0);
	  Connector.connect(fork3, 1, pow, 0);
	  
	  Connector.connect(source2, fork2);
	  Connector.connect(fork2, 0, pow, 1);
	  Connector.connect(fork2, 1, pow2, 1);
	  
	  Connector.connect(pow, group2);
	  Connector.connect(group, 0, pow2, 0);
	  Connector.connect(pow2, 0, minus, 1);
	  Connector.connect(group2, 0, minus, 0);
	  
	  Pullable p = minus.getPullableOutput();
	  
	  for (int i = 0; i < 6; i++)
	  {
	      System.out.println("The event is: " + p.pull());
	  }
	}

  static void exo2_11()
  {
	  Fork fork = new Fork(2);
	  QueueSource source = new QueueSource().setEvents(5,8,10,52,65);
	  ApplyFunction str = new ApplyFunction(Strings.toString);
	  ApplyFunction end = new ApplyFunction(Strings.endsWith);
	  ApplyFunction end2 = new ApplyFunction(Strings.endsWith);
	  ApplyFunction bor = new ApplyFunction(Booleans.or);
	  QueueSource five = new QueueSource().setEvents("5");
	  QueueSource zero = new QueueSource().setEvents("0");
	  ApplyFunction ite = new ApplyFunction(IfThenElse.instance);
	  QueueSource multiple = new QueueSource().setEvents("This is a multiple of 5");
	  QueueSource notmult = new QueueSource().setEvents("This is something else");
	  
	  Connector.connect(source, str);
	  Connector.connect(str, fork);
	  Connector.connect(fork, 0, end, 0);
	  Connector.connect(zero, 0, end, 1);
	  
	  Connector.connect(fork, 1, end2, 0);
	  Connector.connect(five, 0, end2, 1);
	  
	  Connector.connect(end, 0, bor, 0);
	  Connector.connect(end2, 0, bor, 1);
	  
	  Connector.connect(bor, 0, ite, 0);
	  Connector.connect(multiple, 0, ite, 1);
	  Connector.connect(notmult, 0, ite, 2);
	  
	  Pullable p = ite.getPullableOutput();
	  
	  for (int i = 0; i < 5; i++)
	  {
	      System.out.println("The event is: " + p.pull());
	  }
  }
  
  //Issue avec Maps.Get
  static void exo2_12()
  {
	  QueueSource source = new QueueSource().setEvents(true,true,false,false,true,false,true,true);
	  
	  Function slicing_fct = new IdentityFunction(1);
	  
	  GroupProcessor counter = new GroupProcessor(1, 1);
	  {
	      TurnInto to_one = new TurnInto(1);
	      Cumulate sum = new Cumulate(
	          new CumulativeFunction<Number>(Numbers.addition));
	      Connector.connect(to_one, sum);
	      counter.addProcessors(to_one, sum);
	      counter.associateInput(0, to_one, 0);
	      counter.associateOutput(0, sum, 0);
	  }
	  Slice slicer = new Slice(slicing_fct, counter);
	
	  Window win = new Window(slicer, 3);
	  
	  Connector.connect(source, win);
	  
	  Pullable p = win.getPullableOutput();
	  
	  for (int i = 0; i < 6; i++)
	  {
	      System.out.println("The event is: " + p.pull());
	  }
  }
  
  static void exo2_13()
  {
	  QueueSource source = new QueueSource().setEvents(1,8,-5,-3,9,1,-5,2,-6).loop(false);
	  QueueSource zero = new QueueSource().setEvents(0);
	  QueueSource one = new QueueSource().setEvents(1);
	  Filter filter = new Filter();
	  ApplyFunction gte = new ApplyFunction(Numbers.isGreaterOrEqual);
	  ApplyFunction lt = new ApplyFunction(Numbers.isLessThan);
	  ApplyFunction and = new ApplyFunction(Booleans.and);
	  Cumulate sum = new Cumulate(
		        new CumulativeFunction<Number>(Numbers.addition));
	  
	  Fork fork = new Fork(2);
	  Fork fork2 = new Fork(2);
	  Trim trim = new Trim(1);
	  
	  Connector.connect(source, fork);
	  Connector.connect(fork, 1, trim, 0);
	  
	  Connector.connect(trim, 0, lt, 0);
	  Connector.connect(zero, fork2);
	  Connector.connect(fork2, 0, lt, 1);
	  
	  Connector.connect(fork, 0, gte, 0);
	  Connector.connect(fork2, 1, gte, 1);
	  
	  Connector.connect(gte, 0, and, 0);
	  Connector.connect(lt, 0, and, 1);
	  
	  Connector.connect(one, 0, filter, 0);
	  Connector.connect(and, 0, filter, 1);
	  
	  Connector.connect(filter, sum);
	  
	  Pullable p = sum.getPullableOutput();
	  
	  for (int i = 0; i < 3; i++)
	  {
	      System.out.println("The event is: " + p.pull());
	  }
  }
 
  static void exo3_1()
  {
	  QueueSource source = new QueueSource();
	  source.addEvent(UtilityMethods.createList(1, 3, 5));
	  source.addEvent(UtilityMethods.createList(4, 2));
	  source.addEvent(UtilityMethods.createList(4, 4, 8));
	  
	  Fork fork = new Fork(2);
	  ApplyFunction size = new ApplyFunction(Bags.getSize);
	  
	  FunctionTree tree = new FunctionTree(Bags.contains,
		                StreamVariable.X, StreamVariable.Y);
	  
	  ApplyFunction cont = new ApplyFunction(tree);
	  
	  Connector.connect(source, fork);
	  Connector.connect(fork, 0, size, 0);
	  Connector.connect(size, 0, cont, 1);
	  Connector.connect(fork, 1, cont, 0);
	  
	  Pullable p = cont.getPullableOutput();
	  
	  for (int i = 0; i < 3; i++)
	  {
	      System.out.println("The event is: " + p.pull());
	  }
  }

  static void exo3_2()
  {
	  QueueSource source = new QueueSource().setEvents(4,-5,8).loop(false);
	  QueueSource source2 = new QueueSource().setEvents(8,7,2).loop(false);
	  QueueSource source3 = new QueueSource().setEvents(-8,5,6).loop(false);

	  Function pos = new isPositive();
	  
	  ApplyFunction fct = new ApplyFunction(pos);
	  ApplyFunction to_list = new ApplyFunction(
		        new Bags.ToList(Number.class, Number.class, Number.class));
		Connector.connect(source, 0, to_list, 0);
		Connector.connect(source2, 0, to_list, 1);
		Connector.connect(source3, 0, to_list, 2);
		Connector.connect(to_list, fct);
	  
	  Pullable p = fct.getPullableOutput();
	  
	  for (int i = 0; i < 3; i++)
	  {
	      System.out.println("The event is: " + p.pull());
	  }
  }
  static void exo3_3()
  {
	  QueueSource source = new QueueSource();
	  source.addEvent(UtilityMethods.createList(1, 3, 5, 6, 2, 4, 2));
	  
	  ApplyFunction max = new ApplyFunction(Bags.maxValue);
	  
	  Connector.connect(source, max);
	  
	  Pullable p = max.getPullableOutput();
	  
	  for (int i = 0; i < 1; i++)
	  {
	      System.out.println("The event is: " + p.pull());
	  }
  }
  static void exo3_4()
  {
	  QueueSource source = new QueueSource().setEvents("this is an abridged text");
	  QueueSource a = new QueueSource().setEvents("a");
	  ApplyFunction split = new ApplyFunction(new Strings.SplitString(" "));
	  Lists.Pack pack = new Lists.Pack();
	  Fork fork = new Fork(2);
	  
	  ApplyFunction sw = new ApplyFunction(Strings.startsWith);
	  Lists.Unpack unpack = new Lists.Unpack();
	  Filter filter = new Filter();
	  ApplyFunction not = new ApplyFunction(Booleans.not);
 
	  Connector.connect(source, split);
	  Connector.connect(split, 0, unpack, 0);
	  Connector.connect(unpack, fork);
	  Connector.connect(fork, 0, sw, 0);
	  Connector.connect(a, 0, sw, 1);
	  Connector.connect(sw, not);
	  Connector.connect(not, 0, filter, 1);
	  Connector.connect(fork, 1, filter, 0);
	  
	  
	  Pullable p = filter.getPullableOutput();
	  
	  for (int i = 0; i < 3; i++)
	  {
		  System.out.println(p.pull());
	  }
  }
  static void exo3_5()
  {
	  QueueSource source = new QueueSource().setEvents('a','b','a','c','c','b','a');
	  
	  Function slicing_fct = new IdentityFunction(1);
	  GroupProcessor counter = new GroupProcessor(1, 1);
	  {
	      TurnInto to_one = new TurnInto(1);
	      Cumulate sum = new Cumulate(
	          new CumulativeFunction<Number>(Numbers.addition));
	      Connector.connect(to_one, sum);
	      counter.addProcessors(to_one, sum);
	      counter.associateInput(0, to_one, 0);
	      counter.associateOutput(0, sum, 0);
	  }
	  Slice slicer = new Slice(slicing_fct, counter);
	  Connector.connect(source, slicer);
	  
	  ApplyFunction vals = new ApplyFunction(Maps.values);
	  
	  Connector.connect(slicer, vals);
	  Cumulate max = new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
	  Bags.RunOn ro = new Bags.RunOn(max);

	  Connector.connect(vals, ro);
	  
	  Pullable p = ro.getPullableOutput();
	  for (int i = 0; i < 7; i++)
	  {
	      Object o = p.pull();
	      System.out.println(o);
	  }
  }
  static void exo3_6() throws ClassNotFoundException
  {
      try 
      {
         Class cls = Class.forName("beepbeeptests.main");

         // returns the ClassLoader object associated with this Class
         ClassLoader cLoader = cls.getClassLoader();
         
         // input stream
         InputStream i = cLoader.getResourceAsStream("testweb.html");
         ReadLines reader = new ReadLines(i);
         FindPattern pat = new FindPattern("^.*html.*$");
         Cumulate sum = new Cumulate(
        		    new CumulativeFunction<Number>(Numbers.addition));
         TurnInto to_one = new TurnInto("1");
         ApplyFunction to_number = new ApplyFunction(Numbers.numberCast);
         KeepLast kl = new KeepLast();
         
         QueueSource source = new QueueSource().setEvents(1);
         
         Connector.connect(reader, pat);
         Connector.connect(pat, to_one);
         Connector.connect(to_one, to_number);
         Connector.connect(to_number, sum);
         Connector.connect(sum, kl);
         
         Pullable p = kl.getPullableOutput();

         
         while (p.hasNext())
         {
             System.out.println(p.next());
         }
         
         i.close();
      } 
      catch(Exception e) 
      {
         System.out.println(e);
      }
  }
}
