package xxkuba3.lang;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class LangService {
    private LangRepository repository;

    public LangService(){
        this(new LangRepository());
    }

    public LangService(LangRepository repository){
        this.repository = repository;
    }

    public List<LangDTO> findAll(){
        return repository
                .findAll()
                .stream()
                .map(LangDTO::new)
                .collect(toList());
    }
}


