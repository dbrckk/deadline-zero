package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;

/**
 * Optional low-cost fullscreen shader used later for subtle glow/color grading.
 * Failure is non-fatal: callers can fall back to the default SpriteBatch shader.
 */
public final class PostFxShader implements Disposable {
    private static final String VERTEX = "attribute vec4 a_position;\n" +
        "attribute vec4 a_color;\n" +
        "attribute vec2 a_texCoord0;\n" +
        "uniform mat4 u_projTrans;\n" +
        "varying vec4 v_color;\n" +
        "varying vec2 v_texCoords;\n" +
        "void main(){ v_color=a_color; v_texCoords=a_texCoord0; gl_Position=u_projTrans*a_position; }\n";

    private static final String FRAGMENT = "#ifdef GL_ES\nprecision mediump float;\n#endif\n" +
        "varying vec4 v_color;\n" +
        "varying vec2 v_texCoords;\n" +
        "uniform sampler2D u_texture;\n" +
        "uniform float u_intensity;\n" +
        "void main(){\n" +
        "  vec4 c=texture2D(u_texture,v_texCoords)*v_color;\n" +
        "  float l=max(max(c.r,c.g),c.b);\n" +
        "  vec3 lifted=c.rgb + c.rgb*max(0.0,l-0.55)*u_intensity;\n" +
        "  lifted=pow(max(lifted,vec3(0.0)),vec3(0.96));\n" +
        "  gl_FragColor=vec4(lifted,c.a);\n" +
        "}\n";

    private ShaderProgram shader;
    private boolean available;

    public PostFxShader() {
        ShaderProgram.pedantic = false;
        try {
            shader = new ShaderProgram(VERTEX, FRAGMENT);
            available = shader.isCompiled();
            if (!available) Gdx.app.log("PostFxShader", shader.getLog());
        } catch (RuntimeException ex) {
            available = false;
        }
    }

    public boolean available() { return available; }

    public ShaderProgram shader(float intensity) {
        if (!available) return null;
        shader.bind();
        shader.setUniformf("u_intensity", Math.max(0f, Math.min(1f, intensity)));
        return shader;
    }

    @Override public void dispose() {
        if (shader != null) shader.dispose();
    }
}
