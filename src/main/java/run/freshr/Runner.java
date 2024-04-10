package run.freshr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Runner implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) {
    log.info("-------------------------------------------------------------------");
    log.info(" _______ .______       _______     _______. __    __  .______");
    log.info("|   ____||   _  \\     |   ____|   /       ||  |  |  | |   _  \\");
    log.info("|  |__   |  |_)  |    |  |__     |   (----`|  |__|  | |  |_)  |");
    log.info("|   __|  |      /     |   __|     \\   \\    |   __   | |      /");
    log.info("|  |     |  |\\  \\----.|  |____.----)   |   |  |  |  | |  |\\  \\----.");
    log.info("|__|     | _| `._____||_______|_______/    |__|  |__| | _| `._____|");
    log.info("  ______  __        ______    __    __   _______");
    log.info(" /      ||  |      /  __  \\  |  |  |  | |       \\");
    log.info("|  ,----'|  |     |  |  |  | |  |  |  | |  .--.  |");
    log.info("|  |     |  |     |  |  |  | |  |  |  | |  |  |  |");
    log.info("|  `----.|  `----.|  `--'  | |  `--'  | |  '--'  |");
    log.info(" \\______||_______| \\______/   \\______/  |_______/");
    log.info("  _______      ___   .___________. ___________    __    ____  ___   ____    ____");
    log.info(" /  _____|    /   \\  |           ||   ____\\   \\  /  \\  /   / /   \\  \\   \\  /   /");
    log.info("|  |  __     /  ^  \\ `---|  |----`|  |__   \\   \\/    \\/   / /  ^  \\  \\   \\/   /");
    log.info("|  | |_ |   /  /_\\  \\    |  |     |   __|   \\            / /  /_\\  \\  \\_    _/");
    log.info("|  |__| |  /  _____  \\   |  |     |  |____   \\    /\\    / /  _____  \\   |  |");
    log.info(" \\______| /__/     \\__\\  |__|     |_______|   \\__/  \\__/ /__/     \\__\\  |__|");
    log.info("-------------------------------------------------------------------");
  }

}
