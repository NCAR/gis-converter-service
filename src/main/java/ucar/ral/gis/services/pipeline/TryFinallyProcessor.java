package ucar.ral.gis.services.pipeline;

import ucar.ral.gis.services.messages.ConversionRequestMessage;

public class TryFinallyProcessor implements Processor {

    private final Processor tryProcessor;
    private final Processor finallyProcessor;

    public TryFinallyProcessor(Processor tryProcessor, Processor finallyProcessor) {
        this.tryProcessor = tryProcessor;
        this.finallyProcessor = finallyProcessor;
    }

    public void process(ConversionRequestMessage conversionRequest) {
        try {
            tryProcessor.process(conversionRequest);
        } finally {
            finallyProcessor.process(conversionRequest);
        }
    }
}
