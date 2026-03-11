package seedu.address.ui;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;

import javafx.application.Platform;

/**
 * A GUI unit test class for JavaFX components.
 */
public abstract class GuiUnitTest {

    private static boolean initialized = false;

    @BeforeAll
    public static void initToolkit() throws Exception {
        if (!initialized) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            if (!latch.await(5, TimeUnit.SECONDS)) {
                throw new RuntimeException("JavaFX initialization timeout");
            }
            Platform.setImplicitExit(false);
            initialized = true;
        }
    }

    /**
     * Runs the given runnable on the JavaFX Application Thread and waits for completion.
     */
    protected void runAndWait(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Throwable[] exception = new Throwable[1];

        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                exception[0] = t;
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(10, TimeUnit.SECONDS)) {
            throw new AssertionError("Test timed out");
        }

        if (exception[0] != null) {
            if (exception[0] instanceof Exception) {
                throw (Exception) exception[0];
            } else {
                throw (Error) exception[0];
            }
        }
    }
}
