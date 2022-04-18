# 6. AOP 프로그래밍

## AOP란?
- AOP란 핵심 기능과 공통 기능의 구현을 분리함으로써 핵심 기능을 구현한 코드의 수정 없이 공통 기능을 적용할 수 있게 만드는 방법이다.

``` java
public class ExeTimeCalculator implements Calculator {

	private Calculator delegate;

	public ExeTimeCalculator(Calculator delegate) {
        this.delegate = delegate;
    }

	@Override
	public long factorial(long num) {
		long start = System.nanoTime();
		long result = delegate.factorial(num);
		long end = System.nanoTime();
		System.out.printf("%s.factorial(%d) 실행 시간 = %d\n",
				delegate.getClass().getSimpleName(),
				num, (end - start));
		return result;
	}
}
```
- 위 코드는 프록시 객체를 이용한 코드이다.
- factorial 값과 연산 시간을 모두 구하고 싶을 때 factorial연산을 수행하는 객체와 연산 시간을 구하는 객체를 구분한다.
- 즉, 연산 시간을 구하는 공통 기능과 연산을 진행하는 핵심 기능으로 분리.
  - 이러한 기능 구분을 통해 핵심 기능의 코드 수정 없이 공통 기능을 적용할 수 있게 된다.
- AOP 또한 예시와 같다. 핵심 기능과 공통 기능을 분리하여 핵심 기능의 수정 없이 공통 기능을 적용할 수 있게 만든다.
  - 실제로 스프링 또한 프록시를 이용해 AOP를 구현하고 있다.  

### `@Aspect`

### `@Pointcut`

### `@Around`

### `ProceedingJoinPoint`

### `@Aspect`

### `프록시 생성 방식, 인터페이스 상속 VS 클래스 상속`

### `excution 명시자`

### `한 Pointcut에 여러 Advice 적용시 순서의 중요성`

### `@Pointcut과 @Around의 분리를 통한 @Pointcut 재사용`