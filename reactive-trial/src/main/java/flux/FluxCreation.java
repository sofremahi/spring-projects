package flux;


import com.github.javafaker.Faker;
import reactor.core.publisher.Flux;

public class FluxCreation {
    public static void main(String[] args) throws InterruptedException {


        Flux.range(1, 10)
                .map(i -> i != 7 ? i * 10 : new RuntimeException())
                .doOnComplete(() -> System.out.println("completed"))
                .doOnError((i) -> System.out.println("error" + i))
                .doOnNext((i) -> System.out.println("next" + i))
                .onErrorContinue(RuntimeException.class, (ex, obj) ->

                        System.out.println("error" + obj +ex)
                )
                .onErrorReturn(-5).subscribe();


        var flux = Flux.create(fluxSink -> {
            for (var i = 0; i < 10; i++) {
                fluxSink.next(Faker.instance().overwatch().hero());
            }
            fluxSink.complete();
        });
        flux.subscribe(
                System.out::println,
                (i) -> System.out.println("error"),
                () -> System.out.println("done")
        );
        var refactor = new Refactor();
        var fluxRefactor = Flux.create(refactor);
        fluxRefactor.subscribe(
//                System.out::println,
//                (i) -> System.out.println("error"),
//                () -> System.out.println("done")
        );
        for (var i = 0; i < 10; i++) {
            refactor.generate();
        }
        Thread.sleep(200);
    }
}
