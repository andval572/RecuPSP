import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) { //CREAR HILOS

        for (int i = 1; i <= 20 ; i++) {
            Hilos hilo = new Hilos(0);
            hilo.setName("Huesped numero "+ i );
            hilo.start();
        }

    }

}

class Hilos extends Thread { //CREAR CLASE HILOS FUERA DEL MAIN
    int llegada;
    public Hilos( int llegada) {
    this.llegada=llegada;
    }

    @Override
    public void run() {
        Tomar.solicitarAcceso(this);
    }
}

class Tomar { //CREAR EL PROCESO

    private static final int NUM_ACCESO_SIMULTANEOS = 10;
    private static final int NUM_ACCESO_SIMULTANEOS1 = 4;
    static Semaphore semaphore = new Semaphore(NUM_ACCESO_SIMULTANEOS,true);
    static Semaphore semaphore2 = new Semaphore(NUM_ACCESO_SIMULTANEOS1,true);

    public static void solicitarAcceso(Hilos hilo) {

        try {
            semaphore.acquire(1);
            solicitarCafe(hilo);
            Thread.sleep(3000);
            semaphore.release(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void solicitarCafe(Hilos hilo) {

        System.out.println("El " + hilo.getName() + " se ha sentado en la silla");

        try {
            semaphore2.acquire(1);
            cogerCafe(hilo);
            Thread.sleep(3000);
            semaphore2.release(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void cogerCafe(Hilos hilo) throws InterruptedException {

        System.out.println("El cliente " + hilo.getName() + " recoge su taza");

        Thread.sleep((new Random().nextInt(4) +1)  * 1000);

        System.out.println("El cliente " + hilo.getName() + " comienza su cafe ");

        Thread.sleep((new Random().nextInt(4) +1)  * 1000);

        System.out.println("El cliente " + hilo.getName() + " libera un hueco ");


    }

}
