import java.util.ArrayList; // para el arraylist que se usara en Superficie_Plana
import java.util.List;

abstract class Figura_Geometrica{
    protected String nombre;

    public Figura_Geometrica() {}

    public Figura_Geometrica(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreFigura) {
        this.nombre = nombreFigura;
    }

    public abstract double calcular_area_figura();
    public abstract boolean es_regular();
}

class Punto{
    int coordenada_x, coordenada_y;

    // respuestas a la pregunta 2.b, constructor vacio y otro con las coordenadas
    public Punto() { // se empieza con (0,0), luego se usara este dato
        this.coordenada_x = 0;
        this.coordenada_y = 0;
    }

    public Punto(int coordenada_x, int coordenada_y) {
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
    }

    // respuesta de la pregunta 2.c, getters y setters
    public int getCoordenada_x() {
        return coordenada_x;
    }

    public int getCoordenada_y() {
        return coordenada_y;
    }

    public void setCoordenada_x(int coordenada_x) {
        this.coordenada_x = coordenada_x;
    }

    public void setCoordenada_y(int coordenada_y) {
        this.coordenada_y = coordenada_y;
    }

    // respuestas de la pregunta 2.d
    public double getDistance(Punto otro_punto) {
        int dx = this.coordenada_x - otro_punto.getCoordenada_x();
        int dy = this.coordenada_y - otro_punto.getCoordenada_y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getDistancia() {
        // metodo que no cuenta con parametros, se empezo con la posicicon (0,0)
        return this.getDistance(new Punto(0, 0));
    }

    // respuesta a la pregunta 2.a
    public String toString() {
        return "El punto tiene las siguientes coordenadas: (" + coordenada_x + "," + coordenada_y + ")";
    }
}

class Triangulo extends Figura_Geometrica{
    Punto punto1;
    Punto punto2;
    Punto punto3;

    // respuestas a la pregunta 3.b
    public Triangulo() {
        super();
    }

    public Triangulo(String nombre, Punto punto1, Punto punto2, Punto punto3) {
        super(nombre);
        this.punto1 = punto1;
        this.punto2 = punto2;
        this.punto3 = punto3;
    }

    // respuestas a pregunta 3.c, incluye los getters y setters
    public Punto getPunto1() {
        return punto1;
    }

    public Punto getPunto2() {
        return punto2;
    }

    public Punto getPunto3() {
        return punto3;
    }

    public void setPunto1(Punto pt1) {
        this.punto1 = pt1;
    }

    public void setPunto2(Punto pt2) {
        this.punto2 = pt2;
    }

    public void setPunto3(Punto pt3) {
        this.punto3 = pt3;
    }

    // respuesta a pregunta 3.d
    @Override
    public double calcular_area_figura() {
        double ladoA = punto1.getDistance(punto2);
        double ladoB = punto2.getDistance(punto3);
        double ladoC = punto3.getDistance(punto1);
        double semiperimetro = (ladoA + ladoB + ladoC) / 2.0;
        double calculo_interno = semiperimetro * (semiperimetro - ladoA) * (semiperimetro - ladoB) * (semiperimetro - ladoC);
        return calculo_interno > 0 ? Math.sqrt(calculo_interno) : 0;
    }

    @Override
    public boolean es_regular() {
        double ladoA = punto1.getDistance(punto2);
        double ladoB = punto2.getDistance(punto3);
        double ladoC = punto3.getDistance(punto1);
        double epsilon = 0.0001;
        return Math.abs(ladoA - ladoB) < epsilon && Math.abs(ladoB - ladoC) < epsilon;
    }

    // respuesta a pregunta 3.a
    public String toString() {
        return "Triangulo: " + getNombre() + " tiene 3 Puntos:\n "+ getPunto1().toString() + ",\n " + getPunto2().toString()+ ",\n " + getPunto3().toString();
    }
}

class Superficie_Plana {
    private List<Figura_Geometrica> figuras_geometricas;

    public Superficie_Plana() {
        this.figuras_geometricas = new ArrayList<>();
    }

    public void agregar_figura(Figura_Geometrica figura) {
        this.figuras_geometricas.add(figura);
    }

    public void imprimir_areas() {
        for (Figura_Geometrica figura : figuras_geometricas) {
            System.out.println("Figura: " + figura.getNombre() + " - Area: " + figura.calcular_area_figura());
        }
    }
}

public class Main {
    // Pregunta 4:
    // ¿qué atributos y métodos heredan?
    // el atributo que se hereda es el nombre, asi como los metodos de getNombre, setNombre, calcular_area_figura, es_regular

    // Pregunta 5:
    // Identificar las clases que conforman una composición.
    // la clase triangulo es el que conforma una composicion, pues combina 3 puntos (clase Punto), y a la vez
    // la superficie plana lo puede registrar con una lista, esas tres partes forman un conjunto que crea una superfice, y sus triangulos

    // Pregunta 6:
    // ¿Qué es una superclase y una subclase?
    // Una superclase es el padre de una clase, y una subclase es el hijo de alguna clase

    // Pregunta 7:
    // ¿Por qué usamos abstract? ¿Se puede dejar de heredar un método de una clase abstracta?
    // se utiliza para establecer un metodo en comun, pero sin la estructura definida, siendo que esta estructura se podrá sobrecargar
    // en la clase que lo utilice por su propia cuenta. Por lo que se podría ver como una plantilla.

    // Pregunta 8:
    // ¿Qué anotación utilizo para sobreescribir métodos?
    // se utiliza @override

    // Pregunta 9:
    // Los atributos de la clase Figura Geométrica conviértalas en protected. ¿En qué condición
    // convierte a los atributos? ¿Es posible acceder a los atributos protegidos sin utilizar una
    // invocación a super() o sin método get?.
    // El modificador protected hace que el atributo sea visible para la propia clase, para las clases del mismo proyecto y para todas sus subclases
    // y tambien es posible acceder a este atributo directamente con this.nombre. La que elimina la necesidad de usar getters o super()

    // Pregunta 10:
    // ¿Cómo aplicarías polimorfismo?
    // El polimorfismo es la capacidad de reaccionar de diferente manera cuande se tiene diferentes entradas, se podria aplicar en la superficie plana
    // porque solicita una figura geometrica, no necesariamente es un triangulo, si se implementa, tambien se puede agregar cuadrados, circulos, etc
    public static void main(String[] args) {
        Superficie_Plana superficie = new Superficie_Plana(); // creacion de la superficie
        // nuevos puntos
        Punto p1 = new Punto(0, 0);
        Punto p2 = new Punto(4, 0);
        Punto p3 = new Punto(2, 3);
        // nuevo triangulo
        Triangulo triangulo1 = new Triangulo("triangulo 1", p1, p2, p3);
        // se agrega a la superficie
        superficie.agregar_figura(triangulo1);

        System.out.println(triangulo1.toString());
        System.out.println("¿Es un triangulo regular?: " + triangulo1.es_regular());
        System.out.println(" ");
        System.out.println("-- Areas en la superficie plana --");
        superficie.imprimir_areas();
    }
}