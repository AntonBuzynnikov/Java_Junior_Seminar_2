import Annotations.*;

public class TestRunnerDemo {

    public static void main(String[] args) {
        TestRunner.run(TestRunnerDemo.class);
    }

    @BeforeEach
    void beforeEach1(){
        System.out.println("beforeEach1");
    }
    @BeforeEach
    void beforeEach2(){
        System.out.println("beforeEach2");
    }
    @Test(order = 10)
    void test1(){
        System.out.println("test1");
    }
    @Test(order = 0)
    void test2(){
        System.out.println("test2");
    }
    @Test(order = 20)
    void test3(){
        System.out.println("test3");
    }
    @BeforeEach
    void beforeEach3(){
        System.out.println("beforeEach2");
    }
    @AfterEach
    void afterEach1(){
        System.out.println("afterEach1");
    }
    @AfterEach
    void afterEach2(){
        System.out.println("afterEach2");
    }
    @AfterAll
    void afterAll1(){
        System.out.println("afterAll1");
    }
    @AfterAll
    void afterAll2(){
        System.out.println("afterAll2");
    }
    @BeforeAll
    void beforeAll1(){
        System.out.println("beforeAll1");
    }
    @BeforeAll
    void beforeAll2(){
        System.out.println("beforeAll2");
    }
}
