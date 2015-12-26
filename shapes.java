//	Contains the multiple shapes of the project
//
//  shapes.java
//
// @author: Martin Suarez
//

public class shapes extends simpleShape {
	
	final float PI = (float)Math.PI;
	final float twoPI = 2.0f * PI;

    /**
     * Object selection variables
     */
    public static final int CUBE = 0;
    public static final int CYLINDER = 1;
    public static final int CONE = 2;
    public static final int SPHERE = 3;
    
    /**
     * Constructor
     */
    public shapes() {
    }
    
    //
    // makeCube - Create a unit cube, centered at the origin, with a given
    // number of subdivisions in each direction on each face.
    //
    // @param subdivision - number of equal subdivisons to be made in each
    //        direction along each face
    //
    // Can only use calls to addTriangle()
    ///
    public void makeCube (int subdivisions)
    {
        if( subdivisions < 1 )
            subdivisions = 1;
        
        float x = -0.5f; // Initial x of each face
        float y = -0.5f; // Initial y of each face
        float nX, nY; // next x and next y
        float of = 0.5f; // face offset
        float offset = 1.0f/subdivisions; // cell offset
        
        for ( int i = 0; i < subdivisions; i++ ) {
        	nX = x + offset;
        	for ( int j = 0; j < subdivisions; j++ ) {
        		nY = y + offset;		
        		// Face A
				addTriangle(
					nX, nY, of,
					x, nY, of,
					x, y, of );
				addTriangle(
					x, y, of,
					nX, y, of,
					nX, nY, of );
				// Face B
				addTriangle(
					x, y, -of,
					x, nY, -of,
					nX, nY, -of );
				addTriangle(
					nX, nY, -of,
					nX, y, -of,
					x, y, -of );
				
				// Face C
				addTriangle(
					nY, -of, nX,
					y, -of, nX,
					y, -of, x );
				addTriangle(
					y, -of, x,
					nY, -of, x,
					nY, -of, nX );
				
				// Face D
				addTriangle(
					y, of, x,
					y, of, nX,
					nY, of, nX );
				addTriangle(
					nY, of, nX,
					nY, of, x,
					y, of, x );
				
				// Face E
				addTriangle(
					of, nX, nY,
					of, x, nY,
					of, x, y );
				addTriangle(
					of, x, y,
					of, nX, y,
					of, nX, nY );
				// Face F
				addTriangle(
					-of, x, y,
					-of, x, nY,
					-of, nX, nY );
				addTriangle(
					-of, nX, nY,
					-of, nX, y,
					-of, x, y );

				y += offset;
			}
			y = -0.5f;
			x += offset;
		}
        
    }

    ///
    // makeCylinder - Create polygons for a cylinder with unit height, centered
    // at the origin, with separate number of radial subdivisions and height
    // subdivisions.
    //
    // @param radius - Radius of the base of the cylinder
    // @param radialDivision - number of subdivisions on the radial base
    // @param heightDivisions - number of subdivisions along the height
    //
    // Can only use calls to addTriangle()
    ///
    public void makeCylinder (float radius, int radialDivisions, int heightDivisions)
    {
        if( radialDivisions < 3 )
            radialDivisions = 3;

        if( heightDivisions < 1 )
            heightDivisions = 1;
        
        float step = twoPI/radialDivisions; // face step
        float x,y,angle;
        float h = 0.5f; 
        float hstep = h*2/heightDivisions; // shaft step
        float nextX, nextY;
                
        for (angle = 0.0f; angle < twoPI; angle += step)  {
        	// Update current
        	x = radius * (float)Math.sin( angle );
        	y = radius * (float)Math.cos( angle );
        	// Update future
			nextX = radius * (float)Math.sin( angle + step ); // next x
			nextY = radius * (float)Math.cos( angle + step ); // next y
        	
        	// Face
        	addTriangle(
        		0, 0, h,
        		x, y, h,
        		nextX, nextY, h
        	);
        	
        	// Shaft
        	float he = h;   // Used to partition shaft
        	for ( int j = 0; j < heightDivisions; j++ ) {
				addTriangle(
					nextX, nextY, he,
					x, y, he,
					x, y, he-hstep 
				);
				
				addTriangle(
					x, y, he-hstep,
					nextX, nextY, he-hstep, 
					nextX, nextY, he
				);
				he -= hstep;
			}
			
			// Back Face
			addTriangle(
        		x, y, -h,
        		0, 0, -h,
        		nextX, nextY, -h
        	);
        }
    }

    ///
    // makeCone - Create polygons for a cone with unit height, centered at the
    // origin, with separate number of radial subdivisions and height
    // subdivisions.
    //
    // @param radius - Radius of the base of the cone
    // @param radialDivision - number of subdivisions on the radial base
    // @param heightDivisions - number of subdivisions along the height
    //
    // Can only use calls to addTriangle()
    ///
    public void makeCone (float radius, int radialDivisions, int heightDivisions)
    {
        if( radialDivisions < 3 )
            radialDivisions = 3;

        if( heightDivisions < 1 )
            heightDivisions = 1;
        
        float step = twoPI/radialDivisions; // face step
        float x,y,angle;
        float h = 0.5f;
        float hstep = h*2/heightDivisions; // shaft step
        float rstep = radius/heightDivisions; // radius step
        float nextX, nextY;
        
        for (angle = 0.0f; angle < twoPI; angle += step)  {
        	// Update current
        	x = radius * (float)Math.sin( angle );
        	y = radius * (float)Math.cos( angle );
        	// Update future
			nextX = radius * (float)Math.sin( angle + step ); // next x
			nextY = radius * (float)Math.cos( angle + step ); // next y

        	// Face
			addTriangle(
        		0, 0, h,
        		x, y, h,
        		nextX, nextY, h
        	);
        	
        	
        	
        	// Draw shaft
			float rad_delta = radius; // Holds next shaft radius
			float xd, yd, nextXd, nextYd; // Holds future values
			float he = h;  // Used to partition the shaft
			for ( int j = 0; j < heightDivisions; j++ ) {
				// Update future
				rad_delta -= rstep;
				xd = rad_delta * (float)Math.sin( angle );
				yd = rad_delta * (float)Math.cos( angle );
				nextXd = rad_delta * (float)Math.sin( angle + step ); // next x
        		nextYd = rad_delta * (float)Math.cos( angle + step ); // next y
        		
        		addTriangle(
					nextX, nextY, he,
					x, y, he,
					xd, yd, he-hstep 
				);
				
				addTriangle(
					xd, yd, he-hstep,
					nextXd, nextYd, he-hstep, 
					nextX, nextY, he
				);
				
				// Update current
				x = rad_delta * (float)Math.sin( angle );
				y = rad_delta * (float)Math.cos( angle );
				nextX = rad_delta * (float)Math.sin( angle + step ); // next x
        		nextY = rad_delta * (float)Math.cos( angle + step ); // next y
        		he -= hstep;
        		
			}
        }
        
    }

    ///
    // makeSphere - Create sphere of a given radius, centered at the origin,
    // using spherical coordinates with separate number of thetha and
    // phi subdivisions.
    //
    // @param radius - Radius of the sphere
    // @param slides - number of subdivisions in the theta direction
    // @param stacks - Number of subdivisions in the phi direction.
    //
    // Can only use calls to addTriangle
    ///
    public void makeSphere (float radius, int slices, int stacks)
    {
        if( slices < 3 )
            slices = 3;

        if( stacks < 3 )
            stacks = 3;
        
        float theta_step = twoPI/slices; // theta step
        float phi_step = PI/stacks; // phi step
        float theta, phi;
        float n_phi, n_theta;
        float x,y,z,x2,y2,z2,x3,y3,x4,y4; // futures

        for (theta = 0.0f; theta < twoPI; theta += theta_step)  {
        	for (phi = 0.0f; phi < PI; phi += phi_step)  {
        		// Update angles
        		n_phi = phi + phi_step;
        		n_theta = theta + theta_step;
        		// Update current (lower left)
        		x = radius * (float)Math.cos( theta ) * (float)Math.sin( phi );
        		y = radius * (float)Math.sin( theta ) * (float)Math.sin( phi );
        		z = radius * (float)Math.cos( phi );
        		// Update future upper left 
        		x2 = radius * (float)Math.cos( theta ) * (float)Math.sin( n_phi );
        		y2 = radius * (float)Math.sin( theta ) * (float)Math.sin( n_phi );
        		z2 = radius * (float)Math.cos( n_phi );
        		// Update future upper right 
        		x3 = radius * (float)Math.cos( n_theta ) * (float)Math.sin( n_phi );
        		y3 = radius * (float)Math.sin( n_theta ) * (float)Math.sin( n_phi );
        		// Update future lower right 
        		x4 = radius * (float)Math.cos( n_theta ) * (float)Math.sin( phi );
        		y4 = radius * (float)Math.sin( n_theta ) * (float)Math.sin( phi );
        		
        		// Draw cell
        		addTriangle(
					x, y, z,
					x2, y2, z2,
					x3, y3, z2 );
				addTriangle(
					x3, y3, z2,
					x4, y4, z,
					x, y, z );
				
        	}
        }
    }

    // Chooses which shape to draw
    public void makeShape( int choice ) {
        if( choice == shapes.CUBE )
            makeCube( 5 );
        else if( choice == shapes.CYLINDER)
        	makeCylinder( 0.5f, 30, 30 );
        else if( choice == shapes.CONE)
        	makeCone( 0.5f, 30, 30 );
        else
        	makeSphere( 0.5f, 30, 30 );
    }
}
