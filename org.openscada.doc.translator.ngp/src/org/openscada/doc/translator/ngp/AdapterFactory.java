package org.openscada.doc.translator.ngp;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.openscada.doc.content.Cell;
import org.openscada.doc.content.Column;
import org.openscada.doc.content.ContentFactory;
import org.openscada.doc.content.CrossReference;
import org.openscada.doc.content.ListItem;
import org.openscada.doc.content.Paragraph;
import org.openscada.doc.content.Row;
import org.openscada.doc.content.Section;
import org.openscada.doc.content.SimpleTable;
import org.openscada.doc.content.UnorderedList;
import org.openscada.doc.model.doc.fragment.Content;
import org.openscada.doc.model.doc.map.SectionMarker;
import org.openscada.protocol.ngp.model.Protocol.Attribute;
import org.openscada.protocol.ngp.model.Protocol.AttributeStructure;
import org.openscada.protocol.ngp.model.Protocol.Enum;
import org.openscada.protocol.ngp.model.Protocol.Interface;
import org.openscada.protocol.ngp.model.Protocol.Message;
import org.openscada.protocol.ngp.model.Protocol.Protocol;
import org.openscada.protocol.ngp.model.Protocol.Structure;

public class AdapterFactory implements IAdapterFactory
{

    @SuppressWarnings ( "rawtypes" )
    @Override
    public Object getAdapter ( final Object adaptableObject, final Class adapterType )
    {
        if ( adaptableObject instanceof Protocol && SectionMarker.class.equals ( adapterType ) )
        {
            return makeAdapter ( (Protocol)adaptableObject );
        }
        return null;
    }

    @SuppressWarnings ( "rawtypes" )
    @Override
    public Class[] getAdapterList ()
    {
        return new Class[] { Content.class };
    }

    private SectionMarker makeAdapter ( final Protocol protocol )
    {
        return new SectionMarker () {

            @Override
            public String getTitle ()
            {
                return protocol.getName ();
            }

            @Override
            public String getId ()
            {
                return makeRef ( protocol );
            }

            private String makeRef ( final Protocol protocol )
            {
                return makeRefId ( protocol );
            }

            @Override
            public List<? extends Object> getContent ()
            {
                return createContent ( protocol );
            }
        };
    }

    private String makeRefId ( final Protocol protocol )
    {
        return "protocol." + protocol.getName ();
    }

    private String makeRefId ( final Interface i )
    {
        return String.format ( "interface.%s.%s", i.getProtocol ().getName (), i.getName () );
    }

    private String makeRefId ( final Message message )
    {
        return String.format ( "message.%s.%s", message.getProtocol ().getName (), message.getName () );
    }

    protected List<? extends Object> createContent ( final Protocol protocol )
    {
        final List<Object> result = new LinkedList<Object> ();

        if ( protocol.getDescription () != null )
        {
            final Paragraph p = ContentFactory.eINSTANCE.createParagraph ();
            FeatureMapUtil.addText ( p.getContent (), protocol.getDescription () );
            result.add ( p );
        }

        if ( !protocol.getIncludedProtocols ().isEmpty () )
        {
            result.add ( makeText ( "This protocol inherits from the following protocols:" ) );
            final UnorderedList ul = ContentFactory.eINSTANCE.createUnorderedList ();
            result.add ( ul );
            for ( final Protocol ref : protocol.getIncludedProtocols () )
            {
                final ListItem item = ContentFactory.eINSTANCE.createListItem ();
                final CrossReference xref = ContentFactory.eINSTANCE.createCrossReference ();
                xref.setType ( "section" );
                xref.setId ( makeRefId ( ref ) );
                FeatureMapUtil.addText ( xref.getContent (), ref.getName () );
                item.getXref ().add ( xref );
                ul.getItem ().add ( item );
            }
        }

        result.add ( makeText ( String.format ( "The protocol version is: %s", protocol.getVersion () ) ) );

        {
            final Section s = ContentFactory.eINSTANCE.createSection ();
            s.setTitle ( "Enums" );
            result.add ( s );
            createEnums ( s, protocol );
        }

        {
            final Section s = ContentFactory.eINSTANCE.createSection ();
            s.setTitle ( "Interfaces" );
            result.add ( s );
            createInterfaces ( s, protocol );
        }

        {
            final Section s = ContentFactory.eINSTANCE.createSection ();
            s.setTitle ( "Structures" );
            result.add ( s );
            createStructures ( s, protocol );
        }

        {
            final Section s = ContentFactory.eINSTANCE.createSection ();
            s.setTitle ( "Messages" );
            result.add ( s );
            createMessages ( s, protocol );
        }

        return result;
    }

    private void createMessages ( final Section s, final Protocol protocol )
    {
        if ( protocol.getMessages ().isEmpty () )
        {
            createEmptyMarker ( s, "messages" );
            return;
        }

        s.getP ().add ( makeText ( "The following messages are defined:" ) );

        // overview map

        final SimpleTable t = ContentFactory.eINSTANCE.createSimpleTable ();
        s.getSimpleTable ().add ( t );

        t.getColumn ().add ( makeCol ( "Name" ) );
        t.getColumn ().add ( makeCol ( "Code" ) );

        // details

        for ( final Message message : protocol.getMessages () )
        {
            // overview entry

            final Row row = ContentFactory.eINSTANCE.createRow ();
            row.getCell ().add ( makeXRefCell ( message.getName (), "section", makeRefId ( message ) ) );
            row.getCell ().add ( makeCell ( String.format ( "0x%04X", message.getCode () ) ) );
            t.getRow ().add ( row );

            // section

            final Section s1 = makeProtocolSubSection ( s, message );

            s1.getP ().add ( makeText ( String.format ( "The message code is: 0x%04X", message.getCode () ) ) );
            s1.setId ( makeRefId ( message ) );

            renderAttributeStructure ( s1, message );

            if ( !message.getInterfaces ().isEmpty () )
            {
                s1.getP ().add ( makeText ( "The message implements the following interfaces:" ) );

                final UnorderedList ul = ContentFactory.eINSTANCE.createUnorderedList ();
                s1.getUl ().add ( ul );

                for ( final Interface i : message.getInterfaces () )
                {
                    final ListItem item = ContentFactory.eINSTANCE.createListItem ();
                    ul.getItem ().add ( item );
                    final CrossReference xref = ContentFactory.eINSTANCE.createCrossReference ();
                    xref.setType ( "section" );
                    xref.setId ( makeRefId ( i ) );
                    FeatureMapUtil.addText ( xref.getContent (), i.getName () );
                    item.getXref ().add ( xref );
                }
            }
        }
    }

    private void createStructures ( final Section s, final Protocol protocol )
    {
        if ( protocol.getStructures ().isEmpty () )
        {
            createEmptyMarker ( s, "structures" );
            return;
        }

        s.getP ().add ( makeText ( "The following structures are defined:" ) );

        for ( final Structure struct : protocol.getStructures () )
        {
            final Section s1 = makeProtocolSubSection ( s, struct );
            renderAttributeStructure ( s1, struct );
        }
    }

    private void createInterfaces ( final Section s, final Protocol protocol )
    {
        if ( protocol.getInterfaces ().isEmpty () )
        {
            createEmptyMarker ( s, "interfaces" );
            return;
        }

        s.getP ().add ( makeText ( "The following interfaces are defined:" ) );

        for ( final Interface i : protocol.getInterfaces () )
        {
            final Section s1 = makeProtocolSubSection ( s, i );
            s1.setId ( makeRefId ( i ) );
            renderAttributeStructure ( s1, i );
        }
    }

    protected void renderAttributeStructure ( final Section s1, final AttributeStructure as )
    {
        final SimpleTable t = ContentFactory.eINSTANCE.createSimpleTable ();
        s1.getSimpleTable ().add ( t );

        t.getColumn ().add ( makeCol ( "#" ) );
        t.getColumn ().add ( makeCol ( "Name" ) );
        t.getColumn ().add ( makeCol ( "Type" ) );
        t.getColumn ().add ( makeCol ( "Type Modifier" ) );

        for ( final Attribute attr : as.getAttributes () )
        {
            final Row r = ContentFactory.eINSTANCE.createRow ();
            t.getRow ().add ( r );
            r.getCell ().add ( makeCell ( String.format ( "%d", attr.getFieldNumber () ) ) );
            r.getCell ().add ( makeCell ( attr.getName () ) );
            r.getCell ().add ( makeCell ( makeTypeName ( attr ) ) );
            r.getCell ().add ( makeCell ( String.format ( "%s", attr.getType () ) ) );
        }
    }

    private Section makeProtocolSubSection ( final Section s, final AttributeStructure as )
    {
        final Section s1 = makeSubSection ( s, as.getName () );

        if ( as.getDescription () != null )
        {
            s1.getP ().add ( makeText ( as.getDescription () ) );
        }
        return s1;
    }

    private String makeTypeName ( final Attribute attr )
    {
        final String name = attr.getClass ().getSimpleName ();
        if ( name.endsWith ( "AttributeImpl" ) ) //$NON-NLS-1$
        {
            return name.substring ( 0, name.length () - "AttributeImpl".length () ); //$NON-NLS-1$
        }
        return name;
    }

    private void createEnums ( final Section s, final Protocol protocol )
    {
        if ( protocol.getEnums ().isEmpty () )
        {
            createEmptyMarker ( s, "enums" );
            return;
        }

        s.getP ().add ( makeText ( "The following enums are defined:" ) );

        for ( final Enum e : protocol.getEnums () )
        {
            final Section s1 = makeSubSection ( s, e.getName () );
            s1.getP ().add ( makeText ( e.getDescription () ) );

            final SimpleTable t = ContentFactory.eINSTANCE.createSimpleTable ();
            s1.getSimpleTable ().add ( t );

            t.getColumn ().add ( makeCol ( "Ordinal" ) );
            t.getColumn ().add ( makeCol ( "Literal" ) );

            int i = 0;
            for ( final String name : e.getLiterals () )
            {
                final Row r = ContentFactory.eINSTANCE.createRow ();
                t.getRow ().add ( r );
                r.getCell ().add ( makeCell ( String.format ( "%d", i ) ) );
                r.getCell ().add ( makeCell ( name ) );
                i++;
            }
        }
    }

    private Section makeSubSection ( final Section s, final String name )
    {
        final Section s1 = ContentFactory.eINSTANCE.createSection ();
        s1.setTitle ( name );
        s.getSection ().add ( s1 );
        return s1;
    }

    private Cell makeCell ( final String string )
    {
        final Cell cell = ContentFactory.eINSTANCE.createCell ();
        if ( string != null )
        {
            FeatureMapUtil.addText ( cell.getContent (), string );
        }
        return cell;
    }

    private Cell makeXRefCell ( final String name, final String type, final String id )
    {
        final Cell cell = ContentFactory.eINSTANCE.createCell ();
        final CrossReference xref = ContentFactory.eINSTANCE.createCrossReference ();
        xref.setId ( id );
        xref.setType ( type );
        FeatureMapUtil.addText ( xref.getContent (), name );

        cell.getXref ().add ( xref );

        return cell;
    }

    private Column makeCol ( final String title )
    {
        final Column col = ContentFactory.eINSTANCE.createColumn ();
        col.setTitle ( title );
        return col;
    }

    private void createEmptyMarker ( final Section s, final String string )
    {
        s.getP ().add ( makeText ( String.format ( "No %s defined.", string ) ) );
    }

    private static Paragraph makeText ( final String text )
    {
        final Paragraph p = ContentFactory.eINSTANCE.createParagraph ();
        if ( text != null )
        {
            FeatureMapUtil.addText ( p.getContent (), text );
        }
        return p;
    }
}
