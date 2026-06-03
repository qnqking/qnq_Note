/**
 * 重写（Override）vs 重载（Overload）对比演示
 * 直接复制到 IDEA 中运行即可
 */
public class OverrideOverloadDemo {

    // ==================== 第一部分：重载（Overload）====================
    // 规则：同一类中，方法名相同，参数列表不同（个数/类型/顺序）
    // 与返回值类型、访问修饰符无关

    /**
     * 计算最大值 —— 重载演示
     */
    static class Calculator {

        // 两个 int
        public int max(int a, int b) {
            System.out.println("调用 max(int, int)");
            return a > b ? a : b;
        }

        // 三个 int（参数个数不同）
        public int max(int a, int b, int c) {
            System.out.println("调用 max(int, int, int)");
            return max(max(a, b), c);
        }

        // 两个 double（参数类型不同）
        public double max(double a, double b) {
            System.out.println("调用 max(double, double)");
            return a > b ? a : b;
        }

        // 参数顺序不同（int, double）vs（double, int）
        public double max(int a, double b) {
            System.out.println("调用 max(int, double)");
            return a > b ? a : b;
        }

        public double max(double a, int b) {
            System.out.println("调用 max(double, int)");
            return a > b ? a : b;
        }
    }

    // ==================== 第二部分：重写（Override）====================
    // 规则：子类重新编写父类的方法
    // 要求：方法名、参数列表、返回值类型必须相同（或协变）

    /**
     * 父类：动物
     */
    static class Animal {
        public void sound() {
            System.out.println("动物在叫...");
        }

        public void eat() {
            System.out.println("动物在吃东西...");
        }

        // 这个方法不让子类重写（final）
        public final void breathe() {
            System.out.println("呼吸中...");
        }
    }

    /**
     * 子类：狗
     */
    static class Dog extends Animal {
        // 重写 sound()
        @Override
        public void sound() {
            System.out.println("汪汪汪！");
        }

        // 重写 eat()
        @Override
        public void eat() {
            System.out.println("狗在啃骨头");
        }

        // ❌ 不能重写 final 方法
        // public void breathe() { }  // 编译错误！
    }

    /**
     * 子类：猫
     */
    static class Cat extends Animal {
        @Override
        public void sound() {
            System.out.println("喵喵喵~");
        }

        @Override
        public void eat() {
            System.out.println("猫在吃鱼");
        }
    }

    // ==================== main ====================

    public static void main(String[] args) {
        System.out.println("========== 1. 重载演示 ==========");
        Calculator calc = new Calculator();

        // 编译器根据参数自动匹配调用哪个方法
        System.out.println("结果: " + calc.max(3, 8));             // max(int, int)
        System.out.println("结果: " + calc.max(1, 5, 9));          // max(int, int, int)
        System.out.println("结果: " + calc.max(3.5, 2.8));         // max(double, double)
        System.out.println("结果: " + calc.max(3, 5.5));           // max(int, double)
        System.out.println("结果: " + calc.max(5.5, 3));           // max(double, int)
        System.out.println();  // ↑ 编译时就能确定调用哪个 → 编译时多态

        System.out.println("========== 2. 重写演示 ==========");

        Animal a1 = new Dog();
        Animal a2 = new Cat();

        // 同一句代码，不同对象不同行为 → 运行时多态
        a1.sound();   // 汪汪汪！（编译看左边 Animal，运行看右边 Dog）
        a1.eat();     // 狗在啃骨头

        a2.sound();   // 喵喵喵~（编译看左边 Animal，运行看右边 Cat）
        a2.eat();     // 猫在吃鱼

        System.out.println();

        // 多态集合
        System.out.println("--- 多态集合遍历 ---");
        Animal[] zoo = {new Dog(), new Cat(), new Dog()};
        for (Animal a : zoo) {
            a.sound();   // 运行时决定到底是谁在叫
        }

        System.out.println();

        // 多态参数
        System.out.println("--- 多态参数 ---");
        makeItSound(new Dog());
        makeItSound(new Cat());
    }

    // 参数是父类型，什么动物都能传
    public static void makeItSound(Animal a) {
        System.out.print("传入一只动物 → ");
        a.sound();
    }
}
