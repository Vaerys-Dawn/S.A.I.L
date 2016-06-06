import java.lang.annotation.*;

/**
 * Created by Vaerys on 04/06/2016.
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAnnotation {
    String name();
    String channel();
    String description();
}
