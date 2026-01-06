package fm.example.personal_finance_manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;


@SpringBootTest
class PersonalFinanceManagerApplicationTests {
	@Bean
	public void debugSecurity() {
		System.out.println(">>> SECURITY CONFIG APPLIED - CSRF DISABLED <<<");
	}
	@Test
	void contextLoads() {
		System.out.println(">>> SECURITY CONFIG LOADED <<<");
	}
}
