package de.bsd.replicator;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Locale;

@Path("/tea")
public class TeaResource {

//    @Inject
//    Brewery brewery;

//    @RestClient
//    PaymentService paymentService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String makeTea(@QueryParam("kind") String kind) throws Exception {


        String name = kind.toLowerCase(Locale.ROOT);

//        Tea tea = Tea.findByName(name);
//        if (tea == null) {
//            throw new NotFoundException("No such tea " + kind);
//        }

//        boolean paid = checkPayment(kind);
//        if (!paid) {
//            throw new NotPaidException(kind);
//        }


//        brewery.brewTea(kind);

        return "Here is your " + kind + " tea - enjoy!";
    }

//    private boolean checkPayment(String kind) {
//        return Boolean.parseBoolean(paymentService.isPaid(kind));
//    }


}
