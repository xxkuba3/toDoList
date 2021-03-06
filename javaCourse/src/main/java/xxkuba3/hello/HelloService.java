package xxkuba3.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xxkuba3.lang.Lang;
import xxkuba3.lang.LangRepository;

import java.util.Optional;

public class HelloService {

    public static final String FALLBACK_NAME = "world";
    public static final Lang FALLBACK_LANG = new Lang(1, "Hello", "en");
    private final Logger logger = LoggerFactory.getLogger(HelloService.class);

    private LangRepository repository;

    public HelloService(){
        this(new LangRepository());
    }

    public HelloService(LangRepository repository){
        this.repository = repository;
    }

    public String prepareGreetings(String name, Integer langId) {
        langId = Optional.ofNullable(langId).orElse(FALLBACK_LANG.getId());
        var welcomeMsg = repository.findById(langId).orElse(FALLBACK_LANG).getWelcomeMsg();
        var nameToWelcome = Optional.ofNullable(name).orElse(FALLBACK_NAME);
        return welcomeMsg + " " + nameToWelcome + "!";
    }}

