import org.junit.Test;
import xxkuba3.hello.HelloService;
import xxkuba3.lang.Lang;
import xxkuba3.lang.LangRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


public class HelloServiceTest {
   private static final String WELCOME = "Hello";
    private static final String FALLBACK_ID_WELCOME = "Hola";

    @Test
    public void test_prepareGreeting_nullName_returnGreetingWithFallbackName() {
        // given
        var mockRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService(mockRepository);

        // when
        var result = SUT.prepareGreetings(null, -1);

        //then
        assertEquals(WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_prepareGreeting_name_returnGreetingWithName() {
        // given
        var mockRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService();
        String name = "test";

        // when
        var result = SUT.prepareGreetings(name,-1);

        //then
        assertEquals(WELCOME + " " + name + "!", result);
    }

    @Test
    public void test_prepareGreeting_name_returnGreetingWithFallbackIdLang() {
        // given
        var mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository);

        // when
        var result = SUT.prepareGreetings(null,null);

        //then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

//    @Test
//    public void test_prepareGreeting_textLang_returnGreetingWithFallbackIdLang() {
//        // given
//        var mockRepository = fallbackLangIdRepository();
//        var SUT = new HelloService(mockRepository);
//
//        // when
//        var result = SUT.prepareGreetings(null,"abc");
//
//        //then
//        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
//    }

    @Test
    public void test_prepareGreeting_nonExistingLang_returnsGreetingWithFallbackLang() {
        // given
        var mockRepository = new LangRepository(){
            @Override
            public Optional<Lang> findById(Integer id) {
              return Optional.empty();
            }
        };
        var SUT = new HelloService(mockRepository);

        // when
        var result = SUT.prepareGreetings(null,-1);

        //then
        //assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
        assertEquals(HelloService.FALLBACK_LANG.getWelcomeMsg() + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    private LangRepository fallbackLangIdRepository() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                if (id.equals(HelloService.FALLBACK_LANG.getId())) {
                    return Optional.of(new Lang(null, FALLBACK_ID_WELCOME, null));
                }
                return Optional.empty();
            }
        };
    }

    private LangRepository  alwaysReturningHelloRepository(){
        return new LangRepository(){
        @Override
        public Optional<Lang> findById(Integer id){
            return Optional.of(new Lang(null, WELCOME,null));
        }};
    }
}
