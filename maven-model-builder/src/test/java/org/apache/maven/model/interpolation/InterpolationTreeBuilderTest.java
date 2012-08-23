package org.apache.maven.model.interpolation;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterpolationTreeBuilderTest
    extends TestCase
{

    public void testBuildTree() throws Exception {
        TestC test = new TestC();
        InterpolationTreeBuilder treeBuilder = new InterpolationTreeBuilder();

        InterpolationTreeBuilder.Elements elements = treeBuilder.buildTree( test );
        System.out.println(elements);
    }

    public void testStringTree() throws Exception {
        ListC test = new ListC();
        InterpolationTreeBuilder treeBuilder = new InterpolationTreeBuilder();

        InterpolationTreeBuilder.Elements elements = treeBuilder.buildTree( test );
        System.out.println(elements);
    }


    public void testFinalListField()
        throws IllegalAccessException
    {
        List<String[]> values = new ArrayList<String[]>();
        values.add( new String[] { "${key}", "${key2}" } );
        values.add( new String[] { "${key3}", "${key4}" } );
        ObjectWithListField sut = new ObjectWithListField( values );
        ObjectWithListField interpolate = interpolate( sut );
        assertNotNull( interpolate);

    }

    public void testSimpleInterpolatio()
        throws IllegalAccessException
    {
        RegularNestedObject testA = interpolate( new RegularNestedObject() );
        assertEquals(  "INTP${aFoo}", testA.anObj.aFoo );


    }

    public void testArrayInterpolation()
        throws IllegalAccessException
    {
        ArrayOfObjects sut = interpolate( new ArrayOfObjects());
        AnObj first = (AnObj) sut.array[0];
        assertEquals( "INTP${aFoo}", first.aFoo );
        assertEquals( "INTP${fubz}", sut.array[1] );
    }

    public void testNestedArrays()
        throws IllegalAccessException
    {
        NestedArrays sut = interpolate( new NestedArrays() );
        Object[] objects = (Object[]) sut.array[1];
        assertEquals( "INTP${aFoo}", ((AnObj) objects[0]).aFoo );
        assertEquals( "appa", objects[1] );
    }

    public void testMap()
        throws IllegalAccessException
    {
        Map1 sut = interpolate( new Map1() );
        AnObj key1 = (AnObj) sut.map.get( "key1" );
        assertEquals("INTP${aFoo}", key1.aFoo );
    }

    private <T> T interpolate( T sut )
        throws IllegalAccessException
    {
        InterpolationTreeBuilder treeBuilder = new InterpolationTreeBuilder();
        InterpolationTreeBuilder.Elements elements = treeBuilder.buildTree( sut );
        elements.interpolate( sut, simpleINterpolator() );
        return sut;
    }



    private InterpolationTreeBuilder.Interpolator simpleINterpolator()
    {
        return new InterpolationTreeBuilder.Interpolator()
        {
            public String interpolate( String original )
            {
                return "INTP" + original;
            }
        };
    }

    @SuppressWarnings( "UnusedDeclaration" )
    class InnerTest {
        String foo = "${foo}";
    }
    class AnObj {
        String aFoo = "${aFoo}";
    }
    @SuppressWarnings( "UnusedDeclaration" )
    class TestC {
        InnerTest innerTest = new InnerTest();
        String[] messages = new String[]{"not", "${fud}", "yet"};
        Object[] objs = new Object[]{"avc", new AnObj()};
        Map<String, String> strings = new HashMap<String, String>(){
            {
                put("abc", "cde");
                put( "cba", "${inAMap}" );
            }
        };
    }

    class RegularNestedObject
    {
        AnObj anObj = new AnObj();
    }

    class ArrayOfObjects {
        Object[] array = new Object[]{new AnObj(), "${fubz}"};
    }

    @SuppressWarnings( "UnusedDeclaration" )
    class NestedArrays {
        Object[] array = new Object[]{new AnObj(), new Object[]{ new AnObj(), "appa"} };
        int foo = 42;
    }


    class Map1 {
        Map<String, Object> map = new HashMap<String, Object>(  ){{
            put( "key1", new AnObj() );
        }};
    }

    @SuppressWarnings( "UnusedDeclaration" )
    class ListC {
        List<String> strings = new ArrayList<String>(){{add( "abc" );add( "cd${ef}" );add( "notatAll" );add( "az${fo}" );}};
    }


    private static final class ObjectWithListField
    {
        private final List<?> values;

        public ObjectWithListField( List<?> values )
        {
            this.values = values;
        }
    }


}
