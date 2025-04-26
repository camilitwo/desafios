package com.desafios.sistordenes.service;

import com.desafios.sistordenes.dto.OrdenResponse;
import com.desafios.sistordenes.dto.ProductoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public byte[] generarCsv(List<OrdenResponse> ordenes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        // Encabezado
        writer.println("Orden ID,Cliente,Fecha,Total,Producto,Cantidad,Precio Unitario");

        for (OrdenResponse orden : ordenes) {
            if (orden.getProductos() == null || orden.getProductos().isEmpty()) {
                // Si no hay productos, igualmente agregamos la orden
                writer.println(
                        orden.getId() + "," +
                                orden.getCliente() + "," +
                                orden.getFecha() + "," +
                                orden.getTotal() + ",,,"
                );
            } else {
                for (ProductoResponse producto : orden.getProductos()) {
                    writer.println(
                            orden.getId() + "," +
                                    orden.getCliente() + "," +
                                    orden.getFecha() + "," +
                                    orden.getTotal() + "," +
                                    producto.getNombre() + "," +
                                    producto.getCantidad() + "," +
                                    producto.getPrecioUnitario()
                    );
                }
            }
        }

        writer.flush();
        return out.toByteArray();
    }


    public void enviarEmailConCsv(List<String> destinatarios, List<OrdenResponse> ordenes) throws MessagingException {
        byte[] csvData = generarCsv(ordenes);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject("Órdenes de Compra");
        helper.setText("Adjunto encontrarás las órdenes solicitadas en formato CSV.");
        helper.setFrom("tu_email@gmail.com");
        helper.setTo(destinatarios.toArray(new String[0]));

        helper.addAttachment("ordenes.csv", new ByteArrayResource(csvData));

        mailSender.send(message);
    }
}
