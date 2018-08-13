**Solution to Ripple Java Technical Challenge**

Install project based on maven pom.xml file located in 'money-transfer' directory.
You can control logs by editing levels and packages in application.properties file.
You can also edit configurations located in same file.


Step for running project:
1. Open a terminal and go to project directory where pom.xml is located and run this command for generating artifacts.

    Host-VK:money-transfer vkoya$ pwd
    /Users/vkoya/money-transfer
    Host-VK:money-transfer vkoya$ mvn clean install

    Running 'mvn clean install' would also run unit and integration tests in additional generating artifacts.
    
2. If you would like to run tests in command line interface, follow further steps.
    Open another terminal and go to 'money-transfer' directory run application at port 8080/Alice
    
    Host-VK:money-transfer vkoya$ ./mvnw.sh spring-boot:run

3. Open another terminal and go to 'money-transfer' directory and run services at port 8082/Bob.

    Host-VK:money-transfer vkoya$ pwd
    /Users/vkoya/money-transfer
    Host-VK:money-transfer vkoya$ ./mvnw.sh spring-boot:run -Dserver.port=8082

4. Open another terminal and go to 'money-transfer' directory and run test. You could also use either Postman or Advanced REST Client.
    MoneyTransferRequestCommandLine class doesn't end with 'Test' so surefire maven plugin won't pick this test while running 'install'.
    Make sure to stop applications running at both ports in case interested in running the integration test.
    
    Host-VK:money-transfer vkoya$ mvn -Dtest=MoneyTransferRequestCommandLine test

Example logs from servers running at ports 8080 and 8082:
2018-08-13 12:54:08.138  INFO 44881 --- [           main] c.t.s.m.TrustlineApplication             : Starting TrustlineApplication on Host-VK with PID 44881 (/Users/vkoya/money-transfer/target/classes started by vkoya in /Users/vkoya/money-transfer)
2018-08-13 12:54:08.142  INFO 44881 --- [           main] c.t.s.m.TrustlineApplication             : No active profile set, falling back to default profiles: default
2018-08-13 12:54:10.860  INFO 44881 --- [           main] c.t.s.m.TrustlineApplication             : Started TrustlineApplication in 3.046 seconds (JVM running for 6.096)
2018-08-13 12:54:10.864  INFO 44881 --- [           main] c.t.s.m.TrustlineApplication             : Welcome to the Trustline
2018-08-13 12:54:10.864  INFO 44881 --- [           main] c.t.s.m.TrustlineApplication             : Trustline balance is: 0
2018-08-13 12:54:26.261  INFO 44881 --- [nio-8080-exec-1] c.t.s.m.service.MoneyTransferService     : Paying 10.0 to Bob...
2018-08-13 12:54:26.564  INFO 44881 --- [nio-8080-exec-1] c.t.s.m.service.MoneyTransferService     : Trustline balance is: -10.0
2018-08-13 12:54:26.658  INFO 44881 --- [nio-8080-exec-2] c.t.s.m.service.MoneyTransferService     : You were paid 60.0!
2018-08-13 12:54:26.658  INFO 44881 --- [nio-8080-exec-2] c.t.s.m.service.MoneyTransferService     : Trustline balance is: 50.0


2018-08-13 12:54:12.322  INFO 44887 --- [           main] c.t.s.m.TrustlineApplication             : Starting TrustlineApplication on Host-VK with PID 44887 (/Users/vkoya/money-transfer/target/classes started by vkoya in /Users/vkoya/money-transfer)
2018-08-13 12:54:12.325  INFO 44887 --- [           main] c.t.s.m.TrustlineApplication             : No active profile set, falling back to default profiles: default
2018-08-13 12:54:14.234  INFO 44887 --- [           main] c.t.s.m.TrustlineApplication             : Started TrustlineApplication in 2.226 seconds (JVM running for 6.072)
2018-08-13 12:54:14.237  INFO 44887 --- [           main] c.t.s.m.TrustlineApplication             : Welcome to the Trustline
2018-08-13 12:54:14.238  INFO 44887 --- [           main] c.t.s.m.TrustlineApplication             : Trustline balance is: 0
2018-08-13 12:54:26.532  INFO 44887 --- [nio-8082-exec-1] c.t.s.m.service.MoneyTransferService     : You were paid 10.0!
2018-08-13 12:54:26.533  INFO 44887 --- [nio-8082-exec-1] c.t.s.m.service.MoneyTransferService     : Trustline balance is: 10.0
2018-08-13 12:54:26.610  INFO 44887 --- [nio-8082-exec-2] c.t.s.m.service.MoneyTransferService     : Paying 60.0 to Alice...
2018-08-13 12:54:26.667  INFO 44887 --- [nio-8082-exec-2] c.t.s.m.service.MoneyTransferService     : Trustline balance is: -50.0