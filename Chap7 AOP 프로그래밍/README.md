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
- 위 예시 코드를 보면 mesure 메서드에 매개변수로 ProceedingJoinPoint를 받는다.
- Around Advice의 경우 공통 로직이 핵심 로직 전, 후, 예외 상황 모두에 들어가야 한다.
- 때문에 핵심 로직을 수행할 수 있는 ProceedingJoinPoint를 매개변수로 받아 핵심 로직을 수행할 때 proceed() 메서드를 통해 수행한다.
- Before Advice의 경우 핵심 로직 이전에 공통 로직이 수행되기 때문에 따로 ProceedingJoinPoint를 매개변수로 받을 필요가 없다.

### `프록시 생성 방식, 인터페이스 상속 VS 클래스 상속`
``` java
@Configuration
@EnableAspectJAutoProxy
public class AppCtx {
	@Bean
	public ExeTimeAspect exeTimeAspect() {
		return new ExeTimeAspect();
	}

	@Bean
	public Calculator calculator() {
		return new RecCalculator();
	}
}
```
- 위의 calculator Bean은 RecCalculator 클래스를 반환한다.
  - 하지만 RecCalculator는 AOP가 적용되어 있다.
  - 때문에 getBean 메서드로 calculator Bean을 꺼내어 확인해보면 $Proxy 클래스인 것을 확인할 수 있다.
- 이때 Proxy 클래스가 생성되는 과정에 주목해봐야 한다.
  - RecCalculator 클래스는 Calculator 인터페이스를 상속받아 구현됐다.
  - 때문에 Proxy 또한 Calculator 인터페이스를 상속받아 구현된다.
  - 확인을 위해 Calculator cal = ctx.getBean("calculator", RecCalculator.class)를 수행해보면 예외가 발생한다.
    - 이유는 calculator Bean은 Proxy 객체이고, Proxy 객체는 Calculator 인터페이스를 상속하여 Calculator 타입으로는 가져올 수 있지만
    - Calculator의 구현체인 RecCalculator 타입으로는 가져올 수 없기 떄문이다.
- `이와 같이 인터페이스를 상속 받아 구현된 클래스에 AOP를 적용할 때, Proxy가 인터페이스를 상속받는 것이 아닌 해당 클래스를 상속받아 구현하고 싶다면 @EnableAspectJAutoProxy(proxyTargetClass = true)를 사용하면 된다.`


### `한 Pointcut에 여러 Advice 적용시 순서의 중요성`
``` java
public class RecCalculator implements Calculator {

	@Override
	public long factorial(long num) {
        if (num == 0)
            return 1;
        else
            return num * factorial(num - 1);
	}
}
```
``` java
@Aspect
@Order(1)
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
``` java
@Aspect
// @Order(2)
public class CacheAspect {

	private Map<Long, Object> cache = new HashMap<>();

	@Pointcut("execution(public * chap07..*(long))")
	public void cacheTarget() {
	}
	
	@Around("cacheTarget()")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		Long num = (Long) joinPoint.getArgs()[0];
		if (cache.containsKey(num)) {
			System.out.printf("CacheAspect: Cache에서 구함[%d]\n", num);
			return cache.get(num);
		}

		Object result = joinPoint.proceed();
		cache.put(num, result);
		System.out.printf("CacheAspect: Cache에 추가[%d]\n", num);
		return result;
	}

}
```
- 위 코드를 보면 factorial 메서드에 mesure와 excute 로직이 모두 Around Advice 시점으로 적용되는 것을 확인할 수 있다.
- 이 경우에 Calculator 객체를 받아와 factorial을 실행하면 어떤 방식으로 동작하게 될까.
  - 기본적으로 정확환 순서가 없다. Spring Framework나 Java 버전에 따라 순서는 변한다.
- 때문에 동작 순서가 중요할 경우 @Order('순번')을 통해 실행 순서를 명시해주어야 한다.  

  
### `@Pointcut과 @Around의 분리를 통한 @Pointcut 재사용`