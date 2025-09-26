package com.braintribe.common.symbol;

/**
 * Symbol is a pattern to implement a set of well-known string constants.
 * <p>
 * They have an advantage over pure strings in that the possible values can be looked up and referenced, and unlike enums they are extensible.
 * <p>
 * They are intended as a better alternative to string identifiers in various APIs, typically registries for different types of objects.
 * <p>
 * We recommend that "register" methods require a Symbol, while lookup methods accept both a Symbol and a string.
 * 
 * <h3>Example:</h3>
 * 
 * Let's use a fonts registry for illustration.
 * <p>
 * <b>Step 1</b>: Create a FontSymbol, which serves as a space that separates fonts from other symbols:
 * 
 * <pre>{@code
 * {@literal @}FunctionalInterface
 * public interface FontSymbol extends Symbol {
 *     // empty
 * }
 * }</pre>
 * 
 * <p>
 * <b>Step 2</b>: Define a Font Registry, which internally indexes Fonts by name (String):
 * 
 * <pre>{@code
 * public interface FontRegistry {
 *  // register only with a Symbol 
 * 	void register(FontSymbol symbol, Font font);
 *
 *  // lookup with a Symbol or a string 
 * 	Font lookup(FontSymbol symbol);
 * 	Font lookup(String name);
 * }
 * }</pre>
 * 
 * <p>
 * <b>Step 3</b>: Define Symbol instances:
 * 
 * <pre>{@code
 * public interface BasicFonts {
 * 	FontSymbol ARIAL = () -> "Arial";
 * 	FontSymbol TIMES = () -> "Times";
 * 	FontSymbol COURIER = () -> "Courier";
 * 	FontSymbol HELVETICA = () -> "Helvetica";
 * }
 * }</pre>
 * 
 * <b>Step 4</b>: Register and reference the fonts:
 * 
 * <pre>{@code
 * fontRegistry.register(BasicFonts.ARIAL, new ArialFont());
 * 
 * fontRegistry.lookup(BasicFonts.ARIAL);
 * fontRegistry.lookup("Arial");
 * }</pre>
 * 
 * <p>
 * This has the advantage over pure Strings in that the possible values can be looked-up and referenced, and unlike enums
 */
@FunctionalInterface
public interface Symbol {
	String name();
}
