package io.bit3.jsass;

import org.apache.commons.io.Charsets;

import java.net.URI;
import java.nio.charset.Charset;

import io.bit3.jsass.context.Context;
import io.bit3.jsass.context.FileContext;
import io.bit3.jsass.context.StringContext;

/**
 * The compiler compiles SCSS files, strings and contexts.
 */
public class Compiler {

  /**
   * The default defaultCharset that is used for compiling strings.
   */
  public final Charset defaultCharset = Charsets.UTF_8;

  /**
   * sass library adapter.
   */
  private final NativeAdapter adapter;

  /**
   * Create new compiler.
   */
  public Compiler() {
    adapter = new NativeAdapter();
  }

  /**
   * Compile string.
   *
   * @param string  The input string.
   * @param options The compile options.
   * @return The compilation output.
   * @throws CompilationException If the compilation failed.
   */
  public Output compileString(String string, Options options) throws CompilationException {
    return compileString(string, defaultCharset, null, null, options);
  }

  /**
   * Compile string.
   *
   * @param string  The input string.
   * @param charset The defaultCharset of the input string.
   * @param options The compile options.
   * @return The compilation output.
   * @throws CompilationException If the compilation failed.
   */
  public Output compileString(String string, Charset charset, Options options)
      throws CompilationException {
    return compileString(string, charset, null, null, options);
  }

  /**
   * Compile string.
   *
   * @param string     The input string.
   * @param inputPath  The input path.
   * @param outputPath The output path.
   * @param options    The compile options.
   * @return The compilation output.
   * @throws CompilationException If the compilation failed.
   */
  public Output compileString(String string, URI inputPath, URI outputPath, Options options)
      throws CompilationException {
    return compileString(string, defaultCharset, inputPath, outputPath, options);
  }

  /**
   * Compile string.
   *
   * @param string     The input string.
   * @param charset    The defaultCharset of the input string.
   * @param inputPath  The input path.
   * @param outputPath The output path.
   * @param options    The compile options.
   * @return The compilation output.
   * @throws CompilationException If the compilation failed.
   */
  public Output compileString(
      String string, Charset charset, URI inputPath, URI outputPath,
      Options options
  ) throws CompilationException {
    StringContext context = new StringContext(string, charset, inputPath, outputPath, options);

    return compile(context);
  }

  /**
   * Compile file.
   *
   * @param inputPath  The input path.
   * @param outputPath The output path.
   * @param options    The compile options.
   * @return The compilation output.
   * @throws CompilationException If the compilation failed.
   */
  public Output compileFile(URI inputPath, URI outputPath, Options options)
      throws CompilationException {
    FileContext context = new FileContext(inputPath, outputPath, options);
    return compile(context);
  }

  /**
   * Compile context.
   *
   * @param context The context.
   * @return The compilation output.
   * @throws CompilationException If the compilation failed.
   */
  public Output compile(Context context) throws CompilationException {
    if (context instanceof FileContext) {
      return compile((FileContext) context);
    }

    if (context instanceof StringContext) {
      return compile((StringContext) context);
    }

    throw new RuntimeException(
        String.format(
            "Context type \"%s\" is not supported",
            null == context ? "null" : context.getClass().getName()
        )
    );
  }

  /**
   * Compile a string context.
   *
   * @param context The string context.
   * @return The compilation output.
   * @throws CompilationException If the compilation failed.
   */
  public Output compile(StringContext context) throws CompilationException {
    return adapter.compile(context);
  }

  /**
   * Compile file.
   *
   * @param context The file context.
   * @return The compilation output.
   * @throws CompilationException If the compilation failed.
   */
  public Output compile(FileContext context) throws CompilationException {
    return adapter.compile(context);
  }
}
