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
- factorial 값과 연산 시간을 모두 구하고 싶을 때 factorial 연산을 수행하는 객체와 연산 시간을 구하는 객체를 구분한다.
- 즉, 연산 시간을 구하는 공통 기능과 연산을 진행하는 핵심 기능으로 분리.
  - 이러한 기능 구분을 통해 핵심 기능의 코드 수정 없이 공통 기능을 적용할 수 있게 된다.
- AOP 또한 예시와 같다. 핵심 기능과 공통 기능을 분리하여 핵심 기능의 수정 없이 공통 기능을 적용할 수 있게 만든다.
  - 실제로 스프링 또한 프록시 객체를 이용해 AOP를 구현하고 있다.  

### `@Aspect, @Pointcut 그리고 Advice`

``` java
@Aspect
public class ExeTimeAspect {

	@Pointcut("execution(public * chap07..*(..))")
	private void publicTarget() {
	}

	@Around("publicTarget()")
	public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		try {
			Object result = joinPoint.proceed();
			return result;
		} finally {
			long finish = System.nanoTime();
			Signature sig = joinPoint.getSignature();
			System.out.printf("%s.%s(%s) 실행 시간 : %d ns\n",
					joinPoint.getTarget().getClass().getSimpleName(),
					sig.getName(), Arrays.toString(joinPoint.getArgs()),
					(finish - start));
		}
	}
}
```
- @Aspect
  - 여러 객체에 공통으로 적용할 기능을 Aspect라 하고, 어노테이션을 통해 Aspect를 지정할 수 있다.
  - 위 예시에서 ExeTimeAspect 클래스가 Aspect로 이용될 것이라고 이해할 수 있다.
- @Pointcut
  - 공통 기능이 적용될 위치를 나타낸다.
  - 이때 excution 명시자가 이용된다.
  - 위 코드에서는 chap07 패키지 하위 빈 객체의 public 메서드에 공통 기능을 적용한다고 명시되어 있다.
- Advice
  - 어떤 메서드에 공통 기능을 적용할 건지를 정했다면, 공통 기능이 수행될 타이밍을 조금 더 구체적으로 정해야 한다.
  - 이를 결정하는 것이 Advice이다.
  - 위 코드에서는 Around Advice를 사용하였고. 이는 메서드 실행 전, 후 또는 익셉션 발생 시점에 공통 기능을 실행하게 된다.
  - 이외에도 Before, After Returning, After Throwing, After, Around 등이 있다.
- 위 코드를 요약하자면 chap07 패키지 하위 빈의 public 메서드가 실행되면 mesure 메서드로 대체되어 기존 메서드 로직 전, 후, 예외 발생 시점 등에 공통 기능이 추가된다.

### `ProceedingJoinPoint`

### `프록시 생성 방식, 인터페이스 상속 VS 클래스 상속`

### `excution 명시자`

### `한 Pointcut에 여러 Advice 적용시 순서의 중요성`

### `@Pointcut과 @Around의 분리를 통한 @Pointcut 재사용`