public class Main {
    // 缓冲区最大容量
    private static final int MAX_SIZE = 5;
    // 缓冲区数组
    private static int[] buffer = new int[MAX_SIZE];
    private static int count = 0;

    // 生产者
    static class Producer implements Runnable{
        @Override
        public void run() {
            int item = 1;
            while(true){
                synchronized (buffer){
                    //缓冲区满，等待
                    while(count == MAX_SIZE){
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    buffer[count] = item;
                    System.out.println("生产者生产商品：" + item);
                    count++;
                    item++;
                    buffer.notifyAll();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //消费者
    static class Consumer implements Runnable{
        @Override
        public void run() {
            while(true){
                synchronized (buffer){
                    //缓冲区空，等待
                    while(count == 0){
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int item = buffer[count -1];
                    System.out.println("消费者消费商品：" + item);
                    count--;
                    buffer.notifyAll();
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
    }
}
