package application;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class app extends Thread {

    public String ruta, rutaFinal="";
    static  long start, end;
    int contadorCaracteres=0;

    public String[] rutas = new String[3];

    boolean x1;

    /*
    Declaramos como globales la ruta, y los contadores de empiece y final.
     */

    app(String rutaEntrada, boolean respuesta, String rutafinal){
        this.ruta=rutaEntrada;
        this.x1=respuesta;
        this.rutaFinal = rutafinal;
    }

    app(String rutaEntrada, boolean respuesta){
        this.ruta=rutaEntrada;
        this.x1=respuesta;
    }
    /*
    Declaracion de dos constructores que usaremos. Uno para el hilo padre
    otro para los hilos hijos que leeran los archivos
     */

    @Override
    public synchronized void run() {
        try {
            /*
            Declaro un booleano x1 el cual solo podra accederse si es falso,
            el cual cuando declaramos el padre es falso, los hijos true.
             */
            if (!x1){
                x1=true;
                int contadorArray=0;
                FileReader file = new FileReader(ruta);

                //Delaramos un contador de caracteres y la ruta (ya introducida en el main)

                int num = file.read();
                this.rutaFinal+=(char) num;

                /*Leemos el primer caracter y lo pasamos al string "s" ya que si no introducimos
                esta linea, nos lo saltaria*/
                while (num != -1){
                    //System.out.println((char) num); linea para leer caracter y mostrarlo
                    num = file.read();
                    //leemos el siguiente caracter
                    if (num != -1){
                        //Comprobador doble debido a que puede colarse algun caracter malo
                        this.rutaFinal+=(char) num;
                        //Sumamos el char a un string
                        if (rutaFinal.contains("_")){
                            //Comprobamos que el string contenga un separador
                            rutaFinal="";
                            contadorArray+=1;
                            //En caso de que lo contenga, lo vaciamos y aumentamos el contador del array de rutas
                        }
                        else {
                            rutas[contadorArray]=rutaFinal;
                            /*Vamos sobre-escribiendo la ruta hasta que contenga un "_", si no cambiariamos el
                            contador cambiando a fichero3, etc. */
                        }
                    }
                }

                start = System.currentTimeMillis();
                /*
                Empezamos a contar los segundos.
                */

                app p2 = new app(rutas[0], true);
                p2.start();
                p2.join();

                app p3 = new app(rutas[1], true);
                p3.start();
                p3.join();

                app p4 = new app(rutas[2], true);
                p4.start();
                p4.join();

                /*Ejecutamos los hijos con el segundo constructor, usando la ruta del array y true
                saltando la primera parte del codigo */

                end = System.currentTimeMillis() - start;
                System.out.println("Hilos tiempo: "+end);
                System.out.println("___________________");
                System.out.println("\nLa suma de los caracteres de los hilos es el mismo que el apartado");
                System.out.println("\n___________________");

                /*Le pasamos los segundos a end, restando cuando comenzo y mostramos la info desde
                el hilo padre*/

            }
            else {

                //Ejecutamos los hijos hijos leyendo.

                FileReader file = new FileReader(ruta);

            /*
             Declaramos FileReader, llamado file, pasando la ruta ya introducida
             en el constructor
             */

                int num = file.read();
                //Declaramos un intenger y que lea el caracter el fichero
                while (num != -1){
                    //System.out.println((char) num); linea para mostrar cada linea
                    num = file.read();
                    contadorCaracteres+=1;
                //Pasamos al siguiente caracter y aumentamos el contador de caracter
                }
                System.out.println("Caracteres leídos hilos: "+contadorCaracteres);
                //Mostramos los caracteres
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void apartado1(){
        //Misma operacion ya comentada en run.
        try {
            int num, contador=0;
            start = System.currentTimeMillis();
            FileReader file = new FileReader("src\\fichero2.txt");
            num = file.read();
            while (num != -1){
                contador+=1;
                num = file.read();
            }
            file = new FileReader("src\\fichero3.txt");
            num = file.read();
            while (num != -1){
                contador+=1;
                num = file.read();
            }
            file = new FileReader("src\\fichero4.txt");
            num = file.read();
            while (num != -1){
                contador+=1;
                num = file.read();
            }

            //Leemos el file pasando la ruta, pasamos primer caracter, contador, etc

            end = System.currentTimeMillis() - start;
            System.out.println("Apartado1 tiempo: "+end+" caracteres: "+ contador);
            System.out.println("___________________");

            //Mostramos info

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        apartado1();

        //Ejecuto el apartado 1
        
        app p1 = new app("src//fichero1.txt", false, "");
        p1.start();
        p1.join();
        
        //Ejecuto el hilo padre

    }
}
