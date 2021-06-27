package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "6/23/2021"
)
/*
Provides accessors to the builder methods for render layers, for building custom render layers.
 */

@Mixin(RenderPhase.class)
public interface RenderPhaseAccessor {

    @Accessor("NO_TRANSPARENCY")
    static RenderPhase.Transparency getNoTransparency() {
        throw new AssertionError();
    }
    @Accessor("ADDITIVE_TRANSPARENCY")
    static RenderPhase.Transparency getAdditiveTransparency() {
        throw new AssertionError();
    }
    @Accessor("LIGHTNING_TRANSPARENCY")
    static RenderPhase.Transparency getLightningTransparency() {
        throw new AssertionError();
    }
    @Accessor("GLINT_TRANSPARENCY")
    static RenderPhase.Transparency getGlintTransparency() {
        throw new AssertionError();
    }
    @Accessor("CRUMBLING_TRANSPARENCY")
    static RenderPhase.Transparency getCrumblingTransparency() {
        throw new AssertionError();
    }
    @Accessor("TRANSLUCENT_TRANSPARENCY")
    static RenderPhase.Transparency getTranslucentTransparency() {
        throw new AssertionError();
    }

    @Accessor("ZERO_ALPHA")
    static RenderPhase.Alpha getZeroAlpha() {
        throw new AssertionError();
    }
    @Accessor("ONE_TENTH_ALPHA")
    static RenderPhase.Alpha getOneTenthAlpha() {
        throw new AssertionError();
    }
    @Accessor("HALF_ALPHA")
    static RenderPhase.Alpha getHalfAlpha() {
        throw new AssertionError();
    }

    @Accessor("SHADE_MODEL")
    static RenderPhase.ShadeModel getShadeModel() {
        throw new AssertionError();
    }
    @Accessor("SMOOTH_SHADE_MODEL")
    static RenderPhase.ShadeModel getSmoothShadeModel() {
        throw new AssertionError();
    }

    @Accessor("MIPMAP_BLOCK_ATLAS_TEXTURE")
    static RenderPhase.Texture getMipmapBlockAtlasTexture() {
        throw new AssertionError();
    }
    @Accessor("BLOCK_ATLAS_TEXTURE")
    static RenderPhase.Texture getBlockAtlasTexture() {
        throw new AssertionError();
    }
    @Accessor("NO_TEXTURE")
    static RenderPhase.Texture getNoTexture() {
        throw new AssertionError();
    }
    
    @Accessor("DEFAULT_TEXTURING")
    static RenderPhase.Texturing getDefaultTexturing() {
        throw new AssertionError();
    }
    @Accessor("OUTLINE_TEXTURING")
    static RenderPhase.Texturing getOutlineTexturing() {
        throw new AssertionError();
    }
    @Accessor("GLINT_TEXTURING")
    static RenderPhase.Texturing getGlintTexturing() {
        throw new AssertionError();
    }
    @Accessor("ENTITY_GLINT_TEXTURING")
    static RenderPhase.Texturing getEntityGlintTexturing() {
        throw new AssertionError();
    }
    
    @Accessor("ENABLE_LIGHTMAP")
    static RenderPhase.Lightmap getEnableLightmap() {
        throw new AssertionError();
    }
    @Accessor("DISABLE_LIGHTMAP")
    static RenderPhase.Lightmap getDisableLightmap() {
        throw new AssertionError();
    }
    
    @Accessor("ENABLE_OVERLAY_COLOR")
    static RenderPhase.Overlay getEnableOverlayColor() {
        throw new AssertionError();
    }
    @Accessor("DISABLE_OVERLAY_COLOR")
    static RenderPhase.Overlay getDisableOverlayColor() {
        throw new AssertionError();
    }

    @Accessor("ENABLE_DIFFUSE_LIGHTING")
    static RenderPhase.DiffuseLighting getEnableDiffuseLighting() {
        throw new AssertionError();
    }
    @Accessor("DISABLE_DIFFUSE_LIGHTING")
    static RenderPhase.DiffuseLighting getDisableDiffuseLighting() {
        throw new AssertionError();
    }

    @Accessor("ENABLE_CULLING")
    static RenderPhase.Cull getEnableCulling() {
        throw new AssertionError();
    }
    @Accessor("DISABLE_CULLING")
    static RenderPhase.Cull getDisableCulling() {
        throw new AssertionError();
    }

    @Accessor("ALWAYS_DEPTH_TEST")
    static RenderPhase.DepthTest getAlwaysDepthTest() {
        throw new AssertionError();
    }
    @Accessor("EQUAL_DEPTH_TEST")
    static RenderPhase.DepthTest getEqualDepthTest() {
        throw new AssertionError();
    }
    @Accessor("LEQUAL_DEPTH_TEST")
    static RenderPhase.DepthTest getLEqualDepthTest() {
        throw new AssertionError();
    }

    @Accessor("ALL_MASK")
    static RenderPhase.WriteMaskState getAllMask() {
        throw new AssertionError();
    }
    @Accessor("COLOR_MASK")
    static RenderPhase.WriteMaskState getColorMask() {
        throw new AssertionError();
    }
    @Accessor("DEPTH_MASK")
    static RenderPhase.WriteMaskState getDepthMask() {
        throw new AssertionError();
    }

    @Accessor("NO_LAYERING")
    static RenderPhase.Layering getNoLayering() {
        throw new AssertionError();
    }
    @Accessor("POLYGON_OFFSET_LAYERING")
    static RenderPhase.Layering getPolygonOffsetLayering() {
        throw new AssertionError();
    }
    @Accessor("VIEW_OFFSET_Z_LAYERING")
    static RenderPhase.Layering getViewOffsetZLayering() {
        throw new AssertionError();
    }

    @Accessor("NO_FOG")
    static RenderPhase.Fog getNoFog() {
        throw new AssertionError();
    }
    @Accessor("FOG")
    static RenderPhase.Fog getFog() {
        throw new AssertionError();
    }
    @Accessor("BLACK_FOG")
    static RenderPhase.Fog getBlackFog() {
        throw new AssertionError();
    }

    @Accessor("MAIN_TARGET")
    static RenderPhase.Target getMainTarget() {
        throw new AssertionError();
    }
    @Accessor("OUTLINE_TARGET")
    static RenderPhase.Target getOutlineTarget() {
        throw new AssertionError();
    }
    @Accessor("TRANSLUCENT_TARGET")
    static RenderPhase.Target getTranslucentTarget() {
        throw new AssertionError();
    }
    @Accessor("PARTICLES_TARGET")
    static RenderPhase.Target getParticlesTarget() {
        throw new AssertionError();
    }
    @Accessor("WEATHER_TARGET")
    static RenderPhase.Target getWeatherTarget() {
        throw new AssertionError();
    }
    @Accessor("CLOUDS_TARGET")
    static RenderPhase.Target getCloudsTarget() {
        throw new AssertionError();
    }
    @Accessor("ITEM_TARGET")
    static RenderPhase.Target getItemTarget() {
        throw new AssertionError();
    }

    @Accessor("FULL_LINE_WIDTH")
    static RenderPhase.LineWidth getFullLineWidth() {
        throw new AssertionError();
    }
}
