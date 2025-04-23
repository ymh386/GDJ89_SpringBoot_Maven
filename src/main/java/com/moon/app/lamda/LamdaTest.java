package com.moon.app.lamda;

import java.util.List;

public class LamdaTest {
	
	public void test2() {
		
		Test3Interface t3 = (List<Object> ar)->{
			System.out.println("test");
		};
		
		t3.t1(null);
		
		//interface에 두개 이상의 메서드가 있는 경우
		//Lamda 사용 X
//		Test2Interface t2 = (int n1)->{
//			
//		};
		
	}
	
	public void test() {
		TestInterface t = new Plus();
		t.cal(10, 20);
		
		t = new Minus();
		t.cal(10, 20);
		
		
		t = (int n1, int n2) -> n1+n2;
		int result =t.cal(10, 20);
		
		t = (int n1, int n2) -> {
			return n1*n2;	
		};
		result = t.cal(1, 2);
		
		t = new TestInterface() {
			
			@Override
			public int cal(int n1, int n2) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		
		t.cal(1, 2);
		
	}

}
