import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import config.AppConf1;
import config.AppConf2;

public class MainForConfigurationType {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConf1.class, AppConf2.class);
        ctx.close();
    }

}