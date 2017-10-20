package prog2.concur.exercice2;

class Producteur implements Runnable {

    private AbstractFileBloquanteBornee<String> file;
    private String id;

    public Producteur(AbstractFileBloquanteBornee<String> f, String i) {
        file = f;
        id = i;
    }

    public void run() {
        try 
        {
            for(int i=0; i<50; i++) {
                //Thread.sleep((int)(Math.random()*1000));
                file.deposer(id+i);
                System.out.println("deposer : " + id + i);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

    }
}

class Consommateur implements Runnable {

    private AbstractFileBloquanteBornee<String> file;
    private String id;

    public Consommateur(AbstractFileBloquanteBornee<String> f, String i) {
        file = f;
        id = i;
    }

    public void run() {
        String elem;
        try 
        {
            for(int i=0; i<50; i++) {
                //Thread.sleep((int)(Math.random()*1000));
                elem = file.prendre();
                System.out.println(id + "prendre : " + elem);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

    }
}

public class Exercice2 {

    public static void main(String args[]) {
        AbstractFileBloquanteBornee<String> f = new File1<String>(5);

        new Thread(new Producteur(f, "P1")).start();
        new Thread(new Producteur(f, "P2")).start();
        new Thread(new Producteur(f, "P3")).start();
        new Thread(new Producteur(f, "P4")).start();
        new Thread(new Producteur(f, "P5")).start();
        new Thread(new Producteur(f, "P6")).start();

        new Thread(new Consommateur(f, "C1")).start();
        new Thread(new Consommateur(f, "C2")).start();
        new Thread(new Consommateur(f, "C3")).start();
        new Thread(new Consommateur(f, "C4")).start();
    }
}
