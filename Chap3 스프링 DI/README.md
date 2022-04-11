# 2. 스프링 DI(Dependency Injection)

## Dependency(의존)란 무엇인가?
  - ```java 
      public class MemberRegisterService {
        
        private MemberDao memberDao = new MemberDao();
    
        public void regist(RegisterRequest req){
        
          Member member = memberDao.selectByEmail(req.getEmail());
          
          if(member == null) memberDao.insert(new Member());
        }  
      }
    ```
  - MemberRegisterService 클래스를 보면 속성으로 memberDao를 가지고 있다.
  - 또한 memberDao를 활용해 로직을 구현하고 있다.
  - 이처럼 어떤 클래스가 다른 클래스를 속성으로 가지고 있고, 이를 활용할 때 "의존한다"는 표현을 사용한다.  


## Dependency Injection(의존 주입)이란 무엇인가?
  - MemberRegisterService 클래스 코드를 보면 클래스 내부에서 MemberDao 객체를 생성한다.
  - 이처럼 의존 객체를 생성하는 작업을 의존 주입이라 한다.
  - 하지만 위 코드와 같은 내부 의존 주입은 유지 보수 관점에서 문제점을 유발할 수 있다.
    - 어떤 문제점?
    - 만약 의존 속성의 구현체를 변경하게 된다면, 모든 클래스 코드를 일일이 수정해줘야 한다.
  - 내부 구현보다 조금 더 개선된 방법으로는 생성자 주입과 setter 주입이 있다.
  - ```java 
      // 생성자 주입
      public class MemberRegisterService {
        
        private MemberDao memberDao;
    
        public MemberRegisterService(MemberDao memberDao){
          this.memberDao = memberDao;
        }
    
        public void regist(RegisterRequest req){
        
          Member member = memberDao.selectByEmail(req.getEmail());
          
          if(member == null) memberDao.insert(new Member());
        }  
      }
    ```
  - ```java 
      // setter 주입
      public class MemberRegisterService {
        
        private MemberDao memberDao;
    
        public void regist(RegisterRequest req){
        
          Member member = memberDao.selectByEmail(req.getEmail());
          
          if(member == null) memberDao.insert(new Member());
        }
    
        public void setMemberDao(MemberDao memberDao){
          this.memberDao = memberDao;
        }  
      }
    ```
  - 위와 같이 코드를 구현하면 생성자 혹은 setter의 매개변수로 입력하는 객체만 수정하면 되기 때문에 내부 주입에 비해서는 유지 보수 관점에서 개선된 코드라 할 수 있다. 
  - 생성자 방식의 장점
    - 빈 객체를 생성하는 시점에 모든 의존 객체가 주입된다.
  - setter 방식의 장점
    - setter메서드 이름을 통해 어떤 의존 객체가 주입되는지 알 수 있다.
  - 위 장단점을 바탕으로 상황에 맞게 활용하면 된다.  
