package org.apache.maven.model.immutable;

import org.codehaus.plexus.util.StringUtils;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kristian on 19.09.15.
 */
public class ImmutableModelBuilder
{

    public Node buildModel( InputStream source )
        throws XMLStreamException
    {
        XMLInputFactory2 xmlInputFactory = (XMLInputFactory2) XMLInputFactory.newInstance();
        XMLStreamReader2 streamReader = (XMLStreamReader2) xmlInputFactory.createXMLStreamReader( source );
        List<List<Node>> kids = new ArrayList<>( 10 );
        for ( int i = 0; i < 10; i++ )
        {
            kids.add( new ArrayList<Node>() );
        }

        int indent = 0;
        while ( streamReader.hasNext() )
        {
            int eventType = streamReader.next();
            switch ( eventType )
            {
                case XMLStreamReader2.START_ELEMENT:
                    writeOutIndent( indent );
                    System.out.print( "<" + streamReader.getLocalName() );
                    for ( int x = 0; x < streamReader.getAttributeCount(); x++ )
                    {
                        System.out.print( streamReader.getAttributeName( x ) );
                        System.out.print( "=" );
                        System.out.print( streamReader.getAttributeValue( x ) + "\"" );

                    }
                    System.out.println( ">" );
                    indent++;
                    break;
                case XMLStreamReader2.END_ELEMENT:
                    String localName = streamReader.getLocalName();
                    List<Node> nodes = kids.get( indent );
                    Node result = new Node( localName, nodes.toArray( new Node[nodes.size()] ) );
                    kids.get( indent ).clear();
                    indent--;
                    kids.get( indent ).add( result );
                    if ( indent == 0 )
                    {
                        return result;
                    }
                    writeOutIndent( indent );
                    System.out.println( "</" + localName + ">" );
                    break;

                case XMLStreamReader2.CHARACTERS:
                    writeOutIndent( indent );
                    String text = streamReader.getText();
                    if ( !StringUtils.isEmpty( text ) )
                    {
                        System.out.println( text );
                    }
                    break;

                case XMLStreamReader2.ATTRIBUTE:
                    break;
            }

            System.out.println( "kids = " + kids );
            //... more event types handled here...
        }
        throw new IllegalStateException( "Unsupported nesting" );
    }

    private void writeOutIndent( int indent )
    {
        for ( int i = 0; i < indent; i++ )
        {
            System.out.print( " " );
        }
    }
}
