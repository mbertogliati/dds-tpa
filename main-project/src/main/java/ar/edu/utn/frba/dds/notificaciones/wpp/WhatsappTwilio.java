package ar.edu.utn.frba.dds.notificaciones.wpp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class WhatsappTwilio implements AdapterWPP {
    private final String ACCOUNT_SID = "AC9d7a04c7222182ca1576b33f206f6492";
    private final String AUTH_TOKEN = "fdd8c6d6bae479546f1195f392e9d001";


    @Override
    public void enviarWPP(String mensaje, int telefono) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message notificacion = Message.creator(
                new PhoneNumber("whatsapp:+549" + String.valueOf(telefono)),
                new PhoneNumber("whatsapp:+14155238886"),
                mensaje
        ).create();

        System.out.println(notificacion.getSid());
        System.out.println("Se envió el mensaje: '" + mensaje + "'.\n Al número: '" + String.valueOf(telefono) + "'.");
    }
}
