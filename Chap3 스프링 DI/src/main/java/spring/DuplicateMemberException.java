package spring;

public class DuplicateMemberException extends RuntimeException{

    public DuplicateMemberException(String message){
        // super는 부모 클래스의 다른 생성자를 호출할 떄 사용한다.
        super(message);
    }
}
