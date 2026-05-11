package utils;

import java.awt.Font;
import java.io.InputStream;

public class AppFonts {
	private static Font baseFont;

	static {
	    try {
	        InputStream is = AppFonts.class.getClassLoader().getResourceAsStream("assets/font/Roboto.ttf");
	        if (is == null) throw new Exception("Fuente no encontrada en classpath");
	        baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
	    } catch (Exception e) {
	        System.err.println("Error cargando fuente: " + e.getMessage());
	        baseFont = new Font("Arial", Font.PLAIN, 14);
	    }
	}

    // Texto normal
    public static Font regular(float size) {
        return baseFont.deriveFont(Font.PLAIN, size);
    }

    // Negrita
    public static Font bold(float size) {
        return baseFont.deriveFont(Font.BOLD, size);
    }

    // Titulos grandes
    public static Font title() {
        return baseFont.deriveFont(Font.BOLD, 20f);
    }

    // Subtitulos
    public static Font subtitle() {
        return baseFont.deriveFont(Font.PLAIN, 18f);
    }

    // Botones
    public static Font button() {
        return baseFont.deriveFont(Font.BOLD, 15f);
    }

    // Texto pequeño
    public static Font small() {
        return baseFont.deriveFont(Font.PLAIN, 12f);
    }
}
