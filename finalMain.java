// @author: Martin Suarez
//
// finalMain.java
//
// Main class for final assignment
//

import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.*;
import com.jogamp.opengl.util.Animator;

public class finalMain implements GLEventListener, KeyListener
{

    /**
     * We set 3 array buffers for every shape that we draw.
     */
    private int vbuffer[];
    private int ebuffer[];
    private int numVerts[];


    /**
     * Program IDs - current, and all variants
     */
    public int program;
    public int phong;


    /**
     * Shape info
     */
    shapes myShape;

    /**
     * Lighting information
     */
    lightingParams myPhong;

    /**
     * Viewing information
     */
    viewParams myView;

    /**
     * My canvas
     */
    GLCanvas myCanvas;
    private static Frame frame;

    /**
     * Constructor
     */
    public finalMain( GLCanvas G )
    {
        vbuffer = new int[4];
        ebuffer = new int[4];
        numVerts = new int[4];

        myCanvas = G;

        // Initialize lighting and view
        myPhong = new lightingParams();
        myView = new viewParams();

        // Set up event listeners
        G.addGLEventListener (this);
        G.addKeyListener (this);
    }

    private void errorCheck (GL2 gl2)
    {
        int code = gl2.glGetError();
        if (code == GL.GL_NO_ERROR)
            System.err.println ("All is well");
        else
            System.err.println ("Problem - error code : " + code);
    }


    /**
     * Called by the drawable to initiate OpenGL rendering by the client.
     */
    public void display(GLAutoDrawable drawable)
    {
        // get GL
        GL2 gl2 = (drawable.getGL()).getGL2();   
        
        // clear and draw params..
        gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );

        // use the correct program
        gl2.glUseProgram( program );

        // set up viewing and projection parameters
        myView.setUpFrustum( program, gl2 );

        // DRAW SCENE
         // set up the camera
        myView.setUpCamera( program, gl2,
            //0.0f, 2.0f, 4.0f,
            //0.0f, 0.0f, -20.0f,
            -7f, 4f, -4f,
            0f, 0f, -5f, 
            0.0f, 1.0f, 0.0f
        );
        
        // set up transformations for the table
        myPhong.setUpPhong( program, gl2, 
        	0.6f, 0.5f, 0.4f, // ambient 
        	0.8f, 0.7f, 0.4f, // diffuse
        	0.8f, 0.8f); // ka, kb
        myView.setUpTransforms( program, gl2,
            0, -1f, -1f - 8f,
            0f,
            0f,
            0f,
            13f, 1f, 16f
        );
        
        // draw it
        selectBuffers( gl2, shapes.CUBE );
        gl2.glDrawElements( GL.GL_TRIANGLES,
            numVerts[shapes.CUBE],
            GL.GL_UNSIGNED_SHORT, 0l
        );
        
        // set up transformations for the lolipop ball
        myPhong.setUpPhong( program, gl2, 
        	0.3f, 0.2f, 0.0f, // ambient 
        	0.6f, 0.4f, 0.0f, // diffuse
        	0.5f, 0.5f); // ka, kb
        myView.setUpTransforms( program, gl2,
            -4f, 0.5f, -3f,
            90,
            180,
            0,
            1f, 1f, 1f
        );

        // draw it
        selectBuffers( gl2, shapes.SPHERE);
        gl2.glDrawElements( GL.GL_TRIANGLES,
            numVerts[shapes.SPHERE],
            GL.GL_UNSIGNED_SHORT, 0l
        );
        
        
        // set up transformations for the lollipop stick
        myPhong.setUpPhong( program, gl2, 
        	0.7f, 0.7f, 0.7f, // ambient 
        	1.0f, 1f, 1f, // diffuse
        	0.7f, 0.8f); // ka, kb
        myView.setUpTransforms( program, gl2,
            -4f, 0.5f, -2f,
            0,
            0,
            0,
            0.2f, 0.2f, 1.5f
        );

        // draw it
        selectBuffers( gl2, shapes.CUBE);
        gl2.glDrawElements( GL.GL_TRIANGLES,
            numVerts[shapes.CUBE],
            GL.GL_UNSIGNED_SHORT, 0l
        );

        
        // set up transformations for the ping pong ball
        myPhong.setUpPhong( program, gl2, 
        	0.5f, 0.4f, 0.0f, // ambient 
        	1.0f, 0.8f, 0.0f, // diffuse
        	0.6f, 0.9f); // ka, kb
        myView.setUpTransforms( program, gl2,
            0f, 1.75f, -5f,
            0,
            0,
            0,
            1.5f, 1.5f, 1.5f
        );

        // draw it
        selectBuffers( gl2, shapes.SPHERE);
        gl2.glDrawElements( GL.GL_TRIANGLES,
            numVerts[shapes.SPHERE],
            GL.GL_UNSIGNED_SHORT, 0l
        );
        
        
        // set up transformations for the book
        myPhong.setUpPhong( program, gl2, 
        	0.6f, 0.4f, 0.5f, // ambient 
        	0.7f, 0.7f, 0.6f, // diffuse
        	0.4f, 0.6f); // ka, kb
        myView.setUpTransforms( program, gl2,
            0f, 0.0f, -5f,
            0,
            0,
            0,
            5f, 2f, 7f
        );
        
        // draw it
        selectBuffers( gl2, shapes.CUBE);
        gl2.glDrawElements( GL.GL_TRIANGLES,
            numVerts[shapes.CUBE],
            GL.GL_UNSIGNED_SHORT, 0l
        );
    }

    /**
     * Notifies the listener to perform the release of all OpenGL
     * resources per GLContext, such as memory buffers and GLSL
     * programs.
     */
    public void dispose(GLAutoDrawable drawable)
    {
    }

    /**
     * Verify shader creation
     */
    private void checkShaderError( shaderSetup myShaders, int program,
        String which )
    {
        if( program == 0 ) {
            System.err.println( "Error setting " + which +
                " shader - " +
                myShaders.errorString(myShaders.shaderErrorCode)
            );
            System.exit( 1 );
        }
    }

    /**
     * Called by the drawable immediately after the OpenGL context is
     * initialized.
     */
    public void init(GLAutoDrawable drawable)
    {
        // get the gl object
        GL2 gl2 = drawable.getGL().getGL2();

        // Load shaders, verifying each
        shaderSetup myShaders = new shaderSetup();

        phong = myShaders.readAndCompile( gl2, "phong.vert", "phong.frag" );
        checkShaderError( myShaders, phong, "phong" );

        // Default shader program
        program = phong;

        // Create all four shapes
        createShape( gl2, shapes.CUBE );
        createShape( gl2, shapes.CYLINDER);
        createShape( gl2, shapes.CONE );
        createShape( gl2, shapes.SPHERE );
                                                               
        // Other GL initialization
        gl2.glEnable( GL.GL_DEPTH_TEST );
        gl2.glEnable( GL.GL_CULL_FACE );
        gl2.glCullFace(  GL.GL_BACK );
        gl2.glFrontFace( GL.GL_CCW );
        gl2.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
        gl2.glDepthFunc( GL.GL_LEQUAL );
        gl2.glClearDepth( 1.0f );
        
    }


    /**
     * Called by the drawable during the first repaint after the component
     * has been resized.
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                     int height)
    {
    }


    /**
     * Create vertex and element buffers for a shape
     */
    public void createShape(GL2 gl2, int obj )
    {
        // clear the old shape
        myShape = new shapes();

        // make the shape
        myShape.makeShape( obj );

        
        // save the vertex count
        numVerts[obj] = myShape.nVertices();

        // get the vertices
        Buffer points = myShape.getVertices();
        long dataSize = myShape.nVertices() * 4l * 4l;

        // get the normals
        Buffer normals = myShape.getNormals();
        long ndataSize = myShape.nVertices() * 3l * 4l;

        // get the element data
        Buffer elements = myShape.getElements();
        long edataSize = myShape.nVertices() * 2l;

        // generate the vertex buffer
        int bf[] = new int[1];

        gl2.glGenBuffers( 1, bf, 0 );
        vbuffer[obj] = bf[0];
        gl2.glBindBuffer( GL.GL_ARRAY_BUFFER, vbuffer[obj] );
        gl2.glBufferData( GL.GL_ARRAY_BUFFER, dataSize + ndataSize, null,
        GL.GL_STATIC_DRAW );
        gl2.glBufferSubData( GL.GL_ARRAY_BUFFER, 0, dataSize, points );
        gl2.glBufferSubData( GL.GL_ARRAY_BUFFER, dataSize, ndataSize,
        normals );

        // generate the element buffer
        gl2.glGenBuffers (1, bf, 0);
        ebuffer[obj] = bf[0];
        gl2.glBindBuffer( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[obj] );
        gl2.glBufferData( GL.GL_ELEMENT_ARRAY_BUFFER, edataSize, elements,
            GL.GL_STATIC_DRAW );
         

    }

    /**
     * Bind the correct vertex and element buffers
     *
     * Assumes the correct shader program has already been enabled
     */

    private void selectBuffers( GL2 gl2, int obj )
    {
        // bind the buffers
        gl2.glBindBuffer( GL.GL_ARRAY_BUFFER, vbuffer[obj] );
        gl2.glBindBuffer( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[obj]);

        // calculate the number of bytes of vertex data
        long dataSize = numVerts[obj] * 4l * 4l;

        // set up the vertex attribute variables
        int vPosition = gl2.glGetAttribLocation( program, "vPosition" );
        gl2.glEnableVertexAttribArray( vPosition );
        gl2.glVertexAttribPointer( vPosition, 4, GL.GL_FLOAT, false,
                                       0, 0l );
        int vNormal = gl2.glGetAttribLocation( program, "vNormal" );
        gl2.glEnableVertexAttribArray( vNormal );
        gl2.glVertexAttribPointer( vNormal, 3, GL.GL_FLOAT, false,
                                   0, dataSize );

    }
    

    /**
     * Because I am a Key Listener...we'll only respond to key presses
     */
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    /**
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e)
    {
        // Get the key that was pressed
        char key = e.getKeyChar();

        // Respond appropriately
        switch( key ) {

            case 'q': case 'Q':
		frame.dispose();
                System.exit( 0 );
                break;
        }

        // do a redraw
        myCanvas.display();
    }


    /**
     * main program
     */
    public static void main(String [] args)
    {
        // GL setup
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        // create your tessMain
        finalMain myMain = new finalMain(canvas);


        frame = new Frame("CG - Shading Assignment");
        frame.setSize(600, 600);
        frame.add(canvas);
        frame.setVisible(true);

        // by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
		frame.dispose();
                System.exit(0);
            }
        });
    }
}
