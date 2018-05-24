package ucar.ral.gis.services.pipeline;

import org.junit.Before;
import org.junit.Test;
import ucar.ral.gis.services.messages.ConversionRequestMessage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

public class TryFinallyProcessorTest {

    private ConversionRequestMessage message;
    private TestProcessor tryProcessor;
    private TestProcessor finallyProcessor;
    private TryFinallyProcessor tryFinallyProcessor;

    @Before
    public void setUp() throws Exception {
        message = mock(ConversionRequestMessage.class);
        tryProcessor = new TestProcessor();
        finallyProcessor = new TestProcessor();
        tryFinallyProcessor = new TryFinallyProcessor(tryProcessor, finallyProcessor);
    }

    @Test
    public void given_no_exception__when_process__then_try_and_finally_processor_executed() {
        tryFinallyProcessor.process(message);
        assertThat(tryProcessor.isExecuted(), is(true));
        assertThat(finallyProcessor.isExecuted(), is(true));
    }

    @Test(expected = TryException.class)
    public void given_exception_in_try_processor__when_process__then_try_and_finally_processor_executed_and_try_exception_thrown() {
        tryProcessor.setException(new TryException());

        try {
            tryFinallyProcessor.process(message);
        } catch (TryException e) {
            assertProcessorsExecutedWithMessage();
            throw e;
        }
    }

    @Test(expected = FinallyException.class)
    public void given_exception_in_finally_processor__when_process__then_try_and_finally_processor_executed_and_finally_exception_thrown() {
        finallyProcessor.setException(new FinallyException());

        try {
            tryFinallyProcessor.process(message);
        } catch (FinallyException e) {
            assertProcessorsExecutedWithMessage();
            throw e;
        }
    }

    @Test(expected = FinallyException.class)
    public void given_exception_in_both_processors__when_process__then_try_and_finally_processor_executed_and_finally_exception_thrown() {
        tryProcessor.setException(new TryException());
        finallyProcessor.setException(new FinallyException());

        try {
            tryFinallyProcessor.process(message);
        } catch (FinallyException e) {
            assertProcessorsExecutedWithMessage();
            throw e;
        }
    }

    private void assertProcessorsExecutedWithMessage() {
        assertThat(tryProcessor.isExecuted(), is(true));
        assertThat(finallyProcessor.isExecuted(), is(true));
        assertThat(tryProcessor.getMessageProcessed(), is(message));
        assertThat(finallyProcessor.getMessageProcessed(), is(message));
    }

    private class TestProcessor implements Processor {

        private RuntimeException exception;
        private Boolean executed = false;
        private ConversionRequestMessage messageProcessed;

        public void process(ConversionRequestMessage message) {
            executed = true;
            messageProcessed = message;
            if (exception != null) {
                throw exception;
            }
        }

        public void setException(RuntimeException exception) {
            this.exception = exception;
        }

        public Boolean isExecuted() {
            return executed;
        }

        public ConversionRequestMessage getMessageProcessed() {
            return messageProcessed;
        }
    }

    private class TryException extends RuntimeException {

    }

    private class FinallyException extends RuntimeException {

    }

}