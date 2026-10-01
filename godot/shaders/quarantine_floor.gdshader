shader_type spatial;
render_mode diffuse_burley, specular_schlick_ggx;

uniform vec3 base_color : source_color = vec3(0.038, 0.047, 0.052);
uniform vec3 slab_color : source_color = vec3(0.060, 0.072, 0.078);
uniform vec3 grid_color : source_color = vec3(0.055, 0.34, 0.46);
uniform float grid_emission = 0.18;
uniform float roughness_value = 0.90;
uniform float metallic_value = 0.06;

float containment_grid(vec2 uv) {
    vec2 cell = abs(fract(uv) - vec2(0.5));
    float edge = min(0.5 - cell.x, 0.5 - cell.y);
    return 1.0 - smoothstep(0.0, 0.028, edge);
}

void fragment() {
    vec2 arena_uv = UV * 36.0;
    vec2 slab_uv = UV * 9.0;

    float grid = containment_grid(arena_uv);
    float slab = step(0.5, fract(floor(slab_uv.x) * 0.618 + floor(slab_uv.y) * 0.414));
    float diagonal = smoothstep(0.82, 1.0, sin((UV.x + UV.y) * 90.0) * 0.5 + 0.5) * 0.06;

    vec3 surface = mix(base_color, slab_color, slab * 0.34);
    surface += diagonal * vec3(0.055, 0.060, 0.062);
    surface = mix(surface, grid_color, grid * 0.34);

    ALBEDO = surface;
    ROUGHNESS = roughness_value;
    METALLIC = metallic_value;
    EMISSION = grid_color * grid * grid_emission;
}
