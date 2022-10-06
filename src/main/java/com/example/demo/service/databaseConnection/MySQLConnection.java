//package com.example.demo.service.databaseConnection;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class MySQLConnection implements CommandLineRunner{
//	
//	private JdbcTemplate jdbcTemplate;
//	
//	
//	@GetMapping("/test")
//	@Override
//	public void run(String... args) throws Exception {
//		String sql = "select * from login_accounts";
//        
//        int result = jdbcTemplate.getFetchSize();
//         
//        if (result > 0) {
//            System.out.println("A new row has been inserted.");
//        }
//        else {
//			System.out.println("Nothing");
//		}
//	}
//	
//}
