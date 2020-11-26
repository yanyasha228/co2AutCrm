package com.co2AutomaticCrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@ProductsSynchronizerImpl.sync() method Sheduled


@SpringBootApplication
public class Co2AutomaticCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(Co2AutomaticCrmApplication.class, args);
    }

//	@Bean("CRunner")
//	public CommandLineRunner testDataProd(UserService userService ,
//										  ProductService productService ,
//										  SalaryIncreaseService<ManagerSalaryIncrease> salaryIncreaseService){
//		return (args -> {
//
////			List<Product> productList = productService.findAll();
////
////			for (Product prodInDB :
////					productList) {
////				prodInDB.increaseAmount(10);
////			}
////
////			productService.save(productList);
////
////			Set<Role> roleSet = new HashSet<>();
////			Set<Role> roleSet1 = new HashSet<>();
////
////			roleSet.add(Role.ADMIN);
////			roleSet.add(Role.MANAGER);
////
////			roleSet1.add(Role.MANAGER);
////
////
////			User user = new Manager();
////			user.setId(1L);
////			user.setPassword("228_Artem_228777");
////			user.setEmail("777frolov@gmail.com");
////			user.setUsername("777frolov@gmail.com");
////
////
////			User user1 = new Manager();
////			user1.setId(6L);
////			user1.setPassword("228_Alex_228777");
////			user1.setEmail("co2shopm@gmail.com");
////			user1.setUsername("co2shopm@gmail.com");
////
////
////			User user2 = new Manager();
////			user2.setId(8L);
////			user2.setPassword("228_Stas_228777");
////			user2.setEmail("stanislavco2@gmail.com");
////			user2.setUsername("stanislavco2@gmail.com");
////
////
////
////			userService.saveUserWithRoles(user,roleSet);
////			userService.saveUserWithRoles(user1,roleSet1);
////			userService.saveUserWithRoles(user2,roleSet1);
//
//
//		});
//	}
}
