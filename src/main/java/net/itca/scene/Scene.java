package net.itca.scene;

import net.itca.geometry.Renderable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * Created by dylan on 11.02.18.
 * Represents a scene with renderable objects
 */
public class Scene {
    @NotNull
    private final List<Renderable> renderables;

    public Scene(@Nullable List<Renderable> renderables){
        this.renderables = renderables == null ? Collections.<Renderable>emptyList() : renderables;
    }

    @NotNull
    public List<Renderable> getRenderables(){
        return renderables;
    }
}
