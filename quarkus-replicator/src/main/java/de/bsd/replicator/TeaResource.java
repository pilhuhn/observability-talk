package de.bsd.replicator;

import de.bsd.loggerService.LoggerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Locale;

@Path("/tea")
public class TeaResource {

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

        String name = kind.toLowerCase(Locale.ROOT);
        name = name.replaceAll("%20"," ");

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

        loggerService.sendLog(kind, paid);

        if (!paid) {
            throw new NotPaidException(kind);
        }


        brewery.brewTea(kind);

        return "Here is your " + kind + " tea - enjoy!";
    }

    private boolean checkPayment(String kind) {
        return Boolean.parseBoolean(paymentService.isPaid(kind));
    }


}
