package ar.edu.utn.frba.dds.notificaciones.wpp;

public class APIWhatsapp implements AdapterWPP {
    @Override
    public void enviarWPP(String mensaje, int numero) {
        System.out.println("Se envia:");
        System.out.println(mensaje);
        System.out.println("Al whatsapp:");
        System.out.println(numero);
    }
}
