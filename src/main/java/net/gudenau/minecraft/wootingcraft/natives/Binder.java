package net.gudenau.minecraft.wootingcraft.natives;

import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.Platform;

import java.io.IOException;
import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static net.gudenau.minecraft.wootingcraft.WootingCraft.MOD_ID;

public final class Binder {
    private static final Arena ARENA = Arena.ofAuto();
    private static final Linker LINKER = Linker.nativeLinker();

    private static final String PLATFORM_NAME;
    private static final boolean SUPPORTED;
    private static final boolean UNIX;
    static {
        var os = Util.getOperatingSystem();
        boolean unix = true;
        var osName = switch(os) {
            case LINUX -> "linux";
            case WINDOWS -> {
                unix = false;
                yield "windows";
            }
            case OSX -> "osx";
            default -> "unknown";
        };
        UNIX = unix;

        var arch = Platform.getArchitecture();
        var archName = switch(arch) {
            case X64 -> "amd64";
            default -> "unknown";
        };
        PLATFORM_NAME = osName + '/' + archName;
        SUPPORTED = !PLATFORM_NAME.contains("unknown");
    }

    /**
     * Returns true when the JVM is running on a supported platform.
     *
     * @return True if the platform is supported
     */
    public static boolean supported() {
        return SUPPORTED;
    }

    /**
     * Creates a new Binder instance from the extracted natives.
     *
     * @param name The name of the library to link against
     * @return The created Binder instance
     */
    @NotNull
    static Binder of(@NotNull String name) {
        if(!supported()) {
            throw new IllegalStateException("Can not created a Binder on an unsupported platform");
        }

        var mappedName = System.mapLibraryName(name);

        var resourcePath = "/natives/" + MOD_ID + '/' + PLATFORM_NAME + '/' + mappedName;
        var input = Binder.class.getResourceAsStream(resourcePath);
        if(input == null) {
            throw new RuntimeException("Could not find natives for " + name + " on " + PLATFORM_NAME);
        }
        try(input) {
            var outputFile = Files.createTempFile(MOD_ID, mappedName);
            try {
                try(var output = Files.newOutputStream(outputFile, StandardOpenOption.CREATE)) {
                    input.transferTo(output);
                }

                return new Binder(outputFile);
            } finally {
                // Windows is janky, you can't delete files that are in use so we litter the temp dir.
                if(UNIX) {
                    Files.delete(outputFile);
                }
            }
        } catch(IOException e) {
            throw new RuntimeException("Failed to extract natives for " + name, e);
        }
    }

    private final SymbolLookup lookup;

    private Binder(@NotNull Path libraryPath) {
        lookup = SymbolLookup.libraryLookup(libraryPath, ARENA);
    }

    /**
     * Binds a native function to a {@link MethodHandle}.
     *
     * @param name The name of the symbol to lookup
     * @param result The result of the function, null for void
     * @param args The arguments of the function
     * @return The bound handle
     */
    @NotNull
    public MethodHandle bind(@NotNull String name, @Nullable MemoryLayout result, @NotNull MemoryLayout @NotNull ... args) {
        var descriptor = result == null ?
            FunctionDescriptor.ofVoid(args) :
            FunctionDescriptor.of(result, args);

        var symbol = lookup.find(name)
            .orElseThrow(() -> new UnsatisfiedLinkError("Failed to find symbol " + name));

        return LINKER.downcallHandle(symbol, descriptor);
    }
}
