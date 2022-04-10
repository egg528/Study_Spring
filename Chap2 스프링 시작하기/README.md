# 1. 스프링 시작하기

- 메이븐
  - pom.xml 파일에 의존 설정 시 로컬 레포에서 jar파일 존재 여부를 확인하고 없으면 원격 중앙 레포에서 다운로드 받는다.
  - 특정 컴포넌트를 다운받을 때 해당 컴포넌트가 의존하는 컴포넌트도 연쇄적으로 다운 받는다.  


- 스프링 컨테이너 (ApplicationContext)
  - AnnotationConfigApplicationContext, GenericXmlApplicationContext, GenericGroovyApplicationContext
  - 3개의 클래스는 각각 Java코드, XML, 그루비를 통해 정보를 읽어 객체 생성과 초기화를 수행한다.
  - 즉, ApplicationContext의 구현체이다.
  - 각 클래스는 설정 정보로부터 Bean을 생성/초기화/의존 주입을 하고 내부에 보관한다.  
  

- 싱글톤 객체
  - ```java 
      public class Main {
        public static void main(String[] args) {
          AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
          
          Greeter g1 = ctx.getBean("greeter", Greeter.class);
          Greeter g2 = ctx.getBean("greeter", Greeter.class)
          System.out.println(g1 == g2); // true
          ctx.close();
        }
      }
      ```
  - 위 코드를 시행하면 true가 출력된다. 즉, 스프링 컨테이너는 기본적으로 Bean에 대해 하나의 객체만 생성한다.
  - ```java 
      @Configuration
      public class AppContext {

        @Bean
        public Greeter greeter(){
          Greeter g = new Greeter();
          g.setFormat("%s, 안녕하세요!");
          return g;
        }

        @Bean
        public Greeter greeter1(){
          Greeter g = new Greeter();
          g.setFormat("%s, 안녕하세요! greeter1");
          return g;
        }

      }
      ```
  - @Bean에 대해 하나의 객체를 생성하기에 위와 같이 작성할 경우 2개의 객체가 생성된다.