package study.lambdas;

@FunctionalInterface
public interface Mensaje {
    //public void emitirMensaje(); //sin parametro
//    public void emitirMensaje(String nombre); //sin parametro
    public void emitirMensaje(String nombre, String lugar); //sin parametro
}

