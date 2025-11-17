package de.bsd;

import de.bsd.replicator.TeaExtractionService;
import de.bsd.replicator.TeaInfo;
import io.quarkus.test.Mock;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Mock implementation of TeaExtractionService for testing
 * Mimics the real server running on port 9080
 */
@Mock
@ApplicationScoped
public class MockTeaExtractionService implements TeaExtractionService {

    @Override
    public TeaInfo extractTea(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            // Return empty response for error case
            TeaInfo teaInfo = new TeaInfo();
            teaInfo.teaType = "";
            teaInfo.brewingInstructions = new TeaInfo.BrewingInstructions();
            return teaInfo;
        }

        if (sentence.contains("sencha")) {
            return createSenchaResponse();
        } else if (sentence.toLowerCase().contains("earl") && sentence.toLowerCase().contains("grey")) {
            return createEarlGreyResponse();
        }

        // Default response
        return createDefaultResponse(sentence);
    }

    private TeaInfo createSenchaResponse() {
        TeaInfo teaInfo = new TeaInfo();
        teaInfo.teaType = "sencha";
        teaInfo.brewingInstructions = new TeaInfo.BrewingInstructions();
        teaInfo.brewingInstructions.temperature = "70-80°C";
        teaInfo.brewingInstructions.steepingTime = "1-2 minutes";
        teaInfo.brewingInstructions.ratio = "1 tsp per 200ml";
        teaInfo.brewingInstructions.infusions = "2-3 infusions possible";
        teaInfo.brewingInstructions.notes = "Lower temperature preserves delicate umami flavor";
        return teaInfo;
    }

    private TeaInfo createEarlGreyResponse() {
        TeaInfo teaInfo = new TeaInfo();
        teaInfo.teaType = "earl grey";
        teaInfo.brewingInstructions = new TeaInfo.BrewingInstructions();
        teaInfo.brewingInstructions.temperature = "95-100°C";
        teaInfo.brewingInstructions.steepingTime = "3-5 minutes";
        teaInfo.brewingInstructions.ratio = "1 tsp per 200ml";
        teaInfo.brewingInstructions.infusions = "1-2 infusions possible";
        teaInfo.brewingInstructions.notes = "Black tea with bergamot oil; pairs well with milk";
        return teaInfo;
    }

    private TeaInfo createDefaultResponse(String sentence) {
        TeaInfo teaInfo = new TeaInfo();
        teaInfo.teaType = sentence.toLowerCase();
        teaInfo.brewingInstructions = new TeaInfo.BrewingInstructions();
        teaInfo.brewingInstructions.temperature = "85-95°C";
        teaInfo.brewingInstructions.steepingTime = "3-4 minutes";
        teaInfo.brewingInstructions.ratio = "1 tsp per 200ml";
        teaInfo.brewingInstructions.infusions = "2 infusions possible";
        teaInfo.brewingInstructions.notes = "Generic brewing instructions";
        return teaInfo;
    }
}