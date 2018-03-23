/*
Design / Overall Layout:

web:/   [btn:compose] [list of all messages]
    security: login NOT required
web:/newMessage     
    [btn:messages] [btn:logout] 
    Message (textarea)
    security: requires login  

done: write security classes & authentication
done: write properties file for db
tbw: put bootstrap and format pages
 */

package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootBullHornApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBullHornApplication.class, args);
	}
}
