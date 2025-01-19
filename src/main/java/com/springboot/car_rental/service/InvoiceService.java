package com.springboot.car_rental.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import io.jsonwebtoken.io.IOException;

@Service
public class InvoiceService {

    public byte[] generateInvoice(String rentalDetails) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Facture de location"));
        document.add(new Paragraph("Détails : " + rentalDetails));

        document.close();
        return baos.toByteArray();
    }
}
