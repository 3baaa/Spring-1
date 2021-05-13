package chap07;

public class ExeTimeCalculator implements Calculator{
	
	private Calculator delegate;
	
	public ExeTimeCalculator(Calculator delegage) {
		this.delegate=delegage;
	}

	@Override
	public long factorial(long num) {
		long start = System.nanoTime();
		long result = delegate.factorial(num);
		long end = System.nanoTime();
		System.out.printf("%s.factorial(%d) 실행 시간 = %d\n",delegate.getClass().getSimpleName(),num,(end-start));
		return result;
	}
	
	
}
