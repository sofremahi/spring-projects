package flux;

import com.github.javafaker.Faker;
import reactor.core.publisher.Flux;

public class AsyncFluxSink {
    public static void main(String[] args) {
        Flux.just(1, 2, 3)
                .repeat(3)
                .subscribe(System.out::println);
        Flux.generate(
                (objectSynchronousSink) -> {
                    var obj = Faker.instance().overwatch().hero();
                    objectSynchronousSink.next(obj);
                    if (obj.equalsIgnoreCase("moira")){
                        objectSynchronousSink.complete();
                    }

                }).subscribe(System.out::println);

    }
}
