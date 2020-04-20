package ru.geekbrains.home_level2_lesson2;

public class Main {
    static final int size = 10_000_000;
    static final int h = size / 2;

    // Метод заполняет массив "1" или по формуле. Возвращает время заполнения в миллисекундах
    private static long fillArrayNumber (int size, float [] arr, int flag) {
        long startTime, endTime;
        if (flag == 1) {
            startTime = System.currentTimeMillis();
            for (int i = 0; i < size; i++) arr[i] = 1;
            endTime = System.currentTimeMillis();
        }
          else {
            startTime = System.currentTimeMillis();
            for (int i = 0; i < size; i++)
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            endTime = System.currentTimeMillis();
        }

        return endTime-startTime;
    }

    // Метод разделяет исходный массив на 2 массива. Возвращает время заполнения в миллисекундах
    private static long divadedArray(float[] arr, float[] a1, float[] a2) {
        long startTime = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, Main.h);
        System.arraycopy(arr, Main.h, a2, 0, Main.h);
        return System.currentTimeMillis() - startTime;
    }

    // Метод склеивает исходные 2 массива в 1 массив. Возвращает время заполнения в миллисекундах
    private static long stikArray (float[] a1, float [] a2, float [] arr) {
        long startTime = System.currentTimeMillis();
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) throws InterruptedException {

        float[] arr = new float[size];
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        long startTime;

        // Заполняем  массив "1" выводим время
        System.out.println("Время заполнения массива еденицей (msec) " + fillArrayNumber(size,arr,1));

        // Вычисляем эдементы массива, выводим время
        System.out.println("Время вычисления массива простой метод (msec) " + fillArrayNumber(size,arr,2));

        // Заполняем  массив "1" выводим время
       fillArrayNumber(size,arr,1);

        Thread first = new Thread() {
            @Override
            public void run() {
                for (int i =0; i < h; i++)
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                interrupt();
            }
        };
        Thread second = new Thread() {
            @Override
            public void run() {
                for (int i =0; i < h; i++) {
                    a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                interrupt();
            }
        };

        // Разбиваем массив, выводим время
        System.out.println("Время разбивки массива (msec) " + divadedArray(arr,a1,a2));

        // Запускаем потоки
        startTime = System.currentTimeMillis();
        first.start();
        second.start();
        first.join();
        second.join();

        System.out.println("Время вычисления массива потоками (msec) " + (System.currentTimeMillis()-startTime));
        System.out.println("Время склейки массива потоком (msec) " + (stikArray(a1,a2,arr)));


        System.out.println("Проверка элементов массива " + arr[0] +" " +arr[1] + " "+ arr[9_999_998] + " " +arr[9_999_999]);

        //  Решение через отдельный класс

        // Заполняем 1 и разбиваем на 2 массива
        fillArrayNumber(size,arr,1);
        divadedArray(arr,a1,a2);

        CalculateArrayThred t1 = new CalculateArrayThred(0,a1);
        CalculateArrayThred t2 = new CalculateArrayThred(0,a2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // Склеиваем массивы
        stikArray(a1,a2,arr);
        System.out.println("Время расчета потоком 1 " + t1.getTime());
        System.out.println("Время расчета потоком 2 " + t2.getTime());

        System.out.println("Проверка элементов массива " + arr[0] +" " +arr[1] + " "+ arr[9_999_998] + " " +arr[9_999_999]);

    }
}
