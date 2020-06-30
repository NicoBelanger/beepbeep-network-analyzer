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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ca.uqac.lif.cep.Context;
import ca.uqac.lif.cep.EventTracker;
import ca.uqac.lif.cep.functions.Function;

public class isPositive extends Function
{

	@Override
	public Function duplicate(boolean arg0) {
		// TODO Auto-generated method stub
		return new isPositive();
	}

	@Override
	public void evaluate(Object[] arg0, Object[] arg1, Context arg2, EventTracker arg3) {
		// TODO Auto-generated method stub
		List list = new ArrayList();;
		List<Integer> input = (List) arg0[0];
		
		for (Integer num : input) 
		{ 
		    if(num >= 0)
		    	list.add(num);
		}
		
		arg1[0] = list;
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
			arg0.add(List.class);
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
	        return List.class;
	    return null;
	}
}