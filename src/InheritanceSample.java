class Animal{
    int height;
    int weight;
    int age;
    String type = "Animal";
    void move(){}
    void getType() {System.out.println("this type is " + this.type);}
} // end of class Animal

class Dog extends Animal{
    String hair;
    String type = "Dog";
    void bark(){}
    void getType() { System.out.println("this type is " + this.type); }
    // Animal also has getType(), override here
    void getSuperType() { System.out.println("super type is " + super.type); }
} // end of class Dog

class Cat extends Animal{
    String type = "Cat";
    String name;
    int age;
    static int totalCount = 0;
    Cat(){
        name = "untitled";
        age = -1;  // 使用-1來標記沒有被設定，否則會初始化為0，但貓有可能0歲
        totalCount++;
    }
    Cat(String str){
        this(); // this(.) 建構子只能放在第一行！！！
        this.name = str;
    }
    Cat(String str,int a){
        this(str);
        this.age = a;
    }
    void meow(){}
    void getType() { System.out.println("this type is " + this.type); }
    // Animal also has getType(), override here
    void getSuperType() { System.out.println("super type is " + super.type); }
    void printInfo(){ System.out.println(name+" 年齡："+age+" 目前總貓數："+totalCount); }
}

public class InheritanceSample {
    public static void main(String[] args) {
        System.out.println("InheritanceSample!");
        Dog dog = new Dog();
        dog.getType(); // this type is Dog
        dog.getSuperType(); // super type is Animal

        // 用Cat來示範用this()來延伸constructor
        Cat c1 = new Cat();
        c1.printInfo();
        Cat c2 = new Cat("小木");
        c2.printInfo();
        Cat c3 = new Cat("小婷",18);
        c3.printInfo();
        // untitled 年齡：-1 目前總貓數：1
        //小木 年齡：-1 目前總貓數：2
        //小婷 年齡：18 目前總貓數：3
    }
}
