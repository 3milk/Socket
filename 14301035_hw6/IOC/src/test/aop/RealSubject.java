package test.aop;


public class RealSubject implements ISubject{

	@Override
	public void printFirstMessage() {
		System.out.println("First Message");
	}

	@Override
	public void printSecondMessage() {
		System.out.println("Second Message");
	}

}
