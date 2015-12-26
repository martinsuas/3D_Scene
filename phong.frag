#version 120
// @author: Martin Suarez
// Phong shading fragment shader

// Ambient and Source Light colors
uniform vec4 ambient_light_col, source_light_col;

// Material Properties
uniform vec4 m_ambient, m_diffuse, m_specular, ka, kd, ks;
uniform float n;

// Vectors
varying vec4 N, L, R, V;

void main()
{
	// Calculate ambient
    vec4 I_amb = ambient_light_col * ka * m_ambient;
    // Calculate diffuse
	vec4 I_diff = kd * m_diffuse * max( 0.0, dot(L, N) );
	// Calculate specular
	vec4 I_spec = ks * m_specular * pow( max ( dot(V, R), 0.0 ), n );
	
	// Set color
    gl_FragColor = I_amb + I_diff + I_spec;
}
