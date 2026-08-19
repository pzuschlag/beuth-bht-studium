/*
 * WebGL core teaching framwork
 * (C)opyright Hartmut Schirmacher, hschirmacher.beuth-hochschule.de
 *
 * Vertex Shader: Per-Vertex-Color
 *
 * This shader expects each vertex to come with two attributes:
 * vertexPosition and vertexColor.
 *
 * the vertex position is transformed by modelViewMatrix and
 * projectionMatrix; vertexColor is "passed through" to the
 * fragment shader using a varying variable named fragColor.
 *
 */
//uniform mat4 modelview - hier unnötig, denn three.js fügt sie automatisch ein
//varying vec2 vUv
//uniform float pos - globale variable, die der shader bekommt ()

varying vec3 fragColor;

void main() {
     gl_Position = projectionMatrix *
                   modelViewMatrix *
                   vec4(position,1.0); //die 1.0 ist der alphakanal
     gl_PointSize = 3.0;
     fragColor = color;
}
