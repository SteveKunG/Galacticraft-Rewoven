/*
 * Copyright (c) 2019-2021 Team Galacticraft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.galacticraft.mod.world.dimension;

import dev.galacticraft.mod.Constant;
import dev.galacticraft.mod.block.GalacticraftBlock;
import dev.galacticraft.mod.world.gen.chunk.MoonChunkGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.*;

/**
 * @author <a href="https://github.com/TeamGalacticraft">TeamGalacticraft</a>
 */
public class GalacticraftDimension {
    public static final RegistryKey<World> MOON = RegistryKey.of(Registry.DIMENSION, new Identifier(Constant.MOD_ID, "moon"));

    public static void register() {
        BuiltinRegistries.add(BuiltinRegistries.CHUNK_GENERATOR_SETTINGS, new Identifier(Constant.MOD_ID, "moon"), new ChunkGeneratorSettings(
                new StructuresConfig(false),
                new GenerationShapeConfig(
                        256, new NoiseSamplingConfig(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D),
                        new SlideConfig(-10, 3, 0), new SlideConfig(-30, 0, 0),
                        1, 2, 1.0D, -0.46875D, true,
                        true, false, false),
                GalacticraftBlock.MOON_ROCKS[0].getDefaultState(), Blocks.AIR.getDefaultState(), -10, 0, 63, false)
        );

        Registry.register(Registry.CHUNK_GENERATOR, new Identifier(Constant.MOD_ID, "moon"), MoonChunkGenerator.CODEC);
        //FabricDimensions.registerDefaultPlacer(MOON, GalacticraftDimensions::placeEntity);
    }
}
