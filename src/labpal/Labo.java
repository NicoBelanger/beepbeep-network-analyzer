package labpal;

import ca.uqac.lif.labpal.Laboratory;

public class Labo extends Laboratory {

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		add(new DadExp());
	}
	
	public static void main(String[] args) {
	    initialize(args, Labo.class);
	  }

}
