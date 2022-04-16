# 3. 컴포넌트 스캔

## 컴포넌트 스캔이란?
- 스프링이 직접 클래스를 검색해서 빈으로 등록해주는 기능
- @Configuration을 붙인 클래스에 Bean 생성 메서드를 작성하지 않아도 되기에 코드 양을 크게 줄일 수 있다.
- 빈 등록을 원하는 클래스 위에 @Component을 붙이면 끝. 애노테이션에 값을 붙여 빈 이름을 수동 설정할 수도 있다.   

   
### `스캔 범위 설정하기`
``` java
@Configuration
@ComponentScan(basePackages = {"spring"}, 
	excludeFilters = { 
			@Filter(type = FilterType.ANNOTATION, classes = ManualBean.class )			
})
public class AppCtxWithExclude {

}
```
- 설정 정보 클래스에 @ComponentScan을 붙이면 컴포넌트 스캔을 수행한다.
- basePackeges에 설정한 패키지 하위의 모든 파일을 스캔한다.
- 단, excludeFilters를 활용해 특정 클래스를 스캔 대상에서 제외할 수 있다.

### `기본 스캔 대상`
- 아래의 애너테이션이 붙은 클래스는 컴포넌트 스캔의 대상이 된다.
  - @Component
  - @Controller
  - @Service
  - @Repository
  - @Aspect
  - @Configuration

### `메서드를 활용한 수동 등록 vs 컴포넌트 스캔을 통한 자동 등록`
- 수동 등록한 빈이 우선권을 가진다.
- 단, 같은 클래스라도 메서드 혹은 @Component(이름)을 다르게 등록한다면 각각의 Bean이 생성된다.