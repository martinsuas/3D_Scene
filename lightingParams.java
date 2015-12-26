// @author: Martin Suarez
//
// lightingParams.java
//
// Simple class for setting up the viewing and projection transforms
// for the Shading Assignment.
//
// Students are to complete this class.
// Martin Suarez

import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.*; 

public class lightingParams
{
    // Add any global class variables you need here.

    /**
     * constructor
     */
    public lightingParams()
    {
      
    }
    /**
     * This functions sets up the lighting, material, and shading parameters
     * for the Phong shader.
     *
     * You will need to write this function, and maintain all of the values
     * needed to be sent to the vertex shader.
     *
     * @param program - The ID of an OpenGL (GLSL) shader program to which
     * parameter values are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpPhong (int program, GL2 gl2,
    	float aR, float aG, float aB, float dR, float dG, float dB,
    	float ka, float kd)
    {
        // Ambient light
        gl2.glUniform4f( (gl2.glGetUniformLocation(program,"ambient_light_col")),
        				  0.5f, 0.5f, 0.5f, 1.0f);
        // Source light
        gl2.glUniform4f( (gl2.glGetUniformLocation(program,"lPosition")),
        				  -4.0f, 10.0f, -7.0f, 1.0f);
        gl2.glUniform4f( (gl2.glGetUniformLocation(program,"source_light_col")),
        				  0.0f, 5.0f, 2.0f, 1.0f);
        
        // Material Properties
        gl2.glUniform4f( (gl2.glGetUniformLocation(program,"m_ambient")),
        				  aR, aG, aB, 1.0f);
        gl2.glUniform4f( (gl2.glGetUniformLocation(program,"ka")),
        				  ka, ka, ka, 1.0f);
        gl2.glUniform4f( (gl2.glGetUniformLocation(program,"m_diffuse")),
        				  dR, dG, dB, 1.0f);
        gl2.glUniform4f( (gl2.glGetUniformLocation(program,"kd")),
        				  kd, kd, kd, 1.0f);
        gl2.glUniform4f( (gl2.glGetUniformLocation(program,"m_specular")),
        				  1.0f, 1.0f, 1.0f, 1.0f);
        gl2.glUniform4f( (gl2.glGetUniformLocation(program,"ks")),
        				  1.0f, 1.0f, 1.0f, 1.0f);
        gl2.glUniform1f( (gl2.glGetUniformLocation(program,"n")),
        				  10.0f);
    }
}
