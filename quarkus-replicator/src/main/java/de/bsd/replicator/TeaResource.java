package de.bsd.replicator;

import de.bsd.loggerService.LoggerService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.Locale;

@Path("/tea")
public class TeaResource {

    @Inject
    LLMTextExtractorService extractorService;

    @Inject
    Brewery brewery;

    @RestClient
    PaymentService paymentService;

    @Inject
    LoggerService loggerService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String makeTea(@QueryParam("kind") String kind) throws Exception {

        if (kind==null) {
            throw new IllegalArgumentException("No request passed - how am I supposed to work under these conditions?");
        }

        String extractedKind = extractorService.extract(kind);

        String name = extractedKind.toLowerCase(Locale.ROOT);
        name = name.replaceAll("%20"," ");
        Log.log(Logger.Level.INFO, "Extracted kind: " + name);

        Tea tea = Tea.findByName(name);
        if (tea == null) {
            throw new NotFoundException("No such tea " + kind);
        }

        boolean paid = false;
        try {
            paid = checkPayment(name);
        } catch (Exception e) {
            System.err.println("Is the payment service configured?  -> " + e.getMessage());
        }

        loggerService.sendLog(name, paid);

        if (!paid) {
            throw new NotPaidException(kind);
        }


        brewery.brewTea(name);

        return "Here is your " + name + " tea - enjoy!";
    }

    private boolean checkPayment(String kind) {
        return Boolean.parseBoolean(paymentService.isPaid(kind));
    }


}
