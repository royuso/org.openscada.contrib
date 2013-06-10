package org.openscada.doc.translator.ngp;

import java.util.Collection;
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
import org.openscada.protocol.ngp.model.Protocol.EnumAttribute;
import org.openscada.protocol.ngp.model.Protocol.Interface;
import org.openscada.protocol.ngp.model.Protocol.Message;
import org.openscada.protocol.ngp.model.Protocol.Protocol;
import org.openscada.protocol.ngp.model.Protocol.Structure;
import org.openscada.protocol.ngp.model.Protocol.StructureAttribute;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

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

    private String makeRefId ( final Enum e )
    {
        return String.format ( "enum.%s.%s", e.getProtocol ().getName (), e.getName () );
    }

    private String makeRefId ( final Structure s )
    {
        return String.format ( "structure.%s.%s", s.getProtocol ().getName (), s.getName () );
    }

    private String makeRefId ( final Message message )
    {
        return String.format ( "message.%s.%s", message.getProtocol ().getName (), message.getName () );
    }

    private String makeRefId ( final Protocol p, final AttributeStructure as, final Attribute attribute )
    {
        return String.format ( "attribute.%s.%s.%s", p.getName (), as.getName (), attribute.getName () );
    }

    protected List<? extends Object> createContent ( final Protocol protocol )
    {
        final List<Object> result = new LinkedList<Object> ();

        if ( protocol.getShortDescription () != null )
        {
            result.add ( makeText ( protocol.getShortDescription () ) );
        }
        if ( protocol.getDescription () != null )
        {
            for ( final String p : protocol.getDescription ().split ( "[\\r\\n]+" ) )
            {
                result.add ( makeText ( p ) );
            }
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
        t.getColumn ().add ( makeCol ( "Description" ) );

        // details

        for ( final Message message : protocol.getMessages () )
        {
            // overview entry

            final Row row = ContentFactory.eINSTANCE.createRow ();
            row.getCell ().add ( makeXRefCell ( message.getName (), "section", makeRefId ( message ) ) );
            row.getCell ().add ( makeCell ( String.format ( "0x%04X", message.getCode () ) ) );
            if ( message.getShortDescription () != null )
            {
                row.getCell ().add ( makeCell ( message.getShortDescription () ) );
            }
            t.getRow ().add ( row );

            // section

            final Section s1 = makeProtocolSubSection ( s, message );

            s1.getP ().add ( makeText ( String.format ( "The message code is: 0x%04X", message.getCode () ) ) );
            s1.setId ( makeRefId ( message ) );

            renderAttributeStructure ( protocol, s1, message );

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
            s1.setId ( makeRefId ( struct ) );
            renderAttributeStructure ( protocol, s1, struct );
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
            renderAttributeStructure ( protocol, s1, i );
        }
    }

    private static Predicate<Attribute> longDescriptionAttributes = new Predicate<Attribute> () {

        @Override
        public boolean apply ( final Attribute input )
        {
            return !input.isDeleted () && input.getDescription () != null;
        }
    };

    private static Predicate<Attribute> nonTransientAttributes = new Predicate<Attribute> () {

        @Override
        public boolean apply ( final Attribute input )
        {
            return !input.isDeleted () && !input.isTransient ();
        }
    };

    private static Predicate<Attribute> transientAttributes = new Predicate<Attribute> () {

        @Override
        public boolean apply ( final Attribute input )
        {
            return !input.isDeleted () && input.isTransient ();
        }
    };

    private static Predicate<Attribute> equalityAttributes = new Predicate<Attribute> () {

        @Override
        public boolean apply ( final Attribute input )
        {
            return !input.isDeleted () && input.isEquality ();
        }
    };

    protected void renderAttributeStructure ( final Protocol p, final Section s1, final AttributeStructure as )
    {
        // equality

        final Collection<Attribute> equality = Collections2.filter ( as.getAttributes (), equalityAttributes );
        if ( !equality.isEmpty () )
        {
            s1.getP ().add ( makeText ( "Equality is defined by all of the following attributes:" ) );
            final UnorderedList ul = ContentFactory.eINSTANCE.createUnorderedList ();
            s1.getUl ().add ( ul );

            for ( final Attribute attr : equality )
            {
                final ListItem item = ContentFactory.eINSTANCE.createListItem ();
                ul.getItem ().add ( item );
                FeatureMapUtil.addText ( item.getContent (), attr.getName () );
            }
        }

        // attributes

        final Collection<Attribute> nonTrans = Collections2.filter ( as.getAttributes (), nonTransientAttributes );
        if ( !nonTrans.isEmpty () )
        {
            s1.getP ().add ( makeText ( "The structure consists of the following attributes:" ) );
            renderAttributesTable ( s1, p, as, nonTrans );
        }

        final Collection<Attribute> trans = Collections2.filter ( as.getAttributes (), transientAttributes );
        if ( !trans.isEmpty () )
        {
            s1.getP ().add ( makeText ( "The following attributes are transient and not encoded when transmitted:" ) );
            renderAttributesTable ( s1, p, as, trans );
        }

        // long desc attributes

        for ( final Attribute attr : Iterables.filter ( as.getAttributes (), longDescriptionAttributes ) )
        {
            final Section s2 = makeSubSection ( s1, attr.getName () );
            s2.setId ( makeRefId ( p, as, attr ) );
            renderLongDescription ( s2, attr.getDescription () );
        }
    }

    protected void renderAttributesTable ( final Section s1, final Protocol p, final AttributeStructure as, final Collection<Attribute> attributes )
    {
        final SimpleTable t = ContentFactory.eINSTANCE.createSimpleTable ();
        s1.getSimpleTable ().add ( t );

        t.getColumn ().add ( makeCol ( "#" ) );
        t.getColumn ().add ( makeCol ( "Name" ) );
        t.getColumn ().add ( makeCol ( "Type" ) );
        t.getColumn ().add ( makeCol ( "Modifier" ) );
        t.getColumn ().add ( makeCol ( "Description" ) );

        for ( final Attribute attr : attributes )
        {
            if ( attr.isDeleted () )
            {
                continue;
            }

            final Row r = ContentFactory.eINSTANCE.createRow ();
            t.getRow ().add ( r );
            r.getCell ().add ( makeCell ( String.format ( "%d", attr.getFieldNumber () ) ) );

            if ( attr.getDescription () != null )
            {
                r.getCell ().add ( makeXRefCell ( attr.getName (), "section", makeRefId ( p, as, attr ) ) );
            }
            else
            {
                r.getCell ().add ( makeCell ( attr.getName () ) );
            }

            r.getCell ().add ( makeTypeNameCell ( attr ) );
            r.getCell ().add ( makeCell ( String.format ( "%s", attr.getType () ) ) );

            r.getCell ().add ( makeCell ( attr.getShortDescription () ) );
        }
    }

    private Section makeProtocolSubSection ( final Section s, final AttributeStructure as )
    {
        final Section s1 = makeSubSection ( s, as.getName () );

        if ( as.getShortDescription () != null )
        {
            s1.getP ().add ( makeText ( as.getShortDescription () ) );
        }

        renderLongDescription ( s1, as.getDescription () );
        return s1;
    }

    private void renderLongDescription ( final Section s, final String description )
    {
        if ( description == null )
        {
            return;
        }

        StringBuilder sb = new StringBuilder ();
        for ( final String p : description.split ( "[\\r\\n]" ) )
        {
            if ( p.isEmpty () )
            {
                if ( sb.length () > 0 )
                {
                    s.getP ().add ( makeText ( sb.toString () ) );
                    sb = new StringBuilder ();
                }
            }
            else
            {
                sb.append ( p );
                sb.append ( ' ' );
            }
        }

        // final last words
        if ( sb.length () > 0 )
        {
            s.getP ().add ( makeText ( sb.toString () ) );
        }
    }

    private Cell makeTypeNameCell ( final Attribute attr )
    {
        if ( attr instanceof StructureAttribute )
        {
            return makeXRefCell ( ( (StructureAttribute)attr ).getStructure ().getName (), "section", makeRefId ( ( (StructureAttribute)attr ).getStructure () ) );
        }
        else if ( attr instanceof EnumAttribute )
        {
            return makeXRefCell ( ( (EnumAttribute)attr ).getEnumType ().getName (), "section", makeRefId ( ( (EnumAttribute)attr ).getEnumType () ) );
        }
        else
        {
            final String name = attr.getClass ().getSimpleName ();
            if ( name.endsWith ( "AttributeImpl" ) ) //$NON-NLS-1$
            {
                return makeCell ( name.substring ( 0, name.length () - "AttributeImpl".length () ) ); //$NON-NLS-1$
            }
            return makeCell ( name );
        }
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
            s1.setId ( makeRefId ( e ) );
            s1.getP ().add ( makeText ( e.getShortDescription () ) );
            renderLongDescription ( s1, e.getDescription () );

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
