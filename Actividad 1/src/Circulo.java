public class Circulo { // 1
    // 1.a
    private double radio = 12.5;
    private String color = "azul";

    // 1.b
    public Circulo() { // constructor default
    }

    public Circulo(double radio) { // el otro constructor
        // this.radio = radio;
    }

    // 1.c
    public double getRadio() { // metodo para el radio
        return this.radio;
    }

    public double getArea() {
        return this.radio * this.radio * Math.PI; // r^2*pi
    }
    // Prueba 1 (ejercicio 2): sin ningún objeto creado, el código compila pero no devuelve nada, pues no se indicó nada.

    // 4.i metodos setters para la pregunta 4.1, 4.j y 4.l
    public double setRadio(double radio_nuevo) {
        return this.radio = radio_nuevo;
    }

    public String setColor(String color_nuevo) {
        return this.color = color_nuevo;
    }

    // 4.l metodo toString()
    public String toString(){
        // reporte de datos
        return "Circulo [radio=" + this.radio + ", area=" + getArea() + ", color=" + this.color + "]";
    }
}

class CirculoTest{
    // Prueba 2 (ejercicio 3): con la creación de un objeto (c1) se imprimio correctamente los datos a partir del constructor por defecto
    // asimismo, con el segundo objeto (c2), el cual se creo con el constructor con un valor de 6.55 en el radio, tambien se mostro correctamente

    // respuesta de la última pregunta del ejercicio 3: lo que tienen en comun es la creacion de un objeto, pues si no hay objeto no se puede hacer uso de sus metodos
    // y al momento de iniciar el constructor con "= new Circulo()/(arg)", ya sea un constructor sobrecargado o no, el proceso es el mismo.
    public static void main(String[] args) {
        Circulo c1 = new Circulo();
        System.out.println("-- Constructor por defecto --");
        System.out.println("a. Radio: " + c1.getRadio());
        System.out.println("b. Area: " + c1.getArea());

        Circulo c2 = new Circulo(6.55);
        System.out.println(" ");
        System.out.println("-- Constructor sobrecargado --");
        System.out.println("a. Radio: " + c2.getRadio());
        System.out.println("b. Area: " + c2.getArea());
        // respuesta del ejercicio 4:
        // a. el constructor permite dar forma a un objeto de una clase. Los constructores actuan como una plantilla, de las cuales un objeto puede adquirir sus funciones,
        // variables, etc.
        // b. usando el metodo getRadio()
        // c. por el pilar de encapsulamiento en el paradigma de POO, de esta forma se protege de modificaciones ajenas
        // d. directamente no se puede, pues es privada y solo accesible por los metodos de la clase
        // e. indica error, mostrando el siguiente mensaje: 'radio' has private access in 'Circulo'. E incluso si se intenta ejecutar, muestra: "java: cannot find symbol"
        // f. muestra el siguiente mensaje: 'radio' has private access in 'Circulo', esto significa que es una variable privada solo accesible dentro de la clase Circulo
        // g. indica lo mismo que la pregunta e, el significado es igual a la respuesta de la pregunta f.
        // h. asi como hay getters (getVar), existen los setters (setVar), por lo que se puede crear un metodo que permita modificar el valor de estas variables privadas
        // i.
        System.out.println(" ");
        Circulo c3 = new Circulo(7.1);
        System.out.println("Radio: " + c3.getRadio());
        System.out.println("Area: " + c3.getArea());
        System.out.println(" ");

        c3.setRadio(11.5);
        System.out.println("Radio: " + c3.getRadio());
        System.out.println("Area: " + c3.getArea());

        // j. this funcion como indicador de un nombre, imaginemos que se usan muchos nombres de variables, y para no confundirlos entre metodos.
        // se usa this.var, para hacer unico ese nombre dentro del metodo.
        // por ejemplo:
        // public class Ejem {
        //  String nombre; -> variable de la clase Ejem
        //  public void cambiarNombre(String nombre) { -> solicitara un atributo string en la variable con el nombre "nombre"
        //   por eso se utiliza this, para referirse al parametro que recibió y el compilador lo entiende
        //     this.nombre = nombre;
        //  }
        // }
        // nota: a pesar de que el compilador lo entiende, humanamente hablando, es confuso. Por eso, en mi caso al menos, prefiero usar otros nombres

        // k. toString() convierte el valor de una variable a tipo texto, o cadena (de texto), si se habla más tecnico
        // l. la implementacion del metodo toString() se encuentra arriba
        // modificacion de datos, para que se impriman y se vea la diferencia:
        c1.setRadio(20.8);
        c1.setColor("amarillo");
        c3.setColor("blanco");
        System.out.println(c1.toString());
        System.out.println(c2.toString());
        System.out.println(c3.toString());
    }
}