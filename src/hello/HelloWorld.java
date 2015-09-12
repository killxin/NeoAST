package hello;

import java.util.ArrayList;
import java.util.List;

public class HelloWorld {
	private List<String> list = new ArrayList<String>();

	public void addlist(String str) {
		list.add(str);
	}

	public String gettop() {
		return list.get(0);
	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		HelloWorld hw = new HelloWorld();
		hw.addlist("hello world!");
		System.out.println(hw.gettop());
		{
			int x = 3;
			while ((boolean) (x == 4)) {
				;
			}
		}
		L2: {
			for (int i = 0; i < 3; i++) {
				System.out.println();
			}
		}
		int x = 3;
		while ((boolean) (x == 4)) {
			;
		}
		do {
			int a = 1;
		} while (true);
	}
}
