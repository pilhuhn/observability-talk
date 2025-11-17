package de.bsd.replicator;

import de.bsd.loggerService.LoggerService;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
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

    @RestClient
    TeaExtractionService teaExtractionService;

    @Inject
    LoggerService loggerService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String makeTea(@SpanAttribute("teaRequestInput") @QueryParam("kind") String kind) throws Exception {


        if (kind==null) {
            throw new IllegalArgumentException("No request passed - how am I supposed to work under these conditions?");
        }

        // Extract tea type from the sentence using the extraction service
        TeaInfo teaInfo = teaExtractionService.extractTea(kind);
        String name = teaInfo.teaType.toLowerCase(Locale.ROOT);

        Span.current().setAttribute("detectedTea", name);


        Tea tea = Tea.findByName(name);
        if (tea == null) {
            throw new NotFoundException("No such tea " + name);
        }

        boolean paid = false;
        try {
            paid = checkPayment(name);
        } catch (Exception e) {
            System.err.println("Is the payment service configured?  -> " + e.getMessage());
        }

        loggerService.sendLog(name, paid);

        if (!paid) {
            throw new NotPaidException(name);
        }


        brewery.brewTea(kind);

        return "Here is your " + name + " tea - enjoy!";
    }

    private boolean checkPayment(String kind) {
        return Boolean.parseBoolean(paymentService.isPaid(kind));
    }


}
