# 9. 스프링 MVC 프레임워크 동작 방식 



### (1) 스프링 MVC 핵심 구성 요소

![](image/스프링 MVC.png)

- 요청부터 응답 사이의 과정에 대한 설명
  - 웹 브라우저로부터 요청이 들어오면 DispatcherServlet은 요청에 해당하는 Controller 검색을 HandlerMapping이라는 빈 객체에 요청한다.
  - D-S은 HandlerMapping에서 반환 받은 Controller 객체의 로직 처리를 HandlerAdapter에 위임하고 로직 수행의 결과로 얻은 ModelAndView를 리턴 받는다.
  - D-S는 ModelAndView의 결과를 보여주기 위한 View를 찾기 위해 ViewResolver에 검색을 요청한다.
  - 검색 결과로 ViewResolver가 생성한 View 객체에 응답 결과 생성을 요청하고 JSP를 싱행하여 응답 결과를 생성한다.
  - 최종적으로 생성된 View를 웹 브라우저에 리턴한다.





### (2) DispatcherServlet과 스프링 컨테이너

- 앞서 언급한 HandlerMapping, HandlerAdapter, 컨트롤러, View Resolver 등의 빈은 DispatcherServlet이 생성한 스프링 컨테이너에서 구한다.
- 이때 스프링 컨테이너 또한 DispatcherServlet이 전달받은 설정 파일을 이용해 생성한다.





### (3) @Controller를 위한 HandlerMapping과 HandlerAdapter

- DispatcherServlet은 요청을 처리하기 위해 Controller를 찾고 로직을 수행해야 한다.
- 때문에 컨테이너 내에 알맞은 HandlerMapping/Adapter 객체가 있어야 한다.
- 수동으로 빈을 등록할 수도 있지만 아래의 어노테이션을 사용하면 보다 간편하게 빈을 등록할 수 있다.



### @EnableWebMvc

- 매우 다양한 빈을 자동으로 추가해준다.
- DispatcherServlet이 @Controller 타입을 다루기 위한 HandlerMapping, HandlerAdapter도 포함되어 있다.





### (4) WebMvcConfigurer 인터페이스와 설정

```java
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

   @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
      configurer.enable();
   }

   @Override
   public void configureViewResolvers(ViewResolverRegistry registry) {
      registry.jsp("/WEB-INF/view/", ".jsp");
   }

}
```

- 설정을 위한 클래스는 WebMvcConfigurer 인터페이스를 구현한다.
- @Configuration을 사용했기에 MvcConfig 클래스는 WebMvcConfigurer 타입 빈으로 컨테이너에 등록된다.
- @EnableWebMvc를 사용하면 WebMvcConfigurer 타입 빈의 메서드를 수행해 MVC 설정을 추가한다.
- 기본적인 설정 정보는 WebMvcConfigurer 인터페이스에 default 메서드로 등록되어 있어 자동으로 추가된다.
- 단, 설정을 변경하고 싶을 때 메서드를 재정의 하는 방식으로 설정을 변경할 수 있다.

