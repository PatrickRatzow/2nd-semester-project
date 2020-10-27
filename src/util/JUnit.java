package util;

public class JUnit {
    // https://stackoverflow.com/a/12717377
    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }

        return false;
    }
}
