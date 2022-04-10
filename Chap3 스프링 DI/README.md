# 2. 스프링 DI(Dependency Injection)

- 의존이란 무엇인가?
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


- 의존 주입
  - MemberRegisterService 클래스 코드를 보면 클래스 내부에서 MemberDao 객체를 생성한다.
  - 하지만 이러한 내부 의존 주입은 유지 보수 관점에서 문제점을 유발할 수 있다.
    - 어떤 문제점?
    - 만약 의존 속성의 구현체를 변경하게 된다면, 모든 클래스 코드를 일일이 수정해줘야 한다.
  - 
